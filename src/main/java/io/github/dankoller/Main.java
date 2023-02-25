package io.github.dankoller;

public class Main {
    private static final int NUMBER_OF_MINERS = 10;

    /**
     * Create new miners and start them. Then print the blocks. The blockchain class is final and
     * gets initialized when the blockchain is accessed for the first time (private constructor).
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Miner[] miners = new Miner[NUMBER_OF_MINERS];
        try {
            startMiners(miners);
        } catch (InterruptedException e) {
            System.err.println("Error while starting miners: " + e.getMessage());
        }
        printBlocks();
    }

    /**
     * Start all miners and wait for them to finish. When a miner gets started, it will start mining a
     * new block. When the miner finishes mining, it will add the block to the blockchain. The miners
     * run in parallel and the main thread waits for all miners to finish.
     *
     * @param miners Array of miners
     * @throws InterruptedException If the thread is interrupted
     */
    private static void startMiners(Miner[] miners) throws InterruptedException {
        for (int i = 1; i <= miners.length; i++) {
            miners[i - 1] = new Miner((long) i);
            miners[i - 1].start();
        }
        for (int i = 1; i <= miners.length; i++) {
            miners[i - 1].join();
        }
    }

    /**
     * Print all blocks of the blockchain.
     */
    private static void printBlocks() {
        Blockchain.getBlocks().forEach(System.out::println);
    }
}
