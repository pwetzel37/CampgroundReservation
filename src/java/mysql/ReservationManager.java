package mysql;

import common.Reservation;
import database.SQLUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import utilities.WebErrorLogger;
/**
 * Represents a manager of the reservation database, which implements the 
 * ReservationManager interface. This class can be used to modify and extract the 
 * reservation's data which is stored in the database.
 *
 * @author Scott Harchar(2021) Conor Smetana(2021)
 */
public class ReservationManager implements database.ReservationManager {
    
    /**
     * Adds a Reservation to the reservations database and returns this Reservation
     * object which has been added successfully.
     * 
     * @param reservation A reservation object which will be used to add to the 
     * reservations database.
     * 
     * @return A reservation object which has been added to the reservations database.
     */
    @Override
    public Reservation addReservation(Reservation reservation){
        String sql = "INSERT INTO reservations (reservation_id, customer_id, "
                + "arrival_date, departure_date, number_of_nights, "
                + "number_of_sites_requested, number_of_sites_assigned, "
                + "date_reservation_made, deposit, notes) "
                + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt = null;
        ResultSet keys = null;
        try {
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, reservation.getCustomerId());
            stmt.setTimestamp(2, Timestamp.valueOf(reservation.getArrivalDate()));
            stmt.setTimestamp(3, Timestamp.valueOf(reservation.getDepartureDate()));
            stmt.setInt(4, reservation.getNumberOfNights());
            stmt.setInt(5, reservation.getNumberOfSitesRequested());
            stmt.setInt(6, reservation.getNumberOfSitesAssigned());
            stmt.setTimestamp(7, Timestamp.valueOf(reservation.getDateReservationMade()));
            stmt.setBigDecimal(8, reservation.getDeposit());
            stmt.setString(9, reservation.getNotes());
            stmt.executeUpdate();
            keys= stmt.getGeneratedKeys();
            keys.next();
            int reservationId = keys.getInt(1);
            reservation.setReservationId(reservationId);

        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in addReservation"
                    + "(Reservation reservation) reservation="+reservation+
                    " error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return reservation;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return getReservationByID(reservation.getReservationId());
        
    }
    
