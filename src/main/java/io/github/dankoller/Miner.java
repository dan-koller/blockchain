package io.github.dankoller;

import io.github.dankoller.util.StringUtil;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Miner extends Thread {
    private Long duration;
    private final Long minerId;
    private boolean isMining;

    public Miner(Long minerId) {
        this.minerId = minerId;
    }

    @Override
    public void run() {
        isMining = true;
        mine();
    }

    private void mine() {
        while (isMining && Block.getLastId() <= 15) {
            findMagicNumber();
        }
    }

    public Long getMinerId() {
        return minerId;
    }

    private void findMagicNumber() {
        Timer timer = new Timer();
        duration = 0L;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                duration++;
            }
        }, 0, 1000);
        generateMagicNumber();
        // Some random transactions to simulate real blockchain
        if (new Random().nextInt() % 2 == 0) {
            Blockchain.sendMessage("Miner " + minerId + " sends 30 VC");
            Blockchain.sendMessage("Miner " + minerId + " sends 20 VC");
            Blockchain.sendMessage("Miner " + minerId + " spends 5 VC");
        } else {
            Blockchain.sendMessage("Miner " + minerId + " sends 20 VC to NICK");
        }
        timer.cancel();
    }

    private void generateMagicNumber() {
        String regex = String.format("^[0]{%d}([1-9]|[A-z])([A-z]|[0-9])*$", Blockchain.getZeros());
        Random random = new Random();
        int zerosPrev = Blockchain.getZeros();
        long idPrev = Block.getLastId();
        long randInt;
        String temp;
        long timestamp;
        Long lastId;
        String lastBlock;
        do {
            timestamp = new Date().getTime();
            lastId = Block.getLastId();
            lastBlock = Blockchain.getLastBlockValue();
            randInt = random.nextInt();
            temp = StringUtil.applySha256(lastId.toString() + minerId + timestamp + lastBlock + randInt);
        } while (!temp.matches(regex) && Block.getLastId() <= 15 &&
                Block.getLastId() == idPrev && Blockchain.getZeros() == zerosPrev);
        Blockchain.insertNewBlock(new Block(timestamp, randInt, duration, temp, this));
    }
}