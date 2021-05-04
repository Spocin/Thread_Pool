package zad1;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {

    private int buttonNumber = 1;

    public Button buttonStop;
    public Button buttonCreateNew;

    public ScrollPane scrollPane;

    public ListView<String> listView;

    public HBox buttonBox;

    private ExecutorService executor;

    public void initialize () {
        this.executor = Executors.newCachedThreadPool();

        buttonCreateNew.setOnAction( action -> {

            buttonBox.getChildren().add(new MyButton(buttonNumber,buttonBox,executor,listView));
            buttonNumber++;
        });

        buttonStop.setOnAction( action -> {

            executor.shutdownNow();

            buttonStop.setDisable(true);
            buttonCreateNew.setDisable(true);

            buttonBox.getChildren().forEach(e -> e.setDisable(true));
        });
    }

    public void shutdown() {
        executor.shutdownNow();
    }
}
