package admin.utils.inputFields;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class LabelTextBox extends HBox {
    private Label label;
    private TextField textField;
    private SimpleStringProperty textProperty;

    public LabelTextBox(String labelText, String initialText) {
        // Create label
        label = new Label(labelText);

        // Create text input field (TextField)
        textField = new TextField(initialText);

        // Create a StringProperty for the text value
        textProperty = new SimpleStringProperty(initialText);

        textProperty.addListener((observable, oldValue, newValue) -> {
            textField.textProperty().set(newValue);
        });

        // Bind the text property of the TextField to the StringProperty
        textField.textProperty().bindBidirectional(textProperty);

        // Add label and textField to the HBox
        getChildren().addAll(label, textField);

        // Set spacing and other styling as needed
        setSpacing(10); // Adjust spacing between label and textField
    }

    public String getLabelText() {
        return label.getText();
    }
    public SimpleStringProperty textProperty() {
        return textProperty;
    }

    // You can add more methods to customize the behavior of this controller
}