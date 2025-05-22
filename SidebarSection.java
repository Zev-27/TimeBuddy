import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SidebarSection {
    private final VBox container;
    private final VBox itemsBox;
    private boolean expanded = true;

    public SidebarSection(String title) {
        container = new VBox();
        container.setSpacing(5);

        Label header = new Label(title);
        header.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-cursor: hand;");
        header.setOnMouseClicked(e -> toggle());

        itemsBox = new VBox();
        itemsBox.setSpacing(5);

        container.getChildren().addAll(header, itemsBox);
    }

    public void addItem(SidebarItem item) {
        itemsBox.getChildren().add(item.getView());
    }

    public VBox getView() {
        return container;
    }

    private void toggle() {
        if (expanded) {
            container.getChildren().remove(itemsBox);
        } else {
            container.getChildren().add(itemsBox);
        }
        expanded = !expanded;
    }
}
