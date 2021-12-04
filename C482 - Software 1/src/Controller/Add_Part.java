package Controller;
import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Add_Part implements Initializable {
        Stage stage;
        Parent scene;

        @FXML
        private Label InHouseLabel;
        @FXML
        private Button CancelBtn;
        @FXML
        private RadioButton InHouseRBtn;
        @FXML
        private TextField InventoryTxt;
        @FXML
        private TextField MachineIDTxt;
        @FXML
        private TextField MaxTxt;
        @FXML
        private TextField MinTxt;
        @FXML
        private RadioButton OutSourcedRBtn;
        @FXML
        private TextField PartIDTxt;
        @FXML
        private TextField PartNameTxt;
        @FXML
        private TextField PriceTxt;
        @FXML
        private Button SaveBtn;

        private String Manufacture;


        /**
         * Saves new part if all text fields are valid
         * @param event
         * @throws IOException
         */
        @FXML
        void AddPartSave(ActionEvent event) throws IOException {
                try {
                        String PartName = PartNameTxt.getText();
                        String PartID = PartIDTxt.getText();
                        String Price = PriceTxt.getText();
                        String inventory = InventoryTxt.getText();
                        String Min = MinTxt.getText();
                        String Max = MaxTxt.getText();
                        String Manufacture = MachineIDTxt.getText();

                        int NewPartID = 25;
                        for (Part prt : Inventory.getAllParts()) {
                                if (prt.getId() >= NewPartID) {
                                        NewPartID = prt.getId() + 5;
                                }
                        }

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
                        if(PartName.isBlank()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Dialog");
                                alert.setContentText("Please Enter Valid Part Name!");
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
                        if (Double.parseDouble(Price) < 0.00) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Dialog");
                                alert.setContentText("Please Enter Price Greater Than 0!");
                                alert.showAndWait();
                                return;
                        }
                        if(InHouseRBtn.isSelected()) {
                                InHouse InPart = new InHouse();
                                InPart.setId(Integer.parseInt(String.valueOf(NewPartID)));
                                InPart.setName(PartName);
                                InPart.setPrice(Double.parseDouble(Price));
                                InPart.setStock(Integer.parseInt(inventory));
                                InPart.setMin(Integer.parseInt(Min));
                                InPart.setMax(Integer.parseInt(Max));
                                InPart.SetMachineID(Integer.parseInt(Manufacture));
                                Inventory.addPart(InPart);

                        }
                        if (OutSourcedRBtn.isSelected()) {
                                OutSourced OutPart = new OutSourced();
                                OutPart.setId(Integer.parseInt(String.valueOf(NewPartID)));
                                OutPart.setName(PartName);
                                OutPart.setPrice(Double.parseDouble(Price));
                                OutPart.setStock(Integer.parseInt(inventory));
                                OutPart.setMin(Integer.parseInt(Min));
                                OutPart.setMax(Integer.parseInt(Max));
                                OutPart.SetCompanyName(Manufacture);
                                Inventory.addPart(OutPart);

                        }

                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/View/Main_Form.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();

                } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setContentText("Please Enter Valid Values in Text Fields: Name = String, Price = Double, Stock = Integer, Min = Integer, Max = Integer, Machine Id = Integer, Company Name = String");
                        alert.showAndWait();
                }

        }

        /**Cancels out of Add Part screen and sends user back to Main Form*/
        @FXML
        void CancelAddPartSave(ActionEvent event) throws IOException {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "New Part has not been saved, would you like to continue?");

                Optional<ButtonType> results = alert.showAndWait();
                if (results.isPresent() && results.get() == ButtonType.OK) {


                        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/View/Main_Form.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                }
        }
        /**Sets Radio Button to InHouse*/
        @FXML
        void AddPartInHouse(ActionEvent event) {
                InHouseRBtn.setSelected(true);
                InHouseLabel.setText("Machine ID");
                Manufacture = "InHouse";
        }
        /**Set Radio Button to OutSourced*/
        @FXML
        void AddPartOutsourced(ActionEvent event) {
                InHouseRBtn.setSelected(false);
                InHouseLabel.setText("Company Name");
                Manufacture = "OutSourced";

        }
/** Sets InHouse radio button as default, disables the Part ID textfield, and sets Part ID textfield to Auto Generated.*/
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                InHouseRBtn.setSelected(true);
                PartIDTxt.setDisable(true);
                PartIDTxt.setText("Auto Generated");
        }

}
