import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class TopBar {
    private final HBox view;
    private final Button toggleBtn;

    public TopBar(Stage stage) {
        view = new HBox(10);
        view.setPadding(new Insets(5, 10, 5, 10));
        view.setAlignment(Pos.CENTER_LEFT);
        view.setStyle("-fx-background-color: black;");

        // Logo from project root
        String logoUri = new File("Logo.png").toURI().toString();
        ImageView logo = new ImageView(new Image(logoUri));
        logo.setFitHeight(24);
        logo.setPreserveRatio(true);

        // Toggle button (rides sidebar edge)
        toggleBtn = new Button("<<");
        toggleBtn.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-cursor: hand;"
        );
        toggleBtn.setOnAction(e -> {
            Window w = (Window) stage.getUserData();
            w.toggleSidebar();
            toggleBtn.setText(w.isSidebarVisible() ? "<<" : ">>");
        });

        // Spacer pushes controls right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Window controls
        Button min = new Button("-");
        Button max = new Button("☐");
        Button close = new Button("X");
        for (Button b : new Button[]{min, max, close}) {
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 12px; -fx-cursor: hand;");
        }
        min.setOnAction(e -> stage.setIconified(true));
        max.setOnAction(e -> stage.setFullScreen(!stage.isFullScreen()));
        close.setOnAction(e -> stage.close());

        view.getChildren().addAll(logo, toggleBtn, spacer, min, max, close);
    }

    public HBox getView() {
        return view;
    }
}
