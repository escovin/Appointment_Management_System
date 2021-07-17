package model;

/**
 * customers class
 * @author Erik Scovin
 */
public class Customers {

    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private int divisionId;

    public Customers(int customerId, String customerName, String customerAddress,
                     String customerPostalCode, String customerPhoneNumber, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.divisionId = divisionId;
    }

    public int getCustomerId() { return customerId; }

    public String getCustomerName() { return customerName; }

    public String getCustomerAddress() { return customerAddress; }

    public String getCustomerPostalCode() { return customerPostalCode; }

    public String getCustomerPhoneNumber() { return customerPhoneNumber; }

    public int getDivisionId() { return divisionId; }


    //Corrects combo box display by overriding toString
    @Override
    public String toString() {
        return (customerId + " " + customerName);
    }


}
