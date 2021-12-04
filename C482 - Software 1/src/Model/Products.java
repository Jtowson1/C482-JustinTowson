package Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product Model Variables
 */
public class Products {

    private String name;
    private int id;
    private ObservableList<Part> AssociatedParts = FXCollections.observableArrayList();
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for product model
     * @param ProductId
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Products(int ProductId, String name, double price, int stock, int min, int max) {
        this.id = ProductId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public Products() {
    }

    /**
     * Setters and Getters for Product Model
     * @return
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }


    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Getter for Observable list of parts associated with Product
     * @return
     */
    public ObservableList<Part> getAssociatedParts() {
        return AssociatedParts;
    }

    /**
     * Method to add associated part to a product
     * @param AssociatedParts
     */
    public void AddAssociatedPart(Part AssociatedParts) {
        this.AssociatedParts.add(AssociatedParts);
    }

    /**
     * Method to remove associated part from a product
     * @param part
     */
    public void removeAssociatedPart(Part part) {
        AssociatedParts.remove(part);
    }

    public int SizeOfAssociatedParts(){
        return AssociatedParts.size();
    }
}


