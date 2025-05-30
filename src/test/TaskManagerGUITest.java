package test;

import GUI.TaskManagerGUI;
import model.Priority;
import model.Task;
import model.TaskFolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the  TaskManagerGUI class.
 */
public class TaskManagerGUITest {

    private TaskManagerGUI taskManagerGUI;
    private TaskFolder folder;

    /**
     * Initializes a new instance of the TaskManagerGUI class and adds a test folder to it.
     */
    @BeforeEach
    public void setUp() {
        taskManagerGUI = new TaskManagerGUI();
        folder = new TaskFolder("Test Folder");
        taskManagerGUI.getFolders().add(folder);
        taskManagerGUI.getFolderListModel().addElement(folder);
    }

    /**
     * Tests the application of filters to tasks.
     *
     * This test creates four tasks with different priorities and statuses, adds them to the test folder,
     * and applies filters to the tasks based on their status and priority. It then verifies that the
     * tasks are correctly filtered and displayed in the GUI
     */
    @Test
    public void testApplyFilters() {
        Task task1 = new Task("High priority task");
        task1.setPriority(Priority.HIGH);
        task1.setDone(false);
        task1.setDeadline(LocalDate.of(2025, 12, 31));

        Task task2 = new Task("Medium priority task");
        task2.setPriority(Priority.MEDIUM);
        task2.setDone(true);

        Task task3 = new Task("Low priority task");
        task3.setPriority(Priority.LOW);
        task3.setDone(false);

        Task task4 = new Task("Another high priority task");
        task4.setPriority(Priority.HIGH);
        task4.setDone(true);

        folder.addTask(task1);
        folder.addTask(task2);
        folder.addTask(task3);
        folder.addTask(task4);

        taskManagerGUI.getFolderList().setSelectedValue(folder, true);

        taskManagerGUI.getShowDone().setSelected(true);
        taskManagerGUI.getShowUndone().setSelected(false);
        taskManagerGUI.getShowHighPriority().setSelected(false);
        taskManagerGUI.applyFilters();

        assertEquals(2, taskManagerGUI.getTaskDefaultListModel().getSize());
        assertTrue(taskManagerGUI.getTaskDefaultListModel().contains(task2));
        assertTrue(taskManagerGUI.getTaskDefaultListModel().contains(task4));
        assertFalse(taskManagerGUI.getTaskDefaultListModel().contains(task1));
        assertFalse(taskManagerGUI.getTaskDefaultListModel().contains(task3));

        taskManagerGUI.getShowDone().setSelected(false);
        taskManagerGUI.getShowUndone().setSelected(true);
        taskManagerGUI.getShowHighPriority().setSelected(true);
        taskManagerGUI.applyFilters();

        assertEquals(1, taskManagerGUI.getTaskDefaultListModel().getSize());
        assertTrue(taskManagerGUI.getTaskDefaultListModel().contains(task1));
        assertFalse(taskManagerGUI.getTaskDefaultListModel().contains(task2));
        assertFalse(taskManagerGUI.getTaskDefaultListModel().contains(task3));
        assertFalse(taskManagerGUI.getTaskDefaultListModel().contains(task4));

        taskManagerGUI.getShowDone().setSelected(true);
        taskManagerGUI.getShowUndone().setSelected(true);
        taskManagerGUI.getShowHighPriority().setSelected(false);
        taskManagerGUI.applyFilters();

        assertEquals(4, taskManagerGUI.getTaskDefaultListModel().getSize());
    }
}
