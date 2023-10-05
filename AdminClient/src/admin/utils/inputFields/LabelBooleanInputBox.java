package admin.utils.inputFields;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class LabelBooleanInputBox extends HBox {
    private Label label;
    private CheckBox checkBox;
    private BooleanProperty valueProperty;

    public LabelBooleanInputBox(String labelText, boolean initialValue) {
        // Create label
        label = new Label(labelText);

        // Create CheckBox with specified initial value
        checkBox = new CheckBox();
        checkBox.setSelected(initialValue);

        // Add label and CheckBox to the HBox
        getChildren().addAll(label, checkBox);

        // Set spacing and other styling as needed
        setSpacing(10); // Adjust spacing between label and CheckBox
    }

    public BooleanProperty valueProperty() {
        return checkBox.selectedProperty();
    }

    public void setValue(boolean value) {
        checkBox.setSelected(value);
    }

    public boolean getValue() {
        return checkBox.isSelected();
    }

    public String getLabelText() {
        return label.getText();
    }
}

