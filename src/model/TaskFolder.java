package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represent a task folder with list of tasks.
 */
public class TaskFolder implements Serializable {

    private String name;
    private ArrayList<Task> tasks;

    public TaskFolder(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task t){
        tasks.add(t);
    }

    @Override
    public String toString() {
        return name;
    }
}
