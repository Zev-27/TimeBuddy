import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.util.List;

public class HomePage {
    private final VBox view = new VBox(10);
    private final ObservableList<Page> pages;

    public HomePage(Window window) {
        view.setPadding(new Insets(20));
        view.setStyle("-fx-background-color:#4B408D;");

        // header + new-page button
        HBox header = new HBox(10);
        Label title = new Label("My Pages");
        title.setStyle("-fx-text-fill:white;-fx-font-size:20px;");
        Button add = new Button("+ New Page");
        header.getChildren().addAll(title, add);
        view.getChildren().add(header);

        // load persisted
        List<Page> loaded = PageStorageManager.load();
        pages = FXCollections.observableArrayList(loaded);
        ListView<Page> list = new ListView<>(pages);
        list.setCellFactory(lv -> new PageCell(window));
        VBox.setVgrow(list, Priority.ALWAYS);
        view.getChildren().add(list);

        // add new
        add.setOnAction(e -> {
            // choose template
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Note","Note","Task List");
            dialog.setTitle("Choose Template");
            dialog.setHeaderText("Select a page template");
            String choice = dialog.showAndWait().orElse("Note");
            Page p = choice.equals("Task List")? Page.newTaskList(): Page.newNote();
            pages.add(p);
            PageStorageManager.save(pages);
            window.openPage(p);
        });

        // double-click opens
        list.setOnMouseClicked(e -> {
            if (e.getButton()==MouseButton.PRIMARY && e.getClickCount()==2) {
                Page sel = list.getSelectionModel().getSelectedItem();
                if (sel!=null) window.openPage(sel);
            }
        });
    }

    public VBox getView() { return view; }

    // Custom cell with rename/delete/favorite
    private class PageCell extends ListCell<Page> {
        private final HBox box = new HBox(8);
        private final Label lbl = new Label();
        private final Label star = new Label();
        private final Window window;
        public PageCell(Window w) { this.window=w;
            star.setStyle("-fx-text-fill:white;");
            ContextMenu menu = new ContextMenu();
            MenuItem rename = new MenuItem("Rename");
            MenuItem delete = new MenuItem("Delete");
            menu.getItems().addAll(rename,new SeparatorMenuItem(),delete);

            rename.setOnAction(e->{
                TextInputDialog ti = new TextInputDialog(getItem().titleProperty().get());
                ti.setHeaderText("Enter new title");
                String s = ti.showAndWait().orElse(null);
                if (s!=null) {
                    getItem().titleProperty().set(s);
                    PageStorageManager.save(pages);
                    updateItem(getItem(),false);
                }
            });
            delete.setOnAction(e->{
                pages.remove(getItem());
                PageStorageManager.save(pages);
            });
            box.setOnContextMenuRequested(e-> menu.show(box,e.getScreenX(),e.getScreenY()));
            box.getChildren().addAll(star,lbl);
            box.setPadding(new Insets(5));
        }
        @Override protected void updateItem(Page p, boolean empty) {
            super.updateItem(p,empty);
            if (empty||p==null) { setGraphic(null); return; }
            lbl.setText(p.titleProperty().get());
            star.setText(p.isTaskList()? "★":"☆");
            setGraphic(box);
        }
    }
}
