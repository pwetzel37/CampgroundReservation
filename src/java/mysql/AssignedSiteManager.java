
package mysql;

import common.AssignedSite;
import database.SQLUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import utilities.WebErrorLogger;

/**
 * Represents a manager of the database, which implements
 * <code>AssignedSiteManager</code> interface.This class can be used to modify and
 * extract the assigned site data which is stored in the database.
 * 
 * 
 * @author Drew Wagner, Scott Harchar (2021)
 */
public class AssignedSiteManager implements database.AssignedSiteManager {
    /**
     * Returns this <code>AssignedSite</code> object which has been added to the assigned
     * site's database successfully.
     *
     * @param assignedSite An <code>AssignedSite</code> object which will be used to add 
     * to the database.
     * @return An <code>AssignedSite</code> object which has been added to the assigned site's
     * database.
     */
    @Override
    public AssignedSite addAssignedSite(AssignedSite assignedSite){
        String sql = "INSERT INTO assigned_site "
                + "(assigned_site_id, reservation_id, customer_id, campsite_id, "
                + "lock_site, deposit, checked_in, arrival_date,"
                + "departure_date) VALUES (DEFAULT,?,?,?,?,?,?,?,?); ";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt2 = null;
        try {
            stmt2 = conn.prepareStatement(sql);//Stop SQL insertion issues with prepared statements 
            stmt2.setInt(1, assignedSite.getReservationId());
            stmt2.setInt(2, assignedSite.getCustomerId());
            stmt2.setInt(3, assignedSite.getCampsiteId());
            stmt2.setBoolean(4, assignedSite.getLockSite());
            stmt2.setBigDecimal(5, assignedSite.getSiteDeposit());
            stmt2.setBoolean(6, assignedSite.getCheckIn());
            stmt2.setTimestamp(7, Timestamp.valueOf(assignedSite.getArrivalDate()));
            stmt2.setTimestamp(8, Timestamp.valueOf(assignedSite.getDepartureDate()));

            stmt2.executeUpdate();

        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in addAssignedSite"
                    + "(AssignedSite assignedSite) assignedSite="+assignedSite+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return assignedSite;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return getAssignedSiteById(assignedSite.getAssignedSiteId());
    }
    
    
    /**
     * Returns an <code>AssignedSite</code> object with latest update, which is stored in
     * the database. 
     *
     * @param assignedSite A <code>AssignedSite</code> object which contains the latest
     * information.
     * @return A <code>AssignedSite</code> object with latest update.
     */
    @Override
    public AssignedSite updateAssignedSite(AssignedSite assignedSite){
        String sql="UPDATE assigned_site SET "
                + "reservation_id =?, customer_id =?, campsite_id =?, "
                + "lock_site =?, deposit =?, checked_in =?, arrival_date =?,"
                + "departure_date =?, WHERE assigned_site_id=?";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt2 = null;
        try {
            stmt2 = conn.prepareStatement(sql);//Stop SQL insertion issues with prepared statements 
            stmt2.setInt(1, assignedSite.getReservationId());
            stmt2.setInt(2, assignedSite.getCustomerId());
            stmt2.setInt(3, assignedSite.getCampsiteId());
            stmt2.setBoolean(4, assignedSite.getLockSite());
            stmt2.setBigDecimal(5, assignedSite.getSiteDeposit());
            stmt2.setBoolean(6, assignedSite.getCheckIn());
            //stmt2.set
            //stmt2.
            stmt2.setInt(9, assignedSite.getAssignedSiteId());

            stmt2.executeUpdate();
            } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in updateAssignedSite"
                    + "(AssignedSite assignedSite) assignedSite="+assignedSite+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return assignedSite;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return getAssignedSiteById(assignedSite.getAssignedSiteId());
    }
    
    /**
     * Returns a <code>AssignedSite</code> object according to the given campsite ID.
     *
     * @param assignedSiteId The ID of an assigned site that is to be exported.
     * @return A <code>AssignedSite</code> object with this assigned site's ID.
     */
    @Override
    public AssignedSite getAssignedSiteById(int assignedSiteId){
        String sql="SELECT * FROM assigned_site WHERE assigned_site_id=?";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        AssignedSite assignedSite = null;
        PreparedStatement stmt2=null;
        try{
            stmt2=conn.prepareStatement(sql);
            stmt2.setInt(1, assignedSiteId);
            
            rs = stmt2.executeQuery();
            if (rs.next() == false) {
                Web_MYSQL_Helper.closePreparedStatement(stmt2);
                Web_MYSQL_Helper.returnConnection(conn);
                return null;
            }
             assignedSite = SQLUtility.convertResultSetToAssignedSite(rs);
            rs.close();
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in getAssignedSiteById"
                    + "(int assignedSiteId) assignedSiteId="+assignedSiteId+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return null;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return assignedSite;
    }
    
    /**
     * Retrieves all assigned sites designated to a given reservation id.
     * 
     * @param reservationId This id of the reservation.
     * 
     * @return A collection of all Assigned Sites designated to the given reservation
     */
    @Override
    public Collection<AssignedSite> getAssignedSiteByReservationId(int reservationId){
        Collection<AssignedSite> assignedSites = new ArrayList<>();
        String sql = "SELECT * FROM assigned_site WHERE reservation_id=?;";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        AssignedSite assignedSite;
        PreparedStatement stmt2=null;
        try{
            stmt2 = conn.prepareStatement(sql);
            stmt2.setInt(1, reservationId);
            rs=stmt2.executeQuery();
            if(rs == null){
                Web_MYSQL_Helper.closePreparedStatement(stmt2);
                Web_MYSQL_Helper.returnConnection(conn);
                return assignedSites;
            }
            while(rs.next()){
                assignedSite=SQLUtility.convertResultSetToAssignedSite(rs);
                assignedSites.add(assignedSite);
            }
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in "
                    + "getAssignedSiteByReservationId(int reservationId)"
                    + "reservationId="+reservationId+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return assignedSites;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return assignedSites;
    }
 
    
    /**
     * Retrieves all assigned sites within the database
     * @return A collection of all <code>AssignedSite</code> objects
     * within the database
     */
    @Override
    public Collection<AssignedSite> getAllAssignedSites(){
        Collection<AssignedSite> assignedSites = new ArrayList<>();
        String sql = "SELECT * FROM assigned_site;";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        AssignedSite assignedSite;
        PreparedStatement stmt2=null;
        try{
            stmt2 = conn.prepareStatement(sql);
            
            rs=stmt2.executeQuery();
            if(rs == null){
                Web_MYSQL_Helper.closePreparedStatement(stmt2);
                Web_MYSQL_Helper.returnConnection(conn);
                return assignedSites;
            }
            while(rs.next()){
                assignedSite=SQLUtility.convertResultSetToAssignedSite(rs);
                assignedSites.add(assignedSite);
            }
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in getAllAssignedSites()", ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return assignedSites;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return assignedSites;
    }
    
    /**
     * Return ture if the given assigned site has been deleted from the database.
     * Otherwise, return false.
     *
     * @param assignedSiteId The id of the assigned site which is expected to be deleted 
     * from database
     * @return True if the given assigned site has been deleted from the database
     * successfully. Otherwise, return false.
     */
    @Override
    public boolean deleteAssignedSiteById(int assignedSiteId){
        String sql="DELETE FROM assigned_site WHERE assigned_site_id =?; ";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt2 = null;
        try{
            stmt2=conn.prepareStatement(sql);
            stmt2.setInt(1, assignedSiteId);
            stmt2.executeUpdate();
        }catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in deleteAssignedSiteById"
                    + "(int assignedSiteId) assignedSiteId="+assignedSiteId+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return false;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return true;
    }
    
    /**
     * Return ture if the given assigned site has been deleted from the database.
     * Otherwise, return false.
     *
     * @param assignedSite The object which is expected to be deleted 
     * from database
     * @return true if the given assigned site have been deleted from the database
     * successfully. Otherwise, return false
     */
    @Override
    public boolean deleteAssignedSite(AssignedSite assignedSite){
        if(assignedSite==null){return false;}
        return deleteAssignedSiteById(assignedSite.getAssignedSiteId());
    }
    @Override
    public boolean sitesAvailableInDateRange(int campsiteId, LocalDateTime startDate, LocalDateTime endDate){
        String sql = "SELECT * FROM assigned_site WHERE campsite_id = ? AND "
                + "campsite_id = ANY (SELECT campsite_id FROM assigned_site WHERE "
                + "? BETWEEN arrival_date AND departure_date "
                + "OR ? BETWEEN arrival_date AND departure_date "
                + "OR campsite_id = ANY (SELECT campsite_id from assigned_site "
                + "WHERE ? <= arrival_date AND ? >=departure_date))";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, campsiteId);
            stmt.setTimestamp(2, Timestamp.valueOf(startDate));
            stmt.setTimestamp(3, Timestamp.valueOf(endDate));
            stmt.setTimestamp(4, Timestamp.valueOf(startDate));
            stmt.setTimestamp(5, Timestamp.valueOf(endDate));
            rs = stmt.executeQuery();
            if(rs == null){
                return true;
            }else if(rs.next() == false)
            {
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return true;
            }
        }catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in "
                    + "sitesAvailableInDateRange(int CampsiteIdString startDate,String endDate) "
                    + "startDate="+startDate+ ", endDate="+endDate + " error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return false;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return false;
    }
}
