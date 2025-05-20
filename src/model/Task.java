package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    private String description;
    private boolean isDone;
    private Priority priority;

    private LocalDate deadline;
    private String notes;
    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.notes="";
        this.deadline=null;
        this.priority = Priority.MEDIUM;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void toggleDone() {
        this.isDone = !this.isDone;
    }

    @Override
    public String toString() {
        return "model.Task{" +
                "description='" + description + '\'' +
                ", isDone=" + isDone +
                '}';
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
