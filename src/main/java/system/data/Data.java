package system.data;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import system.logic.entities.Project;
import system.logic.entities.Task;
import system.logic.entities.User;
import system.logic.utilities.Priority;
import system.logic.utilities.Status;

public class Data {

    private List<Project> projects;
    private List<User> users;

    public Data() {
        projects = new ArrayList<>();
        users = new ArrayList<>();

        // --- USERS ---
        User user1 = new User("Sam Altman", "samaltman@gpt.com");
        User user2 = new User("Greg Brockman", "greg@gpt.com");
        User user3 = new User("Ilya Sutskever", "ilya@gpt.com");
        User user4 = new User("Andrej Karpathy", "karpathy@tesla.com");
        User user5 = new User("Sebastián González", "sbsgonzalez21@gmail.com");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);

        // --- PROJECTS ---
        Project project1 = new Project("Create MVC System in Java", user5);
        Project project2 = new Project("Inverted Index in Java", user1);
        Project project3 = new Project("Tech Gadget Content Creation", user3);

        // --- PROJECT 1 TASKS (MVC SYSTEM) ---
        project1.addTask(new Task(
                "Design MVC architecture",
                LocalDate.of(2025, 12, 11),
                Priority.HIGH,
                Status.OPEN,
                user5));

        project1.addTask(new Task(
                "Define package structure",
                LocalDate.of(2025, 12, 13),
                Priority.HIGH,
                Status.OPEN,
                user5));

        project1.addTask(new Task(
                "Implement model entities",
                LocalDate.of(2025, 12, 17),
                Priority.MEDIUM,
                Status.OPEN,
                user5));

        project1.addTask(new Task(
                "Create Swing views",
                LocalDate.of(2025, 12, 20),
                Priority.MEDIUM,
                Status.OPEN,
                user5));

        project1.addTask(new Task(
                "Implement controllers",
                LocalDate.of(2025, 12, 22),
                Priority.HIGH,
                Status.OPEN,
                user5));

        project1.addTask(new Task(
                "Integrate MVC layers",
                LocalDate.of(2025, 12, 26),
                Priority.HIGH,
                Status.OPEN,
                user5));

        project1.addTask(new Task(
                "Add validation and errors",
                LocalDate.of(2025, 12, 28),
                Priority.MEDIUM,
                Status.OPEN,
                user5));

        project1.addTask(new Task(
                "Build runnable JAR",
                LocalDate.of(2025, 12, 30),
                Priority.LOW,
                Status.OPEN,
                user5));

        // --- PROJECT 2 TASKS (INVERTED INDEX) ---
        project2.addTask(new Task(
                "Define document format",
                LocalDate.of(2025, 12, 12),
                Priority.HIGH,
                Status.OPEN,
                user1));

        project2.addTask(new Task(
                "Implement tokenization",
                LocalDate.of(2025, 12, 15),
                Priority.HIGH,
                Status.OPEN,
                user1));

        project2.addTask(new Task(
                "Create index structures",
                LocalDate.of(2025, 12, 17),
                Priority.HIGH,
                Status.OPEN,
                user1));

        project2.addTask(new Task(
                "Build indexing algorithm",
                LocalDate.of(2025, 12, 20),
                Priority.HIGH,
                Status.OPEN,
                user1));

        project2.addTask(new Task(
                "Implement search queries",
                LocalDate.of(2025, 12, 22),
                Priority.MEDIUM,
                Status.OPEN,
                user1));

        project2.addTask(new Task(
                "Optimize performance",
                LocalDate.of(2025, 12, 27),
                Priority.MEDIUM,
                Status.OPEN,
                user1));

        project2.addTask(new Task(
                "Create search interface",
                LocalDate.of(2025, 12, 30),
                Priority.LOW,
                Status.OPEN,
                user1));

        // --- PROJECT 3 TASKS (CONTENT CREATION) ---
        project3.addTask(new Task(
                "Research tech gadgets",
                LocalDate.of(2025, 12, 12),
                Priority.MEDIUM,
                Status.OPEN,
                user3));

        project3.addTask(new Task(
                "Plan content schedule",
                LocalDate.of(2025, 12, 13),
                Priority.LOW,
                Status.OPEN,
                user3));

        project3.addTask(new Task(
                "Record unboxing videos",
                LocalDate.of(2025, 12, 15),
                Priority.HIGH,
                Status.OPEN,
                user3));

        project3.addTask(new Task(
                "Write review script",
                LocalDate.of(2025, 12, 17),
                Priority.MEDIUM,
                Status.OPEN,
                user3));

        project3.addTask(new Task(
                "Edit videos",
                LocalDate.of(2025, 12, 19),
                Priority.MEDIUM,
                Status.OPEN,
                user3));

        project3.addTask(new Task(
                "Publish and promote",
                LocalDate.of(2025, 12, 20),
                Priority.LOW,
                Status.OPEN,
                user3));

        project3.addTask(new Task(
                "Analyze engagement",
                LocalDate.of(2025, 12, 24),
                Priority.LOW,
                Status.OPEN,
                user3));

        // --- ADD PROJECTS ---
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<User> getUsers() {
        return users;
    }
}