
package mysql;

import common.WaitingList;
import database.SQLUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import utilities.WebErrorLogger;

/**
 * Represents a manager of the database, which implements
 * <code>WaitingListManager</code> interface.This class can be used to modify and
 * extract the waiting list data which is stored in the database.
 * 
 * @author Drew Wagner (2021)
 */
public class WaitingListManager implements database.WaitingListManager {
    
    
    /**
     * Returns this <code>WaitingList</code> object which has been added to the waiting
     * list's database successfully.
     *
     * @param waitingList An <code>WaitingList</code> object which will be used to add 
     * to the database.
     * @return An <code>WaitingList</code> object which has been added to the waiting list's
     * database.
     */
    @Override
    public WaitingList addWaitingList(WaitingList waitingList){
        String sql = "INSERT INTO waiting_list "
                + "(waiting_list_id, customer_id, date_of_request, arrival_date, departure_date, "
                + "number_of_nights, number_of_sites, priority, notes)"
                + " VALUES (DEFAULT,?,?,?,?,?,?,?,?); ";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt2 = null;
        try {
            stmt2 = conn.prepareStatement(sql);//Stop SQL insertion issues with prepared statements 
            stmt2.setInt(1, waitingList.getCustomerId());
            stmt2.setDate(2, Date.valueOf(waitingList.getDateOfRequest()));
            stmt2.setTimestamp(3, Timestamp.valueOf(waitingList.getArrivalDate()));
            stmt2.setTimestamp(4, Timestamp.valueOf(waitingList.getDepartureDate()));
            stmt2.setByte(5, (byte)waitingList.getNumberOfNights());
            stmt2.setByte(6, (byte)waitingList.getNumberOfSites());
            stmt2.setBoolean(7, waitingList.isPriority());
            stmt2.setString(8, waitingList.getNotes());

            stmt2.executeUpdate();

        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in addWaitingList"
                    + "(WaitingList waitingList) waitingList="+waitingList+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return waitingList;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return getWaitingListById(waitingList.getWaitingListId());
    }
    
    
    
    /**
     * Returns an <code>WaitingList</code> object with latest update, which is stored in
     * the database. 
     *
     * @param waitingList A <code>WaitingList</code> object which contains the latest
     * information.
     * @return A <code>WaitingList</code> object with latest update.
     */
    @Override
    public WaitingList updateWaitingList(WaitingList waitingList){
        String sql = "UPDATE waiting_list SET "
                + "waiting_list_id=?, customer_id=?, date_of_request=?, arrival_date=?, departure_date=?, "
                + "number_of_nights=?, number_of_sites=?, priority=?, notes=?"
                + "WHERE waiting_list_id=?";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt2 = null;
        try {
            stmt2 = conn.prepareStatement(sql);//Stop SQL insertion issues with prepared statements 
            stmt2.setInt(1, waitingList.getWaitingListId());
            stmt2.setInt(2, waitingList.getCustomerId());
            stmt2.setDate(3, Date.valueOf(waitingList.getDateOfRequest()));
            stmt2.setTimestamp(4, Timestamp.valueOf(waitingList.getArrivalDate()));
            stmt2.setTimestamp(5, Timestamp.valueOf(waitingList.getDepartureDate()));
            stmt2.setByte(6, (byte)waitingList.getNumberOfNights());
            stmt2.setByte(7, (byte)waitingList.getNumberOfSites());
            stmt2.setBoolean(8, waitingList.isPriority());
            stmt2.setString(9, waitingList.getNotes());
            stmt2.setInt(10, waitingList.getWaitingListId());

            stmt2.executeUpdate();

        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in addWaitingList"
                    + "(WaitingList waitingList) waitingList="+waitingList+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return waitingList;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return getWaitingListById(waitingList.getWaitingListId());
    }
    
    
    /**
     * Returns a <code>WaitingList</code> object according to the given campsite ID.
     *
     * @param waitingListId The ID of a waiting list that is to be exported.
     * @return A <code>WaitingList</code> object with this waiting list's ID.
     */
    @Override
    public WaitingList getWaitingListById(int waitingListId){
        String sql="SELECT * FROM waiting_list WHERE waiting_list_id=?";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        WaitingList waitingList = null;
        PreparedStatement stmt2=null;
        try{
            stmt2=conn.prepareStatement(sql);
            stmt2.setInt(1, waitingListId);
            
            rs = stmt2.executeQuery();
            if (rs.next() == false) {
                Web_MYSQL_Helper.closePreparedStatement(stmt2);
                Web_MYSQL_Helper.returnConnection(conn);
                return null;
            }
             waitingList = SQLUtility.convertResultSetToWaitingList(rs);
            rs.close();
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in getWaitingListById"
                    + "(int waitingListId) waitingListId="+waitingListId+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return null;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return waitingList;
    }
    
    @Override
    public Collection<WaitingList> getWaitingListsByCustomerId(int waitingListId){
        Collection<WaitingList> waitingLists = new ArrayList<>();
        String sql = "SELECT * FROM waiting_list WHERE waiting_list_id=?;";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        WaitingList waitingList;
        PreparedStatement stmt2=null;
        try{
            stmt2 = conn.prepareStatement(sql);
            stmt2.setInt(1, waitingListId);
            rs=stmt2.executeQuery();
            if(rs == null){
                Web_MYSQL_Helper.closePreparedStatement(stmt2);
                Web_MYSQL_Helper.returnConnection(conn);
                return waitingLists;
            }
            while(rs.next()){
                waitingList=SQLUtility.convertResultSetToWaitingList(rs);
                waitingLists.add(waitingList);
            }
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in getAllWaitingLists()", ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return waitingLists;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return waitingLists;
    }
    
    /**
     * Retrieves all waiting lists within the database
     * @return A collection of all <code>WaitingList</code> objects
     * within the database
     */
    @Override
    public Collection<WaitingList> getAllWaitingLists(){
        Collection<WaitingList> waitingLists = new ArrayList<>();
        String sql = "SELECT * FROM waiting_list;";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        WaitingList waitingList;
        PreparedStatement stmt2=null;
        try{
            stmt2 = conn.prepareStatement(sql);
            
            rs=stmt2.executeQuery();
            if(rs == null){
                Web_MYSQL_Helper.closePreparedStatement(stmt2);
                Web_MYSQL_Helper.returnConnection(conn);
                return waitingLists;
            }
            while(rs.next()){
                waitingList=SQLUtility.convertResultSetToWaitingList(rs);
                waitingLists.add(waitingList);
            }
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in getAllWaitingLists()", ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return waitingLists;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return waitingLists;
    }
    
    /**
     * Return ture if the given waiting list has been deleted from the database.
     * Otherwise, return false.
     *
     * @param waitingListId The id of the waiting list which is expected to be deleted 
     * from database
     * @return True if the given waiting list has been deleted from the database
     * successfully. Otherwise, return false.
     */
    @Override
    public boolean deleteWaitingListById(int waitingListId){
        String sql="DELETE FROM waiting_list WHERE waiting_list_id =?; ";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt2 = null;
        try{
            stmt2=conn.prepareStatement(sql);
            stmt2.setInt(1, waitingListId);
            stmt2.executeUpdate();
        }catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in deleteWaitingListById"
                    + "(int waitingListId) waitingListId="+waitingListId+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return false;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return true;
    }
    
    
    /**
     * Return ture if the given waiting list has been deleted from the database.
     * Otherwise, return false.
     *
     * @param waitingList The object which is expected to be deleted 
     * from database
     * @return True if the given waiting list has been deleted from the database
     * successfully. Otherwise, return false.
     */
    @Override
    public boolean deleteWaitingList(WaitingList waitingList){
        if(waitingList == null){return false;}
        return deleteWaitingListById(waitingList.getWaitingListId());
    }
}