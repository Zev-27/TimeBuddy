import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.UUID;

public class Page {
    private final String id;
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty content = new SimpleStringProperty();
    private final boolean isTaskList;

    public Page(String id, String title, String content, boolean isTaskList) {
        this.id = id;
        this.title.set(title);
        this.content.set(content);
        this.isTaskList = isTaskList;
    }
    public static Page newNote() {
        return new Page(UUID.randomUUID().toString(), "Untitled Note", "", false);
    }
    public static Page newTaskList() {
        return new Page(UUID.randomUUID().toString(), "New Task List", "", true);
    }

    public String getId() { return id; }
    public boolean isTaskList() { return isTaskList; }
    public StringProperty titleProperty() { return title; }
    public StringProperty contentProperty() { return content; }
}
