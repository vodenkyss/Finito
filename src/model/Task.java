package model;

import java.io.Serializable;

public class Task implements Serializable {
    private String description;
    private boolean isDone;

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
}
