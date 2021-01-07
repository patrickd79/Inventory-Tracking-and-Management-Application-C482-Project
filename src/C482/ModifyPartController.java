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
 * This Class contains the methods and data that modify parts from the Part Class
 * @author Patrick Denney
 */
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
    private Part thisPart = Inventory.lookupPart(partID);
    private boolean partModified = false;
    /**
     * Checks to see if the current part is an in house part or outsourced.
     * @param thisPart part to be checked ID number.
     * @return true if in house part, false if outsourced part.
     */
    public boolean inHouse(Part thisPart) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == thisPart.getId()) {
                if (part instanceof InHouse) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * If is an in house part the sets radio btn to in house
     * if outsourced part, then sets outsourced radio btn to selected and
     * changes machine ID label to company name and changes prompt text of machine ID input.
     * @param thisPart part currently being modified
     */
    private void isInHousePart(Part thisPart) {
        if (inHouse(thisPart)) {
            inHouseRadio.setSelected(true);
        } else {
            outsourcedRadio.setSelected(true);
            machineIDCompNameLabel.setText("Company Name");
            machineIDInput.setPromptText("Name of supplier");

        }
    }
    /**
     * This allows the user to select either the in house or outsourced radio button to modify the part
     * from being one type to the other.
     * @param event selecting the radio btns
     */
    @FXML
    private void changeTypeOfPart(ActionEvent event) {
        if (inHouseRadio.isSelected()) {
            machineIDCompNameLabel.setText("Machine ID");
        } else {
            machineIDCompNameLabel.setText("Company Name");
        }
    }
    /**
     * Sets the name input field with the original part name upon loading the page.
     * @param thisPart the original part to be modified
     */
    private void setNameField(Part thisPart) {
        nameInput.setText(thisPart.getName());
    }
    /**
     * Sets the inventory input field with the original part inventory level upon loading the page.
     * @param thisPart the original part to be modified
     */
    private void setInvLvlField(Part thisPart) {
        inventoryInput.setText(String.valueOf(thisPart.getStock()));
    }

        /**
         * Sets the price input field with the original part price upon loading the page.
         * @param thisPart the original part to be modified
         */
        private void setPriceField(Part thisPart) {
            priceInput.setText(String.valueOf(thisPart.getPrice()));
        }
    /**
     * Sets the max input field with the original part max upon loading the page.
     * @param thisPart the original part to be modified
     */
    private void setMaxField(Part thisPart) {
        maxInput.setText(String.valueOf(thisPart.getMax()));
    }
    /**
     * Sets the min input field with the original part min upon loading the page.
     * @param thisPart the original part to be modified
     */
    private void setMinField(Part thisPart) {
        minInput.setText(String.valueOf(thisPart.getMin()));
    }
    /**
     * Sets the machine ID input field with the original part machine ID or company name upon loading the page.
     * @param thisPart the original part to be modified
     */
    private void setMachineIdField(Part thisPart) {
        if (thisPart instanceof InHouse) {
            machineIDInput.setText(String.valueOf(((InHouse) thisPart).getMachineID()));
        } else {
            machineIDInput.setText(((Outsourced) thisPart).getCompanyName());
        }
    }

    /***
     * takes user back to main form, such as when cancel btn, or save btn is clicked.
     * @param event
     * @throws IOException
     */
    public void openMainForm(ActionEvent event) throws IOException {
        Parent mainWindow = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
        Scene mainScene = new Scene(mainWindow);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();

    }
    /***
     * Dictates behavior when cancel btn is clicked, shows a confirmation dialog,
     * and takes user to main form if confirmed.
     * @param event
     * @throws IOException
     */
    public void cancelBtn(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to leave this screen and lose all entered data?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            openMainForm(event);
        }
    }
    /**
     *
     * @param thisPart the ID number of the part to be modified
     * @return the index position of the part in the Inventory list.
     */
    public int findPartIndex(Part thisPart) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == thisPart.getId()) {
                index = Inventory.getAllParts().indexOf(part);

            }
        }
        return index;
    }
    /***
     *
     * @return Returns true if the name input contains valid data.
     * If data is not valid, returns false, and displays an error dialog.
     */
    public boolean nameValid() {
        if (name.length() >=1) {
            return true;
        }else{
            styleClass(nameInput).add("error");
            DataValidation.invalidNameAlert("Part name");
        }
        return false;
    }
    /***
     *
     * @return Returns true if the price input contains valid data.
     * If data is not valid, returns false, and displays an error dialog.
     */
    public boolean priceValid(){
        if(priceInput.getText() == null){
            return false;
        }else if(price >= 0){
            return true;
        }else{
            styleClass(priceInput).add("error");
            DataValidation.invalidNumberAlert("Price value");
        }
        return false;
    }
    /***
     *
     * @return Returns true if the inventory input contains valid data.
     * If data is not valid, returns false, and displays an error dialog.
     */
    public boolean inventoryValid(){
        if(stockNum >= 0){
            return true;
        }else{
            styleClass(inventoryInput).add("error");
            DataValidation.invalidNumberAlert("Inventory value");
        }
        return false;
    }
    /***
     *
     * @return Returns true if the machineID input contains valid data.
     * If data is not valid, returns false, and displays an error dialog.
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
     * @return Returns true if the company name input contains valid data.
     * If data is not valid, returns false, and displays an error dialog.
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
     * @return Returns true if the min input is less than max input field.
     * If data is not valid, returns false, and displays an error dialog.
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
     * @return Returns true if the inventory value is between the min and max input values.
     * If data is not valid, returns false, and displays an error dialog.
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
     *  Takes data from the input fields and validates it. If all data is valid, modifies the original part with the new data.
     */
    public void saveChangedPart(ActionEvent event) throws IOException {
        removeAllErrorFlags();
        if (inHouseRadio.isSelected()) {
            name = "";
            price = 0;
            min = 0;
            max = 0;
            stockNum = 0;
            machineID = 0;
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
                    Inventory.updatePart(findPartIndex(thisPart), part);
                    partModified = true;
                }else {
                    partModified = false;
                }
        } else {
            name = "";
            price = 0;
            min = 0;
            max = 0;
            stockNum = 0;
            companyName = "";
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
                }catch (Exception e){
                    styleClass(machineIDInput).add("error");
                    DataValidation.invalidNumberAlert("Company name");
                }
                if (nameValid() && minLessThanMax() &&
                        invBetweenMinMax() && companyNameValid()
                && priceValid() && inventoryValid()) {
                    Part part = new Outsourced(partID, name, price, stockNum, min, max, companyName);
                    Inventory.updatePart(findPartIndex(thisPart), part);
                    partModified = true;
                }else {
                    partModified = false;
                }
        }
        if(partModified){
            openMainForm(event);
        }
    }
    /**
     * Sets the input fields using the original part data.
     */
    public void initialize(){
        isInHousePart(thisPart);
        iDInput.setText(String.valueOf(thisPart.getId()));
        setNameField(thisPart);
        setInvLvlField(thisPart);
        setPriceField(thisPart);
        setMaxField(thisPart);
        setMinField(thisPart);
        setMachineIdField(thisPart);

    }


}
