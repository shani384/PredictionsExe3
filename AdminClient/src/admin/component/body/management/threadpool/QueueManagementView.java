package admin.component.body.management.threadpool;

import DTOManager.impl.MyThreadInfo;
import admin.http.util.Constants;
import admin.http.util.HttpClientUtil;
import admin.utils.inputFields.LabelNumericInputBox;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import okhttp3.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class QueueManagementView {

    @FXML
    private Label ThreadPoolSizeLabel;
    @FXML
    private  HBox setHbox;

    @FXML
    private VBox VBContainer;

    @FXML
    private Label queueSizeLabel;

    @FXML
    private Label workingThreadsLabel;

    @FXML
    private Label finishedThreadsLabel;

    @FXML
    private ProgressBar progressBar; // Inject the ProgressBar

    private LabelNumericInputBox ThreadPoolSizeSpinner;

    Task<MyThreadInfo> myThreadInfoTask;

    @FXML
    public void initialize() {
        if(ThreadPoolSizeSpinner != null)
            setHbox.getChildren().add(ThreadPoolSizeSpinner);
    }

    public QueueManagementView(){
        ThreadPoolSizeSpinner = new LabelNumericInputBox("Set Thread Pool Size:", 0f,Integer.MAX_VALUE, 3);
    }


    public void updateThreadInfo() {
        myThreadInfoTask = new Task<MyThreadInfo>() {
            @Override
            protected MyThreadInfo call() {
                while (true) {
                    if (isCancelled()){
                        break;
                    }
                    MyThreadInfo threadInfo = getThreadInfoFromServer();
                    Platform.runLater(() -> {
                        queueSizeLabel.setText("Queue Size: " + threadInfo.getQueueSize());
                        workingThreadsLabel.setText("Working Threads: " + threadInfo.getWorkingThreads());
                        finishedThreadsLabel.setText("Finished Threads: " + threadInfo.getFinishedThread());
                        ThreadPoolSizeLabel.setText("Thread Pool Size: " + threadInfo.getThreadPoolSize());
                        // Update the progress bar based on your logic
                        double progress = calculateProgress(threadInfo); // Implement this method
                        progressBar.setProgress(progress);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        if (isCancelled()){
                            break;
                        }
                        throw new RuntimeException(e);
                    }
                }
                return new MyThreadInfo(0,0,0,0);
            }
        };
        Thread th = new Thread(myThreadInfoTask);
        th.setDaemon(true);
        th.start();
    }

    private MyThreadInfo getThreadInfoFromServer() {

        Request request = new Request.Builder()
                .url(Constants.GET_THREAD_INFO_URL)
                .addHeader("Accept", "application/json;charset=UTF-8")
                .build();

        try (Response response = HttpClientUtil.HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String json = responseBody.string();
                    Gson gson = new Gson();
                    return gson.fromJson(json, MyThreadInfo.class);
                }
            } else {
                // Handle the error case (e.g., server returned an error status)
                System.err.println("Server returned an error: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception gracefully
        }
        return null;
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


    public void onSetThreadsClicked(ActionEvent event) {
        int newThreadPoolSize = ThreadPoolSizeSpinner.valueProperty().intValue();


        // Create a JSON request body containing the new thread pool size
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String json = "{\"threadPoolSize\":" + newThreadPoolSize + "}";
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(Constants.UPDATE_THREADPOOL_SIZE_URL)
                .put(requestBody) // Use .post(requestBody) for a POST request
                .addHeader("Accept", "application/json;charset=UTF-8")
                .build();

        try (Response response = HttpClientUtil.HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // Handle a successful response, if needed
                System.out.println("Thread pool size updated successfully.");
            } else {
                // Handle the error case (e.g., server returned an error status)
                System.err.println("Server returned an error: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception gracefully
        }
    }



}
