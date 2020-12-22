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

public class AddProductFormController {

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
    private TextField addProdIDInput;
    @FXML
    private TextField addProdNameInput;
    @FXML
    private TextField addProdInvInput;
    @FXML
    private TextField addProdPriceInput;
    @FXML
    private TextField addProdMaxInput;
    @FXML
    private TextField addProdMinInput;
    public int productID = 0;
    ObservableList<Part> pendingAssociatedParts = FXCollections.observableArrayList();

    public void stageAssociatedParts(ActionEvent event) throws IOException{
        ObservableList<Part> selectedParts;
        selectedParts = addProdPartsAllPartsTable.getSelectionModel().getSelectedItems();
        pendingAssociatedParts.addAll(selectedParts);

    }
    public int highestProdID(){
        int largestID = 0;
        for (Product product: Inventory.getAllProducts()){
            if(product.getId() > largestID){
                largestID = product.getId();
            }
        }
        return largestID;
    }
    private int productIDGenerator() {
        if(Inventory.getAllProducts() == null){
            productID = 0;
        }else{
            productID = (highestProdID() +1);
        }
        return productID;
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
    public void addProduct(ActionEvent event) throws IOException{
        String prodName = addProdNameInput.getText().trim();
        int prodStockNum = Integer.parseInt(addProdInvInput.getText().trim());
        double prodPrice = Double.parseDouble(addProdPriceInput.getText().trim());
        int prodMin = Integer.parseInt(addProdMinInput.getText().trim());
        int prodMax = Integer.parseInt(addProdMaxInput.getText().trim());
        Inventory.addProduct(new Product(productIDGenerator(),
                prodName,
                prodPrice,
                prodStockNum,
                prodMin,
                prodMax));
        openMainForm(event);
    }
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
