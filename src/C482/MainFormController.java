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

/**
 * This is the Class that contains the methods and data for the main page
 */
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
    public static int partModId = 0;
    public static int prodModId = 0;

    /**
     * loads any parts or products to the tables upon opening the main page
     */
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

    /**
     *
     * @return the ID number of the part to be modified, or deleted when a part is selected for modification
     */
    public int partToModify(){
        ObservableList<Part> selectedPart;
        selectedPart = mainPartsTable.getSelectionModel().getSelectedItems();
        for(Part part: selectedPart) {
            partModId = part.getId();
        }
        return partModId;
    }
    /**
     *
     * @return the ID number of the product to be modified, or deleted when a product is selected for modification
     */
    public int productToModify(){
        ObservableList<Product> selectedProduct;
        selectedProduct = mainProductsTable.getSelectionModel().getSelectedItems();
        for(Product product: selectedProduct) {
            prodModId = product.getId();
        }
        return prodModId;
    }

    /**
     * deletes the selected part
     */
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

    /**
     * deletes selected product
     */
    public void deleteSelectedProduct() {
        productToModify();
        if (prodModId != 0) {
            ObservableList<Product> selectedProduct, allProducts;
            allProducts = mainProductsTable.getItems();
            selectedProduct = mainProductsTable.getSelectionModel().getSelectedItems();
            for (Product product : selectedProduct) {
                if(product.getAllAssociatedParts().isEmpty()) {
                    if (DataValidation.confirmDelete(product.getName())) {
                        allProducts.remove(product);
                        Inventory.deleteProduct(product);
                    }
                }else{
                    DataValidation.prodHasAssociatedPartsAlert();
                }
            }
        }else{
            DataValidation.pleaseMakeASelection("product");
        }
    }

    /**
     * closes the application on the click of the exit button
     */
    public void exitApplication(){
        System.out.println("Closing");
        System.exit(0);
    }

    /**
     * opens the add part form
     * @param event clicking on the add part btn
     * @throws IOException
     */
    public void openAddPartForm(ActionEvent event) throws IOException {
        Parent addPartWindow = FXMLLoader.load(getClass().getResource("addPart.fxml"));
        Scene addPartScene = new Scene(addPartWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        addPartScene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
        window.setScene(addPartScene);
        window.show();

    }

    /**
     * opens the add product form
     * @param event clicking on the add product btn
     * @throws IOException
     */
    public void openAddProductForm(ActionEvent event) throws IOException {
        Parent addProductWindow = FXMLLoader.load(getClass().getResource("addProductForm.fxml"));
        Scene addProductScene = new Scene(addProductWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        addProductScene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
        window.setScene(addProductScene);
        window.show();

    }

    /**
     * opens the modify part form
     * @param event clicking on the modify part form
     * @throws IOException
     */
    public void openModPartForm(ActionEvent event) throws IOException {
        partToModify();
        if(partModId !=0) {
            Parent modPartWindow = FXMLLoader.load(getClass().getResource("modifyPartForm.fxml"));
            Scene modPartScene = new Scene(modPartWindow);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modPartScene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
            window.setScene(modPartScene);
            window.show();
            partModId = 0;
        }else{
            DataValidation.pleaseMakeASelection("part");
        }
    }

    /**
     * opens the modify product form
     * @param event clicking on the modify product btn
     * @throws IOException
     */
    public void openModProductForm(ActionEvent event) throws IOException {
        productToModify();
        if(prodModId !=0) {
            Parent modProductWindow = FXMLLoader.load(getClass().getResource("modifyProductForm.fxml"));
            Scene modProductScene = new Scene(modProductWindow);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modProductScene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
            window.setScene(modProductScene);
            window.show();
            prodModId = 0;
        }else{
            DataValidation.pleaseMakeASelection("product");
        }

    }
    /**
     * When user presses enter after entering in search term, loads parts that match search criteria.
     * data is supplied from the searchPartNameResultsList method
     * @param event pressing enter after entering search term
     * @throws IOException
     */
    public void getPartsSearchResults(ActionEvent event) throws IOException{
        String name = partsSearchTF.getText();
        ObservableList<Part> parts = searchPartNameResultsList(name);
        mainPartsTable.setItems(parts);
        partsSearchTF.setText("");
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
            if(part.getName().contains(searchStr)){
                results.add(part);
            }
        }
        return results;
    }
    /**
     *
     * @param searchStr user input into search field
     * @return returns a list of products matching the search term
     */
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
    /**
     * When user presses enter after entering in search term, loads products that match search criteria.
     * data is supplied from the searchProdNameResultsList method
     * @param event pressing enter after entering search term
     * @throws IOException
     */
    public void getProductSearchResults(ActionEvent event) throws IOException{
        String name = prodSearchTF.getText();
        ObservableList<Product> prod = searchProdNameResultsList(name);
        mainProductsTable.setItems(prod);
        prodSearchTF.setText("");
    }
}
