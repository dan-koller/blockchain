import io.github.dankoller.Main;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BlockchainUnitTest {

    /**
     * TODO: Implement basic unit test for the blockchain
     * 1. Run the main method
     * 2. Get the output of the standard output stream
     * 3. Check if the output matches the expected output (template is provided)
     * 4. Repeat for all 15 miner outputs
     */

    private static final String EXPECTED_OUTPUT = """
            Block:
            Created by minerX
            miner X gets 100 VCId:
            Timestamp:
            Magic number:
            Hash of the previous block:
            Hash of the block:
            Block data:
            Block was generating for X seconds
            N""";

    @Test
    public void testBlockchain() {
        // Get the output of the standard output stream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // Run the main method
        try {
            Main.main(null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Get the output of the standard output stream
        String output = outContent.toString();
        // Check if the output is not empty
        assertNotEquals("", output);
        // Check if the output matches the expected output
        // TODO: All Xs should be replaced by the miner id
    }
}
