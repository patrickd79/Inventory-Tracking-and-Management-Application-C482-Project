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

public class MainFormController {
    @FXML
    private TextField prodSearchTF;
    @FXML
    private TextField partsSearchTF;
    @FXML
    private TableView<Part> mainPartsTable;
    @FXML
    private TableColumn<Part, Integer> partIDColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInvLevelColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TableView<Product> mainProductsTable;
    @FXML
    private TableColumn<Product, Integer> productIDColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInvLevelColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    public static int partModId;
    public static int prodModId;

    public void initialize(){
            mainPartsTable.setItems(Inventory.getAllParts());
            partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            mainPartsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            mainProductsTable.setItems(Inventory.getAllProducts());
            productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            productInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            mainProductsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    public int partToModify(){
        ObservableList<Part> selectedPart;
        selectedPart = mainPartsTable.getSelectionModel().getSelectedItems();
        for(Part part: selectedPart) {
            partModId = part.getId();
        }
        return partModId;
    }
    public int productToModify(){
        ObservableList<Product> selectedProduct;

        selectedProduct = mainProductsTable.getSelectionModel().getSelectedItems();
        for(Product product: selectedProduct) {
            prodModId = product.getId();
        }
        return prodModId;
    }

   public void deleteSelectedPart() {
       partToModify();
       if (partModId != 0) {
           ObservableList<Part> selectedPart, allParts;
           allParts = mainPartsTable.getItems();
           selectedPart = mainPartsTable.getSelectionModel().getSelectedItems();
           for (Part part : selectedPart) {
               if(DataValidation.confirmDelete(part.getName())) {
                   allParts.remove(part);
                   Inventory.deletePart(part);
               }
           }
       }else{
           DataValidation.pleaseMakeASelection("part");
       }
   }
   public void deleteSelectedProduct() {
        productToModify();
        if (prodModId != 0) {
            ObservableList<Product> selectedProduct, allProducts;
            allProducts = mainProductsTable.getItems();
            selectedProduct = mainProductsTable.getSelectionModel().getSelectedItems();
            for (Product product : selectedProduct) {
                if(DataValidation.confirmDelete(product.getName())) {
                    allProducts.remove(product);
                    Inventory.deleteProduct(product);
                }
            }
        }else{
            DataValidation.pleaseMakeASelection("product");
        }
    }

    public void exitApplication(){
        System.out.println("Closing");
        System.exit(0);
    }

    public void openAddPartForm(ActionEvent event) throws IOException {
        Parent addPartWindow = FXMLLoader.load(getClass().getResource("addPart.fxml"));
        Scene addPartScene = new Scene(addPartWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        addPartScene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
        window.setScene(addPartScene);
        window.show();

    }

    public void openAddProductForm(ActionEvent event) throws IOException {
        Parent addProductWindow = FXMLLoader.load(getClass().getResource("addProductForm.fxml"));
        Scene addProductScene = new Scene(addProductWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        addProductScene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
        window.setScene(addProductScene);
        window.show();

    }

    public void openModPartForm(ActionEvent event) throws IOException {
        partToModify();
        if(partModId !=0) {
            Parent modPartWindow = FXMLLoader.load(getClass().getResource("modifyPartForm.fxml"));
            Scene modPartScene = new Scene(modPartWindow);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modPartScene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
            window.setScene(modPartScene);
            window.show();
        }else{
            DataValidation.pleaseMakeASelection("part");
        }
    }

    public void openModProductForm(ActionEvent event) throws IOException {
        productToModify();
        if(prodModId !=0) {
            Parent modProductWindow = FXMLLoader.load(getClass().getResource("modifyProductForm.fxml"));
            Scene modProductScene = new Scene(modProductWindow);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modProductScene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
            window.setScene(modProductScene);
            window.show();
        }else{
            DataValidation.pleaseMakeASelection("product");
        }

    }
    public void getPartsSearchResults(ActionEvent event) throws IOException{
        String name = partsSearchTF.getText();
        ObservableList<Part> parts = searchPartNameResultsList(name);
        mainPartsTable.setItems(parts);
        partsSearchTF.setText("");
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
    public ObservableList<Product> searchProdNameResultsList(String searchStr){
        ObservableList<Product> results = FXCollections.observableArrayList();
        ObservableList<Product> allProds = Inventory.getAllProducts();
        for(Product prod: allProds){
            if(prod.getName().contains(searchStr)){
                results.add(prod);
            }
        }
        return results;
    }
    public void getProductSearchResults(ActionEvent event) throws IOException{
        String name = prodSearchTF.getText();
        ObservableList<Product> prod = searchProdNameResultsList(name);
        mainProductsTable.setItems(prod);
        prodSearchTF.setText("");
    }

    public boolean deletePart(int id){
        int index = -1;
        for(Part part: Inventory.getAllParts()){
            index++;
            if(part.getId() == id){
                Inventory.getAllParts().remove(part.getId());
                return true;
            }
        }
        return false;
    }
    public boolean deleteProd(int id){
        int index = -1;
        for(Product product: Inventory.getAllProducts()){
            index++;
            if(product.getId() == id){
                Inventory.getAllProducts().remove(product.getId());
                return true;
            }
        }
        return false;
    }

    public Part selectPart(int id){
        for(Part part: Inventory.getAllParts()){
            if(part.getId() == id){
                return part;
            }
        }
        return null;
    }





}
