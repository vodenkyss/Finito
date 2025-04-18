package GUI;

import model.Task;
import model.TaskFolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TaskManagerGUI {

    private JFrame frame;
    private JTextField textfield;

    private JList<Task> taskJList = new JList<Task>();
    private DefaultListModel<Task> taskDefaultListModel = new DefaultListModel<>();

    private JList<TaskFolder> folderList;
    private DefaultListModel<TaskFolder> folderListModel;

    private ArrayList<TaskFolder> folders;

    public TaskManagerGUI() {
        folders = new ArrayList<>();
        initialize();
    }

    public void initialize() {

        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarculaLaf());
            windowColors();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame("Finito!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLayout(new BorderLayout());

        folderListModel = new DefaultListModel<>();
        folderList = new JList<>(folderListModel);
        folderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        folderList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadFolder();
            }
        });

        JButton newFolder = new JButton("+ Add Folder");
        newFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFolder();
            }
        });

        JButton removeFolderButton = new JButton("- Remove Folder");
        removeFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeFolder();
            }
        });

        JPanel sidebar = new JPanel(new BorderLayout());
        JPanel folderButtons = new JPanel(new GridLayout(2, 1));
        folderButtons.add(newFolder);
        folderButtons.add(removeFolderButton);

        sidebar.add(new JScrollPane(folderList), BorderLayout.CENTER);
        sidebar.add(folderButtons, BorderLayout.SOUTH);
        sidebar.setPreferredSize(new Dimension(180, 0));

        JPanel contentPanel = new JPanel(new BorderLayout());

        JPanel bottomPanel = new JPanel(new BorderLayout());
        textfield = new JTextField();
        JButton addButton = new JButton("Add task");
        JButton removeButton = new JButton("Remove");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTask();
            }
        });

        taskDefaultListModel = new DefaultListModel<>();
        taskJList = new JList<>(taskDefaultListModel);
        taskJList.setCellRenderer(new TaskRender());
        taskJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = taskJList.locationToIndex(e.getPoint());
                if (index != -1) {
                    Task task = taskDefaultListModel.get(index);
                    Rectangle bounds = taskJList.getCellBounds(index, index);
                    int iconSize = 16;
                    if (e.getX() - bounds.x <= iconSize) {
                        task.toggleDone();
                        taskJList.repaint();
                    }
                }
            }
        });

        bottomPanel.add(textfield, BorderLayout.CENTER);
        bottomPanel.add(addButton, BorderLayout.EAST);
        bottomPanel.add(removeButton, BorderLayout.SOUTH);

        contentPanel.add(new JScrollPane(taskJList), BorderLayout.CENTER);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(sidebar, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void addTask() {
        String description = textfield.getText().trim();
        if (!description.isEmpty()) {
            Task task = new Task(description);
            TaskFolder selected = folderList.getSelectedValue();
            if (selected != null) {
                selected.addTask(task);
                taskDefaultListModel.addElement(task);
                textfield.setText("");
            }
        }
    }

    private void removeTask() {
        Task selectedTask = taskJList.getSelectedValue();
        TaskFolder selectedFolder = folderList.getSelectedValue();
        if (selectedTask != null && selectedFolder != null) {
            taskDefaultListModel.removeElement(selectedTask);
            selectedFolder.getTasks().remove(selectedTask);
        }
    }

    public void loadFolder() {
        taskDefaultListModel.clear();
        TaskFolder selected = folderList.getSelectedValue();
        if (selected != null) {
            for (Task task : selected.getTasks()) {
                taskDefaultListModel.addElement(task);
            }
        }
    }

    public void addFolder() {
        String name = JOptionPane.showInputDialog(frame, "Folder name:");
        if (name != null && !name.trim().isEmpty()) {
            TaskFolder newFolder = new TaskFolder(name.trim());
            folders.add(newFolder);
            folderListModel.addElement(newFolder);
            folderList.setSelectedValue(newFolder, true);
        }
    }

    public void removeFolder() {
        TaskFolder selected = folderList.getSelectedValue();
        if (selected != null) {
            folders.remove(selected);
            folderListModel.removeElement(selected);
            taskDefaultListModel.clear();
        }
    }

    private void windowColors() {
        Font customFont = new Font("Segoe UI", Font.BOLD, 14);
        UIManager.put("Label.font", customFont);
        UIManager.put("Button.font", customFont);
        UIManager.put("TextField.font", customFont);
        UIManager.put("List.font", customFont);
        UIManager.put("ComboBox.font", customFont);
        UIManager.put("OptionPane.messageFont", customFont);
        UIManager.put("OptionPane.buttonFont", customFont);

        UIManager.put("Component.arc", 10);
        UIManager.put("Button.arc", 15);
        UIManager.put("TextComponent.arc", 10);

    }
}
