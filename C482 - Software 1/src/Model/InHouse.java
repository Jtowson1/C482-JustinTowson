package Model;

/**
 * Extension of Part Model for parts made within the company
 */
public class InHouse extends Part {

    /**
     *  Machine ID is the variable that sets the InHouse Model apart
     */
    private static int MachineID;

    public InHouse(int id, String name, double price, int stock, int min, int max, int MachineId) {
     super(id, name, price, stock, min, max);
        SetMachineID(MachineId);
    }

    public InHouse() {

    }

    /**
     * InHouse Setter
     * @param MachineId
     */
    public void SetMachineID(int MachineId) {
        MachineID = MachineId;
    }

    /**
     * InHouse Getter
     * @return
     */
    public static int getMachineID() {
        return MachineID;
    }



}

