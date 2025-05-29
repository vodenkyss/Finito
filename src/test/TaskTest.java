package test;

import model.Task;
import model.Priority;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testTaskCreation() {
        Task task = new Task("Test task");
        assertEquals("Test task", task.getDescription(), "Description should match");
        assertFalse(task.isDone(), "Task should initially be not done");
        assertNull(task.getDeadline(), "Deadline should initially be null");
        assertEquals(Priority.MEDIUM, task.getPriority(), "Default priority should be MEDIUM");
    }

    @Test
    public void testToggleDone() {
        Task task = new Task("Test task");

        assertFalse(task.isDone(), "Task should initially be not done");

        task.toggleDone();
        assertTrue(task.isDone(), "Task should be done after toggling");

        task.toggleDone();
        assertFalse(task.isDone(), "Task should be not done after toggling again");
    }

    @Test
    public void testSetDeadline() {
        Task task = new Task("Test task");
        LocalDate deadline = LocalDate.of(2025, 12, 31);
        task.setDeadline(deadline);
        assertEquals(deadline, task.getDeadline(), "Deadline should match the set date");
    }

    @Test
    public void testSetPriority() {
        Task task = new Task("Test task");
        task.setPriority(Priority.HIGH);
        assertEquals(Priority.HIGH, task.getPriority(), "Priority should be HIGH");
    }

    @Test
    public void testSetNotes() {
        Task task = new Task("Test task");
        String notes = "This is a test note.";
        task.setNotes(notes);
        assertEquals(notes, task.getNotes(), "Notes should match the set notes");
    }




}

