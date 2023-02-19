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

    public void printInfo() {
        System.out.printf("Block:%n" +
                        "Created by miner%d%n" +
                        "miner%s gets 100 VC%n" +
                        "Id: %d%n" +
                        "Timestamp: %d%n" +
                        "Magic number: %d%n" +
                        "Hash of the previous block:%n" +
                        "%s%n" +
                        "Hash of the block:%n" +
                        "%s%n",
                foundByMiner.getMinerId(),
                foundByMiner.getMinerId(),
                id,
                timestamp,
                magicNumber,
                previousBlock,
                currentBlock);
        if (messages.isEmpty()) {
            System.out.println("Block data: no messages");
        } else {
            System.out.println("Block data:");
            messages.forEach(System.out::println);
        }
        System.out.printf("Block was generating for %d seconds%n", secondsToGenerate);
        validateMiningTime();
        System.out.println();
    }

    private void validateMiningTime() {
        if (secondsToGenerate > 60) {
            System.out.println("N was decreased by 1");
        } else if (secondsToGenerate < 10) {
            System.out.printf("N was increased to %d%n", zeros + 1);
        } else {
            System.out.println("N stays the same");
        }
    }

    @Override
    public int compareTo(Block o) {
        return id.compareTo(o.getId());
    }
}