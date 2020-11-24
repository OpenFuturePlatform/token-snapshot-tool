package io.openfuture.snapshot.snapshoter

import io.openfuture.snapshot.dto.ArchivedNodeSnapshotDto
import io.openfuture.snapshot.dto.SnapshotDto
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.core.methods.response.EthCall
import org.web3j.protocol.core.methods.response.EthLog
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigDecimal
import java.math.BigInteger

class ArchivedNodeBasedSnapshoter(nodeAddress: String) : Snapshoter(nodeAddress) {

    override fun snapshot(from: Int, to: Int, contractAddress: String): List<SnapshotDto> {
        val ethTransferLogs: MutableList<EthLog.LogResult<Any>> = getLogs(contractAddress, from, to)

        return fetchAddressesFromLogs(ethTransferLogs)
                .map {
                    val balance = getTokenBalanceAtBlock(it, contractAddress, to)
                    ArchivedNodeSnapshotDto(it, BigDecimal(balance))
                }
    }

    private fun fetchAddressesFromLogs(transferLogs: List<EthLog.LogResult<Any>>): Set<String> {
        return transferLogs.map {
            val topics: List<String> = (it.get() as EthLog.LogObject).topics

            decodeAddress(topics[2]).value
        }.toSet()
    }

    private fun getTokenBalanceAtBlock(address: String, tokenAddress: String, blockNumber: Int): BigInteger {
        val function = Function(BALANCE_METHOD, listOf(Address(address)), listOf(object : TypeReference<Uint256>() {}))
        val encodedFunction = FunctionEncoder.encode(function)

        val result: EthCall = web3j.ethCall(
                Transaction.createFunctionCallTransaction(null, null, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT, tokenAddress, encodedFunction),
                DefaultBlockParameter.valueOf(blockNumber.toBigInteger()))
                .send()

        return FunctionReturnDecoder.decode(result.value, function.outputParameters).first().value as BigInteger
    }

}
