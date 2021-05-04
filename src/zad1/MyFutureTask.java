package zad1;

import javafx.scene.control.ListView;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MyFutureTask <V> extends FutureTask<V> {

    private final ListView<String> listView;

    public MyFutureTask(Callable<V> callable, ListView<String> listView) {
        super(callable);
        this.listView = listView;
    }

    @Override
    protected void done() {
        try {
            listView.getItems().add(Thread.currentThread().getName() + " " + get() + "\n");
        } catch (InterruptedException | ExecutionException ignored) {}
        super.done();
    }

}
