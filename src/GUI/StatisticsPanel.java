package GUI;

import model.Task;
import model.TaskFolder;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * A panel that displays statistical information about tasks in a selected TaskFolder.
 */
public class StatisticsPanel extends JPanel {

    private JLabel totalTasksLabel;
    private JLabel doneTasksLabel;
    private JLabel lowPriorityLabel;
    private JLabel mediumPriorityLabel;
    private JLabel highPriorityLabel;
    private JLabel withDeadlineLabel;

    private JLabel overdueLabel;
    private JProgressBar doneProgressBar;


    public StatisticsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("📊 Stats:"));

        totalTasksLabel = new JLabel();
        doneTasksLabel = new JLabel();
        lowPriorityLabel = new JLabel();
        mediumPriorityLabel = new JLabel();
        highPriorityLabel = new JLabel();
        withDeadlineLabel = new JLabel();
        overdueLabel = new JLabel();
        doneProgressBar = new JProgressBar(0, 100);

        overdueLabel.setForeground(Color.ORANGE);
        doneProgressBar.setStringPainted(true);

        add(totalTasksLabel);
        add(doneTasksLabel);
        add(lowPriorityLabel);
        add(mediumPriorityLabel);
        add(highPriorityLabel);
        add(withDeadlineLabel);
        add(overdueLabel);
        add(doneProgressBar);
    }

    /**
     * Updates the statistics displayed on statistics panel
     *
     * This method calculates various statistics, including the total number of tasks, the number of completed tasks,
     * the count of task priority, number of tasks with deadline,progress bar , and the number of overdue tasks.
     * @param selectedFolder the task folder containing the tasks for which statistics are to be updated.
     */
    public void updateStatistics(TaskFolder selectedFolder) {
        if (selectedFolder == null) return;

        List<Task> tasks = selectedFolder.getTasks();

        long done = tasks.stream().filter(Task::isDone).count();
        long low = tasks.stream().filter(t -> t.getPriority().name().equals("LOW")).count();
        long med = tasks.stream().filter(t -> t.getPriority().name().equals("MEDIUM")).count();
        long high = tasks.stream().filter(t -> t.getPriority().name().equals("HIGH")).count();
        long deadline = tasks.stream().filter(t -> t.getDeadline() != null).count();
        int percentDone = tasks.isEmpty() ? 0 : (int)((done * 100) / tasks.size());


        long overdue = tasks.stream()
                .filter(t -> t.getDeadline() != null && t.getDeadline().isBefore(LocalDate.now().plusDays(1)))
                .count();

        totalTasksLabel.setText("Total tasks: " + tasks.size());
        doneTasksLabel.setText("Done: " + done+ " (" + percentDone + "%)");
        lowPriorityLabel.setText("Low priority: " + low);
        mediumPriorityLabel.setText("Medium priority: " + med);
        highPriorityLabel.setText("High priority: " + high);
        withDeadlineLabel.setText("With deadline: " + deadline);
        overdueLabel.setText("Due today or earlier: " + overdue);
        doneTasksLabel.setForeground(new Color(89, 201, 89));
        highPriorityLabel.setForeground(new Color(243, 72, 72));

        doneProgressBar.setValue(percentDone);
    }

    public JLabel getTotalTasksLabel() {
        return totalTasksLabel;
    }

    public JLabel getDoneTasksLabel() {
        return doneTasksLabel;
    }
}

