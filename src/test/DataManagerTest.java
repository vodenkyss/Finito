package test;

import data.DataManager;
import model.Task;
import model.TaskFolder;
import model.Priority;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DataManager class.
 */
public class DataManagerTest {

    private final String testFilename = "test_folders.ser";

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        DataManager.file = testFilename;
    }

    /**
     * Cleans up the test environment after each test.
     */
    @AfterEach
    public void tearDown() {
        File file = new File(testFilename);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     *Tests the saving and loading task folders.
     */
    @Test
    public void testSaveAndLoadFolders() {
        TaskFolder folder1 = new TaskFolder("School");
        Task task1 = new Task("Math homework");
        task1.setPriority(Priority.HIGH);
        task1.setDeadline(LocalDate.of(2025, 6, 1));
        task1.setNotes("Page 42–44");

        folder1.addTask(task1);

        TaskFolder folder2 = new TaskFolder("Home");
        Task task2 = new Task("Clean");
        task2.setDone(true);
        folder2.addTask(task2);

        ArrayList<TaskFolder> originalFolders = new ArrayList<>();
        originalFolders.add(folder1);
        originalFolders.add(folder2);

        DataManager.saveFolders(originalFolders);

        List<TaskFolder> loadedFolders = DataManager.loadFolders();

        assertNotNull(loadedFolders);
        assertEquals(2, loadedFolders.size());

        TaskFolder loadedFolder1 = loadedFolders.get(0);
        assertEquals("School", loadedFolder1.getName());
        assertEquals(1, loadedFolder1.getTasks().size());
        Task loadedTask1 = loadedFolder1.getTasks().get(0);
        assertEquals("Math homework", loadedTask1.getDescription());
        assertEquals(Priority.HIGH, loadedTask1.getPriority());
        assertEquals(LocalDate.of(2025, 6, 1), loadedTask1.getDeadline());
        assertEquals("Page 42–44", loadedTask1.getNotes());
        assertFalse(loadedTask1.isDone());

        TaskFolder loadedFolder2 = loadedFolders.get(1);
        assertEquals("Home", loadedFolder2.getName());
        assertEquals(1, loadedFolder2.getTasks().size());
        Task loadedTask2 = loadedFolder2.getTasks().get(0);
        assertEquals("Clean", loadedTask2.getDescription());
        assertTrue(loadedTask2.isDone());
    }

    /**
     * Tests saving and loading empty folder.
     */
    @Test
    public void testSaveEmptyFolder() {
        TaskFolder emptyFolder = new TaskFolder("Empty Folder");
        ArrayList<TaskFolder> originalFolders = new ArrayList<>();
        originalFolders.add(emptyFolder);
        DataManager.saveFolders(originalFolders);
        List<TaskFolder> loadedFolders = DataManager.loadFolders();
        assertEquals(1, loadedFolders.size(), "Should load one folder");
        TaskFolder loadedFolder = loadedFolders.get(0);
        assertEquals("Empty Folder", loadedFolder.getName());
        assertTrue(loadedFolder.getTasks().isEmpty(), "Loaded folder should have no tasks");
    }

}

