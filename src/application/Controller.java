package application;

import javafx.fxml.FXML;
import javafx.scene.control.ToolBar;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class Controller {
    @FXML TextFlow textFlowStop1, textFlowStop2, textFlowStop3, textFlowStop4;
    @FXML Text headingStop1, headingStop2, headingStop3, headingStop4;
    @FXML ToolBar toolBar;

    @FXML
    public void initialize() {
        System.out.println("test");
    }

}
