# ERC-20 TOKEN SNAPSHOT

ERC-20 TOKEN SNAPSHOT exports balance of token holders of erc-20 smart contract at a specific block to csv file.

There are two strategies to obtain balance:
   1. Getting historical balance from archived node
   2. Collecting and calculating from transfer events
   
Can't afford or have no access to archived node? Use any Ethereum node as a service like Infura
and run the application with `--exportStrategy=transfer-event` argument. 

If you have paid plan to Infura or free node from https://archivenode.io/ or any other, then you can pass with an argument `--exportStrategy=archived`  
 
## Running the application
Run from command line `./gradlew bootRun -Pargs= --fromBlock=0, --toBlock=111, ...` passing the proper arguments.

Required arguments:
* contractAddress
* fromBlock
* toBlock
* nodeAddress

Optional arguments:
* exportStrategy (Default is transfer-event)
* fileName (Default is snapshot_at_FromBlockNumber_toBlockNumber.csv)
* decimals (Default is 8)
