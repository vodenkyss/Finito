package test;

import model.Task;
import model.Priority;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Task class.
 */
public class TaskTest {

    /**
     * Tests the creation of new task.
     */
    @Test
    public void testTaskCreation() {
        Task task = new Task("Test task");
        assertEquals("Test task", task.getDescription(), "Description should match");
        assertFalse(task.isDone(), "Task should initially be not done");
        assertNull(task.getDeadline(), "Deadline should initially be null");
        assertEquals(Priority.MEDIUM, task.getPriority(), "Default priority should be MEDIUM");
    }

    /**
     * Tests if the toggle after the task is done works well.
     */
    @Test
    public void testToggleDone() {
        Task task = new Task("Test task");

        assertFalse(task.isDone(), "Task should initially be not done");

        task.toggleDone();
        assertTrue(task.isDone(), "Task should be done after toggling");

        task.toggleDone();
        assertFalse(task.isDone(), "Task should be not done after toggling again");
    }

    /**
     * Tests setting deadline.
     */
    @Test
    public void testSetDeadline() {
        Task task = new Task("Test task");
        LocalDate deadline = LocalDate.of(2025, 12, 31);
        task.setDeadline(deadline);
        assertEquals(deadline, task.getDeadline(), "Deadline should match the set date");
    }

    /**
     * Tests setting priority.
     */
    @Test
    public void testSetPriority() {
        Task task = new Task("Test task");
        task.setPriority(Priority.HIGH);
        assertEquals(Priority.HIGH, task.getPriority(), "Priority should be HIGH");
    }

    /**
     * Tests setting notes.
     */
    @Test
    public void testSetNotes() {
        Task task = new Task("Test task");
        String notes = "This is a test note.";
        task.setNotes(notes);
        assertEquals(notes, task.getNotes(), "Notes should match the set notes");
    }

    /**
     * Tests setting invalid format of deadline.
     */
    @Test
    public void testSetDeadlineWithInvalidFormat() {
        Task task = new Task("Task with invalid deadline");
        String invalidDate = "invalid-date";
        Exception exception = assertThrows(Exception.class, () -> {
            LocalDate.parse(invalidDate);
        });
        assertNotNull(exception, "Should throw an exception for invalid date format");
    }

    /**
     * Tests if the deadline comparison works well.
     */
    @Test
    public void testDeadlineComparison() {
        Task task1 = new Task("Task with future deadline");
        LocalDate futureDeadline = LocalDate.of(2025, 12, 31);
        task1.setDeadline(futureDeadline);

        Task task2 = new Task("Task with past deadline");
        LocalDate pastDeadline = LocalDate.of(2020, 1, 1);
        task2.setDeadline(pastDeadline);

        assertTrue(task1.getDeadline().isAfter(task2.getDeadline()), "Future deadline should be after past deadline");
    }




}

