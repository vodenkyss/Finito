package GUI;

import data.DataManager;
import data.Quotes;
import model.Priority;
import model.Task;
import model.TaskFolder;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * This class is responsible for creating and managing the graphical user interface (GUI) of the task manager application.
 *It includes features such as adding and removing tasks, task folders, and statistics panel.
 *The class also handles user input, such as selecting tasks and folders, and updating the GUI accordingly.
 */
public class TaskManagerGUI {

    private JFrame frame;
    private JTextField textfield;

    private JList<Task> taskJList = new JList<Task>();
    private DefaultListModel<Task> taskDefaultListModel = new DefaultListModel<>();

    private JList<TaskFolder> folderList;
    private DefaultListModel<TaskFolder> folderListModel;

    private ArrayList<TaskFolder> folders;
    private JCheckBox showDone;
    private JCheckBox showUndone;
    private JCheckBox showHighPriority;

    private JTextField searchField;
    private boolean darkMode = true;
    private JButton themeToggle;

    private StatisticsPanel statsPanel;
    private Quotes q = new Quotes();

    private JLabel quoteLabel;

    private JComboBox<String> sortComboBox;

    private JButton completeAllButton;

    private JButton deleteAllCompletedButton;

    private JButton moveSelectedButton;

    /**
     * Constructs the TaskManagerGUI and initializes the GUI components.
     * Loads quotes from a file and displays a random quote.
     */
    public TaskManagerGUI() {
        folders = new ArrayList<>();
        initialize();
        q.loadQuotesFromFile("quotes.txt");
        showRandomQuote();

    }

