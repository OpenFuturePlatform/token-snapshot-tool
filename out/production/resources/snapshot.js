'use strict';


const CLI       = require('clui')
const Spinner   = CLI.Spinner;
const Web3      = require('web3');
const web3      = new Web3(new Web3.providers.HttpProvider('https://mainnet.infura.io/5VkzsA31kYsWyrqC9GCT'));
const parseArgs = require('minimist')(process.argv.slice(1));
const createCsvWriter = require('csv-writer').createObjectCsvWriter;

// OPEN token info
const tokenDecimals = 8;
const contractCreatedBlock = 5551092;
const tokenContractAddress = '0x69c4BB240cF05D51eeab6985Bab35527d04a8C64';
const tokenABI = [{"constant":true,"inputs":[],"name":"name","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_value","type":"uint256"}],"name":"approve","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"totalSupply","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_from","type":"address"},{"name":"_to","type":"address"},{"name":"_value","type":"uint256"}],"name":"transferFrom","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"decimals","outputs":[{"name":"","type":"uint8"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[],"name":"unpause","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"paused","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_subtractedValue","type":"uint256"}],"name":"decreaseApproval","outputs":[{"name":"success","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_owner","type":"address"}],"name":"balanceOf","outputs":[{"name":"balance","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[],"name":"pause","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"owner","outputs":[{"name":"","type":"address"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[],"name":"symbol","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_to","type":"address"},{"name":"_value","type":"uint256"}],"name":"transfer","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_addedValue","type":"uint256"}],"name":"increaseApproval","outputs":[{"name":"success","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_owner","type":"address"},{"name":"_spender","type":"address"}],"name":"allowance","outputs":[{"name":"remaining","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"newOwner","type":"address"}],"name":"transferOwnership","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"inputs":[{"name":"_name","type":"string"},{"name":"_symbol","type":"string"},{"name":"_tokens","type":"uint256"},{"name":"_decimals","type":"uint8"}],"payable":false,"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[],"name":"Pause","type":"event"},{"anonymous":false,"inputs":[],"name":"Unpause","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"previousOwner","type":"address"},{"indexed":true,"name":"newOwner","type":"address"}],"name":"OwnershipTransferred","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"owner","type":"address"},{"indexed":true,"name":"spender","type":"address"},{"indexed":false,"name":"value","type":"uint256"}],"name":"Approval","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"from","type":"address"},{"indexed":true,"name":"to","type":"address"},{"indexed":false,"name":"value","type":"uint256"}],"name":"Transfer","type":"event"}];


class Snapshot {

    constructor(blockNumber) {
        this.blockNumber = blockNumber;
        this.contract = new web3.eth.Contract(tokenABI, tokenContractAddress);
        this.spinner = new Spinner('Starting...');
        this.balances = [];
    }

    async getBatchData() {
        let batchSize = 1000;

        this.spinner.start();

        for (let i = contractCreatedBlock; i <= this.blockNumber; i += batchSize) {
            let toBlock = i + batchSize;
            if (toBlock > this.blockNumber) toBlock = this.blockNumber;

            console.log('\n\rBatch request from block', i, 'to block.', toBlock);

            let batchBalances = await this.getSnapshotData(i, toBlock);

            this.spinner.message('Writing snapshot data to csv file...');
            await this.csvWriter.writeRecords(this.balances);
        }

        // this.spinner.message('Writing snapshot data to csv file...');
        // let csvWriter = this.createWriter(this.blockNumber);
        //
        // await csvWriter.writeRecords(this.balances);
        this.spinner.stop();
    }

    async getSnapshotData(fromBlock, toBlock) {
        let snaphotBalances = []
        let dataTx = await this.getTransferTx(fromBlock, toBlock);
        let snapshotAddresses = this.getAddressesAtBlockNumber(dataTx);
        console.log('\n\rAddresses received:', snapshotAddresses.size)

        let count = 0;
        for (const item of snapshotAddresses.keys()) {
            let progress = count * 100 / snapshotAddresses.size;
            this.spinner.message('Get balances...' + '(' + progress.toFixed(1) + '%)');

            if (this.isDublicate(this.balances, item)) continue;
            let data = await this.getBalance(item);
            if (Number(data)) balances.push({
                'address': item,
                'balance': data * Math.pow(10, -tokenDecimals)
            });
            count++;
        }

        this.spinner.message('Get balances...' + '(100%)');
        return balances
    }

    async getTransferTx(fromBlock, toBlock) {
        this.spinner.message('Get addresses...');
        try {
            return await this.contract.getPastEvents('Transfer', {fromBlock: fromBlock, toBlock: toBlock});
        } catch (err) {
            console.log('Error while retreiving addresses', err);
            this.spinner.stop();
        }
    }

    async getBalance(address) {
        let disconnected = false;
        try {
            return await this.contract.methods.balanceOf(address).call({}, this.blockNumber);
        } catch (err) {
            disconnected = true;
        } finally {
            if (disconnected) {
                disconnected = false;
                return await this.getBalance(address);
            }
        }
    }

    getAddressesAtBlockNumber(dataFromTransferTx) {
        let addresses = new Set();
        for (let item of dataFromTransferTx) {
            let fromAddress = item.returnValues.from;
            if (this.isValid(fromAddress)) addresses.add(fromAddress);

            let toAddress = item.returnValues.to;
            if (this.isValid(toAddress)) addresses.add(toAddress);
        }

        return addresses;
    }

    isValid(address) {
        return web3.utils.isAddress(address);
    }

    isDublicate(array, address) {
        return array.some(function (obj) {
            return obj.address === address;
        });
    }

    createWriter() {
        this.csvWriter = createCsvWriter({
            path: `./snapshot_at_block_${this.blockNumber}.csv`,
            header: [
                {id: 'address', title: 'ADDRESS'},
                {id: 'balance', title: 'BALANCE'},
            ]
        });
    }

}


let tokenSnapshot = new Snapshot(parseArgs.b);
tokenSnapshot.createWriter()
tokenSnapshot.getBatchData();