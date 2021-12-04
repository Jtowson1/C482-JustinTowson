package Model;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

/**
 * Inventory Model has methods for both Part and Product to use
 */
public class Inventory {
    private static ObservableList<Products> AllProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> AllParts = FXCollections.observableArrayList();

    /**
     * Add Part method
     * @param part
     */
    public static void addPart(Part part) {
        AllParts.add(part);
    }

    /**
     * Potential Search Methods for the Part Model
     * @param searchPartID
     * @return
     */
    public static Part LookUpPart(int searchPartID) {
        for (int i=0;i < getAllParts().size(); i++) {
            if (getAllParts().get(i).getId() == searchPartID) {
                return AllParts.get(i);
            }
        }
        return null;
    }

    public static ObservableList<Part> LookUpPart(String PartNametoSearch){
        if(!getAllParts().isEmpty()){
            ObservableList searchPartsList = FXCollections.observableArrayList();
            for (Part prt : getAllParts()) {
                if (prt.getName().contains(PartNametoSearch)){
                    searchPartsList.add(prt);
                }
                return searchPartsList;
            }
        }
        return null;
    }

    /**
     * Potential Search Methods for the Product Model
     * @param searchProductID
     * @return
     */
    public static Products LookUpProduct(int searchProductID){
        for (int i=0;i < getAllProducts().size(); i++) {
            if (getAllProducts().get(i).getId() == searchProductID) {
                return AllProducts.get(i);
            }
        }
        return null;
    }

    public static ObservableList<Products>LookUpProduct(String ProductNametoSearch){
        if(!getAllProducts().isEmpty()){
            ObservableList searchProductsList = FXCollections.observableArrayList();
            for (Products prod : getAllProducts()) {
                if (prod.getName().contains(ProductNametoSearch)){
                    searchProductsList.add(prod);
                }
                return searchProductsList;
            }
        }
        return null;
    }

    /**
     * Remove Part Method
     * @param part
     */
    public static void removePart(Part part){
        AllParts.remove(part);
    }

    /**
     * Remove Product Method
     * @param products
     */
    public static void removeProduct(Products products){
        AllProducts.remove(products);
    }

    /**
     * Add Product Method
     * @param product
     */
    public static void addProduct(Products product) {
        AllProducts.add(product);
    }

    /**
     * Observable List of All Products
     * @return
     */
    public static ObservableList<Products> getAllProducts() {
        return AllProducts;
    }

    /**
     * Observable List of All Parts
     * @return
     */
    public static ObservableList<Part> getAllParts() {
        return AllParts;
    }

    /**
     *  Method for updating part information
     * @param updatePart
     */
    public static void UpdatePart(Part updatePart){
        for(int i = 0; i< AllParts.size(); i++){
            if(AllParts.get(i).getId() == updatePart.getId()){
                AllParts.set(i,updatePart);
            }
        }
    }

    /**
     *  Method for updating product information
     * @param updateProduct
     */
    public static void UpdateProduct(Products updateProduct){
        for(int i = 0; i< AllProducts.size(); i++){
            if(AllProducts.get(i).getId() == updateProduct.getId()){
                AllProducts.set(i,updateProduct);
            }
        }
    }

    /**
     *  Method to find the size of parts list
     * @return
     */
    public int SizeOfPartList(){
        return getAllParts().size();
    }

    /**
     * Method to find the size of products list
     * @return
     */
    public int SizeOfProductList(){
        return getAllProducts().size();
    }


}