    /**
     * Initializes the GUI components and sets up the layout.
     * Configures the main frame, sidebar, content panel, and event listeners
     */
    public void initialize() {


        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarculaLaf());
            windowColors();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame("Finito!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        folderListModel = new DefaultListModel<>();
        folderList = new JList<>(folderListModel);
        folderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        folderList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                applyFilters();
            }
        });

        loadData();

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
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        showDone = new JCheckBox("Show done", true);
        showDone.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showUndone = new JCheckBox("Show undone", true);
        showUndone.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showHighPriority = new JCheckBox("Show high priority only", false);
        showHighPriority.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        searchField = new JTextField(15);
        searchField.putClientProperty("JTextField.placeholderText", "Search...");

        themeToggle = new JButton();
        themeToggle.setFocusPainted(false);
        themeToggle.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        themeToggle.setSize(30, 30);
        themeToggle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themeToggle.setContentAreaFilled(false);
        themeToggle.setOpaque(false);
        updateThemeToggleIcon();
        themeToggle.addActionListener(e -> toggleTheme());


        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                applyFilters();
            }
        });

        statsPanel = new StatisticsPanel();
        frame.add(statsPanel, BorderLayout.EAST);

        quoteLabel = new JLabel();
        quoteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quoteLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        quoteLabel.setBackground(new Color(68, 67, 67));
        quoteLabel.setOpaque(true);
        frame.add(quoteLabel, BorderLayout.NORTH);

        String[] sortOptions = {"Priority", "Deadline", "Name", "Done status"};
        sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.setToolTipText("Sort by");
        sortComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sortComboBox.addActionListener(e -> applyFilters());


        filterPanel.add(searchField);
        filterPanel.add(themeToggle);

        filterPanel.add(showDone);
        filterPanel.add(showUndone);
        filterPanel.add(showHighPriority);
        filterPanel.add(sortComboBox);

        ItemListener filterListener = e -> applyFilters();
        showDone.addItemListener(filterListener);
        showUndone.addItemListener(filterListener);
        showHighPriority.addItemListener(filterListener);

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

                        TaskFolder selectedFolder = folderList.getSelectedValue();
                        statsPanel.updateStatistics(selectedFolder);
                    } else if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                        openTaskEditor(task);
                    }
                }
            }
        });

        completeAllButton = new JButton("Mark all as done");
        completeAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                completeAll();
            }
        });
        deleteAllCompletedButton = new JButton("Delete all completed");
        deleteAllCompletedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAll();
            }
        });
        moveSelectedButton = new JButton("Move selected");
        moveSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSelectedTasks();
            }
        });


        JPanel batchActionsPanel = new JPanel();
        batchActionsPanel.add(completeAllButton);
        batchActionsPanel.add(deleteAllCompletedButton);
        batchActionsPanel.add(moveSelectedButton);

        frame.add(batchActionsPanel, BorderLayout.SOUTH);


        bottomPanel.add(textfield, BorderLayout.CENTER);
        bottomPanel.add(addButton, BorderLayout.EAST);
        bottomPanel.add(removeButton, BorderLayout.SOUTH);

        contentPanel.add(new JScrollPane(taskJList), BorderLayout.CENTER);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);
        contentPanel.add(filterPanel, BorderLayout.NORTH);

        frame.add(sidebar, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveData();
            }
        });

        frame.setVisible(true);
    }


    /**
     * Adds a new task to selected folder.
     *
     * This method retrieves the task description from the text field and prompts the user to enter an
     * optional deadline in the format "dd.MM.yyyy". If a deadline is provided, it attempts to parse
     * the date. The user is also prompted to select a priority for the task. If the selected task folder
     * is not null, the new task is added to that folder and the task list is updated in the user interface.
     * After adding the task, the statistics panel is updated to reflect the changes.
     */
    private void addTask() {
        String description = textfield.getText().trim();
        if (!description.isEmpty()) {
            LocalDate deadline = null;
            String dateStr = JOptionPane.showInputDialog(frame, "Enter deadline (dd.MM.yyyy) or leave it blank:");

            if (dateStr != null && !dateStr.trim().isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    deadline = LocalDate.parse(dateStr.trim(), formatter);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Wrong format");
                }
            }

            Priority priority = (Priority) JOptionPane.showInputDialog(frame, "Choose priority:", "Priority", JOptionPane.QUESTION_MESSAGE, null, Priority.values(), Priority.MEDIUM);

            Task task = new Task(description);
            task.setDeadline(deadline);
            if (priority != null) {
                task.setPriority(priority);
            }
            TaskFolder selected = folderList.getSelectedValue();
            if (selected != null) {
                selected.addTask(task);
                taskDefaultListModel.addElement(task);
                textfield.setText("");
            }
            statsPanel.updateStatistics(selected);


        }
        applyFilters();

    }

    /**
     * Removes the selected task in folder.
     * After removing the task the statistics panel is updated to reflect the changes.
     */
    private void removeTask() {
        Task selectedTask = taskJList.getSelectedValue();
        TaskFolder selectedFolder = folderList.getSelectedValue();
        if (selectedTask != null && selectedFolder != null) {
            taskDefaultListModel.removeElement(selectedTask);
            selectedFolder.getTasks().remove(selectedTask);
        }
        applyFilters();
    }

    /**
     * Applies filters and sorting to the tasks in selected folder.
     *
     * Checks the users preffered filter, including whether to show completed tasks, incomplete tasks, and
     * high-priority tasks. The method also retrieves the search text entered by the user.
     */
    public void applyFilters() {
        taskDefaultListModel.clear();
        TaskFolder selected = folderList.getSelectedValue();
        if (selected == null) return;

        boolean done = showDone.isSelected();
        boolean undone = showUndone.isSelected();
        boolean highOnly = showHighPriority.isSelected();
        String searchText = searchField.getText().toLowerCase().trim();

        String selectedSort = (String) sortComboBox.getSelectedItem();

        Comparator<Task> comparator = switch (selectedSort) {
            case "Deadline" -> Comparator.comparing(Task::getDeadline, Comparator.nullsLast(Comparator.naturalOrder()));
            case "Name" -> Comparator.comparing(Task::getDescription, String.CASE_INSENSITIVE_ORDER);
            case "Done status" -> Comparator.comparing(Task::isDone);
            case "Priority" -> Comparator.comparingInt(t -> -t.getPriority().getLevel());
            default -> (a, b) -> 0;
        };


        selected.getTasks().stream().filter(task -> {
            if (highOnly && task.getPriority() != Priority.HIGH) return false;
            if (!done && task.isDone()) return false;
            if (!undone && !task.isDone()) return false;
            if (!searchText.isEmpty() && !task.getDescription().toLowerCase().contains(searchText)) return false;

            return true;
        }).sorted(comparator).forEach(taskDefaultListModel::addElement);

        statsPanel.updateStatistics(selected);

    }


    /**
     * Prompts the user to enter a name for a new task folder and adds it to the list of folders.
     * If the user provides a valid name (non-null and not empty), a new task folder
     * is created and added to the internal list of folders. The newly created folder is also
     * added to the folder list model and selected in the user interface.
     */
    public void addFolder() {
        String name = JOptionPane.showInputDialog(frame, "Folder name:");
        if (name != null && !name.trim().isEmpty()) {
            TaskFolder newFolder = new TaskFolder(name.trim());
            folders.add(newFolder);
            folderListModel.addElement(newFolder);
            folderList.setSelectedValue(newFolder, true);
        }
    }

    /**
     * Removes the whole folder.
     */
    public void removeFolder() {
        TaskFolder selected = folderList.getSelectedValue();
        if (selected != null) {
            folders.remove(selected);
            folderListModel.removeElement(selected);
            taskDefaultListModel.clear();
        }
    }


    private void saveData() {
        DataManager.saveFolders(folders);
    }


    /**
     * Loads task folders from the data manager and updates the user interface.
     *
     *  It also checks for tasks with upcoming deadlines within
     * the next day and displays a warning message if any are found.
     */
    private void loadData() {
        folders = DataManager.loadFolders();
        for (TaskFolder folder : folders) {
            folderListModel.addElement(folder);
        }
        ArrayList<Task> upcoming = getTasksWithUpcomingDeadlines(1);

        if (!upcoming.isEmpty()) {
            StringBuilder msg = new StringBuilder("You have tasks with approaching deadline!:\n\n");
            for (Task t : upcoming) {
                msg.append("- ").append(t.getDescription()).append(" (do ").append(t.getDeadline()).append(")\n");
            }

            JOptionPane.showMessageDialog(frame, msg.toString(), "Upcoming tasks:", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Opens editing window after doubleclick on task.
     *
     * User can edit description, deadline, priority and add notes.
     * @param task the selected task
     */
    private void openTaskEditor(Task task) {
        TaskEditorDialog dialog = new TaskEditorDialog(frame, task);
        dialog.setVisible(true);
        applyFilters();
    }

    /**
     * Retrieves tasks with upcoming deadlines within the specified number of days.
     *
     *This method iterates through all tasks in all folders and checks if the task has a deadline
     *within the specified number of days from the current date. If a task meets this condition and
     *is not yet done, it is added to the list of upcoming tasks.
     * @param daysAhead the number of days ahead to check for upcoming deadlines
     * @return tasks with upcoming deadline
     */
    private ArrayList<Task> getTasksWithUpcomingDeadlines(int daysAhead) {
        ArrayList<Task> upcoming = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (TaskFolder folder : folders) {
            for (Task task : folder.getTasks()) {
                LocalDate deadline = task.getDeadline();
                if (deadline != null && !task.isDone()) {
                    long daysBetween = ChronoUnit.DAYS.between(today, deadline);
                    if (daysBetween >= 0 && daysBetween <= daysAhead) {
                        upcoming.add(task);
                    }
                }
            }
        }
        return upcoming;
    }

    /**
     * This method switches between dark and light mode.
     */
    private void toggleTheme() {
        try {
            if (darkMode) {
                UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
            } else {
                UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarculaLaf());
            }

            SwingUtilities.updateComponentTreeUI(frame);
            darkMode = !darkMode;
            updateThemeToggleIcon();

        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays a random quote from list
     */
    private void showRandomQuote() {
        if (q.getQuotes().isEmpty()) {
            quoteLabel.setText("No quotes available.");
            return;
        }
        Random random = new Random();
        String quote = q.getQuotes().get(random.nextInt(q.getQuotes().size()));
        quoteLabel.setText("<html><body style='text-align: center;'>" + quote + "</body></html>");
    }


    /**
     * Updates the icon of the dark mode button.
     *
     * Light mode and dark mode have different icons.
     */
    private void updateThemeToggleIcon() {

        if (darkMode) {
            FlatSVGIcon icon = new FlatSVGIcon(getClass().getResource("/moon(1).svg"));
            themeToggle.setIcon(icon);
        } else {
            FlatSVGIcon icon2 = new FlatSVGIcon(getClass().getResource("/moon.svg"));
            themeToggle.setIcon(icon2);

        }
        themeToggle.setToolTipText(darkMode ? "Light mode" : "Dark mode");
    }

    /**
     * Marks all tasks as done in selected folder.
     *
     * Checks if the current folder is not null
     */
    private void completeAll() {

        TaskFolder currentFolder = folderList.getSelectedValue();
        if (currentFolder != null) {
            for (Task task : currentFolder.getTasks()) {
                task.setDone(true);
            }
            applyFilters();
        }
    }

    /**
     * Deletes all tasks in selected folder.
     *
     * Checks if the current folder is not null.
     */
    private void deleteAll() {
        TaskFolder currentFolder = folderList.getSelectedValue();
        if (currentFolder != null) {
            currentFolder.getTasks().removeIf(Task::isDone);
            applyFilters();
        }
    }

    /**
     * Moves selected task between different folders.
     *
     * Shows Dialog window where user chooses where the task will be moved.
     */
    private void moveSelectedTasks() {
        Task selectedTask = taskJList.getSelectedValue();
        if (selectedTask == null) return;

        TaskFolder currentFolder = folderList.getSelectedValue();
        if (currentFolder == null) return;

        TaskFolder[] options = folders.stream().filter(f -> f != currentFolder).toArray(TaskFolder[]::new);
        TaskFolder newFolder = (TaskFolder) JOptionPane.showInputDialog(frame, "Move to:", "Move Task", JOptionPane.PLAIN_MESSAGE, null, options, options.length > 0 ? options[0] : null);

        if (newFolder != null) {
            currentFolder.getTasks().remove(selectedTask);
            newFolder.getTasks().add(selectedTask);
            applyFilters();
        }
    }


    /**
     * This method sets the font of various buttons, labels, combobox etc.
     */
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

    public DefaultListModel<TaskFolder> getFolderListModel() {
        return folderListModel;
    }

    public ArrayList<TaskFolder> getFolders() {
        return folders;
    }

    public JList<TaskFolder> getFolderList() {
        return folderList;
    }

    public DefaultListModel<Task> getTaskDefaultListModel() {
        return taskDefaultListModel;
    }

    public JCheckBox getShowHighPriority() {
        return showHighPriority;
    }

    public JCheckBox getShowDone() {
        return showDone;
    }


    public JCheckBox getShowUndone() {
        return showUndone;
    }

}
