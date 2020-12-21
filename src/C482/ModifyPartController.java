package C482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    @FXML
    public Label machineIDCompNameLabel;
    private final int partID = MainFormController.modId;




    private void isInHousePart(int id) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                if (part instanceof InHouse) {
                    modPartInHouseRadio.setSelected(true);
                }else{
                    modPartOutsourcedRadio.setSelected(true);
                    machineIDCompNameLabel.setText("Company Name");

                }
            }

        }
    }

    private void setNameField(int id) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                modPartNameInput.setText(part.getName());
            }
        }
    }

    private void setInvLvlField(int id) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                modPartInvInput.setText(String.valueOf(part.getStock()));
            }
        }
    }

    private void setPriceField(int id) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                modPartCostInput.setText(String.valueOf(part.getPrice()));
            }
        }
    }
    private void setMaxField(int id) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                modPartMaxInput.setText(String.valueOf(part.getMax()));
            }
        }
    }
    private void setMinField(int id) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                modPartMinInput.setText(String.valueOf(part.getMin()));

            }
        }
    }

    private void setMachineIdField(int id) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                if(part instanceof InHouse) {
                    modPartMachineIDInput.setText(String.valueOf(((InHouse) part).getMachineID()));
                }else{
                    modPartMachineIDInput.setText(String.valueOf(((Outsourced) part).getCompanyName()));

                }
            }
        }
    }




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

    public void initialize(){
        isInHousePart(partID);
        modifyPartIDInput.setText(String.valueOf(partID));
        setNameField(partID);
        setInvLvlField(partID);
        setPriceField(partID);
        setMaxField(partID);
        setMinField(partID);
        setMachineIdField(partID);

    }
}
