package GUI;

import model.Priority;
import model.Task;

import javax.swing.*;
import java.awt.*;

public class TaskEditorDialog extends JDialog {

    private final Task task;

    public TaskEditorDialog(JFrame parent, Task task) {
        super(parent, "Edit task", true);
        this.task = task;

        setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Description:"));
        JTextField nameField = new JTextField(task.getDescription());
        formPanel.add(nameField);

        formPanel.add(new JLabel("Priority:"));
        JComboBox<Priority> priorityComboBox = new JComboBox<>(Priority.values());
        priorityComboBox.setSelectedItem(task.getPriority());
        formPanel.add(priorityComboBox);

        formPanel.add(new JLabel("Notes:"));
        JTextArea notesArea = new JTextArea(task.getNotes(), 3, 20);
        JScrollPane scrollPane = new JScrollPane(notesArea);
        formPanel.add(scrollPane);

        formPanel.add(new JLabel("Done:"));
        JCheckBox completedBox = new JCheckBox();
        completedBox.setSelected(task.isDone());
        formPanel.add(completedBox);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            task.setDescription(nameField.getText());
            task.setPriority((Priority) priorityComboBox.getSelectedItem());
            task.setNotes(notesArea.getText());
            task.setDone(completedBox.isSelected());
            dispose();
        });

        add(formPanel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }
}
