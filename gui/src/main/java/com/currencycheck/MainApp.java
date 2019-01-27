package com.currencycheck;

import com.currencycheck.factory.CurrencyCheckFactory;
import com.currencycheck.model.Currency;
import com.currencycheck.service.CurrencyCheckServiceI;
import com.currencycheck.util.CurrencyCode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage){
        CurrencyCheckFactory factory = new CurrencyCheckFactory();
        CurrencyCheckServiceI service = factory.getCurrencyCheckService();

        Currency currency = null;
        try{
            currency = service.getCurrencyExchangeRate(CurrencyCode.CAD, CurrencyCode.BRL);
        } catch (Exception e){
            System.out.println("Opa " + e.getMessage());
        }

        Label label = new Label();
        label.setText(
                currency.getFromCurrencyCode() + " - " + currency.getFromCurrencyName() + "\n" +
                currency.getToCurrencyCode() + " - " + currency.getToCurrencyName() + "\n" +
                currency.getExchangeRate()
        );

        Scene scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
}
