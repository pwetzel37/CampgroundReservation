
package common;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import utilities.Debug;

/**
 * Represents a waiting list entry
 * 
 * @author Drew Wagner (2021)
 */
public class WaitingList implements Comparable<WaitingList>, Serializable {
    private int waitingListId; //Primary key in our database --autocreated
    private int customerId; 
    private int numberOfNights;
    private int numberOfSites;
    private String notes;
    private LocalDate dateOfRequest;
    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;
    private boolean priority;

    /**
     * Constructs an empty waiting list entry.
     */
    public WaitingList() {
    }

    /**
     * Constructs a new entry in the waiting list with information of waiting list id,
     * customer id, number of nights, number of sites, notes, date of request,
     * arrival date, departure date, and priority
     * 
     * @param waitingListId number represents waiting list entry, primary key
     * in the database
     * 
     * @param customerId number represents customer entry from customer table in database
     * 
     * @param numberOfNights number of nights customer would like to reserve sites
     * 
     * @param numberOfSites number of sites customer would like to reserve
     * 
     * @param notes notes about customer or reservation on waiting list
     * 
     * @param dateOfRequest date customer made request for reservation
     * 
     * @param arrivalDate date customer would like to make reservation for
     * 
     * @param departureDate date customer would like to depart
     * 
     * @param priority notes importance of the request for reservation on waiting list
     */
    public WaitingList(int waitingListId, int customerId, int numberOfNights, 
            int numberOfSites, String notes, LocalDate dateOfRequest, 
            LocalDateTime arrivalDate, LocalDateTime departureDate, boolean priority) {
        this.waitingListId=waitingListId; //Primary key in our database --autocreated
        this.customerId=customerId; 
        this.numberOfNights=numberOfNights;
        this.numberOfSites=numberOfSites;
        this.notes=notes;
        this.dateOfRequest=dateOfRequest;
        this.arrivalDate=arrivalDate;
        this.departureDate=departureDate;
        this.priority=priority;
    }
    
    /**
     * Constructs a new entry in the waiting list with information of customer id,
     * number of nights, number of sites, notes, date of request,
     * arrival date, departure date, and priority
     * 
     * @param customerId number represents customer entry from customer table in database
     * 
     * @param numberOfNights number of nights customer would like to reserve sites
     * 
     * @param numberOfSites number of sites customer would like to reserve
     * 
     * @param notes notes about customer or reservation on waiting list
     * 
     * @param dateOfRequest date customer made request for reservation
     * 
     * @param arrivalDate date customer would like to make reservation for
     * 
     * @param departureDate date customer would like to depart
     * 
     * @param priority notes importance of the request for reservation on waiting list
     */
    public WaitingList(int customerId, int numberOfNights, 
            int numberOfSites, String notes, LocalDate dateOfRequest, 
            LocalDateTime arrivalDate, LocalDateTime departureDate, boolean priority) {
        this.customerId=customerId; 
        this.numberOfNights=numberOfNights;
        this.numberOfSites=numberOfSites;
        this.notes=notes;
        this.dateOfRequest=dateOfRequest;
        this.arrivalDate=arrivalDate;
        this.departureDate=departureDate;
        this.priority=priority;
    }

    /**
     * Gets ID from waiting list database entry.
     *
     * @return primary key representing entry in waiting list table.
     */
    public int getWaitingListId() {
        return waitingListId;
    }

    /**
     * Sets waiting list primary key from database entry.
     *
     * @param waitingListId primary key from waiting list table.
     */
    public void setWaitingListId(int waitingListId) {
        this.waitingListId = waitingListId;
    }
    
    /**
     * Gets ID representing customer in customer table entry
     *
     * @return primary key from customer table entry
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets ID representing customer in customer table entry
     *
     * @param customerId key from customer table entry
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets number of nights customer would like to reserve
     *
     * @return number of nights customer would like to reserve
     */
    public int getNumberOfNights() {
        return numberOfNights;
    }

