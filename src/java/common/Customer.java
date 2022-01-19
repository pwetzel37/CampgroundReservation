package common;

import java.io.Serializable;
import utilities.Debug;

/**
 * Represents a customer.
 *
 * @author Scott Harchar (2021)
 */
public class Customer implements Comparable<Customer>, Serializable {

    private int customerId; //Primary key, auto incremented 
    private int customerInformationId; //Other primary key, auto incremented
    private CustomerType customerType; 
    private String name;
    private String firstName;
    private String lastName;
    private String spouseFirstName;
    private String spouseLastName;
    private String primaryPhone;
    private String mobilePhone;
    private String spousePhone;
    private String addressLine1;
    private String addressLine2;
    private String town;
    private String state;
    private String zip;
    private String emailAddress;
    private String spouseEmailAddress;
    private RVType rvType;
    private int rvLength;
    private int numberOfSlideOuts;
    private boolean flagged;
    private String notes;
    private int groupId;

    /**
     * Constructs an empty customer.
     */
    public Customer() {}

    /**
     * Constructs a new customer
     * 
     * @param customerType The type of customer (individual, group, or club).
     * 
     * @param name The individual/club/group name of this customer.
     * 
     * @param firstName The first name of this customer.
     * 
     * @param lastName The last name of this customer.
     * 
     * @param spouseFirstName The first name of the spouse of this customer.
     * 
     * @param spouseLastName The last name of the spouse of this customer.
     * 
     * @param primaryPhone The primary phone of this customer.
     * 
     * @param mobilePhone The phone number of this customer.
     * 
     * @param spousePhone The secondary phone number of this customer.
     * 
     * @param addressLine1 The primary address line(street address) of this customer.
     * 
     * @param addressLine2 The secondary address line of this customer.
     * 
     * @param town The town of this customer's address.
     * 
     * @param state The state of this customer's address.
     * 
     * @param zip The zip code of this customer's address.
     * 
     * @param emailAddress The email address of this customer.
     * 
     * @param spouseEmailAddress The secondary email address of this customer.
     * 
     * @param rvType The RV type of this customer.
     * 
     * @param rvLength The length of the RV of this customer.
     * 
     * @param numberOfSlideOuts The number of slide outs this customer's RV has.
     * 
     * @param flagged Boolean that represents whether this customer is flagged or not.
     * 
     * @param notes The notes about this customer
     */
    public Customer(CustomerType customerType, String name, String firstName, 
            String lastName, String spouseFirstName, String spouseLastName, 
            String primaryPhone, String mobilePhone,  String spousePhone, 
            String addressLine1, String addressLine2, String town, String state,
            String zip, String emailAddress,  String spouseEmailAddress, 
            RVType rvType, int rvLength, int numberOfSlideOuts, boolean flagged,
            String notes) {
        this.customerType = customerType;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.spouseFirstName = spouseFirstName;
        this.spouseLastName = spouseLastName;
        this.primaryPhone = primaryPhone;
        this.mobilePhone = mobilePhone;
        this.spousePhone = spousePhone;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.town = town;
        this.state = state;
        this.zip = zip;
        this.emailAddress = emailAddress;
        this.spouseEmailAddress = spouseEmailAddress;
        this.rvType = rvType;
        this.rvLength = rvLength;
        this.numberOfSlideOuts = numberOfSlideOuts;
        this.flagged = flagged;
        this.notes = notes;
    }
    
