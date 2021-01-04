package C482;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class DataValidation {
    public static void invNotBetweenMinMaxAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Inventory Amount Error");
        alert.setContentText("Inventory amount is not between Min and Max amounts.");
        alert.showAndWait();
    }
    public static void maxLessThanMin(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Max is Less Than Min");
        alert.setContentText("Max is less than Min");
        alert.showAndWait();
    }
    public static void invalidDataAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Data");
        alert.setContentText("Please enter valid data into the text fields.");
        alert.showAndWait();
    }
    public static void invalidStringAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Data");
        alert.setContentText("Please enter a valid " + message + " value.");
        alert.showAndWait();
    }
    public static void invalidNameAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Name");
        alert.setContentText("Please enter a valid " + message + ".");
        alert.showAndWait();
    }
    public static void pleaseMakeASelection(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("No " + message + " selected.");
        alert.setContentText("Please select a " + message + ", or create a " + message + ".");
        alert.showAndWait();
    }
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
    public static boolean confirmRemoveAssociatedPart(String message, String message2){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Remove Request");
        alert.setContentText("Are you sure that you want to remove " + message + " from " + message2+ "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            return true;
        }else if(result.isPresent() && result.get() == ButtonType.CANCEL){
            return false;
        }
        return false;
    }
}
