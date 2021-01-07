package C482;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *Contains methods and data necessary to create a Product, such as constructors, setters, and getters.
 * @author Patrick Denney
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the product id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the product id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the product name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the product price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the product stock level
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the product stock level to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the product min inventory level
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the product min inventory level to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the product max inventory level
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the product max inventory level to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     *
     * @param part part to add to the list of parts associated with the product in question.
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     *
     * @param selectedAssociatedPart part selected to be deleted from list of associated parts
     * for the product in question
     * @return true if part contained in the list and removes the part, false if not in the list.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if (associatedParts.contains(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        return false;
    }

    /**
     *
     * @return all associated parts in the list for the product in question
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }


}