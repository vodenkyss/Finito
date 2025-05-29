package data;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Quotes {

    private ArrayList<String> quotes;

    public void loadQuotesFromFile(String filename) {
        quotes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
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
