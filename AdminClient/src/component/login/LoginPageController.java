package component.login;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.ws.util.xml.CDATA;
import component.mainapp.AppMainController;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import util.http.Constants;
import util.http.HttpClientUtil;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class LoginPageController {
    @FXML
    public Label LabelLoginPage;
    public Label LabelWelcome;
    @FXML
    public TextField userNameTextField;
    @FXML
    public Button ButtonLogin;
    @FXML
    public Label errorMessageLabel;
    //private ChatAppMainController chatAppMainController;
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
                        appMainController.updateUserName(userName);
//                        appMainController.switchToMainView();
                    });
                }
            }
        });
    }




    @FXML
        private void userNameKeyTyped(KeyEvent event) {
            errorMessageProperty.set("");
        }

        @FXML
        private void quitButtonClicked(ActionEvent e) {
            Platform.exit();
        }

//        private void updateHttpStatusLine(String data) {
//            AppMainController.updateHttpLine(data);
//        }

        public void setAppMainController(AppMainController chatAppMainController) {
            this.appMainController = chatAppMainController;
        }
    }
