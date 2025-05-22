import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class NotePage {
    private final VBox view;

    public NotePage(Page page) {
        view = new VBox(10);
        view.setPadding(new Insets(20));
        view.setAlignment(Pos.TOP_LEFT);
        view.setStyle("-fx-background-color: #4B408D;");

        // Title field bound to page.title
        TextField titleField = new TextField();
        titleField.textProperty().bindBidirectional(page.titleProperty());
        titleField.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Content area bound to page.content
        TextArea contentArea = new TextArea();
        contentArea.textProperty().bindBidirectional(page.contentProperty());
        contentArea.setWrapText(true);
        contentArea.setPrefHeight(600);

        view.getChildren().addAll(titleField, contentArea);
    }

    public VBox getView() {
        return view;
    }
}
