package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.fxml.FXMLLoader;
import model.WebsiteListWrapper;
import controller.Engine;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Website;
import util.BadConnectionException;
import util.ParseWebsite;
import util.TitleExtractor;
import util.WebsiteConfig;
import view.AddWebsiteController;
import view.KnouyHomeController;
import view.RootLayoutController;

public class Main extends Application { // implements Observer, ListChangeListener<Website> {
	/*
	 * www.trustpilot.com - <div class="review-body"> ... </div> www.flyertalk.com -
	 * <div id="post_message_13260597">�</div> www.tripadvisor.com - <div
	 * class="postBody"> <p> ...</p> </div>
	 */
	private Stage primaryStage;
	private AnchorPane homeLayout;
	private BorderPane rootLayout;
	private KnouyHomeController controller;
	private AddWebsiteController addWebsite;
	private static String url;// ="https://www.flyertalk.com/forum/online-travel-booking-bidding-agencies/1768632-vayama-worth-risk.html";

	final static String positive = "Positive";
	final static String negative = "Negative";

	private static Engine engine;

	private static WebsiteConfig websiteConfig = new WebsiteConfig();
	private Website websites;
	private static TitleExtractor a;
	private static URL b;

	private ObservableList<Website> websiteData = FXCollections.observableArrayList();

	 /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Website> getWebsiteData() {
        return websiteData;
    }

	
	public void saveWebsiteDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(WebsiteListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our person data.
			WebsiteListWrapper wrapper = new WebsiteListWrapper();
			wrapper.setWebsites(websiteData);

			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);

			// Save the file path to the registry.
			setWebsiteFilePath(file);
		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file:\n" + file.getPath());

			alert.showAndWait();
		}
	}
	
	   /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     * 
     * @return
     */
    public File getWebsiteFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * 
     * @param file the file or null to remove the path
     */
    public void setWebsiteFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("Website - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("Knouy");
        }
    }
    
    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     * 
     * @param file
     */
    public void loadWebsiteDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(WebsiteListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            WebsiteListWrapper wrapper = (WebsiteListWrapper) um.unmarshal(file);

            websiteData.clear();
            websiteData.addAll(wrapper.getWebsite());

            // Save the file path to the registry.
            setWebsiteFilePath(file);

        } catch (Exception e) { // catches ANY exception
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Could not load data");
        	alert.setContentText("Could not load data from file:\n" + file.getPath());
        	
        	alert.showAndWait();
        }
    }
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	
	
	// public Main() throws IOException, BadConnectionException {
	// // Add some sample data
	// if (url !=null){
	// websiteData.add(new Website(url));
	// }
	//
	// }
	//
	// /**
	// * Returns the data as an observable list of Persons.
	// * @return
	// */
	// public ObservableList<Website> getWebsiteData() {
	// return websiteData;
	// }
	//
//	 @Override
//	 public void start(Stage primaryStage) {
//	 this.primaryStage = primaryStage;
//	 this.primaryStage.setTitle("Knouy Home");
//
//	 // Set the application icon.
//	 // this.primaryStage.getIcons().add(new Image("file:resources/images/address_book_32.png"));
//
//	 initRootLayout();
//	 //showKnowyHome();
//	 }
//
//	 /**
//	 * Initializes the root layout and tries to load the last opened
//	 * file.
//	 */
//	 public void initRootLayout() {
//	 try {
//	 // Load root layout from fxml file.
//	 FXMLLoader loader = new FXMLLoader();
//	 loader.setLocation(Main.class
//	 .getResource("view/RootLayout.fxml"));
//	 rootLayout = (BorderPane) loader.load();
//
//	 // Show the scene containing the root layout.
//	 Scene scene = new Scene(rootLayout);
//	 primaryStage.setScene(scene);
//
//	 // Give the controller access to the main app.
//	 RootLayoutController controller = loader.getController();
//	 controller.setMainApp(this);
//
//	 primaryStage.show();
//	 } catch (IOException e) {
//	 e.printStackTrace();
//	 }
//
//	 // Try to load last opened person file.
//	 File file = getWebsiteFilePath();
//	 if (file != null) {
//	 loadWebsiteDataFromFile(file);
//	 }
//	 }
//
//	 /**
//	 * Shows the person overview inside the root layout.
//	 */
//	 public void showKnowyHome() {
//	 try {
//	 // Load person overview.
//	 FXMLLoader loader = new FXMLLoader();
//	 loader.setLocation(Main.class.getResource("view/KnouyHome.fxml"));
//	 AnchorPane personOverview = (AnchorPane) loader.load();
//
//	 // Set person overview into the center of root layout.
//	 rootLayout.setCenter(personOverview);
//
//	 // Give the controller access to the main app.
//	 KnouyHomeController controller = loader.getController();
//	 controller.setMainApp(this);
//
//	 } catch (IOException e) {
//	 e.printStackTrace();
//	 }
//	 }


	// public Main() {
	// // Add some sample data
	// for(Website website: websites) {
	// website.add(new Website(website.getTitle()));
	// }
	//
	// }
	//
	// /**
	// * Returns the data as an observable list of Website.
	// * @return
	// */
	// public ObservableList<Website> getWebsiteData() {
	// return website;
	// }
	//

	// private void addWebsite() {
	// try {
	// FXMLLoader loader = new FXMLLoader();
	// loader.setLocation(getClass().getResource("../view/AddWebsite.fxml"));
	//
	// homeLayout = loader.load();
	//
	// primaryStage.setScene(new Scene(homeLayout));
	// this.addWebsite = loader.getController();// <-
	//
	// this.primaryStage.setTitle("Knouy Add Website");
	// addWebsite.setMainApp(this);// <-
	// primaryStage.show();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }


	public static void main(String[] args) throws IOException {

		Scanner reader = new Scanner(System.in);
		// Ask for user input
		System.out.print("Please enter website url: \n");
		// Get user input
		url = reader.nextLine();
		reader.close();
		//url = "https://www.flyertalk.com/forum/online-travel-booking-bidding-agencies/1768632-vayama-worth-risk.html";
		// Convert user input to URL
		b = new URL(url);
		// Displaying the Title of the website
		a = new TitleExtractor();
		try {
			a.getPageTitle(b);
		} catch (IOException | BadConnectionException e) {
			e.printStackTrace();
		}
		// Make new object of the Website
		ParseWebsite m = new ParseWebsite();
		// Checking/validating the user input
		m.parsing(url, websiteConfig.getTheRegex(b.getHost()));
		// Making a new object of the engine with the parameter of the parsed website
		engine = new Engine(m);
		// Running the algorithm on the website
		engine.start();
		launch(args);
	 }



	@Override
	public void start(Stage stage) {
		stage.setTitle("Knouy Home");
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
		bc.setTitle("Website Outcome");
		xAxis.setLabel("Emotion");
		yAxis.setLabel("Value");

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Positive");
		String positive = "Positive";
		series1.getData().add(new XYChart.Data(positive, engine.getWebsitePositiveWord()));

		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Negative");
		String negative = "Negative";
		series2.getData().add(new XYChart.Data(negative, engine.getWebsiteNegativeWord()));

		Scene scene = new Scene(bc, 800, 600);
		bc.getData().addAll(series1, series2);
		stage.setScene(scene);
		stage.show();
	}

}
