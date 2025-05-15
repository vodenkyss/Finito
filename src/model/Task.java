package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    private String description;
    private boolean isDone;
    private Priority priority= Priority.MEDIUM;

    private LocalDate deadline;

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Task(String description) {
        this.description = description;
        this.isDone = false;
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
}
