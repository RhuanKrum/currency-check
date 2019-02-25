package com.currencycheck;

import com.currencycheck.util.CurrencyCode;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CurrencyBox extends HBox {

    private final Stage stage;
    private final Pane parent;
    private final CurrencyCode fromCurrency;
    private final CurrencyCode toCurrency;

    private Label currencyValueLabel;
    private Button removeBoxButton;
    private boolean alive;

    public CurrencyBox(Pane parent, Stage stage, CurrencyCode fromCurrency, CurrencyCode toCurrency){
        this.parent = parent;
        this.stage = stage;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;

        this.alive = true;

        initializeButton();
        initializeLabel();
        initializeBox();
    }

    private void initializeButton() {
        removeBoxButton = new Button("-");
        removeBoxButton.setOnAction(e -> {
            alive = false;
            parent.getChildren().remove(this);

            parent.autosize();
            stage.sizeToScene();
        });
    }

    private void initializeBox(){
        this.getChildren().add(currencyValueLabel);
        this.getChildren().add(removeBoxButton);
        this.setStyle(
                "-fx-padding: 10;" +
                        "-fx-background-color: #2f4f4f;"
        );
        this.layout();
        this.applyCss();
    }

    private void initializeLabel(){
        CurrencyCheckUpdater currencyCheckUpdater = new CurrencyCheckUpdater(fromCurrency, toCurrency);
        StringProperty property = new SimpleStringProperty();

        new Thread(() -> {
            long secondsToWait = 60 * 1000;

            while(alive) {
                currencyCheckUpdater.run();

                Platform.runLater(() -> property.setValue(currencyCheckUpdater.getCurrency().toString()));

                try {
                    Thread.sleep(secondsToWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        currencyValueLabel = new Label();
        currencyValueLabel.setText(property.getValue());
        currencyValueLabel.textProperty().bind(property);
        currencyValueLabel.setTextFill(Color.WHITE);
    }

}
