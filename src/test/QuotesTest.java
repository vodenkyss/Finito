package test;

import data.Quotes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Quotes class.
 */
public class QuotesTest {

    private Quotes quotes;
    private final String testFilename = "test_quotes.txt";

    /**
     * Setting up the enviroment before each test.
     */
    @BeforeEach
    public void setUp() {
        quotes = new Quotes();
    }

    /**
     * Deletes the text file if it exists after each test.
     */
    @AfterEach
    public void tearDown() {
        File file = new File(testFilename);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Tests loading quotes from file.
     * @throws IOException
     */
    @Test
    public void testLoadQuotesFromFile() throws IOException {
        try (FileWriter writer = new FileWriter(testFilename)) {
            writer.write("Quote 1\n");
            writer.write("Quote 2\n");
            writer.write("Quote 3\n");
        }

        quotes.loadQuotesFromFile(testFilename);
        ArrayList<String> loadedQuotes = quotes.getQuotes();

        assertNotNull(loadedQuotes, "Loaded quotes should not be null");
        assertEquals(3, loadedQuotes.size(), "Should load three quotes");
        assertEquals("Quote 1", loadedQuotes.get(0), "First quote should match");
        assertEquals("Quote 2", loadedQuotes.get(1), "Second quote should match");
        assertEquals("Quote 3", loadedQuotes.get(2), "Third quote should match");
    }

    /**
     * Tests loading quotes from empty file.
     * @throws IOException
     */
    @Test
    public void testLoadQuotesFromEmptyFile() throws IOException {
        new File(testFilename).createNewFile();

        quotes.loadQuotesFromFile(testFilename);
        ArrayList<String> loadedQuotes = quotes.getQuotes();

        assertNotNull(loadedQuotes, "Loaded quotes should not be null");
        assertTrue(loadedQuotes.isEmpty(), "Loaded quotes should be empty");
    }

    /**
     * Tests loading quotes from non existent file
     */
    @Test
    public void testLoadQuotesFromNonExistentFile() {
        quotes.loadQuotesFromFile("non_existent_file.txt");
        ArrayList<String> loadedQuotes = quotes.getQuotes();

        assertNotNull(loadedQuotes, "Loaded quotes should not be null");
        assertTrue(loadedQuotes.isEmpty(), "Loaded quotes should be empty when file does not exist");
    }


}
