# ERC-20 TOKEN SNAPSHOT

ERC-20 TOKEN SNAPSHOT exports balance of token holders of erc-20 smart contract at a specific block to csv file.

You will need an archived node address, since the application requests for the historical balance of the contract

## Running the application
Run from command line `./gradlew clean build` to build jar file.

Then `java -jar build/libs/snanshot-0.0.1-SNAPSHOT.jar --pass arguments below` to start

Input arguments:
* `--contract-address`  Address of token Smart contract [_required_]
* `--from`              Start block number [_required_]
* `--to`                End block number [_required_]
* `--node-address`      Server url of node connected to [_required_]
* `--decimals`          Snapshot decimals value [_optional_, default is **8**]
* `--filename`          Name of csv file to save [_optional_,default is **snapshot_at_block_toBlocknumber**]
* `-h, --help`          Show available options and exit

Output:
* _clear_Your_File_Name.csv named csv file with headers **ADDRESS, BALANCE**
