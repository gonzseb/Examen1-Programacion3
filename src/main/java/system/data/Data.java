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

        // Preloaded users
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

        // Three projects
        Project project1 = new Project("Create a MVC System from Scratch in Java", user5);
        Project project2 = new Project("Design and Implement an Inverted Index in Java with no libraries", user1);
        Project project3 = new Project("Try Tech Gadgets and create content", user3);

        // --- PROJECT 1 TASKS ---
        Task task1 = new Task("Design MVC architecture pattern based on professor materials", LocalDate.of(2025, 12, 11), Priority.HIGH, Status.OPEN, user5);
        Task task2 = new Task("Define project package structure (model, view, controller, utilities)", LocalDate.of(2025, 12, 13), Priority.HIGH, Status.OPEN, user5);
        Task task3 = new Task("Implement base Model classes and data entities", LocalDate.of(2025, 12, 17), Priority.MEDIUM, Status.OPEN, user5);
        Task task4 = new Task("Create View components using Swing (panels, forms, tables)", LocalDate.of(2025, 12, 20), Priority.MEDIUM, Status.OPEN, user5);
        Task task5 = new Task("Implement Controller classes and event handling", LocalDate.of(2025, 12, 22), Priority.HIGH, Status.OPEN, user5);
        Task task6 = new Task("Integrate Model ↔ Controller ↔ View interactions", LocalDate.of(2025, 12, 26), Priority.HIGH, Status.OPEN, user5);
        Task task7 = new Task("Add input validation and application-wide error handling", LocalDate.of(2025, 12, 28), Priority.MEDIUM, Status.OPEN, user5);
        Task task8 = new Task("Create build script and runnable JAR packaging", LocalDate.of(2025, 12, 30), Priority.LOW, Status.OPEN, user5);

        // Add tasks to project 1
        project1.addTask(task1);
        project1.addTask(task2);
        project1.addTask(task3);
        project1.addTask(task4);
        project1.addTask(task5);
        project1.addTask(task6);
        project1.addTask(task7);
        project1.addTask(task8);


        // --- PROJECT 2 TASKS ---
        Task task9  = new Task("Define document format and text input specifications", LocalDate.of(2025, 12, 12), Priority.HIGH, Status.OPEN, user1);
        Task task10 = new Task("Implement manual tokenization and normalization", LocalDate.of(2025, 12, 15), Priority.HIGH, Status.OPEN, user1);
        Task task11 = new Task("Create data structures for dictionary and posting lists", LocalDate.of(2025, 12, 17), Priority.HIGH, Status.OPEN, user1);
        Task task12 = new Task("Implement index construction algorithm", LocalDate.of(2025, 12, 20), Priority.HIGH, Status.OPEN, user1);
        Task task13 = new Task("Design search functionality (AND, OR queries)", LocalDate.of(2025, 12, 22), Priority.MEDIUM, Status.OPEN, user1);
        Task task14 = new Task("Optimize memory usage and indexing performance", LocalDate.of(2025, 12, 27), Priority.MEDIUM, Status.OPEN, user1);
        Task task15 = new Task("Create simple CLI or GUI interface for search testing", LocalDate.of(2025, 12, 30), Priority.LOW, Status.OPEN, user1);

            // Add tasks to project 2
        project2.addTask(task9);
        project2.addTask(task10);
        project2.addTask(task11);
        project2.addTask(task12);
        project2.addTask(task13);
        project2.addTask(task14);
        project2.addTask(task15);


        // --- PROJECT 3 TASKS ---
        Task task16 = new Task("Research trending tech gadgets for review", LocalDate.of(2025, 12, 12), Priority.MEDIUM, Status.OPEN, user3);
        Task task17 = new Task("Plan content schedule for social platforms", LocalDate.of(2025, 12, 13), Priority.LOW, Status.OPEN, user3);
        Task task18 = new Task("Record unboxing and first-impression videos", LocalDate.of(2025, 12, 15), Priority.HIGH, Status.OPEN, user3);
        Task task19 = new Task("Write script for full gadget review", LocalDate.of(2025, 12, 17), Priority.MEDIUM, Status.OPEN, user3);
        Task task20 = new Task("Edit and finalize video content", LocalDate.of(2025, 12, 19), Priority.MEDIUM, Status.OPEN, user3);
        Task task21 = new Task("Publish and promote content on social platforms", LocalDate.of(2025, 12, 20), Priority.LOW, Status.OPEN, user3);
        Task task22 = new Task("Track performance metrics and audience engagement", LocalDate.of(2025, 12, 24), Priority.LOW, Status.OPEN, user3);

        // Add tasks to project 3
        project3.addTask(task16);
        project3.addTask(task17);
        project3.addTask(task18);
        project3.addTask(task19);
        project3.addTask(task20);
        project3.addTask(task21);
        project3.addTask(task22);


        // Add the projects with their tasks
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