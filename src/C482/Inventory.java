package C482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *Contains methods and data necessary to access products and parts and maintain list of each.
 * @author Patrick Denney
 */
public class Inventory {
    /**
     * This is the list that contains all the parts that have been created
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * This is the list that contains all the products that have been created
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * adds new part to the allParts list
     * @param newPart part to be added to allParts list
     */
    public static void addPart(Part newPart){
         allParts.add(newPart);
    }
    /**
     * adds new product to the allProducts list
     * @param newProduct part to be added to allParts list
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     *
     * @param partId id number of the part to lookup
     * @return return the part object that corresponds to the id number passed in
     */
    public static Part lookupPart(int partId){
        Part thisPart = null;
        int index = -1;
        for (Part part : allParts) {
            index++;
            if (part.getId() == partId) {
                thisPart = part;
            }
        }
        return thisPart;
    }
    /**
     *
     * @param productId id number of the part to lookup
     * @return return the product object that corresponds to the id number passed in
     */
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
    /**
     *
     * @param partName name of the part to lookup
     * @return return the part object that corresponds to the name passed in
     */
    public static ObservableList<Part> lookupPart(String partName){
        return null;

    }
    /**
     *
     * @param productName id number of the product to lookup
     * @return return the product object that corresponds to the name passed in
     */
    public static ObservableList<Product> lookupProduct(String productName){
        return null;

    }

    /**
     * Method to update original to new modified part in the allPArts list
     * @param index index location of  original part in the allPArts list
     * @param selectedPart new part object to replace the original part
     */
    public static void updatePart(int index, Part selectedPart){
            allParts.set(index, selectedPart);
    }
    /**
     * Method to update original to new modified product in the allProducts list
     * @param index index location of  original product in the allProducts list
     * @param newProduct new product object to replace the original product
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /**
     *
     * @param selectedPart part to be removed from allParts list
     * @return true when complete
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.remove(selectedPart)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     *
     * @param selectedProduct part to be removed from allParts list
     * @return true when complete
     */
    public static boolean deleteProduct(Product selectedProduct){
        if (allProducts.remove(selectedProduct)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return all parts in the allParts list
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     *
     * @return all products in the allProducts list
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }


}
