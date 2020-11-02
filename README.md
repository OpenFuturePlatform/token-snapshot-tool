# ERC-20 TOKEN SNAPSHOT

ERC-20 TOKEN SNAPSHOT exports token balance of erc-20 smart contract at specific block to csv file.

There are two strategies to obtain balance:
   1. From archived node
   2. From transfer events

## Running the application
Run from command line `./gradlew bootRun -Pargs= --fromBlock=0, --toBlock=111, ...` command.

Required arguments:
* contractAddress
* fromBlock
* toBlock
* nodeAddress

Optional arguments:
* exportStrategy (Default is archived)
* fileName (snapshot_at_FromBlockNumber_toBlockNumber.csv)
