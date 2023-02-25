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

    /**
     * Start mining a new block when the thread is started.
     */
    @Override
    public void run() {
        isMining = true;
        mine();
    }

    /**
     * Stop mining a new block. The miner will mine a new block until the last block id is 15.
     */
    private void mine() {
        while (isMining && Block.getPrevId() <= 15) {
            scheduleMining();
        }
    }

    /**
     * Get the id of the miner.
     *
     * @return Id of the miner
     */
    public Long getMinerId() {
        return minerId;
    }

    /**
     * Schedule the mining of a new block. The mining will be simulated by a timer. The timer will
     * count the seconds the miner needs to mine a new block. During this time, the miner will
     * generate a random magic number and simulate transactions. When the timer is finished, the
     * miner will add the new block to the blockchain.
     */
    private void scheduleMining() {
        Timer timer = new Timer();
        duration = 0L;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                duration++;
            }
        }, 0, 1000);
        generateMagicNumber();
        simulateTransactions();
        timer.cancel();
    }

    /**
     * Generate a random magic number. The magic number will be part of the block's data. The magic
     * number will be generated until it matches the regular expression.
     */
    private void generateMagicNumber() {
        String regex = String.format("^[0]{%d}([1-9]|[A-z])([A-z]|[0-9])*$", Blockchain.getZeros());
        Random random = new Random();
        int zerosPrev = Blockchain.getZeros();
        long idPrev = Block.getPrevId();
        long randInt;
        String temp;
        long timestamp;
        Long lastId;
        String lastBlock;
        do {
            timestamp = new Date().getTime();
            lastId = Block.getPrevId();
            lastBlock = Blockchain.getPrevBlockHash();
            randInt = random.nextInt();
            temp = StringUtil.applySha256(lastId.toString() + minerId + timestamp + lastBlock + randInt);
        } while (!temp.matches(regex) && Block.getPrevId() <= 15 &&
                Block.getPrevId() == idPrev && Blockchain.getZeros() == zerosPrev);
        Blockchain.insertNewBlock(new Block(timestamp, randInt, duration, temp, this));
    }

    /**
     * Simulate transactions on the blockchain. The transactions will be part of the block's data.
     */
    private void simulateTransactions() {
        if (new Random().nextInt() % 2 == 0) {
            Blockchain.addMessage("Miner " + minerId + " sends 30 VC");
            Blockchain.addMessage("Miner " + minerId + " sends 20 VC");
            Blockchain.addMessage("Miner " + minerId + " spends 5 VC");
        } else {
            Blockchain.addMessage("Miner " + minerId + " sends 20 VC to NICK");
        }
    }
}
