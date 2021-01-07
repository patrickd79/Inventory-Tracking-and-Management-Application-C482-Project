package C482;
/**
 *Contains methods and data necessary to create an In House Part,
 * such as constructors, setters, and getters.
 * @author Patrick Denney
 */
public class InHouse extends Part{
    private int machineID;

    /**
     * Constructor to create an In House part
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     *
     * @param machineID machineID to set for new part
     */
    public void setMachineID(int machineID){
        this.machineID = machineID;
    }

    /**
     *
     * @return machine ID of part in question
     */
    public int getMachineID(){
        return machineID;
    }


}
