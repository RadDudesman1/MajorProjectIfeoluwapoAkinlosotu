package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UnitConverterFX extends Application {
    private UnitConverter<Double> converter;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Unit Converter");

        // Initialize your converter
        converter = new UnitConverter<>();
        converter.addUnit(new Foot());
        converter.addUnit(new Inch());
        converter.addUnit(new Meter());
        converter.addUnit(new Yard());

        // Create UI components
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Create and add UI components to the grid
        Label valueLabel = new Label("Value:");
        GridPane.setConstraints(valueLabel, 0, 0);

        TextField valueInput = new TextField();
        GridPane.setConstraints(valueInput, 1, 0);

        Label fromLabel = new Label("From Unit:");
        GridPane.setConstraints(fromLabel, 0, 1);

        ComboBox<String> fromUnitDropdown = new ComboBox<>();
        fromUnitDropdown.getItems().addAll("Foot", "Inch", "Meter", "Yard");
        fromUnitDropdown.setValue("Foot"); // Default selection
        GridPane.setConstraints(fromUnitDropdown, 1, 1);

        Label toLabel = new Label("To Unit:");
        GridPane.setConstraints(toLabel, 0, 2);

        ComboBox<String> toUnitDropdown = new ComboBox<>();
        toUnitDropdown.getItems().addAll("Foot", "Inch", "Meter", "Yard");
        toUnitDropdown.setValue("Inch"); // Default selection
        GridPane.setConstraints(toUnitDropdown, 1, 2);

        Button convertButton = new Button("Convert");
        GridPane.setConstraints(convertButton, 1, 3);

        TextArea resultTextArea = new TextArea();
        resultTextArea.setEditable(false);
        GridPane.setConstraints(resultTextArea, 1, 4);

        // Event handling for "Convert" button
        convertButton.setOnAction(e -> {
            try {
                double value = Double.parseDouble(valueInput.getText());
                String fromUnit = fromUnitDropdown.getValue();
                String toUnit = toUnitDropdown.getValue();
                Double result = converter.convert(value, fromUnit, toUnit);

                if (result != null) {
                    resultTextArea.setText(value + " " + fromUnit + " is equal to " + result + " " + toUnit);
                } else {
                    resultTextArea.setText("Invalid units or conversion");
                }
            } catch (NumberFormatException ex) {
                resultTextArea.setText("Invalid input. Please enter a valid number.");
            }
        });

        // Set up the UI components and scene
        grid.getChildren().addAll(valueLabel, valueInput, fromLabel, fromUnitDropdown, toLabel, toUnitDropdown, convertButton, resultTextArea);
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}