    /**
     * Sets number of nights customer would like to reserve
     *
     * @param numberOfNights The email address that is set for this user.
     */
    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights=numberOfNights;
    }

    /**
     * Gets number of sites customer would like to reserve
     *
     * @return The number of sites the customer would like to reserve
     * 
     */
    public int getNumberOfSites() {
        return numberOfSites;
    }

    /**
     * Sets the number of sites the customer would like to reserve.
     *
     * @param numberOfSites The number of sites the customer would like to reserve.
     */
    public void setNumberOfSites(int numberOfSites) {
        this.numberOfSites = numberOfSites;
    }

    /**
     * Gets notes pertaining to the customer or reservation.
     *
     * @return the notes about the customer or reservation.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets notes pertaining to the customer or reservation.
     *
     * @param notes The notes about the customer or reservation.
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Gets the date customer requested a reservation.
     *
     * @return The date customer requested a reservation.
     */
    public LocalDate getDateOfRequest() {
        return dateOfRequest;
    }

    /**
     * Sets the date customer requested a reservation.
     *
     * @param dateOfRequest The date customer requested a reservation. 
     */
    public void setDateOfRequest(LocalDate dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    /**
     * Gets the date customer would like to arrive for the reservation.
     * 
     * @return the date the customer would like to arrive for the reservation
     */
    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Sets the date the customer would like to arrive for the reservation.
     *
     * @param arrivalDate The date the customer would like to arrive for the reservation.
     */
    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    /**
     * Gets the date the customer would like to leave in the reservation
     *
     * @return The date the customer would like to leave
     */
    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the date the customer would like to leave in the reservation
     *
     * @param departureDate The date the customer would like to leave
     */
    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }


    /**
     * Returns a boolean value that represents whether the entry in the waiting list
     * is of high priority
     *
     * @return True if high priority, false if low priority
     */
    public boolean isPriority() {
        return priority;
    }

    /**
     * Sets the priority level of the entry in the waiting list
     *
     * @param priority True if the entry is high priority, false if low priority.
     */
    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    /**
     * Returns the order of <code> WaitingList </code> compared to this <code>
     * WaitingList </code>.
     *
     * @param o The <code> WaitingList </code> to compare to this <code> WaitingList </code>.
     * @return The order of the given <code> WaitingList </code> relative to this <code> 
     * WaitingList </code>. Positive for before, zero for equal, and negative for after.
     
     */
    
    @Override
    public int compareTo(WaitingList o) {
        int order = dateOfRequest.compareTo(o.dateOfRequest);
        if (order != 0) {
            return order;
        }
        order = dateOfRequest.compareTo(o.dateOfRequest);
        return order;
    }

    @Override
    public String toString() {
        return "User{" + "waitingListId=" + waitingListId + ", customerId=" + customerId
                + ", dateOfRequest=" + dateOfRequest + ", arrivalDate=" + arrivalDate
                + ", departureDate=" + departureDate
                + ", numberOfNights=" + numberOfNights 
                + ", numberOfSites=" + numberOfSites + ", priority="  + priority 
                + ", notes=" + notes + '}';
    }

    /**
     * Tests the <code>WaitingList</code> class. Reviews all get
     * and set methods, and both constructors
     *
     * @param args Command line arguments.
     */
    public static void main(String args[]) {
        utilities.Debug.setEnabled(true);
        WaitingList w1 = new WaitingList();
        w1.setWaitingListId(1001);
        w1.setCustomerId(7324);
        w1.setDateOfRequest(LocalDate.now());
        w1.setNumberOfNights(3);
        w1.setNumberOfSites(2);
        w1.setArrivalDate(LocalDateTime.of(2021, 6, 15, 0, 0));
        w1.setDepartureDate(LocalDateTime.of(2021, 6, 18, 0, 0));
        w1.setNotes("");
        w1.setPriority(false);
        

        WaitingList w2 = new WaitingList(1002, 9846, 7, 1, "Reschedule", LocalDate.now(), 
                LocalDateTime.of(2021, 7, 3, 0, 0), LocalDateTime.of(2021, 7, 10, 0, 0), true);

        Debug.println("WAITING LIST ID: " + w1.getWaitingListId());
        Debug.println("CUSTOMER ID: " + w1.getCustomerId());
        Debug.println("DATE OF REQUEST: " + w1.getDateOfRequest());
        Debug.println("NUMBER OF NIGHTS: " + w1.getNumberOfNights());
        Debug.println("NUMBER OF SITES: " + w1.getNumberOfSites());
        Debug.println("ARRIVAL DATE: " + w1.getArrivalDate());
        Debug.println("DEPARTURE DATE: " + w1.getDepartureDate());
        Debug.println("NOTES: " + w1.getNotes());
        Debug.println("PRIORITY: " + w1.isPriority());

        Debug.println();
        Debug.println("WAITING LIST ID: " + w2.getWaitingListId());
        Debug.println("CUSTOMER ID: " + w2.getCustomerId());
        Debug.println("DATE OF REQUEST: " + w2.getDateOfRequest());
        Debug.println("NUMBER OF NIGHTS: " + w2.getNumberOfNights());
        Debug.println("NUMBER OF SITES: " + w2.getNumberOfSites());
        Debug.println("ARRIVAL DATE: " + w2.getArrivalDate());
        Debug.println("DEPARTURE DATE: " + w2.getDepartureDate());
        Debug.println("NOTES: " + w2.getNotes());
        Debug.println("PRIORITY: " + w2.isPriority());

        Debug.println();
        Debug.println(w1.equals(w1));
        Debug.println(w1.equals(w2));
        
        Debug.println(w1.compareTo(w1));
        Debug.println(w1.compareTo(w2));
        Debug.println(w2.compareTo(w1));
        
        
        
    }
}
