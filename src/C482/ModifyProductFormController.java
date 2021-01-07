package C482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/***
 * This Class contains the methods and data that modify products from the Product Class
 * @author Patrick Denney
 */
public class ModifyProductFormController {

    @FXML
    private TextField modProductSearch;
    @FXML
    private TextField modProductIDInput;
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
    private TableView<Part> modProdAllPartsTable;
    @FXML
    private TableColumn<Part, Integer> modProdAllPartsIDCol;
    @FXML
    private TableColumn<Part, String> modProdAllPArtsNAmeCol;
    @FXML
    private TableColumn<Part, Integer> modProdAllPartsInvCol;
    @FXML
    private TableColumn<Part, Double> modProdAllPartsPriceCol;
    @FXML
    private TableView<Part> modProdAssociatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> modProdAssociatedPartsIDCol;
    @FXML
    private TableColumn<Part, String> modProdAssociatedPartsNameCol;
    @FXML
    private TableColumn<Part, Integer> modProdAssociatedPartsInvCol;
    @FXML
    private TableColumn<Part, Double> modProdAssociatedPriceCol;
    private final int prodID = MainFormController.prodModId;
    public Product thisProduct = Inventory.lookupProduct(prodID);
    public ObservableList<Part> pendingAssociatedParts = thisProduct.getAllAssociatedParts();
    public ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();
    public Product newProduct;
    private String name;
    private int stockNum;
    private double price;
    private int min;
    private int max;
    /**
     * When user presses enter after entering in search term, loads parts that match search criteria.
     * data is supplied from the searchPartNameResultsList method
     * @param event
     * @throws IOException
     */
    public void getPartsSearchResults(ActionEvent event) throws IOException{
        String name = modProductSearch.getText();
        ObservableList<Part> parts = searchPartNameResultsList(name);
        modProdAllPartsTable.setItems(parts);
        modProductSearch.setText("");
    }
    /**
     *
     * @param searchStr user input into search field
     * @return returns a list of parts matching the search term
     */
    public ObservableList<Part> searchPartNameResultsList(String searchStr){
        ObservableList<Part> results = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        for(Part part: allParts){
            if(part.getName().contains(searchStr) || part.getId() == (Integer.parseInt(searchStr))){
                results.add(part);
            }else{
                DataValidation.partOrProductNotFound("part");
            }
        }
        return results;
    }
    /***
     * When user clicks add btn, this adds the selected parts to pendingAssociatedParts list.
     * @param event
     * @throws IOException
     */
    public void addAssociatedParts(ActionEvent event) throws IOException{
        ObservableList<Part> selectedPart;
        selectedPart = modProdAllPartsTable.getSelectionModel().getSelectedItems();
        for(Part part:selectedPart) {
            thisProduct.addAssociatedPart(part);
        }
    }
    /***
     * When user clicks remove btn, this removes the selected parts from the pendingAssociatedParts list.
     * @param event
     * @throws IOException
     */
    public void removeAssociatedParts(ActionEvent event) throws IOException {
        ObservableList<Part> selectedPartForRemove;
        selectedPartForRemove = modProdAssociatedPartsTable.getSelectionModel().getSelectedItems();
        for (Part part : selectedPartForRemove) {
            if(DataValidation.confirmRemoveAssociatedPart(part.getName(), thisProduct.getName()))
            thisProduct.deleteAssociatedPart(part);
        }
    }
    /**
     * Sets the name input field with the original product name upon loading the page.
     * @param thisProduct the original product to be modified
     */
    private void setNameField(Product thisProduct) {
            nameInput.setText(thisProduct.getName());
        }
    /**
     * Sets the price input field with the original product price upon loading the page.
     * @param thisProduct the original product to be modified
     */
    private void setPriceField(Product thisProduct) {
            priceInput.setText(String.valueOf(thisProduct.getPrice()));
        }
    /**
     * Sets the inventory input field with the original product inventory level upon loading the page.
     * @param thisProduct the original product to be modified
     */
    private void setInvLvlField(Product thisProduct) {
            inventoryInput.setText(String.valueOf(thisProduct.getStock()));
        }
    /**
     * Sets the min input field with the original product min inventory upon loading the page.
     * @param thisProduct the original product to be modified
     */
    private void setMinField(Product thisProduct) {
            minInput.setText(String.valueOf(thisProduct.getMin()));
        }
    /**
     * Sets the max input field with the original product max inventory upon loading the page.
     * @param thisProduct the original product to be modified
     */
    private void setMaxField(Product thisProduct) {
            maxInput.setText(String.valueOf(thisProduct.getMax()));
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
    /***
     *
     * @return Returns true if the name input contains valid data.
     * If data is not valid, returns false, and displays an error dialog.
     */
    public boolean nameValid() {
        if (name.length() >= 2) {
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
        double sumOfPartPrices = 0;
        for(Part part: pendingAssociatedParts){
            double partPrice = part.getPrice();
            sumOfPartPrices += partPrice;
        }
        if(price >= sumOfPartPrices){
            return true;
        }else{
            styleClass(priceInput).add("error");
            DataValidation.prodPriceBelowSumOfPartsPricesAlert();
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
     * @return Returns true if the minimum stock level is less than the Max stock level.
     * Returns false if not, and displays an error message.
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
     * @return Returns true if the inventory stock level is between than the max and min stock level.
     * Returns false if not, and displays an error message.
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
    }
    /***
     *  Takes data from the input fields and validates it. If all data is valid, modifies the product with the new data.
     */
    public void getChangedProd(ActionEvent event) throws IOException  {
        name = "";
        price = 0;
        min = 0;
        max = 0;
        stockNum = 0;
        removeAllErrorFlags();
        name = nameInput.getText().trim();
        if (nameValid()) {
            styleClass(nameInput).remove("error");
        }else {
            styleClass(nameInput).add("error");
            DataValidation.invalidNameAlert("Product name");
        }
        try{
            stockNum = Integer.parseInt(inventoryInput.getText().trim());
        }catch (NumberFormatException e) {
            styleClass(inventoryInput).add("error");
            DataValidation.invalidNumberAlert("Inventory value");
        }
            try {
                price = Double.parseDouble(priceInput.getText().trim());
            } catch (NumberFormatException e) {
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
        if (nameValid() && minLessThanMax() &&
                invBetweenMinMax() && priceValid()
            && inventoryValid()) {
            newProduct = new Product(prodID,
                    name,
                    price,
                    stockNum,
                    min,
                    max);
            tempAssociatedParts.addAll(pendingAssociatedParts);
            Inventory.updateProduct(findProductIndex(prodID), newProduct);
            for(Part part:tempAssociatedParts){
                newProduct.addAssociatedPart(part);
            }
            openMainForm(event);
        }

    }
    /**
     *
     * @param id the ID number of the product to be modified
     * @return the index position of the product in the Inventory list.
     */
    public int findProductIndex(int id){
        int index = -1;
        for(Product product: Inventory.getAllProducts()) {
            if (product.getId() == id) {
                index = Inventory.getAllProducts().indexOf(product);
            }
        }
        return index;
    }
    /**
     * Loads data from parts inventory to the respective tables, so that they can be added or removed from the product.
     * Also, sets the input fields using the original product data.
     */
    public void initialize(){
        modProductIDInput.setText(String.valueOf(prodID));
        setNameField(thisProduct);
        setInvLvlField(thisProduct);
        setPriceField(thisProduct);
        setMaxField(thisProduct);
        setMinField(thisProduct);

        modProdAllPartsTable.setItems(Inventory.getAllParts());
        modProdAllPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProdAllPArtsNAmeCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProdAllPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProdAllPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modProdAllPartsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        modProdAssociatedPartsTable.setItems(pendingAssociatedParts);
        modProdAssociatedPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProdAssociatedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProdAssociatedPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProdAssociatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modProdAssociatedPartsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        }

        }
