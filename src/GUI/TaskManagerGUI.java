package GUI;

import javax.swing.*;
import java.awt.*;

public class TaskManagerGUI {

    private JFrame frame;
    private JTextField textfield;

    public void initialize(){
        frame = new JFrame("Finito!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        JPanel bottomPanel= new JPanel(new BorderLayout());
        textfield = new JTextField();
        JButton addButton = new JButton("Add task");

        bottomPanel.add(textfield, BorderLayout.CENTER);
        bottomPanel.add(addButton, BorderLayout.EAST);

        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);


    }



}
