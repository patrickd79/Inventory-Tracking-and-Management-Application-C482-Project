package C482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TableView<Product> modProdAllPartsTable;
    @FXML
    private TableColumn<Product, Integer> modProdAllPartsIDCol;
    @FXML
    private TableColumn<Product, String> modProdAllPArtsNAmeCol;
    @FXML
    private TableColumn<Product, Integer> modProdAllPartsInvCol;
    @FXML
    private TableColumn<Product, Double> modProdAllPartsPriceCol;
    @FXML
    private TableView<Product> modProdAssociatedPartsTable;
    @FXML
    private TableColumn<Product, Integer> modProdAssociatedPartsIDCol;
    @FXML
    private TableColumn<Product, String> modProdAssociatedPartsNameCol;
    @FXML
    private TableColumn<Product, Integer> modProdAssociatedPartsInvCol;
    @FXML
    private TableColumn<Product, Double> modProdAssociatedPriceCol;
    private final int prodID = MainFormController.prodModId;

    private void setNameField(int id) {
        int index = -1;
        for (Product product : Inventory.getAllProducts()) {
            index++;
            if (product.getId() == id) {
                modProductNameInput.setText(product.getName());
            }
        }
    }
    private void setPriceField(int id) {
        int index = -1;
        for (Product product : Inventory.getAllProducts()) {
            index++;
            if (product.getId() == id) {
                modProductPriceInput.setText(String.valueOf(product.getPrice()));
            }
        }
    }
    private void setInvLvlField(int id) {
        int index = -1;
        for (Product product : Inventory.getAllProducts()) {
            index++;
            if (product.getId() == id) {
                modProductInvInput.setText(String.valueOf(product.getStock()));
            }
        }
    }
    private void setMinField(int id) {
        int index = -1;
        for (Product product : Inventory.getAllProducts()) {
            index++;
            if (product.getId() == id) {
                modProductMinInput.setText(String.valueOf(product.getMin()));
            }
        }
    }
    private void setMaxField(int id) {
        int index = -1;
        for (Product product : Inventory.getAllProducts()) {
            index++;
            if (product.getId() == id) {
                modProductMaxInput.setText(String.valueOf(product.getMax()));
            }
        }
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
        return new Product(prodID,
                prodName,
                prodPrice,
                prodStockNum,
                prodMin,
                prodMax);
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
        Inventory.updateProduct(findProductIndex(prodID), getChangedProd());
        openMainForm(event);
    }
    public void initialize() {
        modProductIDInput.setText(String.valueOf(prodID));
        setNameField(prodID);
        setInvLvlField(prodID);
        setPriceField(prodID);
        setMaxField(prodID);
        setMinField(prodID);

    }

}
