package C482;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class DataValidation {




    public static boolean isInt(TextField inputField, String data){
        try{
            int enteredValue = Integer.parseInt(inputField.getText().trim());
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
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
    public static void invalidIntAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Data");
        alert.setContentText("Please enter a whole number in the " + message + " field.");
        alert.showAndWait();
    }
    public static void emptyStringAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Data");
        alert.setContentText("Please enter a valid " + message + " value.");
        alert.showAndWait();
    }
    public static void invalidStringAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Data");
        alert.setContentText("Please enter a valid " + message + " value.");
        alert.showAndWait();
    }
    public static void InvalidDoubleAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Data");
        alert.setContentText("Please enter a decimal number in the " + message + " field.");
        alert.showAndWait();
    }




}
