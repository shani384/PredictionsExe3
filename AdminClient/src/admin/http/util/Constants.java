package admin.http.util;

import com.google.gson.Gson;

public class Constants {
    // global constants
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static int REFRESH_RATE = 2000;

    // fxml locations
    public static final String MAIN_PAGE_FXML_RESOURCE_LOCATION = "/admin/component/mainapp/mainPanelView.fxml";

    // Server resources locations
    public final static String BASE_DOMAIN = "localhost";
    public static final String MANAGEMENT_VIEW_URL ="/admin/component/body/management/managementView.fxml" ;
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/Server_Web_exploded";

    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;
    public final static String FILE_UPLOAD_URL = FULL_SERVER_PATH + "/admin/upload-file";
    public final static String LOGIN_ADMIN_URL = FULL_SERVER_PATH + "/admin/login";


    // GSON instance
    public final static Gson GSON_INSTANCE = new Gson();
}
