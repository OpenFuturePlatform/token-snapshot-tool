# ERC-20 TOKEN SNAPSHOT

ERC-20 TOKEN SNAPSHOT exports balance of token holders of erc-20 smart contract at a specific block to csv file.

There are two strategies to obtain balance:
   1. Getting historical balance from archived node
   2. Collecting and calculating from transfer events
   
Can't afford or have no access to archived node? Use any Ethereum node as a service like Infura
and run the application with `--strategy=transfer_event` argument. 

If you have paid plan to Infura or free node from https://archivenode.io/ or any other, 
then you can pass with an argument `--strategy=archived`  
 
## Running the application
Run from command line `./gradlew clean build` to build jar file.

Then `java -jar build/libs/snanshot-0.0.1-SNAPSHOT.jar --pass arguments below` to start

Options:
* `--contract-address`  Address of token Smart contract [_required_]
* `--from`              Start block number [_required_]
* `--to`                End block number [_required_]
* `--node-address`      Server url of node connected to [_required_]
* `--decimals`          Snapshot decimals value [_optional_, default is **8**]
* `--filename`          Name of csv file to save [_optional_,default is **snapshot_at_block_toBlocknumber**]
* `--strategy`          Snapshot export strategy (archived, transfer_event) [_optional_, default is **transfer_event**]
* `-h, --help`          Show available options and exit
