package client.http.util;

import com.google.gson.Gson;

public class Constants {

    // global constants
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static int REFRESH_RATE = 2000;

    // fxml locations
    public static final String MAIN_PAGE_FXML_RESOURCE_LOCATION = "/client/component/mainapp/mainPanelView.fxml";
    public static final String LOGIN_PAGE_FXML_RESOURCE_LOCATION = "/client/component/login/loginPageView.fxml";

    // Server resources locations
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/Server_Web_exploded";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;
    public final static String LOGIN_PAGE = FULL_SERVER_PATH + "/login";

    // GSON instance
    public final static Gson GSON_INSTANCE = new Gson();
}
