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
 * This Class contains the methods and data that create products from the Product Class
 * @author Patrick Denney
 */
public class AddProductFormController {
    @FXML
    private TextField addProdSearchField;
    @FXML
    private TableView<Part> addProdPartsAllPartsTable;
    @FXML
    private TableColumn<Part, Integer> addProdAllPartsIDCol;
    @FXML
    private TableColumn<Part, String> addProdAllPartsNameCol;
    @FXML
    private TableColumn<Part, Integer> addProdAllPartsInvCol;
    @FXML
    private TableColumn<Part, Double> addProdAllPartsPriceCol;
    @FXML
    private TableView<Part> addProdAssociatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> addProdAssociatedPartsIDCol;
    @FXML
    private TableColumn<Part, String> addProdAssociatedPartsNameCol;
    @FXML
    private TableColumn<Part, Integer> addProdAssociatedPartsInvCol;
    @FXML
    private TableColumn<Part,Double> addProdAssociatedPartsPriceCol;
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
    private String name;
    private int stockNum;
    private double price;
    private int min;
    private int max;
    public int productID = 0;
    public ObservableList<Part> pendingAssociatedParts = FXCollections.observableArrayList();
    /**
     * When user presses enter after entering in search term, loads parts that match search criteria.
     * data is supplied from the searchPartNameResultsList method
     * @param event
     * @throws IOException
     */
    public void getPartsSearchResults(ActionEvent event) throws IOException{
        String name = addProdSearchField.getText();
        ObservableList<Part> parts = searchPartNameResultsList(name);
        addProdPartsAllPartsTable.setItems(parts);
        addProdSearchField.setText("");
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
        ObservableList<Part> selectedParts;
        selectedParts = addProdPartsAllPartsTable.getSelectionModel().getSelectedItems();
        pendingAssociatedParts.addAll(selectedParts);

    }
    /***
     * When user clicks remove btn, this removes the selected parts from the pendingAssociatedParts list.
     * @param event
     * @throws IOException
     */
    public void removeAssociatedParts(ActionEvent event) throws IOException {
        ObservableList<Part> selectedPartForRemove;
        selectedPartForRemove = addProdAssociatedPartsTable.getSelectionModel().getSelectedItems();
        pendingAssociatedParts.removeAll(selectedPartForRemove);

    }
    /***
     *
     * @return checks all the products in inventory, and if any exist,
     * returns the largest ID number
     */
    public int highestProdID(){
        int largestID = 0;
        for (Product product: Inventory.getAllProducts()){
            if(product.getId() > largestID){
                largestID = product.getId();
            }
        }
        return largestID;
    }
    /***
     *
     * @return if there are no products in inventory, returns 0,
     *      if not then adds 1 to the highest product ID present
     *      and returns that as the product ID number.
     */
    private int productIDGenerator() {
        if(Inventory.getAllProducts() == null){
            productID = 0;
        }else{
            productID = (highestProdID() +1);
        }
        return productID;
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
     *  Takes data from the input fields and validates it. If all data is valid, creates a new product.
     */
    public void addNewProd(ActionEvent event) throws IOException{
        name = "";
        price = 0;
        min = 0;
        max = 0;
        stockNum = 0;
        removeAllErrorFlags();
        Product product;
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
        if (nameValid() && minLessThanMax() &&
                invBetweenMinMax() && priceValid()
            && inventoryValid()) {
            product = new Product(productIDGenerator(),
                    name,
                    price,
                    stockNum,
                    min,
                    max);
            Inventory.addProduct(product);
            for (Part part : pendingAssociatedParts) {
                product.addAssociatedPart(part);
            }
            openMainForm(event);

        }
    }
    /**
     * Loads data from parts inventory to the respective tables, so that they can be added or removed from the product.
     */
    public void initialize(){
        addProdPartsAllPartsTable.setItems(Inventory.getAllParts());
        addProdAllPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProdAllPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProdAllPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProdAllPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProdPartsAllPartsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        addProdAssociatedPartsTable.setItems(pendingAssociatedParts);
        addProdAssociatedPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProdAssociatedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProdAssociatedPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProdAssociatedPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProdAssociatedPartsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }


}
