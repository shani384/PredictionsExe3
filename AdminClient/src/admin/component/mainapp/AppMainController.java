package admin.component.mainapp;

import admin.component.body.management.ManagementController;
import admin.component.header.HeaderController;
import admin.http.util.Constants;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    @FXML private ManagementController managementController;

    public AppMainController() {
        currentUserName = new SimpleStringProperty("");
    }

    @FXML
    public void initialize() {
        if (headerComponentController != null) {
            headerComponentController.setMainController(this);
        }
    }

    public void updateUserName(String userName) {
        currentUserName.set(userName);
    }



    //@Override
    public void close() throws IOException {

    }



//    private void loadChatRoomPage() {
//        URL loginPageUrl = getClass().getResource(CHAT_ROOM_FXML_RESOURCE_LOCATION);
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(loginPageUrl);
//            chatRoomComponent = fxmlLoader.load();
//            chatRoomComponentController = fxmlLoader.getController();
//            chatRoomComponentController.setChatAppMainController(this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public void updateHttpLine(String line) {
//        httpStatusComponentController.addHttpStatusLine(line);
//    }

//    public void switchToChatRoom() {
//        setMainPanelTo(chatRoomComponent);
//        chatRoomComponentController.setActive();
//    }



    public void switchToMainView() {

    }

    public void onManagementChosen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Constants.MANAGEMENT_VIEW_URL));
            GridPane detailsBox = loader.load();
            managementController = loader.getController();
            managementController.setMainController(this);
            managementController.loadDataFromServer();
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
