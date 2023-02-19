package io.github.dankoller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings({"InstantiationOfUtilityClass", "unused"})
final class Blockchain {
    private static final Blockchain blockchain = new Blockchain();
    private static Set<Block> blocks;
    private static String lastBlockValue;
    private static int zeros;
    private static List<String> messages;

    private Blockchain() {
        zeros = 0;
        lastBlockValue = "0";
        blocks = new TreeSet<>();
        messages = new ArrayList<>();
    }

    public static String getLastBlockValue() {
        return lastBlockValue;
    }

    public static Set<Block> getBlocks() {
        return blocks;
    }

    public static int getZeros() {
        return zeros;
    }

    public static void setZeros(int zeros) {
        Blockchain.zeros = zeros;
    }

    public synchronized static void insertNewBlock(Block block) {
        if (isValidBlockCandidate(block)) {
            block.setMessages(new ArrayList<>(messages));
            lastBlockValue = block.getCurrentBlock();
            block.setId();
            blocks.add(block);
            if (block.getSecondsToGenerate() < 10 && Blockchain.getZeros() < 3) {
                Blockchain.setZeros(Blockchain.getZeros() + 1);
            }
            messages.clear();
        }
    }

    public synchronized static void sendMessage(String message) {
        messages.add(message);
    }

    public static boolean isValidBlockCandidate(Block block) {
        String regex = String.format("^[0]{%d}([1-9]|[A-z])([A-z]|[0-9])*$", zeros);
        return block.getPreviousBlock().equals(lastBlockValue) && block.getCurrentBlock().matches(regex);
    }
}