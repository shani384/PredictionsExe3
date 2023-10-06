package admin.component.mainapp;

import admin.component.body.management.ManagementController;
import admin.component.header.HeaderController;
import admin.http.util.Constants;
import admin.http.util.HttpClientUtil;
import admin.utils.error.AlertToScreen;
import com.sun.istack.internal.NotNull;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static admin.http.util.HttpClientUtil.HTTP_CLIENT;

public class AppMainController {

    private GridPane loginComponent;

    @FXML
    private GridPane dynamicGridPane;

    @FXML
    private Parent headerComponent;
    @FXML
    private HeaderController headerComponentController;


    @FXML
    private BorderPane BorderPaneMain;
    private final StringProperty currentUserName;
    @FXML
    private ManagementController managementController;

    private boolean shutDown = false;

    public AppMainController() {
        currentUserName = new SimpleStringProperty("");
        logInToServer();
    }

    private void logInToServer() {
        String finalUrl = HttpUrl
                .parse(Constants.LOGIN_ADMIN_URL)
                .newBuilder()
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        try {
            // Execute the request synchronously
            Response response = HTTP_CLIENT.newCall(request).execute();

            // Check if the request was successful (HTTP status code 200)
            if (!response.isSuccessful()) {
                shutDown = true;
                AlertToScreen.showErrorDialog(response.body().string());
            }
            // Close the response body
            response.close();

        } catch (Exception e) {// Handle exceptions (e.g., network errors)
            AlertToScreen.showErrorDialog(e);
            shutDown = true;
        }
    }


    @FXML
    public void initialize() {
        if (headerComponentController != null) {
            headerComponentController.setMainController(this);
        }
        if (shutDown) {
            Platform.exit();
        }
    }

    public void updateUserName(String userName) {
        currentUserName.set(userName);
    }


    public void onManagementChosen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Constants.MANAGEMENT_VIEW_URL));
            GridPane detailsBox = loader.load();
            managementController = loader.getController();
            managementController.setMainController(this);
            managementController.loadDataFromServer();
            managementController.getThreadInfo();
            dynamicGridPane.getChildren().clear();
            dynamicGridPane.getChildren().add(detailsBox);
        } catch (IOException e) {
            throw new RuntimeException("fuck");
        }
    }

    public void readWorld() throws IOException {
        String filePath = managementController.getFilePath();
        File f = new File(filePath);

        RequestBody body =
                new MultipartBody.Builder()
                        .addFormDataPart("file1", f.getName(), RequestBody.create(f, MediaType.parse("text/plain")))
                        //.addFormDataPart("key1", "value1") // you can add multiple, different parts as needed
                        .build();

        Request request = new Request.Builder()
                .url(Constants.FILE_UPLOAD_URL)
                .post(body)
                .build();

        Call call = HTTP_CLIENT.newCall(request);

        Response response = call.execute();

        System.out.println(response.body().string());
    }
}
