package admin.component.body.management.threadpool;

import DTOManager.impl.MyThreadInfo;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class QueueManagementView {
    @FXML
    private Label queueSizeLabel;

    @FXML
    private Label workingThreadsLabel;

    @FXML
    private Label finishedThreadsLabel;

    @FXML
    private ProgressBar progressBar; // Inject the ProgressBar

    Task<MyThreadInfo> myThreadInfoTask;

    public void setThreadInfo(MyThreadInfo threadInfo) {
        myThreadInfoTask = new Task<MyThreadInfo>() {
            @Override
            protected MyThreadInfo call() {
                while (true) {
                    if (isCancelled()){
                        break;
                    }
                    Platform.runLater(() -> {

                        queueSizeLabel.setText("Queue Size: " + threadInfo.getQueueSize());
                        workingThreadsLabel.setText("Working Threads: " + threadInfo.getWorkingThreads());
                        finishedThreadsLabel.setText("Finished Threads: " + threadInfo.getFinishedThread());

                        // Update the progress bar based on your logic
                        double progress = calculateProgress(threadInfo); // Implement this method
                        progressBar.setProgress(progress);
                    });
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        if (isCancelled()){
                            break;
                        }
                        throw new RuntimeException(e);
                    }
                    // Run your simulation here
                }
                return new MyThreadInfo(0,0,0,0);
            }
        };
        Thread th = new Thread(myThreadInfoTask);
        th.setDaemon(true);
        th.start();
    }
    public void closeTask(){
        if(myThreadInfoTask != null) {
            myThreadInfoTask.cancel();
        }
    }

    // Implement this method to calculate the progress based on your logic
    private double calculateProgress(MyThreadInfo threadInfo) {
        return (double) threadInfo.getWorkingThreads() / threadInfo.getThreadPoolSize();
    }
}
