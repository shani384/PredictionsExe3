package client.component.login;

import client.http.util.Constants;
import client.http.util.HttpClientUtil;
import com.sun.istack.internal.NotNull;
import client.component.mainapp.AppMainController;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class LoginPageController {
    @FXML
    private Label LabelLoginPage;
    private Label LabelWelcome;
    @FXML
    private TextField userNameTextField;
    @FXML
    private Button ButtonLogin;
    @FXML
    private Label errorMessageLabel;

    private final StringProperty errorMessageProperty = new SimpleStringProperty();
    private AppMainController appMainController;

    @FXML
    public void initialize() {
        errorMessageLabel.textProperty().bind(errorMessageProperty);
//        HttpClientUtil.setCookieManagerLoggingFacility(line ->
//                Platform.runLater(() ->
//                        updateHttpStatusLine(line)));
    }

    @FXML
    private void loginButtonClicked(ActionEvent event) {

        String userName = userNameTextField.getText();
        if (userName.isEmpty()) {
            errorMessageProperty.set("User name is empty. You can't login with empty user name");
            return;
        }

        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(Constants.LOGIN_PAGE)
                .newBuilder()
                .addQueryParameter("username", userName)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        errorMessageProperty.set("Something went wrong: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                            errorMessageProperty.set("Something went wrong: " + responseBody)
                    );
                } else {
                    Platform.runLater(() -> {
                        openMainAppWindow(userName);
                    });
                }
            }
        });
    }

    private void openMainAppWindow(String userName) {
        try {
            // Load the main application FXML and controller
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource(Constants.MAIN_PAGE_FXML_RESOURCE_LOCATION));
            Parent mainRoot = mainLoader.load();
             this.appMainController = mainLoader.getController();
            this.appMainController.updateUserName(userName);
            // Create a new scene with the main application view
            Scene mainScene = new Scene(mainRoot);

            // Create a new stage for the main application
            Stage mainStage = new Stage();

            // Set the main scene in the new stage
            mainStage.setScene(mainScene);
            mainStage.setTitle("Main Application");
            mainStage.show();

            // Close the login window (current stage)
            Stage loginStage = (Stage) ButtonLogin.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions related to loading the main app scene
        }
    }

    public void setAppMainController(AppMainController chatAppMainController) {
        this.appMainController = chatAppMainController;
    }

}
