package admin.component.body.management.simulationDetails;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class SimulationDetailsController {

        @FXML
        private GridPane gridPane;

        @FXML
        private SplitPane splitPane;

        @FXML
        private TreeView<?> treeView;

        @FXML
        private FlowPane details;

        @FXML
        void showItem(MouseEvent event) {
            // Handle showing item details here
            // You can add logic to display details based on the selected tree item
        }

        // You can add additional methods and logic as needed


}
