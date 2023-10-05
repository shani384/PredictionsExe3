package client.component.mainapp;

import client.component.body.simulationDetails.SimulationDetailsController;
import client.component.header.HeaderController;
import client.component.login.LoginPageController;
import client.util.Constants;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;

public class AppMainController {

    private GridPane loginComponent;

    @FXML private GridPane dynamicGridPane;
    private LoginPageController logicController;

    @FXML private Parent headerComponent;
    @FXML
    private HeaderController headerComponentController;
    private SimulationDetailsController simulationDetailsController;


   @FXML private BorderPane BorderPaneMain;
    private final StringProperty currentUserName;
    private static final String Details_FXML_RESOURCE = "/client/component/body/simulationDetails/simulationDetailsView.fxml";

    public AppMainController() {
        currentUserName = new SimpleStringProperty("");
    }

    @FXML
    public void initialize() {
        if (headerComponentController != null) {
            headerComponentController.setMainController(this);
        }
        headerComponentController.nameLabel.textProperty().bind(currentUserName);
    }

    public void updateUserName(String userName) {
        currentUserName.set(userName);
    }

    private void setMainPanelTo(Parent pane) {
        dynamicGridPane.getChildren().clear();
        dynamicGridPane.getChildren().add(pane);
        AnchorPane.setBottomAnchor(pane, 1.0);
        AnchorPane.setTopAnchor(pane, 1.0);
        AnchorPane.setLeftAnchor(pane, 1.0);
        AnchorPane.setRightAnchor(pane, 1.0);
    }

    //@Override
    public void close() throws IOException {

    }

    private void loadLoginPage() {
        URL loginPageUrl = getClass().getResource(Constants.LOGIN_PAGE_FXML_RESOURCE_LOCATION);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            loginComponent = fxmlLoader.load();
            logicController = fxmlLoader.getController();
            logicController.setAppMainController(this);
            setMainPanelTo(loginComponent);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void switchToLogin() {
        Platform.runLater(() -> {
            currentUserName.set("");
            //chatRoomComponentController.setInActive();
            setMainPanelTo(loginComponent);
        });
    }

    public void switchToMainView() {
    }
    public void onDetailsChosen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Details_FXML_RESOURCE));
            GridPane detailsBox = loader.load();
            simulationDetailsController = loader.getController();
            simulationDetailsController.requestWorldsDTO();
            simulationDetailsController.worldsMenu();
            dynamicGridPane.getChildren().clear();
            dynamicGridPane.getChildren().add(detailsBox);
        } catch (IOException e) {
            ////////////////////////////////////
        }
    }
}
