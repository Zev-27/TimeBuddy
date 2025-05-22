import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TimerView {
    private final VBox view;

    public TimerView() {
        view = new VBox();
        view.setStyle("-fx-background-color: #4B408D;");
        view.setPadding(new Insets(20));
        view.setSpacing(10);
        view.setAlignment(Pos.TOP_LEFT);

        Label pageTitle = new Label("Page name");
        pageTitle.setTextFill(Color.LIGHTGRAY);
        pageTitle.setFont(new Font("Arial", 24));

        view.getChildren().addAll(pageTitle);
    }

    public VBox getView() {
        return view;
    }
}
