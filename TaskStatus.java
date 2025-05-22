public enum TaskStatus {
    TO_DO("To Do"),
    IN_PROGRESS("In Progress"),
    DONE("Done");

    private final String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static TaskStatus fromString(String value) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.displayName.equalsIgnoreCase(value)) {
                return status;
            }
        }
        return TO_DO;
    }
}
