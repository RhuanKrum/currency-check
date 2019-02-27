package com.currencycheck;

import com.currencycheck.util.CurrencyCode;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.StringConverter;

public class MainApp extends Application {

    private Stage stage;
    private VBox mainPane;

    double mousePositionRelativeToMainMenuX;
    double mousePositionRelativeToMainMenuY;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage_){
        stage = stage_;

        mainPane = new VBox();
        mainPane.getChildren().add(createMainMenu());
        mainPane.getChildren().add(createConversionMenu());

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    private HBox createMainMenu(){
        HBox mainMenu = new HBox();
        mainMenu.getStyleClass().add("main-menu");

        Label titleLabel = new Label("Currency Check");
        mainMenu.getChildren().add(titleLabel);

        HBox hBoxSpacer = new HBox();
        HBox.setHgrow(hBoxSpacer, Priority.ALWAYS);
        mainMenu.getChildren().add(hBoxSpacer);

        Button buttonExit = new Button("x");
        buttonExit.setTooltip(new Tooltip("Exit"));
        buttonExit.setOnAction(e -> {
            ScaleTransition transition = new ScaleTransition();
            transition.setFromX(1);
            transition.setFromY(1);
            transition.setToX(.5);
            transition.setToY(0);
            transition.setDuration(Duration.millis(300));
            transition.setNode(mainPane);
            transition.setOnFinished(event -> System.exit(0));

            transition.play();
        });
        mainMenu.getChildren().add(buttonExit);

        // Stores the relative position of the mouse on the mainMenu
        mainMenu.setOnMousePressed(event -> {
            mousePositionRelativeToMainMenuX = stage.getX() - event.getScreenX();
            mousePositionRelativeToMainMenuY = stage.getY() - event.getScreenY();
        });

        // Drags the mainMenu according to the mouse position
        mainMenu.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + mousePositionRelativeToMainMenuX);
            stage.setY(event.getScreenY() + mousePositionRelativeToMainMenuY);
            event.consume();
        });

        return mainMenu;
    }

    private HBox createConversionMenu() {
        HBox conversionMenu = new HBox();
        conversionMenu.getStyleClass().add("conversion-menu");

        Label convertLabel = new Label("Convert");
        convertLabel.setPadding(new Insets(0, 5, 0, 5));

        ComboBox comboBoxFromCurrency = createCurrencyComboBox();

        Label toLabel = new Label("To");
        toLabel.setPadding(new Insets(0, 5, 0, 5));

        ComboBox comboBoxToCurrency = createCurrencyComboBox();

        HBox hBoxSpacer = new HBox();
        HBox.setHgrow(hBoxSpacer, Priority.ALWAYS);

        Button buttonAddCurrencyBox = new Button();
        buttonAddCurrencyBox.setText("+");
        buttonAddCurrencyBox.setTooltip(new Tooltip("Add"));
        buttonAddCurrencyBox.setOnAction(e ->
            createCurrencyBox(
                    mainPane,
                    stage,
                    (CurrencyCode) comboBoxFromCurrency.getValue(),
                    (CurrencyCode) comboBoxToCurrency.getValue())
        );

        conversionMenu.getChildren().add(convertLabel);
        conversionMenu.getChildren().add(comboBoxFromCurrency);
        conversionMenu.getChildren().add(toLabel);
        conversionMenu.getChildren().add(comboBoxToCurrency);
        conversionMenu.getChildren().add(hBoxSpacer);
        conversionMenu.getChildren().add(buttonAddCurrencyBox);

        return conversionMenu;
    }

    private void createCurrencyBox(VBox pane, Stage stage, CurrencyCode fromCurrency, CurrencyCode toCurrency) {
        CurrencyBox currencyBox = new CurrencyBox(pane, stage, fromCurrency, toCurrency);
        currencyBox.sceneProperty().addListener((obs, oldScene, newScene) -> stage.sizeToScene());

        // Creates a scaling transition to scale up the content in the currency box when it is added
        ScaleTransition transition = new ScaleTransition();
        transition.setFromX(.5);
        transition.setFromY(0);
        transition.setToX(1);
        transition.setToY(1);
        transition.setDuration(Duration.millis(500));
        transition.setNode(currencyBox);

        transition.play();

        pane.getChildren().add(currencyBox);
    }

    private ComboBox createCurrencyComboBox(){
        ObservableList<CurrencyCode> currencyOptions = FXCollections.observableArrayList(CurrencyCode.values());

        ComboBox comboBox = new ComboBox(currencyOptions);

        // Uses the Enum name in the display, and the code behind, so it can be passed on to the service
        comboBox.setConverter(new StringConverter<CurrencyCode>(){

            @Override
            public String toString(CurrencyCode currencyCode) {
                return currencyCode.getName();
            }

            @Override
            public CurrencyCode fromString(String name) {
                return ((ObservableList<CurrencyCode>) comboBox.getItems()).stream()
                        .filter(a -> a.getName().equals(name))
                        .findFirst().orElse(null);
            }
        });

        comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> stage.sizeToScene());
        comboBox.getSelectionModel().selectFirst();
        return comboBox;
    }
}
