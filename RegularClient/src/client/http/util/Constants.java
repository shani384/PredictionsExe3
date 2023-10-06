package client.http.util;

import DTOManager.impl.actionDTO.ActionDTO;
import DTOManager.impl.actionDTO.ActionDTODeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Constants {
    // global constants
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static String JHON_DOE = "<Anonymous>";
    public final static int REFRESH_RATE = 2000;
    public final static String CHAT_LINE_FORMATTING = "%tH:%tM:%tS | %.10s: %s%n";

    // fxml locations
    public static final String MAIN_PAGE_FXML_RESOURCE_LOCATION = "/client/component/mainapp/mainPanelView.fxml";
    public static final String LOGIN_PAGE_FXML_RESOURCE_LOCATION = "/client/component/login/loginPageView.fxml";

    public static final String Property_Details_FXML_RESOURCE = "/client/component/body/simulationDetails/component/property/propertyDetailsView.fxml";
    public static final String Entity_Details_FXML_RESOURCE = "/client/component/body/simulationDetails/component/entity/entityDetailsView.fxml";
    public static final String Rule_Details_FXML_RESOURCE = "/client/component/body/simulationDetails/component/rule/ruleDetailsView.fxml";
    public static final String IncreaseDecrease_Details_FXML_RESOURCE = "/client/component/body/simulationDetails/component/action/increasedecrease/increaseDecreaseDetailsView.fxml";
    public static final String Calculation_Details_FXML_RESOURCE = "/client/component/body/simulationDetails/component/action/calculation/calculationDetailsView.fxml";
    public static final String SetKill_Details_FXML_RESOURCE = "/client/component/body/simulationDetails/component/action/setkill/setKillDetailsView.fxml";
    public static final String Condition_Details_FXML_RESOURCE = "/client/component/body/simulationDetails/component/action/condition/conditionDetailsView.fxml";
    public static final String ProximityReplace_Details_FXML_RESOURCE = "/client/component/body/simulationDetails/component/action/proximityreplace/proximityReplaceDetailsView.fxml";
    public final static String CHAT_ROOM_FXML_RESOURCE_LOCATION = "/client/component/body/simulationDetails/component/chatroom/chat-room-main.fxml";

    // Server resources locations
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/Server_Web_exploded";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;

    public final static String LOGIN_PAGE = FULL_SERVER_PATH + "/login";
    public final static String SIMULATION_DETAILS = FULL_SERVER_PATH + "/simulations/details";
    public final static String LOGOUT = FULL_SERVER_PATH + "/chat/logout";
    public final static String SEND_CHAT_LINE = FULL_SERVER_PATH + "/pages/chatroom/sendChat";
    public final static String CHAT_LINES_LIST = FULL_SERVER_PATH + "/chat";

    // GSON instance
    public final static Gson GSON_INSTANCE = new GsonBuilder()
            .registerTypeAdapter(ActionDTO.class,new ActionDTODeserializer())
            .create();
}
