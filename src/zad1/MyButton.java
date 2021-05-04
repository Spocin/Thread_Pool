package zad1;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.concurrent.*;

public class MyButton extends Button {

    private int number;
    private final HBox containter;
    private ListView<String> listView;
    private boolean isCPressed;
    private Future<?> future;
    private boolean locked;
    private final Object o = new Object();

    public MyButton(int number, HBox containter, ExecutorService executor, ListView<String> listView) {

        this.number = number;
        this.containter = containter;
        this.listView = listView;
        this.locked = false;

        setText("T" + number);

        setMinHeight(50);
        setMinWidth(75);
        setWrapText(true);

        setOnKeyPressed(x -> {
            if (x.getCode() == KeyCode.C) {
                isCPressed = true;
            }
        });

        setOnKeyReleased(x -> {
            if (x.getCode() == KeyCode.C) {
                isCPressed = false;
            }
        });

        addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {

            if (isCPressed) {
                try {
                    future.cancel(true);
                } catch (NullPointerException ignored) {}
                //textArea.insertText(textArea.getLength(), "Thread " + number + ": Cancelled!\n");
                listView.getItems().add("Thread " + number + ": Cancelled!\n");
                setText("Thread " + number + ": Cancelled!");
                setDisable(true);
            }
        });

        setOnAction( action -> {

            if (getText().equals("T" + number)) {

                future = executor.submit(new MyFutureTask<>(task,listView));
                setText("Suspend " + "T" + number);

            } else if (getText().equals("Suspend " + "T" + number)) {

                locked = true;
                setText("Continue " + "T" + number);

            } else if (getText().equals("Continue " + "T" + number)) {

                synchronized (o) {
                    locked = false;
                    o.notify();
                }

                setText("Suspend " + "T" + number);
            }
        });
    }

    public void setDisableAndRemove () {

        setDisable(true);

        Runnable waitAndRemove = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> containter.getChildren().remove(this));
        };

        Thread removeThread = new Thread(waitAndRemove);
        removeThread.start();
    }

    Callable<String> task = () -> {
        Thread.currentThread().setName("Thread " + number);

        int sum = 0;

        while (sum <= number*100) {

            synchronized (o) {
                while (locked) {
                    o.wait();
                }
            }

            int tmp = ThreadLocalRandom.current().nextInt(50);

            sum += tmp;

            Thread.currentThread().setName("Thread " + number);
            int finalSum = sum;
            Platform.runLater(() -> listView.getItems().add("Thread " + number + " (limit = " + number*100 + "): " + tmp + ", sum = " + finalSum + "\n"));

            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(2000));
        }

        setDisableAndRemove();
        return "Done!";
    };
}
