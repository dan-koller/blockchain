package io.github.dankoller;

import java.util.ArrayList;
import java.util.List;

public class Block implements Comparable<Block> {
    private static Long prevId = 1L;
    private Long id;
    private final Long timestamp;
    private final Long magicNumber;
    private final Long secondsToGenerate;
    private final String previousBlockHash;
    private final String currentBlockHash;
    private final Miner foundByMiner;
    private final int zeros;
    private List<String> data;

    /**
     * Create a new block. The hash of the previous block is set to the hash of the last block in the blockchain.
     * The amount of zeros for the hash is set to the number of zeros in the blockchain.
     *
     * @param timestamp         Timestamp when the block was created
     * @param magicNumber       Random number for the hash
     * @param secondsToGenerate Amount of time in seconds the miner needed to mine the block
     * @param currentBlockHash  Hash of the current block
     * @param foundByMiner      Miner who found the block
     */
    public Block(long timestamp, long magicNumber, long secondsToGenerate, String currentBlockHash, Miner foundByMiner) {
        this.timestamp = timestamp;
        this.magicNumber = magicNumber;
        this.secondsToGenerate = secondsToGenerate;
        this.previousBlockHash = Blockchain.getPrevBlockHash();
        this.currentBlockHash = currentBlockHash;
        this.foundByMiner = foundByMiner;
        this.zeros = Blockchain.getZeros();
        data = new ArrayList<>();
    }

    /**
     * Get the id of the current block.
     *
     * @return Id of the block
     */
    public long getId() {
        return id;
    }

    /**
     * Set the id of the current block.
     */
    public void setId() {
        this.id = prevId++;
    }

    /**
     * Get the amount of time in seconds the miner needed to mine the block.
     *
     * @return Seconds to generate
     */
    public Long getSecondsToGenerate() {
        return secondsToGenerate;
    }

    /**
     * Get the hash of the previous block.
     *
     * @return Hash of the previous block
     */
    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    /**
     * Get the hash of the current block.
     *
     * @return Hash of the current block
     */
    public String getCurrentBlockHash() {
        return currentBlockHash;
    }

    /**
     * Get the id of the previous block.
     *
     * @return Id of the block
     */
    public static Long getPrevId() {
        return prevId;
    }

    /**
     * Set the data of the block. These are random messages that simulate transactions.
     *
     * @param data Data of the block
     */
    public void setData(List<String> data) {
        this.data = data;
    }

    /**
     * Updated toString method to get the data of the block in a readable format.
     *
     * @return Data of the block
     */
    @Override
    public String toString() {
        return "Block:\n" +
                "Created by miner" + foundByMiner.getMinerId() + "\n" +
                "miner" + foundByMiner.getMinerId() + " gets 100 VC\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block:\n" + previousBlockHash + "\n" +
                "Hash of the block:\n" + currentBlockHash + "\n" +
                "Block data:\n" + getBlockData() +
                "Block was generating for " + secondsToGenerate + " seconds\n" +
                getMiningDuration() + "\n";
    }

    /**
     * Helper method to get the data of the block in a readable format.
     *
     * @return Data of the block
     */
    private String getBlockData() {
        if (data.isEmpty()) {
            return "no messages\n";
        } else {
            StringBuilder sb = new StringBuilder();
            data.forEach(message -> sb.append(message).append("\n"));
            return sb.toString();
        }
    }

    /**
     * Helper method to get the mining duration in a readable format.
     *
     * @return Mining duration
     */
    private String getMiningDuration() {
        if (secondsToGenerate > 60) {
            return "N was decreased by 1";
        } else if (secondsToGenerate < 10) {
            return "N was increased to " + (zeros + 1);
        } else {
            return "N stays the same";
        }
    }

    /**
     * Compare the id of the current block with the id of the given block.
     *
     * @param o The object to be compared
     * @return The value 0 if the argument block is equal to this block;
     * a value less than 0 if this block is less than the block argument;
     * and a value greater than 0 if this block is greater than the block argument
     */
    @Override
    public int compareTo(Block o) {
        return id.compareTo(o.getId());
    }
}
