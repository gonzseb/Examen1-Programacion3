package system.data;

import java.util.List;
import java.util.ArrayList;

import system.logic.entities.Project;
import system.logic.entities.User;

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
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<User> getUsers() {
        return users;
    }
}