    /**
     * Updates a Reservation's information the reservations database and returns
     * this Reservation object which has been updated successfully.
     * 
     * @param reservation A reservation object which will be used to update the 
     * reservations database.
     * 
     * @return A reservation object which has been successfully updated.
     */
    @Override
    public Reservation updateReservation(Reservation reservation){
        String sql = "UPDATE reservations SET arrival_date=?, departure_date=?, "
                + "number_of_nights=?, number_of_sites_requested=?, "
                + "number_of_sites_assigned=?, deposit=?, notes=? WHERE reservation_id=? ;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(reservation.getArrivalDate()));
            stmt.setTimestamp(2, Timestamp.valueOf(reservation.getDepartureDate()));
            stmt.setInt(3, reservation.getNumberOfNights());
            stmt.setInt(4, reservation.getNumberOfSitesRequested());
            stmt.setInt(5, reservation.getNumberOfSitesAssigned());
            stmt.setBigDecimal(6, reservation.getDeposit());
            stmt.setString(7, reservation.getNotes());
            stmt.setInt(8, reservation.getReservationId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in updateReservation"
                    + "(Reservation reservation) reservation="+reservation+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return reservation;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return getReservationByID(reservation.getReservationId());
    }
    
    /**
     * Deletes a Reservation from the reservations database.
     * 
     * @param reservation A reservation object which will be used to delete from
     * the reservations database.
     * 
     * @return True if the Reservation was deleted successfully.
     */
    @Override
    public boolean deleteReservation(Reservation reservation){
        String sql = "DELETE FROM reservations WHERE reservation_id=?;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, reservation.getReservationId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in deleteReservation"
                    + "(Reservation reservation) " + reservation + " error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return false;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return !isReservation(reservation);
    }
    
     /**
     * Deletes Reservation by referencing its reservationId.
     * 
     * @param reservationID The id of the reservation to be deleted.
     * 
     * @return True if the Reservation was deleted successfully.
     */
    @Override
    public boolean deleteReservationByID(int reservationID){
        Reservation reservation = getReservationByID(reservationID);
        if(reservation == null){return false;}
        return deleteReservation(reservation);
    }
    
    /**
     * Returns true if the given reservation is a reservation in the 
     * reservations database.
     */
    private boolean isReservation(Reservation reservation){
        return getReservationByID(reservation.getReservationId()) != null;
    }
    
    /**
     * Gets a reservation object from the query results, searched by reservation
     * id.
     * 
     * @param reservationID The reservation id of the reservation to find.
     * 
     * @return The reservation found.
     */
    @Override
    public Reservation getReservationByID(int reservationID){
        String sql = "SELECT * FROM reservations WHERE reservation_id=? LIMIT 1;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Reservation reservation = null;
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, reservationID);
            rs = stmt.executeQuery();
            if(rs == null || rs.next() == false){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return null;
            }
            reservation = SQLUtility.convertResultSetToReservation(rs);
            rs.close();
        }catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in getReservationByID"
                    + "(int reservationID) reservationID="+reservationID + 
                    " error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return null;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return reservation;
        
    } 
    
    /**
     * Gets a collection of reservation objects from the query results, searched 
     * by customer id.
     * 
     * @param customerID The customer id of the reservation to find.
     * 
     * @return The reservations found.
     */
    @Override
    public Collection<Reservation> getReservationsByCustomerID(int customerID){
        Collection<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE customer_id=?;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Reservation reservation = null;
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerID);
            rs = stmt.executeQuery();
            if(rs == null || rs.next() == false){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return reservations;
            }
            do{
                reservation = SQLUtility.convertResultSetToReservation(rs);
                reservations.add(reservation);
            }while(rs.next());
        }catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in "
                    + "getReservationByCustomerID(int reservationID) "
                    + "customerID="+customerID + " error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return reservations;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return reservations;
        
    } 
    
    /**
     * Gets a collection of reservation objects from the query results, searched 
     * by date range.
     * 
     * @param startDate The date to start the search.
     * 
     * @param endDate The date to end the search.
     * 
     * @return The reservations found.
     */
    @Override
    public Collection<Reservation> getReservationsByDateRange(LocalDateTime startDate,
            LocalDateTime endDate){
        Collection<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE "
                + "? BETWEEN arrival_date AND departure_date "
                + "OR ? BETWEEN arrival_date AND departure_date "
                + "OR reservation_id = ANY (SELECT reservation_id from reservations "
                + "WHERE ? <= arrival_date AND ? >=departure_date)";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Reservation reservation;
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(startDate));
            stmt.setTimestamp(2, Timestamp.valueOf(endDate));
            stmt.setTimestamp(3, Timestamp.valueOf(startDate));
            stmt.setTimestamp(4, Timestamp.valueOf(endDate));
            rs = stmt.executeQuery();
            if(rs == null || rs.next() == false){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return reservations;
            }
            do{
                reservation = SQLUtility.convertResultSetToReservation(rs);
                reservations.add(reservation);
            }while(rs.next());
        }catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in "
                    + "getReservationByDateRange(String startDate,String endDate) "
                    + "startDate="+startDate+ ", endDate="+endDate + " error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return reservations;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return reservations;
    }
    
    /**
     * Gets a collection of reservation objects from the query results, filtered 
     * by arrival date, departure date, and customer name.
     * 
     * @param filter The constraint by which reservations are found.
     * 
     * @return The reservations found.
     */
    @Override
    public Collection<Reservation> getAllReservationsWithFilter(ArrayList<String> filter){
        Collection<Reservation> reservations = new ArrayList<>();
        String params[] = new String[5];
        int numParams = 0;
        boolean isFirst = true;
        boolean startDate = false;
        boolean endDate = false;
        boolean name = false;
        String sql = "SELECT * FROM reservations WHERE ";
        
        
        if(filter.get(0)!=null && filter.get(1)!=null){
            isFirst = false;
            sql+="? BETWEEN arrival_date AND departure_date "
                    + "OR ? BETWEEN arrival_date AND departure_date "
                    + "OR reservation_id = ANY (SELECT reservation_id from reservations "
                    + "WHERE ? <= arrival_date AND ? >=departure_date)";
            params[numParams] = filter.get(0);
            numParams++;
            params[numParams] = filter.get(1);
            numParams++;
            params[numParams] = filter.get(0);
            numParams++;
            params[numParams] = filter.get(1);
            numParams++;
        }    
        else if(filter.get(0) != null){
            isFirst = false;
            sql += "arrival_date>=?";
            params[numParams] = filter.get(0);
            numParams++;
            startDate = true;
        }
        else if(filter.get(1) != null){
            isFirst = false;
            sql += "departure_date<=?";
            params[numParams] = filter.get(1);
            numParams++;
            endDate = true;
        }
        
        if(filter.get(2) != null){
            if(!isFirst){sql += " AND ";}
            else{isFirst = false;}
            sql += "customer_id=(SELECT customer_id FROM customers WHERE "
                    + "customer_name LIKE ?)";
            params[numParams] = filter.get(2);
            numParams++;
            name = true;
        }
        sql += ";";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Reservation reservation = null;
        PreparedStatement stmt = null;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try{
            stmt = conn.prepareStatement(sql);
            for (int i = 1; i <= numParams; i++) {
                //If both arrival and departure date are supplied there will be at least 4 parameters
                if (numParams>=4)
                {
                    LocalDateTime start = LocalDateTime.of(Integer.parseInt(params[i-1].substring(6)), Integer.parseInt(params[i-1].substring(0,2)), Integer.parseInt(params[i-1].substring(3,5)), 14, 00);
                    LocalDateTime end = LocalDateTime.of(Integer.parseInt(params[i].substring(6)), Integer.parseInt(params[i].substring(0,2)), Integer.parseInt(params[i].substring(3,5)), 9, 00);
                    stmt.setTimestamp(i, Timestamp.valueOf(start)); //set start date between
                    i++;
                    stmt.setTimestamp(i, Timestamp.valueOf(end)); //set end date between
                    i++;
                    stmt.setTimestamp(i, Timestamp.valueOf(start)); //set start date after
                    i++;
                    stmt.setTimestamp(i, Timestamp.valueOf(end)); //set end date prior
                }
                else if(startDate){
                    LocalDateTime start = LocalDateTime.of(Integer.parseInt(params[i-1].substring(6)), Integer.parseInt(params[i-1].substring(0,2)), Integer.parseInt(params[i-1].substring(3,5)), 14, 00);
                    stmt.setTimestamp(i, Timestamp.valueOf(start));
                    startDate = false;
                }else if(endDate){
                    LocalDateTime end = LocalDateTime.of(Integer.parseInt(params[i-1].substring(6)), Integer.parseInt(params[i-1].substring(0,2)), Integer.parseInt(params[i-1].substring(3,5)), 9, 00);
                    stmt.setTimestamp(i, Timestamp.valueOf(end));
                    endDate = false;
                }else if(name){
                    stmt.setString(i, "%"+params[i-1]+"%");
                }
            }
            rs = stmt.executeQuery();
            if(rs == null || rs.next() == false){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return reservations;
            }
            do{
                reservation = SQLUtility.convertResultSetToReservation(rs);
                reservations.add(reservation);
            }while(rs.next());
        }catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in "
                    + "getAllReservationsWithFilter(ArrayList<String> filter) "
                    + "filter="+filter+" error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return reservations;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return reservations;
    }
    
     /**
     * Returns a collection of reservation objects for all reservations in the 
     * database.
     */
    @Override
    public Collection<Reservation> getAllReservations(){
        Collection<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Reservation reservation = null;
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs == null || rs.next() == false){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return reservations;
            }
            do{
                reservation = SQLUtility.convertResultSetToReservation(rs);
                reservations.add(reservation);
            }while(rs.next());
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in "
                    + "getAllCustomers() error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return reservations;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return reservations;
    }
    
}
