package Controller;
import Model.Inventory;
import Model.Part;
import Model.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Modify_Product implements Initializable {
    Stage stage;
    Parent scene;

    @FXML private Button CancelBtn;
    @FXML private TableColumn<?, ?> ProductID1Col;
    @FXML private TableColumn<?, ?> ProductIDCol;
    @FXML private TextField ProductIDTxt;
    @FXML private TableColumn<?, ?> ProductInvLevel1Col;
    @FXML private TableColumn<?, ?> ProductInvLevelCol;
    @FXML private TextField ProductInvTxt;
    @FXML private TextField ProductMaxTxt;
    @FXML private TextField ProductMinTxt;
    @FXML private TableColumn<?, ?> ProductName1Col;
    @FXML private TableColumn<?, ?> ProductNameCol;
    @FXML private TextField ProductNameTxt;
    @FXML private TableColumn<?, ?> ProductPPUnit1Col;
    @FXML private TableColumn<?, ?> ProductPPUnitCol;
    @FXML private TextField ProductPriceTxt;
    @FXML private Button RemovePartBtn;
    @FXML private Button SaveBtn;
    @FXML private TextField SearchProductTxt;
    @FXML private TableView<Part> PartTableView;
    @FXML private TableView<Part> Part1TableView;
    private ObservableList<Part> prt = Inventory.getAllParts();
    private ObservableList<Part> pArt = FXCollections.observableArrayList();
    private Products products;


    /**
     * Receiver for data when user selects a product and clicks modify
     * @param products
     */
    public void getModProduct(Products products){
        ProductNameTxt.setText(products.getName());
        ProductIDTxt.setText(String.valueOf(products.getId()));
        ProductPriceTxt.setText(String.valueOf(products.getPrice()));
        ProductInvTxt.setText(String.valueOf(products.getStock()));
        ProductMinTxt.setText(String.valueOf(products.getMin()));
        ProductMaxTxt.setText(String.valueOf(products.getMax()));
        pArt.addAll(products.getAssociatedParts());

    }

    /**
     * Selects part from Parts Table and puts it in Associated Part Table
     * @param event Hit the Add button with a part selected
     */
    @FXML void ModifyProductAdd(ActionEvent event) {
        Part SP = PartTableView.getSelectionModel().getSelectedItem();
        pArt.add(SP);
    }

    /**
     * Cancels out of the Modify Product Screen and sends User back to Main Form
     * @param event Press the Cancel Button
     * @throws IOException
     */
    @FXML void ModifyProductCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Changes made to product have not been saved, do you want to continue?");

        Optional<ButtonType> results = alert.showAndWait();
        if (results.isPresent() && results.get() == ButtonType.OK) {

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Main_Form.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Removes Selected Part Associated with Product
     * @param event Press the Remove Button
     */
    @FXML void ModifyProductRemovedAssociatedPart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove associated part, do you want to continue?");
        Optional<ButtonType> results = alert.showAndWait();
        if (results.isPresent() && results.get() == ButtonType.OK) {

            Part SP = Part1TableView.getSelectionModel().getSelectedItem();
            pArt.removeAll(SP);
        }
    }

    /**
     * Saves any changes made while modifying a part
     * @param event Press The Save Button
     * @throws IOException
     */
    @FXML void ModifyProductSave(ActionEvent event) throws IOException {
        try {
            String ProductName = ProductNameTxt.getText();
            String ProductID = ProductIDTxt.getText();
            String Price = ProductPriceTxt.getText();
            String inventory = ProductInvTxt.getText();
            String Min = ProductMinTxt.getText();
            String Max = ProductMaxTxt.getText();

            if (Integer.parseInt(Min) > Integer.parseInt(Max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Minimum Value must be lower than Maximum value");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(Min) > Integer.parseInt(inventory)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Minimum Value must be lower than Inventory value");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(inventory) > Integer.parseInt(Max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory Value must be lower than Maximum value");
                alert.showAndWait();
                return;
            }
            if (ProductName.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please Enter Valid Product Name!");
                alert.showAndWait();
                return;
            } else {
                Products prod = new Products();
                prod.setId(Integer.parseInt(String.valueOf(ProductID)));
                prod.setName(ProductName);
                prod.setPrice(Double.parseDouble(Price));
                prod.setStock(Integer.parseInt((inventory)));
                prod.setMin(Integer.parseInt((Min)));
                prod.setMax(Integer.parseInt((Max)));
                for(Part SP : pArt){
                    prod.AddAssociatedPart(SP);}
                Inventory.UpdateProduct(prod);

            }


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Main_Form.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }catch(NumberFormatException e){
            System.out.println("Please Enter Valid Values in Text Fields: Name = String, Price = Double, Stock = Integer, Min = Integer, Max = Integer");
        }
    }

    /**
     * Starts dynamic filter search of Part Table
     * @param mouseEvent Click Search Bar
     */
    public void AssociatedPartSearch(MouseEvent mouseEvent) {
        FilteredList<Part> filteredParts = new FilteredList<>(prt, b -> true);

        SearchProductTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredParts.setPredicate(Part ->{

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchword = newValue.toLowerCase();

                if(Part.getName().toLowerCase().indexOf(searchword) > -1){
                    return true;
                } else return String.valueOf(Part.getId()).indexOf(searchword) > -1;
            });
        });
        SortedList<Part> sortedPart = new SortedList<>(filteredParts);

        sortedPart.comparatorProperty().bind(PartTableView.comparatorProperty());

        PartTableView.setItems(sortedPart);
    }

    /**
     * Populates Tables on Modify Product Form
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProductIDTxt.setDisable(true);

        PartTableView.setItems(Inventory.getAllParts());
        ProductID1Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductName1Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductPPUnit1Col.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProductInvLevel1Col.setCellValueFactory(new PropertyValueFactory<>("stock"));

        Part1TableView.setItems(pArt);
        ProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductPPUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProductInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));



    }


}


