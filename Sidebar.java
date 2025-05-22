import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Sidebar {
    private final VBox view;
    private boolean isVisible = true;

    public Sidebar() {
        view = new VBox(10);
        view.setPrefWidth(300);
        view.setPadding(new Insets(10));
        view.setStyle("-fx-background-color: #C0618C;");

        // Static items
        view.getChildren().addAll(
            new SidebarItem("🔍 Search", true).getView(),
            new SidebarItem("⌂ Home",  true).getView(),
            new SidebarSection("Favorites").getView(),
            new SidebarSection("Pages").getView()
        );
    }

    public VBox getView() {
        return view;
    }

    /** Slide the sidebar left / back into view. */
    public void toggle() {
        double w = view.getPrefWidth();
        TranslateTransition tt = new TranslateTransition(Duration.millis(300), view);
        if (isVisible) {
            tt.setFromX(0);
            tt.setToX(-w);
        } else {
            tt.setFromX(-w);
            tt.setToX(0);
        }
        tt.play();
        isVisible = !isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }
}
