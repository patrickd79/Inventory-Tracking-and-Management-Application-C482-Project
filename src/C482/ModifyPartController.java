package C482;

import javafx.collections.ObservableList;
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
    private RadioButton inHouseRadio;
    @FXML
    private RadioButton outsourcedRadio;
    @FXML
    private TextField iDInput;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField inventoryInput;
    @FXML
    private TextField priceInput;
    @FXML
    private TextField machineIDInput;
    @FXML
    private TextField maxInput;
    @FXML
    private TextField minInput;
    @FXML
    private Label machineIDCompNameLabel;
    private String name;
    private int stockNum;
    private double price;
    private int min;
    private int max;
    private int machineID;
    private boolean partCreated = false;
    String companyName;
    private final int partID = MainFormController.partModId;

    public boolean inHouse(int id) {
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
            inHouseRadio.setSelected(true);
        } else {
            outsourcedRadio.setSelected(true);
            machineIDCompNameLabel.setText("Company Name");
            machineIDInput.setPromptText("Name of supplier");

        }
    }

    @FXML
    private void changeTypeOfPart(ActionEvent event) {
        if (inHouseRadio.isSelected()) {
            machineIDCompNameLabel.setText("Machine ID");
        } else {
            machineIDCompNameLabel.setText("Company Name");
        }
    }

    private void setNameField(int id) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                nameInput.setText(part.getName());
            }
        }
    }

    private void setInvLvlField(int id) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                inventoryInput.setText(String.valueOf(part.getStock()));
            }
        }
    }

    private void setPriceField(int id) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                priceInput.setText(String.valueOf(part.getPrice()));
            }
        }
    }

    private void setMaxField(int id) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                maxInput.setText(String.valueOf(part.getMax()));
            }
        }
    }

    private void setMinField(int id) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                minInput.setText(String.valueOf(part.getMin()));

            }
        }
    }

    private void setMachineIdField(int id) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                if (part instanceof InHouse) {
                    machineIDInput.setText(String.valueOf(((InHouse) part).getMachineID()));
                } else {
                    machineIDInput.setText(String.valueOf(((Outsourced) part).getCompanyName()));

                }
            }
        }
    }

    public void openMainForm(ActionEvent event) throws IOException {
        Parent mainWindow = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
        Scene mainScene = new Scene(mainWindow);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();

    }

    public boolean nameValid() {
        name = nameInput.getText().trim();
        ObservableList<String> styleClass = nameInput.getStyleClass();
        if (name.length() >= 2) {
            styleClass.removeAll("error");
            return true;
        } else {
            styleClass.add("error");
            DataValidation.invalidStringAlert("name");
            return false;
        }
    }

    public boolean companyNameValid() {
        companyName = machineIDInput.getText().trim();
        ObservableList<String> CompStyleClass = machineIDInput.getStyleClass();
        if (companyName.length() >= 2) {
            CompStyleClass.removeAll("error");
            return true;
        } else {
            CompStyleClass.add("error");
            DataValidation.invalidStringAlert("Company name");
            return false;
        }
    }

    public boolean minLessThanMax() {
        min = Integer.parseInt(minInput.getText().trim());
        max = Integer.parseInt(maxInput.getText().trim());
        ObservableList<String> minStyleClass = minInput.getStyleClass();
        ObservableList<String> maxStyleClass = maxInput.getStyleClass();
        if (min < max) {
            minStyleClass.removeAll("error");
            maxStyleClass.removeAll("error");
            return true;
        } else {
            minStyleClass.add("error");
            maxStyleClass.add("error");
            DataValidation.maxLessThanMin();
            return false;
        }
    }

    public boolean invBetweenMinMax() {
        min = Integer.parseInt(minInput.getText().trim());
        max = Integer.parseInt(maxInput.getText().trim());
        stockNum = Integer.parseInt(inventoryInput.getText().trim());
        ObservableList<String> invStyleClass = inventoryInput.getStyleClass();
        if (stockNum < max && stockNum > min) {
            invStyleClass.removeAll("error");
            return true;
        } else {
            invStyleClass.add("error");
            DataValidation.invNotBetweenMinMaxAlert();
            return false;
        }
    }


    public Part getChangedPart() {
        Part part = null;
        if (inHouseRadio.isSelected()) {
            if (nameValid() && minLessThanMax() && invBetweenMinMax()) {
                try {
                    name = nameInput.getText().trim();
                    stockNum = Integer.parseInt(inventoryInput.getText().trim());
                    price = Double.parseDouble(priceInput.getText().trim());
                    min = Integer.parseInt(minInput.getText().trim());
                    max = Integer.parseInt(maxInput.getText().trim());
                    machineID = Integer.parseInt(machineIDInput.getText().trim());
                    part = new InHouse(partID, name, price, stockNum, min, max, machineID);
                    partCreated = true;
                } catch (NumberFormatException e) {
                    DataValidation.invalidDataAlert();
                    partCreated = false;
                }
            }else {
                partCreated = false;
            }
        }else{
                if (nameValid() && minLessThanMax() && invBetweenMinMax() && companyNameValid()) {
                    try {
                        name = nameInput.getText().trim();
                        stockNum = Integer.parseInt(inventoryInput.getText().trim());
                        price = Double.parseDouble(priceInput.getText().trim());
                        min = Integer.parseInt(minInput.getText().trim());
                        max = Integer.parseInt(maxInput.getText().trim());
                        companyName = machineIDInput.getText().trim();
                        part = new Outsourced(partID, name, price, stockNum, min, max, companyName);
                        partCreated = true;
                    } catch (NumberFormatException e) {
                        DataValidation.invalidDataAlert();
                        partCreated = false;
                    }
                } else {
                    partCreated =false;
                }
                //return part;
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
        if(partCreated) {
            Inventory.updatePart(findPartIndex(partID), getChangedPart());
            openMainForm(event);
        }else{
            DataValidation.invalidDataAlert();
        }
    }

    public void initialize(){
        isInHousePart(partID);
        iDInput.setText(String.valueOf(partID));
        setNameField(partID);
        setInvLvlField(partID);
        setPriceField(partID);
        setMaxField(partID);
        setMinField(partID);
        setMachineIdField(partID);

    }


}
