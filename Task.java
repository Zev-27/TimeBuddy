import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {
    private final StringProperty name;
    private final StringProperty deadline;
    private final StringProperty status;
    private final StringProperty timeSpent;

    public Task(String name, String deadline, String status) {
        this.name = new SimpleStringProperty(name);
        this.deadline = new SimpleStringProperty(deadline);
        this.status = new SimpleStringProperty(status);
        this.timeSpent = new SimpleStringProperty("00:00:00");
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty deadlineProperty() {
        return deadline;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty timeSpentProperty() {
        return timeSpent;
    }

    public void setTimeSpent(String time) {
        this.timeSpent.set(time);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getName() {
        return name.get();
    }
}
