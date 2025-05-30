package test;

import GUI.StatisticsPanel;
import model.Task;
import model.TaskFolder;
import model.Priority;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the StatisticsPanel class.
 */
public class StatisticsPanelTest {

    /**
     * Tests updating statistics after some movement in task manager.
     */
    @Test
    public void testUpdateStatistics() {
        StatisticsPanel statsPanel = new StatisticsPanel();
        TaskFolder folder = new TaskFolder("Test Folder");

        Task task1 = new Task("Task 1");
        task1.setDone(true);
        folder.addTask(task1);

        Task task2 = new Task("Task 2");
        task2.setDone(false);
        folder.addTask(task2);

        statsPanel.updateStatistics(folder);

        assertEquals("Total tasks: 2", statsPanel.getTotalTasksLabel().getText());
        assertEquals("Done: 1 (50%)", statsPanel.getDoneTasksLabel().getText());
    }
}

