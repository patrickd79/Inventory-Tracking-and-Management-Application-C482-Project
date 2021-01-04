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
    private TextField modProductNameInput;
    @FXML
    private TextField modProductInvInput;
    @FXML
    private TextField modProductPriceInput;
    @FXML
    private TextField modProductMaxInput;
    @FXML
    private TextField modProductMinInput;
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
            modProductNameInput.setText(thisProduct.getName());
        }
    private void setPriceField(Product thisProduct) {
            modProductPriceInput.setText(String.valueOf(thisProduct.getPrice()));
        }
    private void setInvLvlField(Product thisProduct) {
            modProductInvInput.setText(String.valueOf(thisProduct.getStock()));
        }
    private void setMinField(Product thisProduct) {
            modProductMinInput.setText(String.valueOf(thisProduct.getMin()));
        }
    private void setMaxField(Product thisProduct) {
            modProductMaxInput.setText(String.valueOf(thisProduct.getMax()));
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
    public Product getChangedProd() {
        String prodName = modProductNameInput.getText().trim();
        int prodStockNum = Integer.parseInt(modProductInvInput.getText().trim());
        double prodPrice = Double.parseDouble(modProductPriceInput.getText().trim());
        int prodMin = Integer.parseInt(modProductMinInput.getText().trim());
        int prodMax = Integer.parseInt(modProductMaxInput.getText().trim());
         newProduct= new Product(prodID,
                prodName,
                prodPrice,
                prodStockNum,
                prodMin,
                prodMax);
         return newProduct;
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
    public void saveModifiedProduct(ActionEvent event) throws IOException {
        tempAssociatedParts.addAll(pendingAssociatedParts);
        getChangedProd();
        Inventory.updateProduct(findProductIndex(prodID), newProduct);
        for(Part part:tempAssociatedParts){
            newProduct.addAssociatedPart(part);
        }
        openMainForm(event);
    }
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
