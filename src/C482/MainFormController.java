package C482;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {

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

   public void deleteSelectedPart(){
        ObservableList<Part> selectedPart, allParts;
        allParts = mainPartsTable.getItems();
        selectedPart = mainPartsTable.getSelectionModel().getSelectedItems();
        for(Part part: selectedPart) {
            allParts.remove(part);
            Inventory.deletePart(part);
        }
    }


    public void deleteSelectedProduct(){
        ObservableList<Product> selectedProduct, allProducts;
        allProducts = mainProductsTable.getItems();
        selectedProduct = mainProductsTable.getSelectionModel().getSelectedItems();
        for(Product product: selectedProduct) {
            allProducts.remove(product);
            Inventory.deleteProduct(product);
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
        window.setScene(addPartScene);
        window.show();

    }

    public void openAddProductForm(ActionEvent event) throws IOException {
        Parent addProductWindow = FXMLLoader.load(getClass().getResource("addProductForm.fxml"));
        Scene addProductScene = new Scene(addProductWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(addProductScene);
        window.show();

    }

    public void openModPartForm(ActionEvent event) throws IOException {
        partToModify();
        Parent modPartWindow = FXMLLoader.load(getClass().getResource("modifyPartForm.fxml"));
        Scene modPartScene = new Scene(modPartWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(modPartScene);
        window.show();

    }

    public void openModProductForm(ActionEvent event) throws IOException {
        productToModify();
        Parent modProductWindow = FXMLLoader.load(getClass().getResource("modifyProductForm.fxml"));
        Scene modProductScene = new Scene(modProductWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(modProductScene);
        window.show();

    }

    public boolean searchPart(int id){
        for(Part part: Inventory.getAllParts()){
            if(part.getId() == id){
                return true;
            }
        }
        return false;
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
