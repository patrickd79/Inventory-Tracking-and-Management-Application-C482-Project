package C482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPartController<event> {

    @FXML
    private TextField addPartNameInput;
    @FXML
    private TextField addPartInventoryInput;
    @FXML
    private TextField addPartCostInput;
    @FXML
    private TextField addPartMaxInput;
    @FXML
    private TextField addPartMinInput;
    @FXML
    private TextField addPartMachineIDInput;
    @FXML
    private RadioButton addPartInHouseRadio;
    @FXML
    private Label machineIDCompNameLabel;
    public int partID = 0;
    public String partName ="name";
    public int stockNum = 0;
    public double partPrice = 0;
    public int partMin = 0;
    public int partMax = 0;
    public int machineID = 0;
    public String companyName = "";

    public int highestPartId(){
        int largestID = 0;
        for (Part part: Inventory.getAllParts()){
            if(part.getId() > largestID){
                largestID = part.getId();
            }
        }
        return largestID;
    }

    private int partIDGenerator() {
        if(Inventory.getAllParts() == null){
            partID = 0;
        }else{
            partID = (highestPartId() +1);
        }
        return partID;
    }

    public void openMainForm(ActionEvent event) throws IOException {
        Parent mainWindow = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
        Scene mainScene = new Scene(mainWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }

    public void addInHousePart(){
        partName = addPartNameInput.getText().trim();
        stockNum = Integer.parseInt(addPartInventoryInput.getText().trim());
        partPrice = Double.parseDouble(addPartCostInput.getText().trim());
        partMin = Integer.parseInt(addPartMinInput.getText().trim());
        partMax = Integer.parseInt(addPartMaxInput.getText().trim());
        machineID = Integer.parseInt(addPartMachineIDInput.getText().trim());
        Inventory.addPart(new InHouse(partIDGenerator(), partName, partPrice, stockNum, partMin, partMax, machineID));
    }

    public void addOutsourcedPart() {
        partName = addPartNameInput.getText().trim();
        stockNum = Integer.parseInt(addPartInventoryInput.getText().trim());
        partPrice = Double.parseDouble(addPartCostInput.getText().trim());
        partMin = Integer.parseInt(addPartMinInput.getText().trim());
        partMax = Integer.parseInt(addPartMaxInput.getText().trim());
        companyName = (addPartMachineIDInput.getText().trim()).toString();
        Inventory.addPart(new Outsourced(partIDGenerator(), partName, partPrice, stockNum, partMin, partMax, companyName));
        System.out.println(Inventory.getAllParts().get(0));

    }
    
    public void clearInputFields(){
        addPartNameInput.clear();
        addPartInventoryInput.clear();
        addPartCostInput.clear();
        addPartMinInput.clear();
        addPartMaxInput.clear();
        addPartMachineIDInput.clear();
    }

    public void inHouseOrOutsourced(){
        if(!addPartInHouseRadio.isSelected()){
            clearInputFields();
            machineIDCompNameLabel.setText("Company Name");
        }else{
            clearInputFields();
            machineIDCompNameLabel.setText("Machine ID");


        }
    }

    public void savePart(ActionEvent event) throws IOException{
        if(addPartInHouseRadio.isSelected()){
            addInHousePart();
        }else{
            addOutsourcedPart();
        }

        openMainForm(event);
    }

    public void initialize(){


    }



}
