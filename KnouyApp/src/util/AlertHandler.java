package util;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class AlertHandler {
	public static void showAlert(Alert.AlertType type, Window owner, String title, String header, String content) {
		Alert error = new Alert(type);
		error.initOwner(owner);
		error.setTitle(title);
		error.setHeaderText(header);
		error.setContentText(content);

		error.showAndWait();
	}
}
