package Model;

/**
 * Extension of Part Model, declares that a part has been made outside of the company
 */
public class OutSourced extends Part{
    /**
     * CompanyName is the variable that differs from the InHouse Model
     */
    private static String CompanyName;

    /**
     * Constructor for OutSourced Model
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param Companyname
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String Companyname){
        super(id, name, price, stock, min, max);
        SetCompanyName(Companyname);
    }

    public OutSourced() {

    }

    /**
     * OutSourced Setter
     * @param Companyname
     */
    public void SetCompanyName(String Companyname){
        CompanyName = Companyname;
    }

    /**
     * OutSourced Getter
     * @return
     */
    public static String getCompanyName(){
        return CompanyName;
    }

}
