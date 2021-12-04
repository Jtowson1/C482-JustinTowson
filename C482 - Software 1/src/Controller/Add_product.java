package Controller;
import Model.Inventory;
import Model.Part;
import Model.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * RUNTIME ERROR: I preemptively labeled the tableviews before realizing that both were going to be parts tables so I had errors when trying to add parts and I had to realize that I had not changed
 *  the fx:id associated with the Tables.
 */
public class Add_product implements Initializable {
    Stage stage;
    Parent scene;

    @FXML private Button AddBtn;
    @FXML private Button CancelBtn;
    @FXML private TableColumn<?, ?> InvLevel1Col;
    @FXML private TableColumn<?, ?> InvLevelCol;
    @FXML private TextField InventoryTxt;
    @FXML private TextField MaxTxt;
    @FXML private TextField MinTxt;
    @FXML private TableColumn<?, ?> PPUnit1Col;
    @FXML private TableColumn<?, ?> PPUnitCol;
    @FXML private TableColumn<?, ?> PartID1Col;
    @FXML private TableColumn<?, ?> PartIDCol;
    @FXML private TableColumn<?, ?> PartName1Col;
    @FXML private TableColumn<?, ?> PartNameCol;
    @FXML private TextField PriceTxt;
    @FXML private TextField ProductIDTxt;
    @FXML private TextField ProductNameTxt;
    @FXML private Button RemovePartBtn;
    @FXML private Button SaveBtn;
    @FXML private TextField SearchPartTxt;
    @FXML private TableView<Part> PartTableView;
    @FXML private TableView<Part> Part1TableView;
    Products products;
    private ObservableList<Part> part = Inventory.getAllParts();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    /**
     * Adds products from the Part table to the Associated Part Table for the new product
     * @param event Press The Add Button
     */
    @FXML void AddProductAdd(ActionEvent event) {
        Part SP = PartTableView.getSelectionModel().getSelectedItem();
        associatedParts.add(SP);

    }

    /**
     * Cancels out of the Add Product screen and sends user back to Main Form
     * @param event Press the Cancel Button
     * @throws IOException
     */
    @FXML void AddProductCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "New product has not been saved, do you want to continue?");

        Optional<ButtonType> results = alert.showAndWait();
        if (results.isPresent() && results.get() == ButtonType.OK) {

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Main_Form.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }

    /**
     * Removes Associated Part to new product
     * @param event Press The Remove Button
     */
    @FXML void AddProductRemoveAssociatedPart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove associated part, do you want to continue?");

        Optional<ButtonType> results = alert.showAndWait();
        if (results.isPresent() && results.get() == ButtonType.OK) {

            Part SP = Part1TableView.getSelectionModel().getSelectedItem();
            associatedParts.remove(SP);

        }
    }

    /**
     * Saves the newly added Product if all text fields are valid
     * @param event Press the Save Button with all fields filled in
     * @throws IOException throws exception for Invalid Values and Null Values
     */
    @FXML void AddProductSave(ActionEvent event) throws IOException {
        try {
            String name = ProductNameTxt.getText();
            String ProductID = ProductIDTxt.getText();
            String price = PriceTxt.getText();
            String stock = InventoryTxt.getText();
            String min = MinTxt.getText();
            String max = MaxTxt.getText();

            int NewProductID = 1010;
            for (Products prod : Inventory.getAllProducts()) {
                if (prod.getId() >= NewProductID) {
                    NewProductID = prod.getId() + 5;
                }
            }

            if (Integer.parseInt(min) > Integer.parseInt(max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Minimum Value must be lower than Maximum value");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(min) > Integer.parseInt(stock)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Minimum Value must be lower than Inventory value");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(stock) > Integer.parseInt(max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory Value must be lower than Maximum value");
                alert.showAndWait();
                return;
            }
            if (name.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please Enter Valid Product Name!");
                alert.showAndWait();
                return;
            } else{
                Products prod = new Products();
                prod.setId(NewProductID);
                prod.setName(name);
                prod.setPrice(Double.parseDouble(price));
                prod.setStock(Integer.parseInt(stock));
                prod.setMin(Integer.parseInt(min));
                prod.setMax(Integer.parseInt(max));
                for(Part SP : associatedParts)
                    prod.AddAssociatedPart(SP);
                Inventory.addProduct(prod);
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
     * Starts the dynamic filter search of the Parts Table
     * @param event On Click of Search Bar
     */
    @FXML void AddProductSearch(ActionEvent event) {
        FilteredList<Part> filteredParts = new FilteredList<>(part, b -> true);

        SearchPartTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredParts.setPredicate(Part ->{

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchword = newValue.toLowerCase();

                if(Part.getName().toLowerCase().indexOf(searchword) > -1){
                    return true;
                } else if(String.valueOf(Part.getId()).indexOf(searchword) > -1){
                    return true;
                } else
                    return false;
            });
        });
        SortedList<Part> sortedPart = new SortedList<>(filteredParts);

        sortedPart.comparatorProperty().bind(PartTableView.comparatorProperty());

        PartTableView.setItems(sortedPart);
    }

    /**
     * Sets Product ID to disabled and displays Auto Generated, also populates the tableviews
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProductIDTxt.setDisable(true);
        ProductIDTxt.setText("Auto Generated");

        PartTableView.setItems(Inventory.getAllParts());
        PartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PPUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        InvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        Part1TableView.setItems(associatedParts);
        PartID1Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartName1Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        PPUnit1Col.setCellValueFactory(new PropertyValueFactory<>("price"));
        InvLevel1Col.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }


}

