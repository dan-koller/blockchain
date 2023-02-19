package io.github.dankoller;

public class Main {

    private static final int NUMBER_OF_MINERS = 10;

    public static void main(String[] args) throws InterruptedException {
        Miner[] miners = new Miner[NUMBER_OF_MINERS];
        for (int i = 1; i <= NUMBER_OF_MINERS; i++) {
            miners[i - 1] = new Miner((long) i);
        }
        for (int i = 1; i <= NUMBER_OF_MINERS; i++) {
            miners[i - 1].start();
        }
        for (int i = 1; i <= NUMBER_OF_MINERS; i++) {
            miners[i - 1].join();
        }
        for (Block b : Blockchain.getBlocks()) {
            b.printInfo();
        }
    }
}