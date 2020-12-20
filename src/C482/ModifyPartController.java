package C482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyPartController {
    @FXML
    private RadioButton modPartInHouseRadio;
    @FXML
    private RadioButton modPartOutsourcedRadio;
    @FXML
    private TextField modifyPartIDInput;
    @FXML
    private TextField modPartNameInput;
    @FXML
    private TextField modPartInvInput;
    @FXML
    private TextField modPartCostInput;
    @FXML
    private TextField modPartMachineIDInput;
    @FXML
    public TextField modPartMaxInput;
    @FXML
    public TextField modPartMinInput;

    public void openMainForm(ActionEvent event) throws IOException {
        Parent mainWindow = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
        Scene mainScene = new Scene(mainWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();

    }
    public boolean modifyPart(int id, Part changedPart){
        int index = -1;
        for(Part part: Inventory.getAllParts()){
            index++;
            if(part.getId() == id){
                Inventory.getAllParts().set(index, changedPart);
                return true;
            }
        }
        return false;
    }
}
