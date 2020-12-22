package C482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static void addPart(Part newPart){
         allParts.add(newPart);

    }
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    public static Part lookupPart(int partId){
        return null;

    }
    public static Product lookupProduct(int productId){
        Product thisProduct = null;
        int index = -1;
        for (Product product : allProducts) {
            index++;
            if (product.getId() == productId) {
                thisProduct = product;
            }
        }
        return thisProduct;
    }
    public static ObservableList<Part> lookupPart(String partName){
        return null;

    }
    public static ObservableList<Product> lookupProduct(String productName){
        return null;

    }
    public static void updatePart(int index, Part selectedPart){
            allParts.set(index, selectedPart);
    }
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }
    public static boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        return true;
    }
    public static boolean deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
        return true;
    }
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }


}
