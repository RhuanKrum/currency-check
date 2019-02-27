package com.currencycheck;

import com.currencycheck.model.Currency;
import com.currencycheck.util.CurrencyCode;
import com.currencycheck.util.MessageFormatter;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CurrencyBox extends HBox {

    private final Stage stage;
    private final Pane parent;
    private final CurrencyCode fromCurrency;
    private final CurrencyCode toCurrency;

    private HBox hBoxSpacer;
    private Label fromCurrencyLabel;
    private Label toCurrencyLabel;
    private Label formattedValueLabel;
    private Label toLabel;
    private Label equalsLabel;
    private Button removeBoxButton;
    private boolean alive;

    public CurrencyBox(Pane parent, Stage stage, CurrencyCode fromCurrency, CurrencyCode toCurrency){
        this.parent = parent;
        this.stage = stage;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;

        this.alive = true;

        initializeButton();
        initializeLabels();
        initializeHBoxSpacer();
        initializeBox();
    }

    private void initializeHBoxSpacer(){
        hBoxSpacer = new HBox();
        HBox.setHgrow(hBoxSpacer, Priority.ALWAYS);
    }

    private void initializeButton() {
        removeBoxButton = new Button("-");
        removeBoxButton.setTooltip(new Tooltip("Remove"));
        removeBoxButton.setOnAction(e -> {
            alive = false;

            // Sets scale transition to shrink the pane when the "-" button is clicked, just before removing it
            ScaleTransition transition = new ScaleTransition();
            transition.setFromX(1);
            transition.setFromY(1);
            transition.setToX(.5);
            transition.setToY(0);
            transition.setDuration(Duration.millis(500));
            transition.setNode(this);
            transition.setOnFinished(event -> {
                parent.getChildren().remove(this);

                // Resize the stage after removing the CurrencyBox
                parent.autosize();
                stage.sizeToScene();
            });

            transition.play();
        });
    }

    private void initializeBox(){
        this.getChildren().add(fromCurrencyLabel);
        this.getChildren().add(toLabel);
        this.getChildren().add(toCurrencyLabel);
        this.getChildren().add(equalsLabel);
        this.getChildren().add(formattedValueLabel);
        this.getChildren().add(hBoxSpacer);
        this.getChildren().add(removeBoxButton);

        this.getStyleClass().add("currency-box");

        this.layout();
        this.applyCss();
    }

    private void initializeLabels(){
        CurrencyCheckUpdater currencyCheckUpdater = new CurrencyCheckUpdater(fromCurrency, toCurrency);

        StringProperty fromCurrencyProperty = new SimpleStringProperty();
        StringProperty toCurrencyProperty = new SimpleStringProperty();
        StringProperty formattedValueProperty = new SimpleStringProperty();

        new Thread(() -> {
            long secondsToWait = 60 * 1000;

            while(alive) {
                currencyCheckUpdater.run();

                Platform.runLater(() -> {
                    Currency currency = currencyCheckUpdater.getCurrency();

                    fromCurrencyProperty.setValue(MessageFormatter.format("{} ({})", currency.getFromCurrencyName(), currency.getFromCurrencyCode()));
                    toCurrencyProperty.setValue(MessageFormatter.format("{} ({})", currency.getToCurrencyName(), currency.getToCurrencyCode()));
                    formattedValueProperty.setValue(currency.getExchangeRateFormatted());
                });

                try {
                    Thread.sleep(secondsToWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        toLabel = new Label("to");
        toLabel.getStyleClass().add("to-label");

        equalsLabel = new Label("=");
        toLabel.getStyleClass().add("equals-label");

        fromCurrencyLabel = new Label();
        fromCurrencyLabel.getStyleClass().add("from-currency-label");
        fromCurrencyLabel.textProperty().bind(fromCurrencyProperty);

        toCurrencyLabel = new Label();
        toCurrencyLabel.getStyleClass().add("to-currency-label");
        toCurrencyLabel.textProperty().bind(toCurrencyProperty);

        formattedValueLabel = new Label();
        formattedValueLabel.getStyleClass().add("formatted-value-label");
        formattedValueLabel.textProperty().bind(formattedValueProperty);
    }
}
