package C482;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * This Class contains warning, alert,
 * and confirmation methods for errors and validation in the other classes
 * @author Patrick Denney
 */
public class DataValidation {
    /**
     * Alerts user that the inventory value is not between the min and max values
     */
    public static void invNotBetweenMinMaxAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Inventory Amount Error");
        alert.setContentText("Inventory amount is not between Min and Max amounts.");
        alert.showAndWait();
    }

    /**
     * Warns user that the product price is not equal or more than the
     * sum total if it's associated parts
     */
    public static void prodPriceBelowSumOfPartsPricesAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Product Price Too Low");
        alert.setContentText("The Products price is less than the sum of it's associated parts prices.");
        alert.showAndWait();
    }

    /**
     * warns user that max value is below min value
     */
    public static void maxLessThanMin(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Max is Less Than Min");
        alert.setContentText("Max is less than Min");
        alert.showAndWait();
    }

    /**
     *
     * @param message name value to correct
     */
    public static void invalidNameAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Name");
        alert.setContentText("Please enter a valid " + message + ".");
        alert.showAndWait();
    }

    /**
     *
     * @param message value to be corrected
     */
    public static void invalidNumberAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid " + message);
        alert.setContentText("Please enter a valid " + message + ".");
        alert.showAndWait();
    }

    /**
     * warns user that they did not make a selection when trying to modify a part or product
     * @param message type of item to select
     */
    public static void pleaseMakeASelection(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("No " + message + " selected.");
        alert.setContentText("Please select a " + message + ", or create a " + message + ".");
        alert.showAndWait();
    }

    /**
     * warns user that no item of the type passed in was found in the search
     * @param message part or product
     */
    public static void partOrProductNotFound(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Not Found");
        alert.setContentText("No " + message + " matching your search criteria found. Please try your search again.");
        alert.showAndWait();
    }

    /**
     * Confirms user wants to delete item
     * @param message name of item to delete
     * @return true if user confirms yes, false if they cancel
     */
    public static boolean confirmDelete(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete Request");
        alert.setContentText("Are you sure that you want to delete " + message + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            return true;
        }else if(result.isPresent() && result.get() == ButtonType.CANCEL){
            return false;
        }
        return false;
    }

    /**
     * warns user that the product they are trying to delete has associated
     * parts, and that they need to be removed prior to deleting the product in question
     */
    public static void prodHasAssociatedPartsAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Associated Parts Warning");
        alert.setContentText("This product cannot be deleted, because it contains associated parts. " +
                "Please remove all associated parts prior to deleting.");
        alert.showAndWait();
    }

    /**
     * confirms that user wants to remove an associated part from a product
     * @param message1 associated part to remove
     * @param message2 product that part is being removed from
     * @return true is user confirms they want to remove part,
     * false if they hit cancel.
     */
    public static boolean confirmRemoveAssociatedPart(String message1, String message2){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Remove Request");
        alert.setContentText("Are you sure that you want to remove " + message1 + " from " + message2+ "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            return true;
        }else if(result.isPresent() && result.get() == ButtonType.CANCEL){
            return false;
        }
        return false;
    }
}
