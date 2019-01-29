package com.currencycheck;

import com.currencycheck.updater.CurrencyCheckUpdater;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage){
        CurrencyCheckUpdater currencyCheckUpdater = new CurrencyCheckUpdater();

        StringProperty property = new SimpleStringProperty();

        new Thread(() -> {
            long secondsToWait = 60 * 1000;

            while(true) {
                currencyCheckUpdater.run();

                Platform.runLater(() -> property.setValue(currencyCheckUpdater.getCurrency().toString()));

                try {
                    Thread.sleep(secondsToWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Label currencyValueLabel = new Label();
        currencyValueLabel.setText(property.getValue());
        currencyValueLabel.textProperty().bind(property);
        currencyValueLabel.setTextFill(Color.WHITE);

        VBox pane = new VBox();

        HBox hbox = new HBox();
        hbox.getChildren().add(currencyValueLabel);
        hbox.setStyle(
                "-fx-padding: 10;" +
                "-fx-background-color: #2f4f4f;"
        );

        pane.getChildren().add(hbox);

        double preferredWidth = 300 > pane.getPrefWidth() ? 300 : pane.getPrefWidth();
        Scene scene = new Scene(new StackPane(pane), preferredWidth, pane.getPrefHeight());

        stage.setTitle("Currency Check");
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.show();
    }

}
