package data;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reading and working with text file
 */
public class Quotes {

    private ArrayList<String> quotes;

    /**
     * Loads quotes from a file inside resources (e.g. "quotes.txt")
     * @param filename resource path (just the name if it's in root of resources)
     */
    public void loadQuotesFromFile(String filename) {
        quotes = new ArrayList<>();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (in == null) {
                throw new IOException("File not found: " + filename);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                quotes.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading quotes.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<String> getQuotes() {
        return quotes;
    }
}
