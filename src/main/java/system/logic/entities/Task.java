package system.logic.entities;

import system.logic.utilities.Priority;
import system.logic.utilities.Status;
import system.logic.utilities.IdGenerator;

public class Task {
    private final String number;
    private final String description;
    private final String spectedEndDate;
    private Priority priority; // Can change
    private Status status; // Can change
    private final User personInCharge;

    public Task() {
        this.number = "";
        this.description = "";
        this.spectedEndDate = "";
        this.priority = null;
        this.status = null;
        this.personInCharge = null;
    }

    public Task(String description, String spectedEndDate, Priority priority, Status status, User personInCharge) {
        this.number = IdGenerator.newTaskId();
        this.description = description;
        this.spectedEndDate = spectedEndDate;
        this.priority = priority;
        this.status = status;
        this.personInCharge = personInCharge;
    }

    public String getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public String getSpectedEndDate() {
        return spectedEndDate;
    }

    public Status getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    public User getPersonInCharge() {
        return personInCharge;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}