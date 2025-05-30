package test;

import model.Task;
import model.TaskFolder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the TaskFolder class.
 */
public class TaskFolderTest {

    /**
     * Tests adding and removing task from task folder.
     */
    @Test
    public void testAddAndRemoveTask() {
        TaskFolder folder = new TaskFolder("Test Folder");
        Task task = new Task("Test Task");

        folder.addTask(task);
        assertEquals(1, folder.getTasks().size(), "Folder should contain 1 task after adding");

        folder.getTasks().remove(task);
        assertEquals(0, folder.getTasks().size(), "Folder should be empty after removing the task");
    }
}

