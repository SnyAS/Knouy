/**
 * 
 */
package view;

import application.Main;
import javafx.stage.Stage;

/**
 * @author sandreicha
 *
 */
public interface IDialogController {
    void setMainApp(Main mainApp);
    void setDialogStage(Stage dialogStage);
    boolean isAddClicked();
}