    /**
     * Constructs a new customer with IDs.
     * 
     * @param customerId A number to represent the customer. 
     * This is the primary key in the customer table and is auto incremented
     * 
     * @param customerInformationId A number to represent the customer 
     * information index. This is the primary key in the customer information 
     * table and is auto incremented.
     * 
     * @param customerType The type of customer (individual, group, or club).
     * 
     * @param name The individual/club/group name of this customer.
     * 
     * @param firstName The first name of this customer.
     * 
     * @param lastName The last name of this customer.
     * 
     * @param spouseFirstName The first name of the spouse of this customer.
     * 
     * @param spouseLastName The last name of the spouse of this customer.
     * 
     * @param primaryPhone The primary phone of this customer.
     * 
     * @param mobilePhone The phone number of this customer.
     * 
     * @param spousePhone The secondary phone number of this customer.
     * 
     * @param addressLine1 The primary address line(street address) of this customer.
     * 
     * @param addressLine2 The secondary address line of this customer.
     * 
     * @param town The town of this customer's address.
     * 
     * @param state The state of this customer's address.
     * 
     * @param zip The zip code of this customer's address.
     * 
     * @param emailAddress The email address of this customer.
     * 
     * @param spouseEmailAddress The secondary email address of this customer.
     * 
     * @param rvType The RV type of this customer.
     * 
     * @param rvLength The length of the RV of this customer.
     * 
     * @param numberOfSlideOuts The number of slide outs this customer's RV has.
     * 
     * @param flagged Boolean that represents whether this customer is flagged or not.
     * 
     * @param notes The notes about this customer
     */
    public Customer(int customerId, int customerInformationId, CustomerType customerType, 
            String name, String firstName, String lastName, String spouseFirstName, 
            String spouseLastName, String primaryPhone, String mobilePhone, 
            String spousePhone, String addressLine1, String addressLine2, 
            String town, String state, String zip, String emailAddress, 
            String spouseEmailAddress, RVType rvType, int rvLength, 
            int numberOfSlideOuts, boolean flagged, String notes) {
        this.customerId = customerId;
        this.customerInformationId = customerInformationId;
        this.customerType = customerType;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.spouseFirstName = spouseFirstName;
        this.spouseLastName = spouseLastName;
        this.primaryPhone = primaryPhone;
        this.mobilePhone = mobilePhone;
        this.spousePhone = spousePhone;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.town = town;
        this.state = state;
        this.zip = zip;
        this.emailAddress = emailAddress;
        this.spouseEmailAddress = spouseEmailAddress;
        this.rvType = rvType;
        this.rvLength = rvLength;
        this.numberOfSlideOuts = numberOfSlideOuts;
        this.flagged = flagged;
        this.notes = notes;
    }
    
     /**
     * Constructs a new Customer for the purpose of connecting to a group
     * 
     * @param customerId A number to represent the customer. 
     * This is the primary key in the customer table and is auto incremented
     * 
     * @param customerInformationId A number to represent the customer 
     * information index. This is the primary key in the customer information 
     * table and is auto incremented.
     * 
     * @param customerType The type of customer (individual, group, or club).
     * 
     * @param name The individual/club/group name of this customer.
     * 
     * @param firstName The first name of this customer.
     * 
     * @param lastName The last name of this customer.
     * 
     * @param spouseFirstName The first name of the spouse of this customer.
     * 
     * @param spouseLastName The last name of the spouse of this customer.
     * 
     * @param primaryPhone The primary phone of this customer.
     * 
     * @param mobilePhone The phone number of this customer.
     * 
     * @param spousePhone The secondary phone number of this customer.
     * 
     * @param addressLine1 The primary address line(street address) of this customer.
     * 
     * @param addressLine2 The secondary address line of this customer.
     * 
     * @param town The town of this customer's address.
     * 
     * @param state The state of this customer's address.
     * 
     * @param zip The zip code of this customer's address.
     * 
     * @param emailAddress The email address of this customer.
     * 
     * @param spouseEmailAddress The secondary email address of this customer.
     * 
     * @param rvType The RV type of this customer.
     * 
     * @param rvLength The length of the RV of this customer.
     * 
     * @param numberOfSlideOuts The number of slide outs this customer's RV has.
     * 
     * @param flagged Boolean that represents whether this customer is flagged or not.
     * 
     * @param notes The notes about this customer
     * 
     * @param groupId The id of the group this customer belongs to
     */
    public Customer(int customerId, int customerInformationId, CustomerType customerType, 
            String name, String firstName, String lastName, String spouseFirstName, 
            String spouseLastName, String primaryPhone, String mobilePhone, 
            String spousePhone, String addressLine1, String addressLine2, 
            String town, String state, String zip, String emailAddress, 
            String spouseEmailAddress, RVType rvType, int rvLength, 
            int numberOfSlideOuts, boolean flagged, String notes, int groupId) {
        this.customerId = customerId;
        this.customerInformationId = customerInformationId;
        this.customerType = customerType;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.spouseFirstName = spouseFirstName;
        this.spouseLastName = spouseLastName;
        this.primaryPhone = primaryPhone;
        this.mobilePhone = mobilePhone;
        this.spousePhone = spousePhone;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.town = town;
        this.state = state;
        this.zip = zip;
        this.emailAddress = emailAddress;
        this.spouseEmailAddress = spouseEmailAddress;
        this.rvType = rvType;
        this.rvLength = rvLength;
        this.numberOfSlideOuts = numberOfSlideOuts;
        this.flagged = flagged;
        this.notes = notes;
        this.groupId = groupId;
    }

