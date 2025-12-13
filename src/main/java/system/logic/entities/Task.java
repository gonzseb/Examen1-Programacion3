package system.logic.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import system.logic.entities.utilities.LocalDateAdapter;
import system.logic.utilities.Priority;
import system.logic.utilities.Status;
import system.logic.utilities.IdGenerator;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class Task {
    @XmlID
    private String number;

    private String description;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate spectedEndDate;

    private Priority priority; // Can change
    private Status status; // Can change

    @XmlIDREF
    private User personInCharge;

    public Task() {
        this.number = null;
        this.description = null;
        this.spectedEndDate = null;
        this.priority = null;
        this.status = null;
        this.personInCharge = null;
    }

    public Task(String description, LocalDate spectedEndDate, Priority priority, Status status, User personInCharge) {
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

    public LocalDate getSpectedEndDate() {
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