import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class TaskListPage {
    private final VBox view;

    public TaskListPage(Page page) {
        view = new VBox(10);
        view.setPadding(new Insets(20));
        view.setStyle("-fx-background-color:#4B408D;");

        Label title = new Label();
        title.textProperty().bind(page.titleProperty());
        title.setStyle("-fx-text-fill:white;-fx-font-size:20px;");
        
        // Load tasks from JSON file (same for all pages, extend later)
        List<Task> loaded = StorageManager.loadTasks();
        ObservableList<Task> data = FXCollections.observableArrayList(loaded);
        FilteredList<Task> filtered = new FilteredList<>(data, t -> true);

        TextField search = new TextField();
        search.setPromptText("Search tasks...");
        search.textProperty().addListener((o,old,n)-> {
            filtered.setPredicate(task ->
                task.getName().toLowerCase().contains(n.toLowerCase()));
        });

        TableView<Task> table = new TableView<>(filtered);
        // Name column
        TableColumn<Task,String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(c -> c.getValue().nameProperty());
        // Deadline column
        TableColumn<Task,String> dateCol = new TableColumn<>("Deadline");
        dateCol.setCellValueFactory(c -> c.getValue().deadlineProperty());
        // Status column
        TableColumn<Task,String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(c -> c.getValue().statusProperty());
        // Time tracked column
        TableColumn<Task,String> timeCol = new TableColumn<>("Time Tracked");
        timeCol.setCellValueFactory(c -> c.getValue().timeSpentProperty());

        table.getColumns().addAll(nameCol, dateCol, statusCol, timeCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        // Add task button
        Button add = new Button("+ Add Task");
        add.setOnAction(e -> {
            Task t = new Task("New Task","yyyy-mm-dd","To Do");
            data.add(t);
            StorageManager.saveTasks(data);
        });

        view.getChildren().addAll(title, search, table, add);
    }

    public VBox getView() {
        return view;
    }
}
