package C482;
/**
 *Contains methods and data necessary to create an Outsourced Part, such as constructors, setters, and getters.
 * @author Patrick Denney
 */
public class Outsourced extends Part{

    private String companyName;

    /**
     * Constructor method to create an Outsourced Part
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    Outsourced(int id, String name,double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;

    }

    /**
     * Sets the company name for an outsourced part
     * @param companyName
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     *
     * @return name of company supplying the outsourced part
     */
    public String getCompanyName(){
        return companyName;
    }
}
