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

public class AddPartController {

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
    private String name = "";
    public int stockNum;
    public double price;
    public int min;
    public int max;
    public int machineID;
    public String companyName = "";
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
    public void addInHousePart(){
        removeAllErrorFlags();
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
                if (nameValid() && minLessThanMax() && invBetweenMinMax() &&
                        priceValid() && inventoryValid() && machineIDValid()) {
                    Inventory.addPart(new InHouse(partIDGenerator(), name, price, stockNum, min, max, machineID));
                    partCreated = true;
                } else {
                    partCreated = false;
                }

        }
    public void addOutsourcedPart() {
        removeAllErrorFlags();
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
            DataValidation.invalidNameAlert("Company name");
            styleClass(machineIDInput).add("error");
        }
            if (nameValid() && minLessThanMax() && invBetweenMinMax() && priceValid()
                    && companyNameValid() && inventoryValid()) {
                    Inventory.addPart(new Outsourced(partIDGenerator(), name, price, stockNum, min, max, companyName));
                    partCreated = true;
                }else{
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
