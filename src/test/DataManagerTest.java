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

public class DataManagerTest {

    private final String testFilename = "test_folders.ser";

    @BeforeEach
    public void setUp() {
        DataManager.file = testFilename;
    }

    @AfterEach
    public void tearDown() {
        File file = new File(testFilename);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     *
     */
    @Test
    public void testSaveAndLoadFolders() {
        TaskFolder folder1 = new TaskFolder("Škola");
        Task task1 = new Task("Dopsat úkol z matiky");
        task1.setPriority(Priority.HIGH);
        task1.setDeadline(LocalDate.of(2025, 6, 1));
        task1.setNotes("Strana 42–44");

        folder1.addTask(task1);

        TaskFolder folder2 = new TaskFolder("Domácnost");
        Task task2 = new Task("Vynést koš");
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
        assertEquals("Škola", loadedFolder1.getName());
        assertEquals(1, loadedFolder1.getTasks().size());
        Task loadedTask1 = loadedFolder1.getTasks().get(0);
        assertEquals("Dopsat úkol z matiky", loadedTask1.getDescription());
        assertEquals(Priority.HIGH, loadedTask1.getPriority());
        assertEquals(LocalDate.of(2025, 6, 1), loadedTask1.getDeadline());
        assertEquals("Strana 42–44", loadedTask1.getNotes());
        assertFalse(loadedTask1.isDone());

        TaskFolder loadedFolder2 = loadedFolders.get(1);
        assertEquals("Domácnost", loadedFolder2.getName());
        assertEquals(1, loadedFolder2.getTasks().size());
        Task loadedTask2 = loadedFolder2.getTasks().get(0);
        assertEquals("Vynést koš", loadedTask2.getDescription());
        assertTrue(loadedTask2.isDone());
    }
}

