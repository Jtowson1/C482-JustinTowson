package Controller;
import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Modify_Part implements Initializable {
    Stage stage;
    Parent scene;


    @FXML
    private Label InHouseLabel;
    @FXML
    private Button CancelBtn;
    @FXML
    private RadioButton InHouseRBtn;
    @FXML
    private TextField MachineIDTxt;
    @FXML
    private RadioButton OutSourcedRBtn;
    @FXML
    private TextField PartIDTxt;
    @FXML
    private TextField PartInventoryTxt;
    @FXML
    private TextField PartMaxTxt;
    @FXML
    private TextField PartMinTxt;
    @FXML
    private TextField PartNameTxt;
    @FXML
    private TextField PartPriceTxt;
    @FXML
    private Button SaveBtn;

    /**
     * Receiver for the data coming from the Main Form when user selects a part and clicks Modify
     * @param part
     */
    public void getModPart(Part part) {
        PartNameTxt.setText(part.getName());
        PartIDTxt.setText(String.valueOf(part.getId()));
        PartPriceTxt.setText(String.valueOf(part.getPrice()));
        PartInventoryTxt.setText(String.valueOf(part.getStock()));
        PartMinTxt.setText(String.valueOf(part.getMin()));
        PartMaxTxt.setText(String.valueOf(part.getMax()));

        if (part instanceof InHouse) {
            InHouseRBtn.setSelected(true);
            MachineIDTxt.setText(String.valueOf(InHouse.getMachineID()));
        } else {
            OutSourcedRBtn.setSelected(true);
            InHouseLabel.setText("Company Name");
            MachineIDTxt.setText(OutSourced.getCompanyName());
        }
    }

    ;

    /**
     * Cancels out of the Modify Part Screen and sends user back to Main Form
     * @param event Press the Cancel Button
     * @throws IOException
     */
    @FXML
    void ModifyPartCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Part Updates have not been saved, would you like to continue?");

        Optional<ButtonType> results = alert.showAndWait();
        if (results.isPresent() && results.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Main_Form.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Sets Radio Button to InHouse
     * @param event Click InHouse Radio Button
     */
    @FXML
    void ModifyPartInHouse(ActionEvent event) {
        InHouseRBtn.setSelected(true);
        InHouseLabel.setText("Machine ID");
    }

    /**
     * Sets Radio Button to OutSourced
     * @param event Click OutSourced Radio Button
     */
    @FXML
    void ModifyPartOutSourced(ActionEvent event) {
        OutSourcedRBtn.setSelected(true);
        InHouseLabel.setText("Company Name");
    }

    /**
     * Saves the changes in Modify Part as long as all text fields are valid
     * @param event Click the Save Button
     * @throws IOException Exceptions thrown for invalid values or null values
     */
    @FXML
    void ModifyPartSave(ActionEvent event) throws IOException {
        try{
        String PartName = PartNameTxt.getText();
        String PartID = PartIDTxt.getText();
        String Price = PartPriceTxt.getText();
        String inventory = PartInventoryTxt.getText();
        String Min = PartMinTxt.getText();
        String Max = PartMaxTxt.getText();
        String Manufacture = MachineIDTxt.getText();
            if (Integer.parseInt(Min) > Integer.parseInt(Max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Minimum Value Must Be Lower Than Maximum Value");
                alert.showAndWait();
                return;


            }
           if (Integer.parseInt(Min) > Integer.parseInt(inventory) || Integer.parseInt(inventory) > Integer.parseInt(Max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory Value Must Be Greater Than the Minimum Value and Lower Than The Maximum Value");
                alert.showAndWait();
                return;

            }
           if (PartName.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please Enter Valid Part Name!");
                alert.showAndWait();
                return;

            }
            if (Double.parseDouble(Price) < 0.00) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please Enter Price Greater Than 0!");
                alert.showAndWait();
                return;
            }
            if(Manufacture.isBlank()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please Enter A Valid Machine ID or Company Name");
                alert.showAndWait();
                return;
            }
            if(InHouseRBtn.isSelected()) {
                    InHouse InPart = new InHouse();
                    InPart.setId(Integer.parseInt(String.valueOf(PartID)));
                    InPart.setName(PartName);
                    InPart.setPrice(Double.parseDouble(Price));
                    InPart.setStock(Integer.parseInt(inventory));
                    InPart.setMin(Integer.parseInt(Min));
                    InPart.setMax(Integer.parseInt(Max));
                    InPart.SetMachineID(Integer.parseInt(Manufacture));
                    Inventory.UpdatePart(InPart);

                }
            if (OutSourcedRBtn.isSelected()) {
                    OutSourced OutPart = new OutSourced();
                    OutPart.setId(Integer.parseInt(String.valueOf(PartID)));
                    OutPart.setName(PartName);
                    OutPart.setPrice(Double.parseDouble(Price));
                    OutPart.setStock(Integer.parseInt(inventory));
                    OutPart.setMin(Integer.parseInt(Min));
                    OutPart.setMax(Integer.parseInt(Max));
                    OutPart.SetCompanyName(Manufacture);
                    Inventory.UpdatePart(OutPart);

                }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Main_Form.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please Enter Valid Values in Text Fields: Name = String, Price = Double, Stock = Integer, Min = Integer, Max = Integer, Machine Id = Integer, Company Name = String");
            alert.showAndWait();
        }
    }
        @Override
        public void initialize (URL url, ResourceBundle resourceBundle){
            PartIDTxt.setDisable(true);
        }

    }
