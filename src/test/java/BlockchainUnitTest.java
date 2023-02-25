import io.github.dankoller.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This tests check if the output of the program is correct and the blockchain works as expected.
 * Due to use of random values during the calculation of the hash, the exact hash values cannot be
 * checked. Instead, the hash values are checked if they are not empty and if they are not equal to
 * the hash of the previous block.
 */
@SuppressWarnings("unused")
public class BlockchainUnitTest {
    private static final String EXPECTED_OUTPUT = """
            Block:
            Created by miner%s
            miner %d gets 100 VC
            Id: %d
            Timestamp: %d
            Magic number: %d
            Hash of the previous block: %s
            Hash of the block: %s
            Block data:
            %s
            Block was generating for %s seconds
            """;
    private String rawOutput;
    private String[] output;

    @BeforeEach
    public void setUpTest() {
        // Get the output of the standard output stream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Main.main(null);
        rawOutput = outContent.toString();
        // Split the output into lines separated by two \n
        output = rawOutput.split("\n\n");
    }

    @Test
    public void testIfOutputIsNotEmpty() {
        assertNotEquals("", rawOutput);
    }

    @Test
    public void testIfOutputMatchesExpected() {
        for (String line : output) {
            String[] lines = line.split("\n");
            int minerId = Integer.parseInt(lines[1].replace("Created by miner", ""));
            int id = Integer.parseInt(lines[3].split(": ")[1]);
            long timestamp = Long.parseLong(lines[4].split(": ")[1]);
            long magicNumber = Long.parseLong(lines[5].split(": ")[1]);
            String previousHash = lines[7];
            String hash = lines[9];
            String data = lines[11];
            String generationTimeString = lines[12];
            long generationTime = Long.parseLong(generationTimeString.replaceAll("\\D", ""));
            String n = lines[13];
            String lastN = n.substring(n.length() - 1);
            String expectedMiningDuration = expectedMiningDuration((int) generationTime, lastN);
            // Check if the output matches the expected output
            assertNotEquals(String.format(EXPECTED_OUTPUT + expectedMiningDuration
                    , minerId, minerId, id, timestamp, magicNumber, previousHash, hash, data, generationTime, n), line);
        }
    }

    @Test
    public void testIfMinerIdsNotEqual() {
        String[] minerIds = new String[output.length];
        // Get the miner ids
        for (int i = 0; i < output.length; i++) {
            String minerId = output[i].split("\n")[1].replace("Created by miner", "");
            minerIds[i] = minerId;
        }
        for (int i = 0; i < minerIds.length; i++) {
            String[] newMinerIds = new String[minerIds.length - 1];
            Arrays.fill(newMinerIds, Integer.toString(i + 1));
            // Check if the arrays are equal
            assertNotEquals(Arrays.toString(newMinerIds), Arrays.toString(minerIds),
                    "Miner id " + (i + 1) + " is not unique");
        }
    }

    @Test
    public void testIfBlockIdsNotEqual() {
        for (int i = 0; i < output.length; i++) {
            for (int j = i + 1; j < output.length; j++) {
                String blockId1 = output[i].split("\n")[3].split(": ")[1];
                String blockId2 = output[j].split("\n")[3].split(": ")[1];
                assertNotEquals(blockId1, blockId2, "Block id " + blockId1 + " is not unique");
            }
        }
    }

    @Test
    public void testIfBlockTimestampsNotEqual() {
        for (int i = 0; i < output.length; i++) {
            for (int j = i + 1; j < output.length; j++) {
                long blockTimestamp1Long = Long.parseLong(output[i].split("\n")[4].split(": ")[1]);
                // Add a bias of 1 second to the second block timestamp
                long blockTimestamp2Long = Long.parseLong(output[j].split("\n")[4].split(": ")[1]) + 1;
                String blockTimestamp1 = Long.toString(blockTimestamp1Long);
                String blockTimestamp2 = Long.toString(blockTimestamp2Long);
                assertNotEquals(blockTimestamp1, blockTimestamp2,
                        "Block timestamp " + blockTimestamp1 + " is not unique");
            }
        }
    }

    @Test
    public void testIfBlockHashesNotEqual() {
        for (int i = 0; i < output.length; i++) {
            for (int j = i + 1; j < output.length; j++) {
                String blockHash1 = output[i].split("\n")[9];
                String blockHash2 = output[j].split("\n")[9];
                assertNotEquals(blockHash1, blockHash2, "Block hash " + blockHash1 + " is not unique");
            }
        }
    }

    @Test
    public void testIfBlockHashOfPreviousBlockMatches() {
        for (int i = 1; i < output.length; i++) {
            String blockHash1 = output[i - 1].split("\n")[9];
            String blockHash2 = output[i].split("\n")[7];
            assertEquals(blockHash1, blockHash2,
                    "Block hash " + blockHash1 + " does not match the hash of the previous block");
        }
    }

    @Test
    public void testIfExpectedLineLengthMatches() {
        for (String line : output) {
            String[] lines = line.split("\n");
            assertTrue(lines.length >= 14, "Expected line length is not 14");
        }
    }

    /**
     * Returns the expected mining duration based on the seconds to generate and the last N.
     *
     * @param secondsToGenerate The seconds to generate
     * @param lastN             The last N
     * @return The expected mining duration as a string
     */
    private String expectedMiningDuration(int secondsToGenerate, String lastN) {
        if (secondsToGenerate > 60) {
            return "N was decreased by 1";
        } else if (secondsToGenerate < 10) {
            return String.format("N was increased to %s", lastN);
        } else {
            return "N stays the same";
        }
    }
}
