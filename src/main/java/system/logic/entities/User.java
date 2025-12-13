package system.logic.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;
import system.logic.utilities.IdGenerator;

@XmlAccessorType(XmlAccessType.FIELD)
public class User {
    @XmlID
    private String id;

    private String name;
    private String email;

    public User() {
        // Initialization for JAXB unmarshalling
        this.id = null;
        this.name = null;
        this.email = null;
    }

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