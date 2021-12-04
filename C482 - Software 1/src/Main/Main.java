

package Main;

import Model.*;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
/**
 * The JavaDoc Folder was Placed in the Main Folder right below the src file
 * This is the Main Class of the application
 * FUTURE ENHANCEMENT:I think it would be a good idea to include Username and Password to access this data since they are able to modify parts and products,just so a disgruntled employee can
 * change things for the worse
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Main_Form.fxml"));
        stage.setTitle("Main Form");
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    public static void main(String[] args) {
        Part part1 = new InHouse(5, "Burner Coil", 7.99, 30, 10, 50, 275);
        Part part2 = new InHouse(10, "Interface Panel", 38.79, 2, 1, 3, 49);
        Part part3 = new OutSourced(15, "Heat Coil", 12.00, 1, 0, 2, "Coils r' us");
        Part part4 = new OutSourced(20, "Oven Knob", 2.99, 12, 5, 15, "Knobs etc.");
        Part part5 = new InHouse(25, "Door Handle", 15.00, 20, 10, 20, 491);
        Products product1 = new Products(1000, "Electric Oven", 949.99, 12, 2, 15);
        Products product2 = new Products(1005, "Gas Oven", 280.00, 7, 1, 10);
        Products product3 = new Products(1010, "Wood-Burn Oven", 499.75, 4, 0, 5);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);


        launch(args);
    }
}