    /**
     * Gets the customer id of this customer.
     * 
     * @return The customer id of this customer. If there is no customer id, null
     * is returned.
     */
    public int getCustomerId(){
        return customerId;
    }
    
    /**
     * Sets the customer id of this customer. No error checking is performed.
     *
     * @param customerId The customer id that is set to this customer.
     */
    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }
    
    /**
     * Gets the customer information id of this customer.
     * 
     * @return The customer information id of this customer. 
     * If there is no customer information id, null is returned.
     */
    public int getCustomerInformationId(){
        return customerInformationId;
    }
    
    /**
     * Sets the customer information id of this customer. No error checking is 
     * performed.
     *
     * @param customerInformationId The customer information id that is set 
     * to this customer.
     */
    public void setCustomerInformationId(int customerInformationId){
        this.customerInformationId = customerInformationId;
    }
  
    /**
     * Gets the customer type of this customer.
     * 
     * @return The customer type of this customer. If there is no customer type,
     * null is returned.
     */
    public CustomerType getCustomerType(){
        return customerType;
    }
    
    /**
     * Sets the customer type of this customer. No error checking is performed.
     *
     * @param customerType The customer type that is set to this customer.
     */
    public void setCustomerType(CustomerType customerType){
        this.customerType = customerType;
    }
    
    /**
     * Gets the name of this customer.
     * 
     * @return The name of this customer. If there is no name, null is returned.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Sets the name of this customer. No error checking is performed.
     *
     * @param name The name that is set to this customer.
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Gets the first name of this customer.
     * 
     * @return The first name of this customer. If there is no first name, null
     * is returned.
     */
    public String getFirstName(){
        return firstName;
    }
    
    /**
     * Sets the first name of this customer. No error checking is performed.
     *
     * @param firstName The first name that is set to this customer.
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    /**
     * Gets the last name of this customer.
     * 
     * @return The last name of this customer. If there is no last name, null
     * is returned.
     */
    public String getLastName(){
        return lastName;
    }
    
    /**
     * Sets the last name of this customer. No error checking is performed.
     * 
     * @param lastName The last name that is set to this customer.
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    /**
     * Gets the first name of the spouse of this customer.
     * 
     * @return The first name of the spouse of this customer. 
     * If there is no first name, null is returned.
     */
    public String getSpouseFirstName(){
        return spouseFirstName;
    }
    
    /**
     * Sets the first name of the spouse of this customer. 
     * No error checking is performed.
     *
     * @param spouseFirstName The first name of the spouse that is set to this customer.
     */
    public void setSpouseFirstName(String spouseFirstName){
        this.spouseFirstName = spouseFirstName;
    }
    
    /**
     * Gets the last name of the spouse this customer.
     * 
     * @return The last name of the spouse of this customer. 
     * If there is no last name, null is returned.
     */
    public String getSpouseLastName(){
        return spouseLastName;
    }
    
    /**
     * Sets the last name of the spouse of this customer. 
     * No error checking is performed.
     * 
     * @param spouseLastName The last name of the spouse that is set to this customer.
     */
    public void setSpouseLastName(String spouseLastName){
        this.spouseLastName = spouseLastName;
    }
    
    /**
     * Gets the primary phone number of this customer.
     * 
     * @return The primary phone number of this customer. If there is no 
     * primary phone number, null is returned.
     */
    public String getPrimaryPhone(){
        return primaryPhone;
    }
    
    /**
     * Sets the primary phone number of this customer. 
     * No error checking is performed.
     * 
     * @param primaryPhone The primary phone number set to this customer.
     */
    public void setPrimaryPhone(String primaryPhone){
        this.primaryPhone = primaryPhone;
    }
    
    /**
     * Gets the mobile phone number of this customer.
     * 
     * @return The mobile phone number of this customer. If there is no 
     * mobile phone number, null is returned.
     */
    public String getMobilePhone(){
        return mobilePhone;
    }
    
    /**
     * Sets the mobile phone number of this customer. 
     * No error checking is performed.
     * 
     * @param mobilePhone The mobile phone number set to this customer.
     */
    public void setMobilePhone(String mobilePhone){
        this.mobilePhone = mobilePhone;
    }
    
    /**
     * Gets the spouse's phone number of this customer.
     * 
     * @return The spouse's phone number of this customer. 
     * If there is no secondary phone number, null is returned.
     */
    public String getSpousePhone(){
        return spousePhone;
    }
    
    /**
     * Sets the spouse's phone number of this customer. 
     * No error checking is performed.
     * 
     * @param spousePhone The spouse's phone number set to this customer.
     */
    public void setSpousePhone(String spousePhone){
        this.spousePhone = spousePhone;
    }
    
    /**
     * Gets the primary address line(street address) of this customer's address.
     * 
     * @return The primary address line(street address) of this customer. 
     * If there is no primary address line(street address), null is returned.
     */
    public String getAddressLine1(){
        return addressLine1;
    }
    
    /**
     * Sets the primary address line(street address) of this customer.
     * No error checking is performed.
     * 
     * @param addressLine1 The primary address line set to this customer
     */
    public void setAddressLine1(String addressLine1){
        this.addressLine1 = addressLine1;
    }
    
    /**
     * Gets the secondary address line of this customer's address.
     * 
     * @return The secondary address line of this customer. 
     * If there is no secondary address line, null is returned.
     */
    public String getAddressLine2(){
        return addressLine2;
    }
    
    /**
     * Sets the secondary address line of this customer.
     * No error checking is performed.
     * 
     * @param addressLine2 The secondary address line set to this customer
     */
    public void setAddressLine2(String addressLine2){
        this.addressLine2 = addressLine2;
    }
    
    /**
     * Gets the town of this customer's address.
     * 
     * @return The town of this customer. If there is no town, null is returned.
     */
    public String getTown(){
        return town;
    }
    
    /**
     * Sets the town of this customer's address. No error checking is performed.
     * 
     * @param town The town of this customer's address.
     */
    public void setTown(String town){
        this.town = town;
    }
    
    /**
     * Gets the state of this customer's address.
     * 
     * @return The state of this customer. If there is no state, null is returned.
     */
    public String getState(){
        return state;
    }
    
    /**
     * Sets the state of this customer's address. No error checking is performed.
     * 
     * @param state The state of this customer's address.
     */
    public void setState(String state){
        this.state = state;
    }
    
    /**
     * Gets the zip code of this customer's address.
     * 
     * @return The zip code of this customer. If there is no zip code, null
     * is returned.
     */
    public String getZip(){
        return zip;
    }
    
    /**
     * Sets the zip code of this customer's address. 
     * No error checking is performed.
     * 
     * @param zip The zip code of this customer's address.
     */
    public void setZip(String zip){
        this.zip = zip;
    }
    
    /**
     * Gets the email address of this customer.
     * 
     * @return The email address of this customer. If there is no email address, 
     * null is returned.
     */
    public String getEmailAddress(){
        return emailAddress;
    }
    
    /**
     * Sets the email address of this customer. No error checking is performed.
     * 
     * @param emailAddress The email address set to this customer.
     */
    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }
    
    /**
     * Gets the spouse's email address of this customer.
     * 
     * @return The spouse's email address of this customer. 
     * If there is no spouse's email address, null is returned.
     */
    public String getSpouseEmailAddress(){
        return spouseEmailAddress;
    }
    
    /**
     * Sets the spouse's email address of this customer. 
     * No error checking is performed.
     * 
     * @param spouseEmailAddress The spouse's email address set to this customer.
     */
    public void setSpouseEmailAddress(String spouseEmailAddress){
        this.spouseEmailAddress = spouseEmailAddress;
    }
    
    /**
     * Gets the RV type of this customer.
     * 
     * @return The RV type of this customer. If there is no RV type, 
     * null is returned.
     */
    public RVType getRvType(){
        return rvType;
    }
    
    /**
     * Sets the RV type of this customer. No error checking is performed.
     *  
     * @param rvType The RV type set to this customer.
     */
    public void setRvType(RVType rvType){
        this.rvType = rvType;
    }
    
    /**
     * Gets the RV length of this customer's RV.
     * 
     * @return The RV length of this customer's RV. If there is no RV length, 
     * null is returned.
     */
    public int getRvLength(){
        return rvLength;
    }
    
    /**
     * Sets the RV length of this customer's RV. No error checking is performed.
     *  
     * 
     * @param rvLength The RV length set to this customer's RV.
     */
    public void setRvLength(int rvLength){
        this.rvLength = rvLength;
    }
    
    /**
     * Gets the number of slide outs of this customer's RV has.
     * 
     * @return The number of slide outs of this customer's RV has.
     * If there is no number of slide outs, null is returned.
     */
    public int getNumberOfSlideOuts(){
        return numberOfSlideOuts;
    }
    
    /**
     * Sets the number of slide outs of this customer's RV.
     * No error checking is performed.
     * 
     * @param numberOfSlideOuts The number of slide outs set to this customer's RV
     */
    public void setNumberOfSlideOuts(int numberOfSlideOuts){
        this.numberOfSlideOuts = numberOfSlideOuts;
    }
 
    /**
     * Returns a boolean value that represents whether this customer is flagged.
     * 
     * @return True if the customer is flagged, false otherwise.
     */
    public boolean isFlagged() {
        return flagged;
    }

    /**
     * Sets the customer to be flagged.
     *
     * @param flagged True if the customer should become flagged.
     */
    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }
    
    /**
     * Gets the notes about this customer.
     * 
     * @return The notes of this customer. If there are no notes, null
     * is returned.
     */
    public String getNotes(){
        return notes;
    }
    
    /**
     * Sets the notes about this customer. 
     * No error checking is performed.
     * 
     * @param notes The notes about this customer.
     */
    public void setNotes(String notes){
        this.notes = notes;
    }
    
    /**
     * Gets the id of the group of this customer.
     * 
     * @return The id of the group of this customer. If there is no id, null
     * is returned.
     */
    public int getGroupId(){
        return groupId;
    }
    
    /**
     * Sets the id of the group of this customer. 
     * No error checking is performed.
     * 
     * @param groupId The id of the group of this customer.
     */
    public void setGroupId(int groupId){
        this.groupId = groupId;
    }

    /**
     * Overrides the equals method to compare two Customers. If the
     * object parameter is not a Customer or is null, return false.
     * Otherwise, if the Customer is equal to this Customer, return true.
     *
     * @param obj The Customer object to be compared to.
     * 
     * @return Whether or not the Customer given is equal to this Customer.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;

        }

        final Customer other = (Customer) obj;
        return this.customerId == other.customerId;
    }

    /**
     * Returns the order of a Customer compared to this Customer
     *
     * @param o The Customer to compare to this Customer.
     * 
     * @return The order of the given Customer relative to this Customer.
     * Positive for before, zero for equal, and negative for after.
     */
    @Override
    public int compareTo(Customer o) {
        int order = lastName.compareToIgnoreCase(o.lastName);
        if (order != 0) {
            return order;
        }
        order = firstName.compareToIgnoreCase(o.firstName);
        return order;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerId=" + customerId 
                + ", customerInformationId=" + customerInformationId
                + ", customerType=" + customerType 
                + ", name=" + name
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", spouseFirstName=" + spouseFirstName
                + ", spouseLastName=" + spouseLastName
                + ", primaryPhone=" + primaryPhone 
                + ", mobilePhone=" + mobilePhone 
                + ", spousePhone=" + spousePhone
                + ", addressLine1="  + addressLine1 
                + ", addressLine2=" + addressLine2 
                + ", town=" + town + ", state=" + state + ", zip=" + zip 
                + ", emailAddress=" + emailAddress 
                + ", spouseEmailAddress=" + spouseEmailAddress
                + ", rvType=" + rvType 
                + ", rvLength=" + rvLength 
                + ", numberOfSlideOuts=" + numberOfSlideOuts
                + ", flagged=" + flagged
                + ", notes=" + notes 
                + ", groupId=" + groupId + "}";
    }

    /**
     * Tests the Customer class.
     *
     * @param args Command line arguments.
     */
    public static void main(String args[]) {
        utilities.Debug.setEnabled(true);
        Customer c1 = new Customer();
        c1.setCustomerId(1);
        c1.setCustomerInformationId(1);
        c1.setCustomerType(CustomerType.Individual);
        c1.setFirstName("Bobby");
        c1.setLastName("Bobberson");
        c1.setMobilePhone("6668675309");
        c1.setSpousePhone("18775277424");
        c1.setAddressLine1("525 East 2nd St.");
        c1.setAddressLine2("6040 MPA");
        c1.setTown("Bloomsburg");
        c1.setState("PA");
        c1.setZip("17815");
        c1.setEmailAddress("bobsemail@bob.com");
        c1.setSpouseEmailAddress("poppop@pop.com");
        c1.setRvType(RVType.Van);
        c1.setNumberOfSlideOuts(0);
        c1.setFlagged(false);

        Customer c2 = new Customer(2, 2, CustomerType.Individual, null, "Johnny", "Johnnerson", 
                null, null, null, "8005882300", "8304765664", "1 Johnnystown Rd.", "", 
                "Johnsville", "Alaska", "12345", "johnnysemail@johnny.com",
                "johnnysotheremail@johns.org", RVType.FifthWheel, 36, 2, false,
                "This is Johnny.");

        Debug.println("CUST ID: " + c1.getCustomerId());
        Debug.println("CUST INFO ID: " + c1.getCustomerInformationId());
        Debug.println("CUST TYPE: " + c1.getCustomerType());
        Debug.println("PHONE: " + c1.getMobilePhone());
        Debug.println("PHONE2: " + c1.getSpousePhone());
        Debug.println("ADDRESS: " + c1.getAddressLine1());
        Debug.println("ADDRESS2: " + c1.getAddressLine2());
        Debug.println("TOWN: " + c1.getTown());
        Debug.println("STATE: " + c1.getState());
        Debug.println("ZIP: " + c1.getZip());
        Debug.println("EMAIL: " + c1.getEmailAddress());
        Debug.println("EMAIL2: " + c1.getSpouseEmailAddress());
        Debug.println("RV TYPE: " + c1.getRvType());
        Debug.println("SLIDE OUTS: " + c1.getNumberOfSlideOuts());
        Debug.println("FLAGGED: " + c1.isFlagged());
        Debug.println("NOTES: " + c1.getNotes());

        Debug.println();
        Debug.println("CUST ID: " + c2.getCustomerId());
        Debug.println("CUST INFO ID: " + c2.getCustomerInformationId());
        Debug.println("CUST TYPE: " + c2.getCustomerType());
        Debug.println("PHONE: " + c2.getMobilePhone());
        Debug.println("PHONE2: " + c2.getSpousePhone());
        Debug.println("ADDRESS: " + c2.getAddressLine1());
        Debug.println("ADDRESS2: " + c2.getAddressLine2());
        Debug.println("TOWN: " + c2.getTown());
        Debug.println("STATE: " + c2.getState());
        Debug.println("ZIP: " + c2.getZip());
        Debug.println("EMAIL: " + c2.getEmailAddress());
        Debug.println("EMAIL2: " + c2.getSpouseEmailAddress());
        Debug.println("RV TYPE: " + c2.getRvType());
        Debug.println("SLIDE OUTS: " + c2.getNumberOfSlideOuts());
        Debug.println("FLAGGED: " + c2.isFlagged());
        Debug.println("NOTES: " + c2.getNotes());
        Debug.println();
        Debug.println(c1.equals(c1));
        Debug.println(c1.equals(c2));
        Debug.println(c1.compareTo(c1));
        Debug.println(c1.compareTo(c2));
        Debug.println(c2.compareTo(c1));

    }
}
