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

/***
 * This Class contains the methods and data that create parts from the Part Class
 * @author Patrick Denney
 */
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

    /***
     *
     * @return checks all the parts in inventory, and if any exist, returns the largest ID number
     */
    public int highestPartId(){
        int largestID = 0;
        for (Part part: Inventory.getAllParts()){
            if(part.getId() > largestID){
                largestID = part.getId();
            }
        }
        return largestID;
    }

    /***
     *
     * @return if there are no parts in inventory, returns 0,
     *      if not then adds one to the highest part ID present and returns that as the partID.
     */
    private int partIDGenerator() {
        if(Inventory.getAllParts() == null){
            partID = 0;
        }else{
            partID = (highestPartId() +1);
        }
        return partID;
    }

    /***
     * takes user back to main form, such as when cancel btn, or save btn is clicked.
     * @param event
     * @throws IOException
     */
    public void openMainForm(ActionEvent event) throws IOException {
        Parent mainWindow = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
        Scene mainScene = new Scene(mainWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }

    /***
     * Dictates behavior when cancel btn is clicked, shows a confirmation dialog, and takes user to main form if confirmed.
     * @param event
     * @throws IOException
     */
    public void cancelBtn(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to leave this screen and lose all entered data?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            openMainForm(event);
        }
    }

    /***
     *
     * @return Returns true if the name input contains valid data. If data is not valid, returns false, and displays an error dialog.
     */
    public boolean nameValid() {
       if (name.length() >= 1) {
           return true;
       }else{
           styleClass(nameInput).add("error");
           DataValidation.invalidNameAlert("Part name");
       }
        return false;
    }

    /***
     *
     * @return Returns true if the price input contains valid data. If data is not valid, returns false, and displays an error dialog.
     */
    public boolean priceValid(){
        if(price >= 0){
            return true;
        }else{
            styleClass(priceInput).add("error");
            DataValidation.invalidNumberAlert("Price value");
        }
        return false;
    }

    /***
     *
     * @return Returns true if the inventory input contains valid data. If data is not valid, returns false, and displays an error dialog.
     */
    public boolean inventoryValid(){
        if(stockNum > 0){
            return true;
        }else{
            styleClass(inventoryInput).add("error");
            DataValidation.invalidNumberAlert("Inventory value");
        }
        return false;
    }

    /***
     *
     * @return Returns true if the machine ID input contains valid data. If data is not valid, returns false, and displays an error dialog.
     */
    public boolean machineIDValid(){
        if(machineID > 0){
            return true;
        }else{
            styleClass(machineIDInput).add("error");
            DataValidation.invalidNumberAlert("Machine ID value");
        }
        return false;
    }

    /***
     *
     * @return Returns true if the company name input contains valid data. If data is not valid, returns false, and displays an error dialog.
     */
    public boolean companyNameValid() {
        if (companyName.length() >= 1) {
            return true;
        }else{
            styleClass(machineIDInput).add("error");
            DataValidation.invalidNameAlert("Company name");
        }
        return false;
    }

    /***
     *
     * @return Returns true if the minimum stock level is less than the Max stock level. Returns false if not, and displays an error message.
     */
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

    /***
     *
     * @return Returns true if the inventory stock level is between than the max and min stock level. Returns false if not, and displays an error message.
     */
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

    /**
     * This allows for a simple method to change the input field style on an error, to red and back again.
     * @param field The text field targeted
     * @return the field targeted styles class attribute
     */
    private ObservableList<String> styleClass(TextField field){
        return field.getStyleClass();
    }

    /**
     * Removes the red error style from all the text fields, when reattempting to save data.
     */
    private void removeAllErrorFlags(){
        styleClass(nameInput).removeAll("error");
        styleClass(inventoryInput).removeAll("error");
        styleClass(priceInput).removeAll("error");
        styleClass(minInput).removeAll("error");
        styleClass(maxInput).removeAll("error");
        styleClass(machineIDInput).removeAll("error");
    }

    /***
     *  Takes data from the input fields and validates it. If all data is valid, creates a new In House Part.
     */
    public void addInHousePart(){
        name = "";
        price = 0;
        min = 0;
        max = 0;
        stockNum = 0;
        machineID = 0;
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
    /***
     *  Takes data from the input fields and validates it. If all data is valid, creates a new Outsourced Part.
     */
    public void addOutsourcedPart() {
        name = "";
        price = 0;
        min = 0;
        max = 0;
        stockNum = 0;
        companyName = "";
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

    /***
     * Checks if in house or outsourced is selected, then adjusts the label and prompt text on the machine ID text field.
     */
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

    /**
     * Checks if in house or outsourced part to be created and calls corresponding method to create part, if successful then takes user back to main form.
     * @param event on click of the save btn.
     * @throws IOException
     */
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
