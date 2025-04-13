package model;

import java.util.ArrayList;

public class TaskFolder {

    private String name;
    private ArrayList<Task> tasks;

    public TaskFolder(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
