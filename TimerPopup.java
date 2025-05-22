import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TimerPopup {
    private long startTime;
    private long pausedTime;
    private boolean running;
    private AnimationTimer timer;
    private Label timerLabel;

    public void show(Stage parent, Runnable onTimeLogged) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(parent);
        popup.setTitle("Task Timer");

        timerLabel = new Label("00:00:00");
        timerLabel.setStyle("-fx-font-size: 24px;");

        Button startBtn = new Button("Start");
        Button stopBtn = new Button("Stop");
        Button resetBtn = new Button("Reset");

        startBtn.setOnAction(e -> start());
        stopBtn.setOnAction(e -> stop());
        resetBtn.setOnAction(e -> reset());

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(timerLabel, new HBox(10, startBtn, stopBtn, resetBtn));

        Scene scene = new Scene(root, 250, 150);
        popup.setScene(scene);
        popup.showAndWait();

        if (onTimeLogged != null) onTimeLogged.run();
    }

    private void start() {
        if (!running) {
            startTime = System.currentTimeMillis() - pausedTime;
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    long elapsed = System.currentTimeMillis() - startTime;
                    long hours = elapsed / (1000 * 60 * 60);
                    long minutes = (elapsed / (1000 * 60)) % 60;
                    long seconds = (elapsed / 1000) % 60;
                    timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                }
            };
            timer.start();
            running = true;
        }
    }

    private void stop() {
        if (running) {
            pausedTime = System.currentTimeMillis() - startTime;
            timer.stop();
            running = false;
        }
    }

    private void reset() {
        if (timer != null) timer.stop();
        pausedTime = 0;
        timerLabel.setText("00:00:00");
        running = false;
    }

    public String getCurrentTime() {
        return timerLabel.getText();
    }
}
