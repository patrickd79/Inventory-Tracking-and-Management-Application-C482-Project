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
    String companyName;
    private int partID = MainFormController.partModId;
    public boolean inHouse(int id) {
        for (Part part : Inventory.getAllParts()) {
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
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == id) {
                nameInput.setText(part.getName());
            }
        }
    }
    private void setInvLvlField(int id) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == id) {
                inventoryInput.setText(String.valueOf(part.getStock()));
            }
        }
    }
    private void setPriceField(int id) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == id) {
                priceInput.setText(String.valueOf(part.getPrice()));
            }
        }
    }
    private void setMaxField(int id) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == id) {
                maxInput.setText(String.valueOf(part.getMax()));
            }
        }
    }
    private void setMinField(int id) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == id) {
                minInput.setText(String.valueOf(part.getMin()));

            }
        }
    }
    private void setMachineIdField(int id) {
        for (Part part : Inventory.getAllParts()) {
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
    public int findPartIndex(int id) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == id) {
                index = Inventory.getAllParts().indexOf(part);

            }
        }
        return index;
    }
    public boolean nameValid() {
        if (name.length() >= 2) {
            return true;
        }else{
            styleClass(nameInput).add("error");
            DataValidation.invalidNameAlert("Part name");
        }
        return false;
    }
    public boolean priceValid(){
        if(price > 0){
            return true;
        }else{
            styleClass(priceInput).add("error");
            DataValidation.invalidNumberAlert("Price value");
        }
        return false;
    }
    public boolean inventoryValid(){
        if(stockNum > 0){
            return true;
        }else{
            styleClass(inventoryInput).add("error");
            DataValidation.invalidNumberAlert("Inventory value");
        }
        return false;
    }
    public boolean machineIDValid(){
        if(machineID > 0){
            return true;
        }else{
            styleClass(machineIDInput).add("error");
            DataValidation.invalidNumberAlert("Machine ID value");
        }
        return false;
    }
    public boolean companyNameValid() {
        if (name.length() >= 2) {
            return true;
        }else{
            styleClass(machineIDInput).add("error");
            DataValidation.invalidNameAlert("Company name");
        }
        return false;
    }
    public boolean minLessThanMax(){
        min = Integer.parseInt(minInput.getText().trim());
        max = Integer.parseInt(maxInput.getText().trim());
        if(min < max){
            return true;
        }else{
            styleClass(minInput).add("error");
            styleClass(maxInput).add("error");
            DataValidation.maxLessThanMin();
            return false;
        }
    }
    public boolean invBetweenMinMax(){
        min = Integer.parseInt(minInput.getText().trim());
        max = Integer.parseInt(maxInput.getText().trim());
        stockNum = Integer.parseInt(inventoryInput.getText().trim());
        if(stockNum < max && stockNum > min){
            return true;
        }else{
            styleClass(inventoryInput).add("error");
            DataValidation.invNotBetweenMinMaxAlert();
            return false;
        }
    }
    private ObservableList<String> styleClass(TextField field){
        return field.getStyleClass();
    }
    private void removeAllErrorFlags(){
        styleClass(nameInput).removeAll("error");
        styleClass(inventoryInput).removeAll("error");
        styleClass(priceInput).removeAll("error");
        styleClass(minInput).removeAll("error");
        styleClass(maxInput).removeAll("error");
        styleClass(machineIDInput).removeAll("error");
    }
    public void saveChangedPart(ActionEvent event) throws IOException {
        removeAllErrorFlags();
        if (inHouseRadio.isSelected()) {
            name = nameInput.getText().trim();
            try{
                stockNum = Integer.parseInt(inventoryInput.getText().trim());
            }catch (NumberFormatException e) {
                styleClass(inventoryInput).add("error");
                DataValidation.invalidNumberAlert("Inventory value");
            }
            try {
                price = Double.parseDouble(priceInput.getText().trim());
            }catch (NumberFormatException e){
                styleClass(priceInput).add("error");
                DataValidation.invalidNumberAlert("Price value");
            }
            try {
                min = Integer.parseInt(minInput.getText().trim());
            }catch (NumberFormatException e){
                styleClass(minInput).add("error");
                DataValidation.invalidNumberAlert("Minimum Inventory value");
            }
            try {
                max = Integer.parseInt(maxInput.getText().trim());
            }catch (NumberFormatException e){
                styleClass(maxInput).add("error");
                DataValidation.invalidNumberAlert("Maximum Inventory value");
            }
            try {
                machineID = Integer.parseInt(machineIDInput.getText().trim());
            }catch (NumberFormatException e){
                styleClass(machineIDInput).add("error");
                DataValidation.invalidNumberAlert("Machine ID value");
            }
                if (nameValid() && minLessThanMax() &&
                        invBetweenMinMax() && priceValid()
                && inventoryValid() && machineIDValid()) {
                    Part part = new InHouse(partID, name, price, stockNum, min, max, machineID);
                    Inventory.updatePart(findPartIndex(partID), part);
                    openMainForm(event);
                }
        } else {
                name = nameInput.getText().trim();
                try{
                    stockNum = Integer.parseInt(inventoryInput.getText().trim());
                }catch (NumberFormatException e) {
                    styleClass(inventoryInput).add("error");
                    DataValidation.invalidNumberAlert("Inventory value");
                }
                try {
                    price = Double.parseDouble(priceInput.getText().trim());
                }catch (NumberFormatException e){
                    styleClass(priceInput).add("error");
                    DataValidation.invalidNumberAlert("Price value");
                }
                try {
                    min = Integer.parseInt(minInput.getText().trim());
                }catch (NumberFormatException e){
                    styleClass(minInput).add("error");
                    DataValidation.invalidNumberAlert("Minimum Inventory value");
                }
                try {
                    max = Integer.parseInt(maxInput.getText().trim());
                }catch (NumberFormatException e){
                    styleClass(maxInput).add("error");
                    DataValidation.invalidNumberAlert("Maximum Inventory value");
                }
                try {
                    companyName = machineIDInput.getText().trim();
                }catch (NumberFormatException e){
                    styleClass(machineIDInput).add("error");
                    DataValidation.invalidNumberAlert("Company name");
                }
                if (nameValid() && minLessThanMax() &&
                        invBetweenMinMax() && companyNameValid()
                && priceValid() && inventoryValid()) {
                    Part part = new Outsourced(partID, name, price, stockNum, min, max, companyName);
                    Inventory.updatePart(findPartIndex(partID), part);
                    openMainForm(event);
                }
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
