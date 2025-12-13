package system.logic.entities.utilities;

public enum Status {
    OPEN("Open"),
    IN_PROGRESS("In Progress"),
    UNDER_REVIEW("Under Review"),
    COMPLETED("Completed");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}