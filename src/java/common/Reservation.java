package common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import utilities.Debug;

/**
 * Represents a reservation.
 *
 * @author Scott Harchar (2021)
 */
public class Reservation implements Comparable<Reservation>, Serializable {

    private int reservationId; //Primary key, auto incremented 
    private int customerId; //Other primary key, auto incremented
    private LocalDateTime arrivalDate; 
    private LocalDateTime departureDate; 
    private int numberOfNights;
    private int numberOfSitesRequested;
    private int numberOfSitesAssigned;
    private LocalDateTime dateReservationMade;
    private BigDecimal deposit;
    private String notes;
    
    /**
     * Constructs an empty reservation
     */
    public Reservation(){}
    
    /**
     * Constructs a new reservation.
     * 
     * @param customerId A number that represents this customer whose 
     * reservation this represents. 
     * This is the primary key in the customer database and is auto incremented.
     * 
     * @param arrivalDate The date in which the reservation starts/customer arrives.
     * 
     * @param departureDate The date in which the reservation ends/customer leaves.
     * 
     * @param numberOfNights The number of nights of the reservation.
     * 
     * @param numberOfSitesRequested The number of sites requested for this reservation.
     * 
     * @param numberOfSitesAssigned The number of sites assigned for this reservation.
     * 
     * @param dateReservationMade The date in which the reservation was made.
     * 
     * @param deposit The deposit made by the customer.
     * 
     * @param notes Notes about this reservation.
     */
    public Reservation(int customerId, LocalDateTime arrivalDate,
            LocalDateTime departureDate, int numberOfNights, int numberOfSitesRequested,
            int numberOfSitesAssigned, LocalDateTime dateReservationMade, 
            BigDecimal deposit, String notes){
        this.customerId = customerId;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.numberOfNights = numberOfNights;
        this.numberOfSitesRequested = numberOfSitesRequested;
        this.numberOfSitesAssigned = numberOfSitesAssigned;
        this.dateReservationMade = dateReservationMade;
        this.deposit = deposit;
        this.notes = notes;
    }
    
    /**
     * Constructs a new reservation with id.
     * 
     * @param reservationId A number that represents this reservation. 
     * This is the primary key in the reservation database and is auto incremented.
     * 
     * @param customerId A number that represents this customer whose 
     * reservation this represents. 
     * This is the primary key in the customer database and is auto incremented.
     * 
     * @param arrivalDate The date in which the reservation starts/customer arrives.
     * 
     * @param departureDate The date in which the reservation ends/customer leaves.
     * 
     * @param numberOfNights The number of nights of the reservation.
     * 
     * @param numberOfSitesRequested The number of sites requested for this reservation.
     * 
     * @param numberOfSitesAssigned The number of sites assigned for this reservation.
     * 
     * @param dateReservationMade The date in which the reservation was made.
     * 
     * @param deposit The deposit made by the customer.
     * 
     * @param notes Notes about this reservation.
     */
    public Reservation(int reservationId, int customerId, LocalDateTime arrivalDate,
            LocalDateTime departureDate, int numberOfNights, int numberOfSitesRequested,
            int numberOfSitesAssigned, LocalDateTime dateReservationMade, 
            BigDecimal deposit, String notes){
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.numberOfNights = numberOfNights;
        this.numberOfSitesRequested = numberOfSitesRequested;
        this.numberOfSitesAssigned = numberOfSitesAssigned;
        this.dateReservationMade = dateReservationMade;
        this.deposit = deposit;
        this.notes = notes;
    }
    
    /**
     * Gets the reservation id of this reservation
     * 
     * @return The reservation id of this reservation. If there is no reservation 
     * id, null is returned.
     */
    public int getReservationId(){
        return reservationId;
    }
    
    /**
     * Sets the reservation id of this reservation. No error checking is performed.
     * 
     * @param reservationId The reservation id set to this reservation.
     */
    public void setReservationId(int reservationId){
        this.reservationId = reservationId;
    }
    
    /**
     * Gets the customer id of the customer whose reservation this represents.
     * 
     * @return The customer id of the customer of this reservation. 
     * If there is no customer id, null is returned.
     */
    public int getCustomerId(){
        return customerId;
    }
    
