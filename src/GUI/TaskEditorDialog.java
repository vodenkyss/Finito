package GUI;

import model.Priority;
import model.Task;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TaskEditorDialog extends JDialog {

    private final Task task;
    private JSpinner deadlineSpinner;

    public TaskEditorDialog(JFrame parent, Task task) {
        super(parent, "Edit task", true);
        this.task = task;

        setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Description:"));
        JTextField nameField = new JTextField(task.getDescription());
        formPanel.add(nameField);

        formPanel.add(new JLabel("Priority:"));
        JComboBox<Priority> priorityComboBox = new JComboBox<>(Priority.values());
        priorityComboBox.setSelectedItem(task.getPriority());
        formPanel.add(priorityComboBox);

        formPanel.add(new JLabel("Deadline:"));
        JTextField deadlineField = new JTextField(10);
        if (task.getDeadline() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            deadlineField.setText(task.getDeadline().format(formatter));
        }
        formPanel.add(deadlineField);

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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String deadlineText = deadlineField.getText().trim();
            if (deadlineText.isEmpty()) {
                task.setDeadline(null);
            } else {
                try {
                    LocalDate deadlineDate = LocalDate.parse(deadlineText, formatter);
                    task.setDeadline(deadlineDate);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Wrong format", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

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
