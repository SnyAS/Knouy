package view;

import application.Main;
import controller.Engine;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.Website;
import util.TitleExtractor;

public class KnouyHomeController {
    @FXML
    private MenuItem addWebsite;
    @FXML
    private MenuItem close;
    @FXML
    private TableColumn<TitleExtractor, Website> displayWebsites;
    @FXML
    private TableView<?> positiveTable;
    @FXML                //score  word
    private TableColumn<Engine, Engine> totalWordPos;
    @FXML
    private TableColumn<Engine, Engine> repeatedWordPos;
    @FXML
    private TableView<?> negativeTable;
    @FXML
    private TableColumn<Engine, Engine> totalWordNeg;
    @FXML
    private TableColumn<Engine, Engine> repeatedWordNeg;
    @FXML
    private TableView<?> commonPhrases;
    @FXML
    private TableColumn<?, ?> totalPhrase;
    @FXML
    private TableColumn<?, ?> repeatedPhrase;
    @FXML
    private TextFlow websiteTitle;

    private Main mainApp;

    final static String positive = "Positive";
    final static String negative = "Negative";

    private Engine engine;

    public void setMainApp(Main main) {
        this.mainApp = mainApp;
    }

    public void start(Stage stage) {
        stage.setTitle("Knouy Home");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc =
                new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Website Outcome");
        xAxis.setLabel("Emotion");
        yAxis.setLabel("Value");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Positive");
        series1.getData().add(new XYChart.Data(positive, engine.getWebsitePositiveWord()));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Negative");
        series2.getData().add(new XYChart.Data(negative, engine.getWebsiteNegativeWord()));

        Scene scene = new Scene(bc, 800, 600);
        bc.getData().addAll(series1, series2);
        stage.setScene(scene);
        stage.show();
    }

    public void getWebsiteTitle(InputMethodEvent inputMethodEvent) {

    }
}
