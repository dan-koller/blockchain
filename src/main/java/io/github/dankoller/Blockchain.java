package io.github.dankoller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings({"InstantiationOfUtilityClass", "unused"})
final class Blockchain {
    private static final Blockchain blockchain = new Blockchain();
    private static int zeros;
    private static String prevBlockHash;
    private static Set<Block> blocks;
    private static List<String> messages;

    private Blockchain() {
        zeros = 0;
        prevBlockHash = "0";
        blocks = new TreeSet<>();
        messages = new ArrayList<>();
    }

    /**
     * Get the number of zeros.
     *
     * @return Number of zeros
     */
    public static int getZeros() {
        return zeros;
    }

    /**
     * Set the number of zeros.
     *
     * @param zeros Number of zeros
     */
    public static void setZeros(int zeros) {
        Blockchain.zeros = zeros;
    }

    /**
     * Get the previous block hash.
     *
     * @return Previous block hash
     */
    public static String getPrevBlockHash() {
        return prevBlockHash;
    }

    /**
     * Get all blocks of the blockchain.
     *
     * @return All blocks of the blockchain
     */
    public static Set<Block> getBlocks() {
        return blocks;
    }

    /**
     * Validate a block candidate and add it to the blockchain.
     *
     * @param block Block candidate
     */
    public synchronized static void insertNewBlock(Block block) {
        if (isValidBlockCandidate(block)) {
            block.setData(new ArrayList<>(messages));
            prevBlockHash = block.getCurrentBlockHash();
            block.setId();
            blocks.add(block);
            if (block.getSecondsToGenerate() < 10 && Blockchain.getZeros() < 3) {
                Blockchain.setZeros(Blockchain.getZeros() + 1);
            }
            messages.clear();
        }
    }

    /**
     * Validate a block candidate by checking the previous block hash and the current block hash.
     *
     * @param block Block candidate
     * @return True if the block candidate is valid, false otherwise
     */
    public static boolean isValidBlockCandidate(Block block) {
        String regex = String.format("^[0]{%d}([1-9]|[A-z])([A-z]|[0-9])*$", zeros);
        return block.getPreviousBlockHash().equals(prevBlockHash) && block.getCurrentBlockHash().matches(regex);
    }

    /**
     * Add a message to the message list.
     *
     * @param message Message
     */
    public synchronized static void addMessage(String message) {
        messages.add(message);
    }
}
