package database;

import common.Reservation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An interface that specifies the allowable operations on reservations in
 * the system. 
 * 
 * 
 * @author Scott Harchar (2021)
 */
public interface ReservationManager {
    public Reservation addReservation(Reservation reservation); 
    public Reservation updateReservation(Reservation reservation);
    public boolean deleteReservation(Reservation reservation);
    public boolean deleteReservationByID(int reservationID);
    public Reservation getReservationByID(int reservationID); 
    public Collection<Reservation> getReservationsByCustomerID(int customerID); 
    public Collection<Reservation> getReservationsByDateRange(LocalDateTime startDate,
            LocalDateTime endDate); 
    public Collection<Reservation> getAllReservationsWithFilter(ArrayList<String> filter);
    public Collection<Reservation> getAllReservations();
}