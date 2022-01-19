package common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import utilities.Debug;

/**
 * Represents an Assigned Campsite. Needs more documentation x
 * 
 * @author Noah Young, Scott Harchar (2021)
 */
public class AssignedSite implements Comparable<AssignedSite>, Serializable {

    private int assignedSiteId; // Primary key, auto incremented.
    private int reservationId;
    private int customerId;
    private int campsiteId;
    private boolean lockSite;
    private BigDecimal siteDeposit;
    private boolean checkedIn;
    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;
    
    /**
     * Constructs an empty assigned site.
     */
    public AssignedSite() {}
    /**
     * Constructs a new Assigned Site.
     * @param reservationId Primary key for the reservation table.
     * @param customerId Primary key for the customer table.
     * @param campsiteId Primary key for the campsite table.
     * @param lockSite Boolean value to determine whether or not to lock the
     * site number for the reservation.
     * @param siteDeposit Represent monetary value of the site deposit.
     * @param checkedInId Primary key for the checked-in table.
     * campsite accepts slide outs.
     * @param arrivalDate Date that customer will arrive at the assigned site
     * @param departureDate Date that customer will leave at the assigned site
     */
    public AssignedSite (int reservationId, int customerId,
                     int campsiteId, boolean lockSite, BigDecimal siteDeposit,
                     boolean checkedInId, LocalDateTime arrivalDate, 
                     LocalDateTime departureDate) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.campsiteId = campsiteId;
        this.lockSite = lockSite;
        this.siteDeposit = siteDeposit;
        this.checkedIn = checkedInId;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }
    
    /**
     * Constructs a new Assigned Site with Id.
     * @param assignedSiteId A number that represents this Assigned Site.
     * This number is the primary key in our database and is auto incremented.
     * @param reservationId Primary key for the reservation table.
     * @param customerId Primary key for the customer table.
     * @param campsiteId Primary key for the campsite table.
     * @param lockSite Boolean value to determine whether or not to lock the
     * site number for the reservation.
     * @param siteDeposit Represent monetary value of the site deposit.
     * @param checkedInId Primary key for the checked-in table.
     * campsite accepts slide outs.
     * @param arrivalDate Date that customer will arrive at the assigned site
     * @param departureDate Date that customer will leave at the assigned site
     */
    public AssignedSite (int assignedSiteId, int reservationId, int customerId,
                     int campsiteId, boolean lockSite, BigDecimal siteDeposit,
                     boolean checkedInId, LocalDateTime arrivalDate, 
                     LocalDateTime departureDate) {
        this.assignedSiteId = assignedSiteId;
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.campsiteId = campsiteId;
        this.lockSite = lockSite;
        this.siteDeposit = siteDeposit;
        this.checkedIn = checkedInId;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }
    
    /**
     * Gets the Assigned site id of this assigned site.
     * 
     * @return The assigned site id of this assigned site. If there is no 
     * assigned site id, null is returned.
     */
    public int getAssignedSiteId(){
        return assignedSiteId;
    }
    
    /**
     * Sets the assigned site id of this assigned site.
     * No error checking is performed.
     *
     * @param assignedSiteId The assigned site id that is set to this assigned
     * site.
     */
    public void setAssignedSiteId(int assignedSiteId){
        this.assignedSiteId = assignedSiteId;
    }
    
    /**
     * Gets the reservation id of this assigned site.
     * 
     * @return The reservation id of this assigned site. If there is no 
     * reservation id, null is returned.
     */
    public int getReservationId(){
        return reservationId;
    }
    
    /**
     * Sets the reservation id of this assigned site.
     * No error checking is performed.
     *
     * @param reservationId The reservation id that is set to this assigned
     * site.
     */
    public void setReservationId(int reservationId){
        this.reservationId = reservationId;
    }
    
    /**
     * Gets the customer id of this assigned site.
     * 
     * @return The customer id of this assigned site. If there is no 
     * customer id, null is returned.
     */
    public int getCustomerId(){
        return customerId;
    }
    
    /**
     * Sets the customer id of this assigned site.
     * No error checking is performed.
     *
     * @param customerId The customer id that is set to this assigned
     * site.
     */
    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }
    
    /**
     * Gets the campsite id of this assigned site.
     * 
     * @return The campsite id of this assigned site. If there is no 
     * campsite id, null is returned.
     */
    public int getCampsiteId(){
        return campsiteId;
    }
    
    /**
     * Sets the campsite id of this assigned site.
     * No error checking is performed.
     *
     * @param campsiteId The campsite id that is set to this assigned
     * site.
     */
    public void setCampsiteId(int campsiteId){
        this.campsiteId = campsiteId;
    }
    
    /**
     * Gets the boolean value of whether or not to lock the
     * site number for the reservation.
     * 
     * @return True if site number for this reservation won’t change, false
     * otherwise
     */
    public boolean getLockSite(){
        return lockSite;
    }
    
    /**
     * Sets the boolean value of whether or not to lock the
     * site number for the reservation.
     *
     * @param lockSite Whether or not the site number for this 
     * reservation won’t change
     */
    public void setLockSite(boolean lockSite){
        this.lockSite = lockSite;
    }
    
    /**
     * Gets the Big Decimal value that represents monetary value of the site
     * deposit.
     * 
     * @return Big Decimal value that represents monetary value of the site
     * deposit.
     */
    public BigDecimal getSiteDeposit(){
        return siteDeposit;
    }
    
    /**
     * Sets the Big Decimal value that represents monetary value of the site
     * deposit.
     *
     * @param siteDeposit Big Decimal value that is to be set for the monetary
     * value of the site deposit.
     */
    public void setSiteDeposit(BigDecimal siteDeposit){
        this.siteDeposit = siteDeposit;
    }
    
    /**
     * Gets the checked in id of this assigned site.
     * 
     * @return The check in id of this assigned site. If there is no 
     * check in id, null is returned.
     */
    public boolean getCheckIn(){
        return checkedIn;
    }
    
    /**
     * Sets the check in id of this assigned site.
     * No error checking is performed.
     *
     * @param checkedIn The check in id that is set to this assigned
     * site.
     */
    public void setCheckIn(boolean checkedIn){
        this.checkedIn = checkedIn;
    }
    
    
    /**
     * Gets the date in which the site is assigned.
     * 
     * @return The date in which the site assignment starts.
     * If there is no arrival date, null is returned.
     */
    public LocalDateTime getArrivalDate(){
        return arrivalDate;
    }
    
    /**
     * Sets the arrival date in which the site is assigned. No error checking is performed.
     * 
     * @param arrivalDate The arrival date set to this site assignment.
     */
    public void setArrivalDate(LocalDateTime arrivalDate){
        this.arrivalDate = arrivalDate;
    }
    
    /**
     * Gets the date in which the site assignment ends.
     * 
     * @return The date in which the site assignment ends.
     * If there is no departure date, null is returned.
     */
    public LocalDateTime getDepartureDate(){
        return departureDate;
    }
    
    /**
     * Sets the date the site assignment ends. No error checking is performed.
     * 
     * @param departureDate The departure date set to this assignedSite.
     */
    public void setDepartureDate(LocalDateTime departureDate){
        this.departureDate = departureDate;
    }
    
    /**
     * Overrides the equals method to compare two <code> AssignedSite </code>s.
     * If the object parameter is not an <code> AssignedSite </code> or is null
     * return false. Otherwise, if the <code> AssignedSite </code> is equal to
     * this <code> AssignedSite </code>, return true.
     *
     * @param obj The <code> AssignedSite </code> object to be compared to.
     * @return Whether or not the <code> AssignedSite </code> given is equal to
     * this <code> AssignedSite </code>.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final AssignedSite other = (AssignedSite) obj;
        return this.assignedSiteId == other.assignedSiteId;
    }

    /**
     * Returns the hash value of this assigned site Id.
     *
     * @return The hash value of this assigned site Id.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.assignedSiteId;
        return hash;
    }
    
    /**
     * Returns the order of a <code> AssignedSite </code> compared to this
     * <code> AssignedSite </code>.
     *
     * @param o The <code> AssignedSite </code> to compare to this <code> 
     * AssignedSite </code>.
     * @return The order of the given <code> AssignedSite </code> relative to
     * this <code> AssignedSite </code>
     * Positive for before, zero for equal, and negative for after.
     */
    @Override
    public int compareTo(AssignedSite o) {
        int order = Integer.compare(assignedSiteId, o.assignedSiteId);
        return order;
    }
    
    @Override
    public String toString() {
        return "AssignedSite{" + "assignedSiteId=" + assignedSiteId
                + ", reservationId="  + reservationId 
                + ", customerId=" + customerId 
                + ", campsiteId=" + campsiteId
                + ", lockSite=" + lockSite 
                + ", siteDeposit=" + siteDeposit
                + ", checkedIn=" + checkedIn
                + ", arrivalDate=" + arrivalDate
                + ", departureDate=" + departureDate + "}";
    }
    
    /**
     * Tests the Campsite class.
     *
     * @param args Command line arguments.
     */
    public static void main(String args[]) {
        utilities.Debug.setEnabled(true);
        BigDecimal siteDeposit1 = new BigDecimal("125.99");
        BigDecimal siteDeposit2 = new BigDecimal("99.95");
        
        AssignedSite a1 = new AssignedSite();
        a1.setAssignedSiteId(1);
        a1.setReservationId(5);
        a1.setCustomerId(12);
        a1.setCampsiteId(2);
        a1.setLockSite(true);
        a1.setSiteDeposit(siteDeposit1);
        a1.setCheckIn(true);
        a1.setArrivalDate(LocalDateTime.of(2021, Month.MARCH, 2, 0, 0));
        a1.setDepartureDate(LocalDateTime.of(2021, Month.MARCH, 4, 0, 0));

        AssignedSite a2 = new AssignedSite(2, 6, 13, 1, false, siteDeposit2, 
                false, LocalDateTime.of(2021, Month.MARCH, 7, 0, 0), LocalDateTime.of(2021, Month.MARCH, 14, 0, 0));

        Debug.println("ASSIGNED SITE ID: " + a1.getAssignedSiteId());
        Debug.println("RESERVATION ID: " + a1.getReservationId());
        Debug.println("CUST ID: " + a1.getCustomerId());
        Debug.println("CAMP ID: " + a1.getCampsiteId());
        Debug.println("SITE LOCKED: " + a1.getLockSite());
        Debug.println("SITE DEPOSIT: " + a1.getSiteDeposit());
        Debug.println("CHECKED IN ID: " + a1.getCheckIn());
        Debug.println("ARRIVAL DATE: " + a1.getArrivalDate());
        Debug.println("DEPARTURE DATE: " + a1.getDepartureDate());

        Debug.println();
        
        Debug.println(a2.toString());
        
        Debug.println();
        
        Debug.println(a1.equals(a1));
        Debug.println(a1.equals(a2));
        Debug.println(a1.compareTo(a1));
        Debug.println(a1.compareTo(a2));
        Debug.println(a2.compareTo(a1));
    }
}