    /**
     * Sets the customer id of the customer whose reservation this represents.
     * No error checking is performed.
     * 
     * @param customerId The customer id set to the customer of this reservation.
     */
    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }
    
    /**
     * Gets the date in which the reservation starts/customer arrives.
     * 
     * @return The date in which the reservation starts/customer arrives.
     * If there is no arrival date, null is returned.
     */
    public LocalDateTime getArrivalDate(){
        return arrivalDate;
    }
    
    /**
     * Sets the arrival date of this reservation. No error checking is performed.
     * 
     * @param arrivalDate The arrival date set to this reservation.
     */
    public void setArrivalDate(LocalDateTime arrivalDate){
        this.arrivalDate = arrivalDate;
    }
    
    /**
     * Gets the date in which the reservation ends/customer leaves.
     * 
     * @return The date in which the reservation ends/customer leaves.
     * If there is no departure date, null is returned.
     */
    public LocalDateTime getDepartureDate(){
        return departureDate;
    }
    
    /**
     * Sets the departure date of this reservation. No error checking is performed.
     * 
     * @param departureDate The departure date set to this reservation.
     */
    public void setDepartureDate(LocalDateTime departureDate){
        this.departureDate = departureDate;
    }
    
    /**
     * Gets the number of nights of this reservation.
     * 
     * @return The number of nights of this reservation. 
     * If there is no number of nights, null is returned.
     */
    public int getNumberOfNights(){
        return numberOfNights;
    }
    
    /**
     * Sets the number of nights of this reservation. No error checking is performed.
     * 
     * @param numberOfNights The number of nights set to this reservation.
     */
    public void setNumberOfNights(int numberOfNights){
        this.numberOfNights = numberOfNights;
    }
    
    /**
     * Gets the number of sites requested for this reservation.
     * 
     * @return The number of sites requested for this reservation.
     * If there are no sites requested, null is returned.
     */
    public int getNumberOfSitesRequested(){
        return numberOfSitesRequested;
    }
    
    /**
     * Sets the number of sites requested for this reservation.
     * No error checking is performed.
     * 
     * @param numberOfSitesRequested The number of sites set to this reservation.
     */
    public void setNumberOfSitesRequested(int numberOfSitesRequested){
        this.numberOfSitesRequested = numberOfSitesRequested;
    }
      
    /**
     * Gets the number of sites assigned to this reservation.
     * 
     * @return The number of sites assigned to this reservation.
     * If no sites are assigned, null is returned.
     */
    public int getNumberOfSitesAssigned(){
        return numberOfSitesAssigned;
    }
    
    /**
     * Sets the number of nights assigned to this reservation,
     * No error checking is performed.
     * 
     * @param numberOfSitesAssigned The number of sites assigned to this reservation.
     */
    public void setNumberOfSitesAssigned(int numberOfSitesAssigned){
        this.numberOfSitesAssigned = numberOfSitesAssigned;
    }
    
    /**
     * Gets the date this reservation was made.
     * 
     * @return The date this reservation was made. 
     * If there is no date this reservation was made, null is returned.
     */
    public LocalDateTime getDateReservationMade(){
        return dateReservationMade;
    }
    
    /**
     * Sets the date this reservation was made. No error checking is performed.
     * 
     * @param dateReservationMade The date this reservation was made.
     */
    public void setDateReservationMade(LocalDateTime dateReservationMade){
        this.dateReservationMade = dateReservationMade;
    }
    
    /**
     * Gets the deposit made by the customer.
     * 
     * @return The deposit made by the customer. 
     * If there is no deposit, null is returned.
     */
    public BigDecimal getDeposit(){
        return deposit;
    }
    
    /**
     * Sets the deposit made by the customer. No error checking is performed.
     * 
     * @param deposit The deposit made by the customer.
     */
    public void setDeposit(BigDecimal deposit){
        this.deposit = deposit;
    }
    
    /**
     * Gets the notes about this reservation
     * 
     * @return The notes about this reservation. 
     * If there are no notes, null is returned.
     */
    public String getNotes(){
        if(notes == null){return "";}
        return notes;
    }
    
    /**
     * Sets the notes of this reservation. No error checking is performed.
     * 
     * @param notes The notes about this reservation. 
     */
    public void setNotes(String notes){
        this.notes = notes;
    }
    
    /**
     * Overrides the equals method to compare two Reservations. If the
     * object parameter is not a Reservation or is null, return false.
     * Otherwise, if the Reservation is equal to this Reservation, return true.
     * 
     * @param obj The Reservation object to be compared to.
     * 
     * @return Whether or not the Reservation given is equal to this Reservation.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;

        }

        final Reservation other = (Reservation) obj;
        return this.reservationId == other.reservationId;
    } 
    
    /**
     * Returns the order of a Reservation compared to this Reservation
     *
     * @param o The Reservation to compare to this Reservation.
     * 
     * @return The order of the given Reservation relative to this Reservation.
     * Positive for before, zero for equal, and negative for after.
     */
    @Override
    public int compareTo(Reservation o) {
        if(this.reservationId < o.reservationId){
            return 1;
        } else if (this.reservationId > o.reservationId){
            return -1;
        } else {return 0;}
    }
   
    
    @Override
    public String toString() {
        return "Reservation{" + "reservationId=" + reservationId 
                + ", customerId=" + customerId
                + ", arrivalDate=" + arrivalDate 
                + ", departureDate=" + departureDate
                + ", numberOfNights=" + numberOfNights
                + ", numberOfSitesRequested=" + numberOfSitesRequested 
                + ", numberOfSitesAssigned=" + numberOfSitesAssigned
                + ", dateReservationMade="  + dateReservationMade 
                + ", deposit=" + deposit
                + ", notes=" + notes + "}";
    }
    
    /**
     * Tests the Reservation class.
     *
     * @param args Command line arguments.
     */
    public static void main(String args[]) {
        utilities.Debug.setEnabled(true);
        Reservation r1 = new Reservation();
        r1.setReservationId(1);
        r1.setCustomerId(1);
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("MM-dd-yy")
            .optionalStart()
            .appendPattern(" HH:mm")
            .optionalEnd()
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .toFormatter();
        r1.setArrivalDate(LocalDateTime.parse("03-05-21", formatter));
        r1.setDepartureDate(LocalDateTime.parse("03-07-21", formatter));
        r1.setNumberOfNights(2);
        r1.setNumberOfSitesRequested(1);
        r1.setNumberOfSitesAssigned(1);
        r1.setDateReservationMade(LocalDateTime.now());
        r1.setNotes("Wheelchair access");

        Reservation r2 = new Reservation(2, 2, LocalDateTime.parse("03-05-21", formatter),
            LocalDateTime.parse("03-07-21", formatter), 2, 1, 1, LocalDateTime.now(),
                BigDecimal.valueOf(10.76), "");

        Debug.println("RES ID: " + r1.getReservationId());
        Debug.println("CUST ID: " + r1.getCustomerId());
        Debug.println("ARRIVAL: " + r1.getArrivalDate());
        Debug.println("DEPARTURE: " + r1.getDepartureDate());
        Debug.println("NUM NIGHTS: " + r1.getNumberOfNights());
        Debug.println("NUM SITES REQUESTED: " + r1.getNumberOfSitesRequested());
        Debug.println("NUM SITES ASSIGNED: " + r1.getNumberOfSitesAssigned());
        Debug.println("DATE RES MADE: " + r1.getDateReservationMade());
        Debug.println("NOTES: " + r1.getNotes());

        Debug.println();
        
        Debug.println("RES ID: " + r2.getReservationId());
        Debug.println("CUST ID: " + r2.getCustomerId());
        Debug.println("ARRIVAL: " + r2.getArrivalDate());
        Debug.println("DEPARTURE: " + r2.getDepartureDate());
        Debug.println("NUM NIGHTS: " + r2.getNumberOfNights());
        Debug.println("NUM SITES REQUESTED: " + r2.getNumberOfSitesRequested());
        Debug.println("NUM SITES ASSIGNED: " + r2.getNumberOfSitesAssigned());
        Debug.println("DATE RES MADE: " + r2.getDateReservationMade());
        Debug.println("DEPOSIT: " + r2.getDeposit());
        Debug.println("NOTES: " + r2.getNotes());
        Debug.println();
        Debug.println(r1.equals(r1));
        Debug.println(r1.equals(r2));
        Debug.println(r1.compareTo(r1));
        Debug.println(r1.compareTo(r2));
        Debug.println(r2.compareTo(r1));

    }
}
