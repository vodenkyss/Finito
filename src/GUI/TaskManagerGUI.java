package GUI;

import model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskManagerGUI {

    private JFrame frame;
    private JTextField textfield;

    private JList<Task> taskJList= new JList<Task>();

    private DefaultListModel<Task> taskDefaultListModel= new DefaultListModel<>();

    public void initialize(){
        frame = new JFrame("Finito!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        JPanel bottomPanel= new JPanel(new BorderLayout());
        textfield = new JTextField();
        JButton addButton = new JButton("Add task");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        taskDefaultListModel = new DefaultListModel<>();
        taskJList = new JList<>(taskDefaultListModel);
        taskJList.setCellRenderer(new TaskRender());


        bottomPanel.add(textfield, BorderLayout.CENTER);
        bottomPanel.add(addButton, BorderLayout.EAST);

        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(new JScrollPane(taskJList),BorderLayout.CENTER);
        frame.setVisible(true);



    }

    private void addTask(){
        String description = textfield.getText().trim();
        if (!description.isEmpty()){
            Task task = new Task(description);
            taskDefaultListModel.addElement(task);
            textfield.setText("");
        }
    }



}
