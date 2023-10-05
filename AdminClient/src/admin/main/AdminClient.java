package admin.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import admin.http.util.Constants;
import admin.http.util.HttpClientUtil;

import java.io.IOException;
import java.net.URL;

public class AdminClient extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(600);
        primaryStage.setTitle("Predictions Admin Client");

        URL url = getClass().getResource(Constants.MAIN_PAGE_FXML_RESOURCE_LOCATION);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(url);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root,750, 550);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        HttpClientUtil.shutdown();
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
