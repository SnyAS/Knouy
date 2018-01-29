package view;

import java.io.IOException;
import java.net.MalformedURLException;

import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Website;
import util.AlertHandler;
import util.BadConnectionException;

public class AddWebsiteController implements IDialogController {

	@FXML
	private Button addURL;
	@FXML
	private Button cancel;
	@FXML
	private TextField urlEntered;
	private Stage dialogStage;
	private boolean addClicked = false;
	private Website newSite;
	private Main mainApp;

	@Override
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;

	}

	@Override
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;

	}

	@Override
	public boolean isAddClicked() {
		return addClicked;
	}
	
	 @FXML
	    public void handleAddURL(ActionEvent actionEvent) {
	        try {
	            newSite = new Website(urlEntered.getText());
	           // addClicked = mainApp.addSite(newSite);

	            dialogStage.close();
	        } catch (MalformedURLException e) {
	            AlertHandler.showAlert(Alert.AlertType.ERROR, dialogStage, "Error", "Malformed URL",
	                    "The URL is malformed:\n" + e.getMessage());
	        } catch (IOException e) {
	            AlertHandler.showAlert(Alert.AlertType.ERROR, dialogStage, "Error", "Connection Failed",
	                    "Connection to the website failed:\n" + e.getMessage());
	        } catch (BadConnectionException e) {
	            AlertHandler.showAlert(Alert.AlertType.ERROR, dialogStage, "Error", "Not a website",
	                    "URL didn't lead to an HTML page:\n" + e.getMessage());
	        }
	    }

	 public Website getNewSite() {
	        return newSite;
	    }

	@FXML
	public void handleCancel(ActionEvent actionEvent) throws Exception{
		//
		Platform.exit();
		dialogStage.close();
	}

}
