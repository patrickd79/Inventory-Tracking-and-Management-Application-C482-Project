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
    private TextField modPartMaxInput;
    @FXML
    private TextField modPartMinInput;
    @FXML
    private Label machineIDCompNameLabel;

    private final int partID = MainFormController.partModId;

    public boolean inHouse(int id){
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                if (part instanceof InHouse) {
                    return true;
                }
            }
        }
        return false;
    }

    private void isInHousePart(int id) {
        if (inHouse(id)) {
            modPartInHouseRadio.setSelected(true);
        } else {
            modPartOutsourcedRadio.setSelected(true);
            machineIDCompNameLabel.setText("Company Name");

        }
    }

    @FXML
    private void changeTypeOfPart(ActionEvent event){
        if(modPartInHouseRadio.isSelected()){
            machineIDCompNameLabel.setText("Machine ID");
        }else{
            machineIDCompNameLabel.setText("Company Name");
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

    public Part getChangedPart(){
        Part part;
        if(modPartInHouseRadio.isSelected()) {
            String partName = modPartNameInput.getText().trim();
            int stockNum = Integer.parseInt(modPartInvInput.getText().trim());
            double partPrice = Double.parseDouble(modPartCostInput.getText().trim());
            int partMin = Integer.parseInt(modPartMinInput.getText().trim());
            int partMax = Integer.parseInt(modPartMaxInput.getText().trim());
            int machineID = Integer.parseInt(modPartMachineIDInput.getText().trim());
            part = new InHouse(partID, partName, partPrice,stockNum,partMin, partMax, machineID);

        }else{
            String partName = modPartNameInput.getText().trim();
            int stockNum = Integer.parseInt(modPartInvInput.getText().trim());
            double partPrice = Double.parseDouble(modPartCostInput.getText().trim());
            int partMin = Integer.parseInt(modPartMinInput.getText().trim());
            int partMax = Integer.parseInt(modPartMaxInput.getText().trim());
            String companyName = modPartMachineIDInput.getText().trim();
            part = new Outsourced(partID, partName, partPrice,stockNum,partMin, partMax, companyName);

        }
        return part;
    }

    public int findPartIndex(int id){
        int index = -1;
        for(Part part: Inventory.getAllParts()) {
            if (part.getId() == id) {
                index = Inventory.getAllParts().indexOf(part);

            }
        }
        return index;
    }

    public void saveModifiedPart(ActionEvent event) throws IOException {
        Inventory.updatePart(findPartIndex(partID), getChangedPart());
        openMainForm(event);
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
