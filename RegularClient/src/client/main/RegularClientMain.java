package client.main;

import client.util.Constants;
import client.util.HttpClientUtil;
import client.component.mainapp.AppMainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class RegularClientMain extends Application {

    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage){
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("../component/login/loginPageView.fxml"));
        Parent loginRoot = null;
        try {
            loginRoot = loginLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create a scene with the login view
        Scene loginScene = new Scene(loginRoot);
        this.primaryStage = primaryStage;
        // Set the scene in the primary stage (login window)
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login Page");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        HttpClientUtil.shutdown();
        this.primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}