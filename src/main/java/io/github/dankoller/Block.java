package io.github.dankoller;

import java.util.ArrayList;
import java.util.List;

public class Block implements Comparable<Block> {
    private static Long lastId = 1L;
    private Long id;
    private final Long timestamp;
    private final String previousBlock;
    private final String currentBlock;
    private final Long magicNumber;
    private final Long secondsToGenerate;
    private final Miner foundByMiner;
    private final int zeros;
    private List<String> messages;

    public Block(long timestamp, long magicNumber, long secondsToGenerate, String currentBlock, Miner foundByMiner) {
        this.timestamp = timestamp;
        this.previousBlock = Blockchain.getLastBlockValue();
        this.magicNumber = magicNumber;
        this.secondsToGenerate = secondsToGenerate;
        this.currentBlock = currentBlock;
        this.foundByMiner = foundByMiner;
        this.zeros = Blockchain.getZeros();
        messages = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId() {
        this.id = lastId++;
    }

    public String getPreviousBlock() {
        return previousBlock;
    }

    public String getCurrentBlock() {
        return currentBlock;
    }

    public static Long getLastId() {
        return lastId;
    }

    public Long getSecondsToGenerate() {
        return secondsToGenerate;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Block:\n" +
                "Created by miner" + foundByMiner.getMinerId() + "\n" +
                "miner" + foundByMiner.getMinerId() + " gets 100 VC\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block:\n" + previousBlock + "\n" +
                "Hash of the block:\n" + currentBlock + "\n" +
                "Block data:\n" + getMessages() +
                "Block was generating for " + secondsToGenerate + " seconds\n" +
                getMiningDuration() + "\n";
    }

    private String getMessages() {
        if (messages.isEmpty()) {
            return "no messages\n";
        } else {
            StringBuilder sb = new StringBuilder();
            messages.forEach(message -> sb.append(message).append("\n"));
            return sb.toString();
        }
    }

    private String getMiningDuration() {
        if (secondsToGenerate > 60) {
            return "N was decreased by 1";
        } else if (secondsToGenerate < 10) {
            return "N was increased to " + (zeros + 1);
        } else {
            return "N stays the same";
        }
    }

    @Override
    public int compareTo(Block o) {
        return id.compareTo(o.getId());
    }
}