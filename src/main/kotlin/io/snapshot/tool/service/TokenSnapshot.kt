package io.snapshot.tool.service

import org.web3j.abi.EventEncoder
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Event
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.request.Transaction.createFunctionCallTransaction
import org.web3j.protocol.core.methods.response.EthLog
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Contract.GAS_LIMIT
import org.web3j.tx.ManagedTransaction.GAS_PRICE
import java.io.PrintWriter
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import java.util.concurrent.Executors


/**
 * @author Igor Pahomov
 */

class TokenSnapshot(
        private val address: String?,
        private val fromBlock: Int?,
        private val toBlock: Int?,
        private val decimals: Int = 18,
        private val batchSize: Int = 100,
        private val server: String = "https://mainnet.infura.io/5VkzsA31kYsWyrqC9GCT",
        private val filename: String = "snapshot_at_block_$toBlock.csv"

) {


    private var writer = PrintWriter(filename, "UTF-8")
    private var web3j: Web3j = Web3j.build(HttpService(server))
    private var executor = Executors.newFixedThreadPool(10)
    private val syncAddresses: MutableSet<String> = Collections.synchronizedSet<String>(setOf())


    fun getBatchSnapshotToCsv() {
        val start = System.currentTimeMillis()

        writer.println(HEADER)

        for (b in toBlock!! downTo fromBlock!! step batchSize) {
            var nextBatch = b - batchSize + 1
            if (nextBatch <= fromBlock) nextBatch = fromBlock

            executor.execute {
                println("Batch snapshot from block $b to block $nextBatch")
                getSnapshotData(nextBatch, b)
            }
        }

        executor.shutdown()
        while (!executor.isTerminated) {
        }

        writer.close()
        println("Process time: ${System.currentTimeMillis() - start} millis")
    }

    private fun getSnapshotData(fromBlock: Int, toBlock: Int) {
        val start = System.currentTimeMillis()
        val addresses = getAddressesFromTransferEvents(fromBlock, toBlock)
        println("Fetched addresses ${addresses.size}")

        val balances = getBalancesAtBlock(addresses)

        writeResult(balances)

        println("Added ${balances.size} balances from block $fromBlock to block $toBlock with ${System.currentTimeMillis() - start} millis")
    }

    private fun getAddressesFromTransferEvents(fromBlock: Int, toBlock: Int): Set<Address> {
        var ethLog = EthLog()
        var disconnect = false
        try {
            ethLog = web3j.ethGetLogs(createTransferFilter(fromBlock, toBlock)).send()
        } catch (e: Exception) {
            disconnect = true
        } finally {
            if (disconnect) {
                web3j = Web3j.build(HttpService(server))
                getAddressesFromTransferEvents(fromBlock, toBlock)
            }
        }

        val ethTransferLogs: MutableList<EthLog.LogResult<Any>> = ethLog.logs

        return fetchAddressesFromLogs(ethTransferLogs)
    }

    private fun createTransferFilter(fromBlock: Int, toBlock: Int): EthFilter {
        return EthFilter(
                DefaultBlockParameter.valueOf(fromBlock.toBigInteger()),
                DefaultBlockParameter.valueOf(toBlock.toBigInteger()),
                address
        )
                .addSingleTopic(EventEncoder.encode(
                        Event(
                                TRANSFER_EVENT,
                                listOf(object : TypeReference<Address>() {}, object : TypeReference<Address>() {}) as List<TypeReference<*>>?,
                                listOf(object : TypeReference<Uint256>() {}) as List<TypeReference<*>>?
                        )))
    }

    private fun fetchAddressesFromLogs(transferLogs: List<EthLog.LogResult<Any>>): Set<Address> {
        return transferLogs.map {
            val topics: List<String> = (it.get() as EthLog.LogObject).topics

            // get recipient address
            decodeAddress(topics[2])
        }.toSet()
    }

    private fun getBalancesAtBlock(addresses: Set<Address>): Map<String, BigDecimal> {
        return addresses
                .map {
                    val balance = getTokenBalance(it)
                    it.value to BigDecimal(balance.toDouble() * Math.pow(10.0, -decimals.toDouble()))
                }
                .filter { it.first !in syncAddresses }
                .toMap()
    }

    private fun getTokenBalance(address: Address): BigInteger {
        val function = Function(BALANCE_METHOD, listOf(address), listOf(object : TypeReference<Uint256>() {}))
        val encodedFunction = FunctionEncoder.encode(function)
        val result = web3j.ethCall(
                createFunctionCallTransaction(null, null, GAS_PRICE, GAS_LIMIT, this.address, encodedFunction),
                DefaultBlockParameter.valueOf(toBlock?.toBigInteger()))
                .send()

        return FunctionReturnDecoder.decode(result.value, function.outputParameters).first().value as BigInteger
    }

    private fun decodeAddress(rawData: String) =
            FunctionReturnDecoder.decodeIndexedValue(rawData, object : TypeReference<Address>() {}) as Address

    private fun writeResult(results: Map<String, BigDecimal>) {
        results.forEach { writer.println("${it.key},${it.value}") }
        writer.flush()
    }

    companion object {
        const val HEADER = "ADDRESS,BALANCE"
        const val TRANSFER_EVENT = "Transfer"
        const val BALANCE_METHOD = "balanceOf"
    }

}