package system.logic;

import system.data.Data;
import system.logic.entities.Project;
import system.logic.entities.Task;
import system.logic.entities.User;

import java.util.List;

import system.logic.utilities.Priority;
import system.logic.utilities.Status;

public class Service {
    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    private Data data;

    private Service() {
        data = new Data();
    }

    // --- Project Operations (CRUD) ---

    // Create (C)
    public void createProject(Project project) throws Exception {
        boolean exists = data.getProjects().stream()
                .anyMatch(i -> i.getDescription().equals(project.getDescription()));
        if (exists) {
            throw new Exception("Project already exists");
        }
        data.getProjects().add(project);
    }

    // Read (R)
    public List<Project> getAllProjects() { // Renamed from findAllProjects()
        return data.getProjects();
    }

    // Read (R) - for Users
    public List<User> getAllUsers() {
        return data.getUsers();
    }

    // Update (U) and Delete (D) operations are intentionally omitted/restricted because of exam SPEC

    // --- Task Operations ---

    public void addTaskToProject(Project project, Task task) throws Exception {
        // 1. Validation (Optional but recommended, it is already implemented in the boardView)
        if (task.getDescription().isEmpty()) {
            throw new Exception("Task description is required");
        }

        // 2. Ensure we are updating the "Real" project in the database
        Project storedProject = data.getProjects().stream()
                .filter(p -> p.getCode().equals(project.getCode()))
                .findFirst()
                .orElseThrow(() -> new Exception("Project not found"));

        // 3. Add the task
        storedProject.addTask(task);
    }

    public void modifyTaskPriorityAndStatus(Task task, Priority priority, Status status) throws Exception {
        // 1. Null validations
        if (task == null) throw new Exception("Task cannot be null");
        if (priority == null) throw new Exception("Priority cannot be null");
        if (status == null) throw new Exception("Status cannot be null");

        // 2. Find the project that contains this task
        Project projectContainingTask = data.getProjects().stream()
                .filter(p -> p.getTaskList().stream()
                        .anyMatch(t -> t.getNumber().equals(task.getNumber())))
                .findFirst()
                .orElseThrow(() -> new Exception("Task not found in any project"));

        // 3. Get the real stored task
        Task storedTask = projectContainingTask.getTaskList().stream()
                .filter(t -> t.getNumber().equals(task.getNumber()))
                .findFirst()
                .orElseThrow(() -> new Exception("Task not found"));

        // 4. Check if values are actually changing
        if (storedTask.getPriority() == priority && storedTask.getStatus() == status) {
            throw new Exception("Priority and status are already set to these values");
        }

        // 5. Update
        storedTask.setPriority(priority);
        storedTask.setStatus(status);
    }
}