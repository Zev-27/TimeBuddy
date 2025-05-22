import java.io.File;

import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Window {
    private BorderPane root;
    private Sidebar   sidebar;
    private TopBar    topBar;
    private Button    toggleBtn;
    private HBox      mainArea;
    private StackPane centerOverlay;

    public Window() { }

    public void show(Stage stage) {
        // Top bar
        topBar = new TopBar(stage);
        stage.setUserData(this);
        root = new BorderPane();
        root.setTop(topBar.getView());

        // Sidebar
        sidebar = new Sidebar();

        // Initial content = HomePage
        Pane initial = new HomePage(this).getView();

        // HBox holding sidebar + content
        mainArea = new HBox(sidebar.getView(), initial);
        HBox.setHgrow(initial, Priority.ALWAYS);

        // Floating toggle button
        toggleBtn = new Button("<<");
        toggleBtn.setStyle(
            "-fx-background-color: #4B408D; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-cursor: hand;"
        );
        toggleBtn.setOnAction(e -> {
            sidebar.toggle();
            toggleBtn.setText(sidebar.isVisible() ? "<<" : ">>");
        });

        // StackPane to layer mainArea + toggle button
        centerOverlay = new StackPane(mainArea, toggleBtn);
        root.setCenter(centerOverlay);

        // Bind toggleBtn.translateX to sidebar’s right edge
        toggleBtn.translateXProperty().bind(
            Bindings.createDoubleBinding(() -> {
                double edge = sidebar.getView().getBoundsInParent().getMaxX();
                return edge - (toggleBtn.getWidth() / 2);
            },
            sidebar.getView().boundsInParentProperty(),
            toggleBtn.widthProperty())
        );
        StackPane.setAlignment(toggleBtn, Pos.TOP_LEFT);
        StackPane.setMargin(toggleBtn, new Insets(10, 0, 0, 0));

        // Scene + CSS
        Scene scene = new Scene(root, 1080, 720);
        String cssUri = new File("Styles.css").toURI().toString();
        scene.getStylesheets().add(cssUri);

        // Stage + icon
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle("TimeBuddy");
        String iconUri = new File("Logo.png").toURI().toString();
        stage.getIcons().add(new Image(iconUri));

        stage.show();
        stage.centerOnScreen();
    }

    /** Called by HomePage or SidebarItem to open a given Page */
    public void openPage(Page page) {
        Pane pageView;
        if (page.isTaskList()) {
            pageView = new TaskListPage(page).getView();
        } else {
            pageView = new NotePage(page).getView();
        }
        // Replace the content pane (index 1 of mainArea)
        mainArea.getChildren().set(1, pageView);
    }

    /** Called by TopBar toggle button or Sidebar */
    public void toggleSidebar() {
        sidebar.toggle();
    }

    /** Exposed for TopBar to update arrow text */
    public boolean isSidebarVisible() {
        return sidebar.isVisible();
    }
}
