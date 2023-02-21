# Blockchain

This is a simple blockchain implementation in Java. It was primarily created to learn about the blockchain technology
and to get a better understanding of how it works.

## Get started

1. Clone the repository

```shell
git clone https://github.com/dan-koller/blockchain.git
```

2. Change into the project directory

```shell
cd blockchain
```

3. Build the project

```shell
gradle build
```

4. Run the application

```shell
gradle run
```

_Note: You can also run the application from your IDE._

## Usage

The app is currently configured to create 15 miners with one thread each. The output will look like this:

```shell
Created by miner9
miner9 gets 100 VC
Id: 2
Timestamp: 1676989076042
Magic number: -658365016
Hash of the previous block:
c1462498f615d04d3c32bb0307c17af6523d1661c5bd3f8983f131815cb8c2f3
Hash of the block:
076fd8d7256dca73942e7d739aa6b24bbf47237b22023b0799e856cd467dc125
Block data:
Miner 2 sends 20 VC to NICK
Block was generating for 1 seconds
N was increased to 2
```

Note that the mining process is not deterministic. The magic number and the hash of the block will be different each
time. Furthermore, the application is designed to be a proof of concept and is not intended to be used in production.

## Testing

The application is tested using JUnit 5. The code coverage is 100% for classes, 100% for methods and 96% for lines.

Run the tests with the following command:

```shell
gradle test
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.