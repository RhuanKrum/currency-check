package com.currencycheck;

import com.currencycheck.util.CurrencyCode;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    private Stage stageo;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage){
        stageo = stage;

        VBox pane = new VBox();

        HBox menu = new HBox();

        Label convertLabel = new Label("Convert");
        convertLabel.setPadding(new Insets(0, 5, 0, 5));

        ComboBox comboBoxFromCurrency = createCurrencyComboBox();
        comboBoxFromCurrency.setPadding(new Insets(0, 5, 0, 5));
        comboBoxFromCurrency.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                stageo.sizeToScene();
            }
        });

        Label toLabel = new Label("To");
        toLabel.setPadding(new Insets(0, 5, 0, 5));

        ComboBox comboBoxToCurrency = createCurrencyComboBox();
        comboBoxToCurrency.setPadding(new Insets(0, 5, 0, 5));
        comboBoxToCurrency.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                stageo.sizeToScene();
            }
        });

        Button button = new Button();
        button.setText("+");
        button.setOnAction(e -> {
            createCurrencyBox(pane, stage, (CurrencyCode) comboBoxFromCurrency.getValue(), (CurrencyCode) comboBoxToCurrency.getValue());
        });

        menu.setAlignment(Pos.BASELINE_LEFT);
        menu.setPadding(new Insets(5, 5, 5, 5));
        menu.getChildren().add(convertLabel);
        menu.getChildren().add(comboBoxFromCurrency);
        menu.getChildren().add(toLabel);
        menu.getChildren().add(comboBoxToCurrency);
        menu.getChildren().add(button);

        pane.getChildren().add(menu);

        Scene scene = new Scene(pane);

        stage.setTitle("Currency Check");
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    private void createCurrencyBox(VBox pane, Stage stage, CurrencyCode fromCurrency, CurrencyCode toCurrency) {
        CurrencyBox currencyBox = new CurrencyBox(pane, stage, fromCurrency, toCurrency);
        pane.getChildren().add(currencyBox);
        stage.sizeToScene();
    }

    private ComboBox createCurrencyComboBox(){

        ObservableList<CurrencyCode> options =
                FXCollections.observableArrayList(
                        CurrencyCode.values()
                );
        return new ComboBox(options);
    }
}
