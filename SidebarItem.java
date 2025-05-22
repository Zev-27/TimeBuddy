import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class SidebarItem {
    private final HBox view;
    private final Label text;
    private final Label more;
    private final Label star;
    private boolean favorited = false;

    public SidebarItem(String name, boolean isStaticItem) {
        view = new HBox();
        view.setPadding(new Insets(5));
        view.setSpacing(10);
        view.setAlignment(Pos.CENTER_LEFT);
        view.setStyle("-fx-background-color: #e080aa; -fx-background-radius: 15;");

        text = new Label(name);
        more = new Label("⋯");
        more.setVisible(false);
        more.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
        more.setCursor(Cursor.HAND);

        star = new Label("☆");
        star.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        star.setCursor(Cursor.HAND);
        star.setVisible(!isStaticItem);
        star.setOnMouseClicked(e -> toggleFavorite());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem rename = new MenuItem("Rename");
        MenuItem delete = new MenuItem("Delete");
        MenuItem separator = new MenuItem("──────────");
        separator.setDisable(true);
        contextMenu.getItems().addAll(rename, separator, delete);

        more.setOnMouseClicked(e -> {
            contextMenu.show(more, e.getScreenX(), e.getScreenY());
        });

        view.setOnMouseEntered(e -> {
            view.setStyle("-fx-background-color: #ffb6c1; -fx-background-radius: 15;");
            more.setVisible(true);
        });

        view.setOnMouseExited(e -> {
            view.setStyle("-fx-background-color: #e080aa; -fx-background-radius: 15;");
            more.setVisible(false);
        });

        view.getChildren().addAll(star, text, spacer, more);
    }

    private void toggleFavorite() {
        favorited = !favorited;
        star.setText(favorited ? "★" : "☆");
        // Notify sidebar or persistence system here if needed
    }

    public boolean isFavorited() {
        return favorited;
    }

    public String getTitle() {
        return text.getText();
    }

    public HBox getView() {
        return view;
    }
}