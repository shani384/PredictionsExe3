package admin.utils.inputFields;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;

public class LabelNumericInputBox extends HBox {
    private Label label;
    private Spinner<Double> spinner;
    private DoubleProperty valueProperty;

    public LabelNumericInputBox(String labelText, double minValue, double maxValue, double initialValue) {
        // Create label
        label = new Label(labelText);

        // Create spinner with specified range and initial value
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(minValue, maxValue, initialValue);
        spinner = new Spinner<>(valueFactory);
        spinner.setEditable(false);
        // Bind the value property of the spinner to a SimpleDoubleProperty
        valueProperty = new SimpleDoubleProperty(initialValue);
        spinner.getValueFactory().valueProperty().addListener((ObservableValue<? extends Double> observable, Double oldValue, Double newValue) -> {
            valueProperty.set(newValue);
        });
        valueProperty.addListener((observable, oldValue, newValue) -> {
            spinner.getValueFactory().setValue(newValue.doubleValue());
        });

        // Add label and spinner to the HBox
        getChildren().addAll(label, spinner);

        // Set spacing and other styling as needed
        setSpacing(10); // Adjust spacing between label and spinner
    }
    public String getLabelText() {
        return label.getText();
    }
    public DoubleProperty valueProperty() {
        return valueProperty;
    }

    // You can add more methods to customize the behavior of this controller
}




