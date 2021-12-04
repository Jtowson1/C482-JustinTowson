package Controller;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Optional;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main_Form implements Initializable {
    Stage stage;
    Parent scene;

    @FXML private Button AddPartBtn;
    @FXML private Button AddProductBtn;
    @FXML private Button DeletePartBtn;
    @FXML private Button DeleteProductBtn;
    @FXML private Button ExitBtn;
    @FXML private Button ModifyPartBtn;
    @FXML private Button ModifyProductBtn;
    @FXML private TableView<Part> PartTableView;
    @FXML private TableView<Products> ProductTableView;
    @FXML private TableColumn<Part, Double> PPUnitCol;
    @FXML private TableColumn<Part, Integer> PartIDCol;
    @FXML private TableColumn<Part, Integer> PartInvLevelCol;
    @FXML private TableColumn<Part, String> PartNameCol;
    @FXML private TableColumn<Products, Integer> ProductIDCol;
    @FXML private TableColumn<Products, Integer> ProductInvLevelCol;
    @FXML private TableColumn<Products, String> ProductNameCol;
    @FXML private TableColumn<Products, Double> ProductPPUnitCol;
    @FXML private TextField SearchPartTxt = new TextField();
    @FXML private TextField SearchProductTxt;
    @FXML private TextField keywordTextField;
    private ObservableList<Part> part = Inventory.getAllParts();
    private ObservableList<Products> Prod = Inventory.getAllProducts();
    private static int ModPart;
    public static int TheModPart(){
        return ModPart;
    }

    /**
     * Sends User to Add Part screen
     * @param event Click Add Part Button
     * @throws IOException
     */
    @FXML void MainFormAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Add_Part.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Sends User to Add Product Screen
     * @param event Click Add Product Button
     * @throws IOException
     */
    @FXML void MainFormAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Add_product.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Deletes Parts from the Main Form Part Table
     * @param event Click Delete Button on Parts Table
     */
    @FXML void MainFormDeletePart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete selected part, do you want to continue?");

        Optional<ButtonType> results = alert.showAndWait();
        if(results.isPresent()  && results.get()  == ButtonType.OK){


        Part SP = PartTableView.getSelectionModel().getSelectedItem();
        Inventory.removePart(SP);
    }
    }

    /**
     * Deletes Products from the Main Form Product Table
     * @param event Delete Product Button Clicked
     */
    @FXML void MainFormDeleteProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete selected product, do you want to continue?");

        Optional<ButtonType> results = alert.showAndWait();
        if (results.isPresent() && results.get() == ButtonType.OK) {
            Products Sp = (Products) ProductTableView.getSelectionModel().getSelectedItem();
            if (Sp.SizeOfAssociatedParts() == 0) {
                Inventory.removeProduct(Sp);
            } else {
                Alert aLert = new Alert(Alert.AlertType.ERROR);
                aLert.setTitle("Error Dialog");
                aLert.setContentText("All Associated Parts Must Be Deleted First");
                aLert.showAndWait();
                return;
            }
        }
    }
    /**
     * System Exit to terminate program
     * @param event Exit Button Clicked
     */
    @FXML void MainFormExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Sends User to the Modify Part Screen
     * @param event Modify Part Button Clicked
     * @throws IOException Part not selected
     */
    @FXML void MainFormModifyPart(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/Modify_Part.fxml"));
            loader.load();

            Modify_Part ModPartControl = loader.getController();
            ModPartControl.getModPart(PartTableView.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.showAndWait();

        } catch(IllegalStateException e){

        } catch(NullPointerException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please Make A Selection!");
            alert.showAndWait();
        }
    }

    /**
     * Button to send User to the Modify Product Screen
      * @param event Modify Product Button Clicked
     * @throws IOException Product not Selected
     */
    @FXML void MainFormModifyProduct(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/Modify_Product.fxml"));
            loader.load();

            Modify_Product ModProdControl = loader.getController();
            ModProdControl.getModProduct(ProductTableView.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.showAndWait();
        } catch (IllegalStateException e){

        } catch (NullPointerException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please Make A Selection!");
            alert.showAndWait();
        }
    }

    /**
     * On click starts the dynamic filter search of the Part Table
     */
    public void MainSearchPart(MouseEvent mouseEvent) {
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
     * On click starts the dynamic filter search of the Product Table
     */
    public void MainFormSearchProduct(MouseEvent mouseEvent) {
        FilteredList<Products> filteredProducts = new FilteredList<>(Prod, b -> true);

        SearchProductTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProducts.setPredicate(Products ->{

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchWord = newValue.toLowerCase();

                if(Products.getName().toLowerCase().indexOf(searchWord) > -1){
                    return true;
                } else if(String.valueOf(Products.getId()).indexOf(searchWord) > -1){
                    return true;
                } else
                    return false;
            });
        });
        SortedList<Products> sortedProduct = new SortedList<>(filteredProducts);

        sortedProduct.comparatorProperty().bind(ProductTableView.comparatorProperty());

        ProductTableView.setItems(sortedProduct);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
/**
 * Populates Table Views on Main Form
 */
        PartTableView.setItems(Inventory.getAllParts());
        PartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PPUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        ProductTableView.setItems(Inventory.getAllProducts());
        ProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductPPUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProductInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
}





