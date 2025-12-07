package system.logic.entities;

import system.logic.utilities.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private final String code;
    private final String description;
    private final User manager;
    private final List<Task> taskList;

    public Project() {
        this.code = "";
        this.description = "";
        this.manager = null;
        this.taskList = new ArrayList<>(); // If it is initialized as null, it can be accessed
    };

    public Project(String description, User manager) {
        this.code = IdGenerator.newProjectId();
        this.description = description;
        this.manager = manager;
        taskList = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public User getManager() {
        return manager;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }

        // Prevent two tasks with the same description in this project
        for (Task t : taskList) {
            if (t.getDescription().equalsIgnoreCase(task.getDescription())) {
                throw new IllegalArgumentException(
                        "A task with the description \"" + task.getDescription() + "\" already exists."
                );
            }
        }

        taskList.add(task);
    }
}