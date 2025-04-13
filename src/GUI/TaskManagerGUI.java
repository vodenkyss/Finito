package GUI;

import model.Task;
import model.TaskFolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TaskManagerGUI {

    private JFrame frame;
    private JTextField textfield;

    private JList<Task> taskJList= new JList<Task>();

    private DefaultListModel<Task> taskDefaultListModel= new DefaultListModel<>();
    private DefaultComboBoxModel<TaskFolder> folderModel;

    private JComboBox<TaskFolder> folderJComboBox;

    private ArrayList<TaskFolder> folders;

    public TaskManagerGUI() {
        folders= new ArrayList<>();
        initialize();
    }

    public void initialize(){

        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
            windowColors();

        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame("Finito!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);

        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        folderModel = new DefaultComboBoxModel<>();
        folderJComboBox = new JComboBox<>(folderModel);
        folderJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFolder();
            }
        });
        JButton newFolder = new JButton("+");
        newFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFolder();
            }
        });

        topPanel.add(folderJComboBox,BorderLayout.CENTER);
        topPanel.add(newFolder,BorderLayout.SOUTH);

        JPanel bottomPanel= new JPanel(new BorderLayout());
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
                Task task = taskDefaultListModel.get(index);
                task.toggleDone();
                taskJList.repaint();
            }
        });


        bottomPanel.add(textfield, BorderLayout.CENTER);
        bottomPanel.add(addButton, BorderLayout.EAST);
        bottomPanel.add(removeButton,BorderLayout.SOUTH);

        frame.add(topPanel,BorderLayout.NORTH);
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

    private void removeTask(){
        Task selectedTask = taskJList.getSelectedValue();
        if (selectedTask!=null){
            taskDefaultListModel.removeElement(selectedTask);
        }
    }

    public void loadFolder(){
        TaskFolder selected = (TaskFolder) folderJComboBox.getSelectedItem();
        if (selected != null) {
            for (Task task : selected.getTasks()) {
                taskDefaultListModel.addElement(task);
            }
        }
    }

    public void addFolder(){
        String name = JOptionPane.showInputDialog(frame, "Folder name:");
        if (name != null && !name.trim().isEmpty()) {
            TaskFolder newFolder = new TaskFolder(name.trim());
            folders.add(newFolder);
            folderModel.addElement(newFolder);
            folderJComboBox.setSelectedItem(newFolder);
        }

    }

    private void windowColors(){
        UIManager.put("Component.arc", 10);
        UIManager.put("Button.arc", 15);
        UIManager.put("TextComponent.arc", 10);

        UIManager.put("Button.background", new Color(139, 217, 142));
        UIManager.put("Button.foreground", new Color(0, 0, 0));

        UIManager.put("Panel.background", new Color(81, 169, 126));
        UIManager.put("List.background", new Color(81, 169, 126));
        UIManager.put("List.selectionBackground", new Color(81, 169, 126));
        UIManager.put("List.selectionForeground", Color.BLACK);

        UIManager.put("TextField.background", new Color(255, 255, 255));
        UIManager.put("TextField.foreground", Color.BLACK);
        UIManager.put("TextField.caretForeground", new Color(33, 66, 44));

    }



}
