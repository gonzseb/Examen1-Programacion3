package system.logic;

import system.data.Data;
import system.data.XmlPersister;
import system.logic.entities.Project;
import system.logic.entities.Task;
import system.logic.entities.User;

import java.time.LocalDate;
import java.util.List;

import system.logic.entities.utilities.Priority;
import system.logic.entities.utilities.Status;

public class Service {
    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    private Data data;

    private Service() {
        try{
            data= XmlPersister.instance().load();
        }
        catch(Exception e){
            data =  new Data();
        }
    }

    public void stop() {
        try {
            XmlPersister.instance().store(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // --- Project Operations (CRUD) ---

    // Create (C)
    public void createProject(String description, User user) throws Exception {
        boolean exists = data.getProjects().stream()
                .anyMatch(i -> i.getDescription().equals(description));

        if (exists) {
            throw new Exception("Project already exists");
        }

        data.getProjects().add(new Project(description, user));
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

    public void addTaskToProject(Project project,
                                 String description,
                                 LocalDate date,
                                 Priority priority,
                                 Status status,
                                 User inCharge) throws Exception {

        // 1. Validación de parámetros básicos
        if (project == null || project.getCode() == null || project.getCode().isEmpty()) {
            throw new Exception("A valid project must be selected");
        }

        if (description == null || description.isEmpty()) {
            throw new Exception("Task description is required");
        }

        if (date == null) {
            throw new Exception("Expected end date is required");
        }

        if (priority == null) {
            throw new Exception("Priority is required");
        }

        if (status == null) {
            throw new Exception("Status is required");
        }

        if (inCharge == null) {
            throw new Exception("A person in charge must be selected");
        }

        // 2. Buscar el proyecto real (almacenado)
        Project storedProject = data.getProjects().stream()
                .filter(p -> p.getCode().equals(project.getCode()))
                .findFirst()
                .orElseThrow(() -> new Exception("Project does not exist"));

        // 3. Validar que la descripción no exista en este proyecto
        boolean exists = storedProject.getTaskList().stream()
                .anyMatch(t -> t.getDescription().equals(description));

        if (exists) {
            throw new Exception("A task with this description already exists in the selected project");
        }

        // 4. Crear la entidad Tarea (EL NEW VA AQUÍ, en SERVICE / CONTROLLER)
        Task newTask = new Task(description, date, priority, status, inCharge);

        // 5. Guardar en el proyecto real
        storedProject.addTask(newTask);
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