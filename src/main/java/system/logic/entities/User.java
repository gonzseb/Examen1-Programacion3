package system.logic.entities;

import system.logic.utilities.IdGenerator;

public class User {
    private final String id;
    private final String name;
    private final String email;

    public User(String name, String email) {
        this.id = IdGenerator.newUserId();
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }

    public String getId() { return id; }

    @Override
    public String toString() { // For the ComboBox
        return getName() + " (" + getId() + ")";
    }
}