package C482;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AddPartController<event> {

    @FXML
    private TextField nameInput;
    @FXML
    private TextField inventoryInput;
    @FXML
    private TextField priceInput;
    @FXML
    private TextField maxInput;
    @FXML
    private TextField minInput;
    @FXML
    private TextField machineIDInput;
    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private Label machineIDCompNameLabel;
    public int partID = 0;
    private String partName = "";
    public int stockNum;
    public double partPrice;
    public int partMin;
    public int partMax;
    public int machineID;
    public String companyName = null;
    private boolean partCreated = false;

    public int highestPartId(){
        int largestID = 0;
        for (Part part: Inventory.getAllParts()){
            if(part.getId() > largestID){
                largestID = part.getId();
            }
        }
        return largestID;
    }

    private int partIDGenerator() {
        if(Inventory.getAllParts() == null){
            partID = 0;
        }else{
            partID = (highestPartId() +1);
        }
        return partID;
    }

    public void openMainForm(ActionEvent event) throws IOException {
        Parent mainWindow = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
        Scene mainScene = new Scene(mainWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }

    public void cancelBtn(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to leave this screen and lose all entered data?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            openMainForm(event);
        }
    }

    public boolean nameValid() {

        ObservableList<String> styleClass = nameInput.getStyleClass();
        if(partName.length() >= 2) {
            styleClass.removeAll("error");
            return true;
        }else{
            styleClass.add("error");
            DataValidation.invalidStringAlert("name");
            return false;
        }
    }

    public boolean companyNameValid() {

        ObservableList<String> CompStyleClass = machineIDInput.getStyleClass();
        if(companyName.length() >= 2) {
            CompStyleClass.removeAll("error");
            return true;
        }else{
            CompStyleClass.add("error");
            DataValidation.invalidStringAlert("Company name");
            return false;
        }
    }

    public boolean minLessThanMax(){
        partMin = Integer.parseInt(minInput.getText().trim());
        partMax = Integer.parseInt(maxInput.getText().trim());
        ObservableList<String> minStyleClass = minInput.getStyleClass();
        ObservableList<String> maxStyleClass = maxInput.getStyleClass();
        if(partMin < partMax){
            minStyleClass.removeAll("error");
            maxStyleClass.removeAll("error");
            return true;
        }else{
            minStyleClass.add("error");
            maxStyleClass.add("error");
            DataValidation.maxLessThanMin();
            return false;
        }
    }

    public boolean invBetweenMinMax(){
        partMin = Integer.parseInt(minInput.getText().trim());
        partMax = Integer.parseInt(maxInput.getText().trim());
        stockNum = Integer.parseInt(inventoryInput.getText().trim());
        ObservableList<String> invStyleClass = inventoryInput.getStyleClass();
        if(stockNum < partMax && stockNum > partMin){
            invStyleClass.removeAll("error");
            return true;
        }else{
            invStyleClass.add("error");
            DataValidation.invNotBetweenMinMaxAlert();
            return false;
        }
    }

    public void addInHousePart(){
        try {
            partName = nameInput.getText().trim();
            stockNum = Integer.parseInt(inventoryInput.getText().trim());
            partPrice = Double.parseDouble(priceInput.getText().trim());
            partMin = Integer.parseInt(minInput.getText().trim());
            partMax = Integer.parseInt(maxInput.getText().trim());
            machineID = Integer.parseInt(machineIDInput.getText().trim());
                if (nameValid() && minLessThanMax() && invBetweenMinMax()) {
                    Inventory.addPart(new InHouse(partIDGenerator(), partName, partPrice, stockNum, partMin, partMax, machineID));
                    partCreated = true;
                } else {
                    partCreated = false;
                }
        }catch(NumberFormatException e){
                DataValidation.invalidDataAlert();
                partCreated = false;
            }
        }


    public void addOutsourcedPart() {


            try {
                partName = nameInput.getText().trim();
                stockNum = Integer.parseInt(inventoryInput.getText().trim());
                partPrice = Double.parseDouble(priceInput.getText().trim());
                partMin = Integer.parseInt(minInput.getText().trim());
                partMax = Integer.parseInt(maxInput.getText().trim());
                companyName = (machineIDInput.getText().trim());
                    if(nameValid() && minLessThanMax() && invBetweenMinMax() && companyNameValid()) {
                        Inventory.addPart(new Outsourced(partIDGenerator(), partName, partPrice, stockNum, partMin, partMax, companyName));
                        partCreated = true;
                    }else{
                        partCreated = false;
                    }
            }catch (NumberFormatException e) {
                DataValidation.invalidDataAlert();
                partCreated = false;
            }

    }
    
    public void inHouseOrOutsourced(){
        if(!inHouseRadio.isSelected()){
            machineIDInput.clear();
            machineIDCompNameLabel.setText("Company Name");
            machineIDInput.setPromptText("Name of Supplier");
        }else{
            machineIDInput.clear();
            machineIDCompNameLabel.setText("Machine ID");
        }
    }

    public void savePart(ActionEvent event) throws IOException{
            if (inHouseRadio.isSelected()) {
                addInHousePart();
            } else {
                addOutsourcedPart();
            }
        if(partCreated) {
            openMainForm(event);
        }
    }

    public void initialize(){


    }



}
