package C482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;

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
    public void getPartsSearchResults(ActionEvent event) throws IOException{
        String name = modProductSearch.getText();
        ObservableList<Part> parts = searchPartNameResultsList(name);
        modProdAllPartsTable.setItems(parts);
        modProductSearch.setText("");
    }
    public ObservableList<Part> searchPartNameResultsList(String searchStr){
        ObservableList<Part> results = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        for(Part part: allParts){
            if(part.getName().contains(searchStr)){
                results.add(part);
            }
        }
        return results;
    }
    public void addAssociatedParts(ActionEvent event) throws IOException{
        ObservableList<Part> selectedPart;
        selectedPart = modProdAllPartsTable.getSelectionModel().getSelectedItems();
        for(Part part:selectedPart) {
            thisProduct.addAssociatedPart(part);
        }
    }
    public void removeAssociatedParts(ActionEvent event) throws IOException {
        ObservableList<Part> selectedPartForRemove;
        selectedPartForRemove = modProdAssociatedPartsTable.getSelectionModel().getSelectedItems();
        for (Part part : selectedPartForRemove) {
            if(DataValidation.confirmRemoveAssociatedPart(part.getName(), thisProduct.getName()))
            thisProduct.deleteAssociatedPart(part);
        }
    }
    private void setNameField(Product thisProduct) {
            nameInput.setText(thisProduct.getName());
        }
    private void setPriceField(Product thisProduct) {
            priceInput.setText(String.valueOf(thisProduct.getPrice()));
        }
    private void setInvLvlField(Product thisProduct) {
            inventoryInput.setText(String.valueOf(thisProduct.getStock()));
        }
    private void setMinField(Product thisProduct) {
            minInput.setText(String.valueOf(thisProduct.getMin()));
        }
    private void setMaxField(Product thisProduct) {
            maxInput.setText(String.valueOf(thisProduct.getMax()));
        }
    public void openMainForm(ActionEvent event) throws IOException {
        Parent mainWindow = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
        Scene mainScene = new Scene(mainWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }
    public void cancelBtn(ActionEvent event) throws IOException{
        openMainForm(event);
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
    }
    public void getChangedProd(ActionEvent event) throws IOException  {
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
    public int findProductIndex(int id){
        int index = -1;
        for(Product product: Inventory.getAllProducts()) {
            if (product.getId() == id) {
                index = Inventory.getAllProducts().indexOf(product);
            }
        }
        return index;
    }
    /*public void saveModifiedProduct(ActionEvent event) throws IOException {
        tempAssociatedParts.addAll(pendingAssociatedParts);
        getChangedProd();
        Inventory.updateProduct(findProductIndex(prodID), newProduct);
        for(Part part:tempAssociatedParts){
            newProduct.addAssociatedPart(part);
        }
        openMainForm(event);
    }*/
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
