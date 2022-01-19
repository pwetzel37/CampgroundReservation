
package mysql;
import common.Campsite;
import common.SiteType;
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
 * <code>CampsiteManager</code> interface.This class can be used to modify and
 * extract the campsite data which is stored in the database.
 * @author Drew Wagner, Scott Harchar (2021)
 */
public class CampsiteManager implements database.CampsiteManager  {
    
    /**
     * Returns this <code>Campsite</code> object which has been added to the campsite's
     * database successfully.
     *
     * @param campsite A <code>Campsite</code> object which will be used to add 
     * to the database.
     * @return A <code>Campsite</code> object which has been added to the campsite's
     * database.
     */
    @Override
    public Campsite addCampsite(Campsite campsite){
        String sql = "INSERT INTO campsite_information "
                + "(campsite_id, campsite_name, campsite_number, campsite_section, max_length, "
                + "width, accepts_slide_out, pull_thru, campsite_type,"
                + "notes, image, image_path) VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?,?); ";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt2 = null;
        try {
            stmt2 = conn.prepareStatement(sql);//Stop SQL insertion issues with prepared statements 
            stmt2.setString(1, campsite.getCampsiteName().trim());
            stmt2.setInt(2, campsite.getCampsiteNumber());
            stmt2.setString(3, String.valueOf(campsite.getCampsiteSection()));
            stmt2.setInt(4, campsite.getMaxLength());
            stmt2.setInt(5, campsite.getWidth());
            stmt2.setBoolean(6, campsite.getAcceptsSlideOut());
            stmt2.setBoolean(7, campsite.getPullThru());
            stmt2.setString(8, campsite.getSiteType().getSiteTypeName());
            stmt2.setString(9, campsite.getNotes().trim());
            stmt2.setBlob(10, campsite.getImage());
            stmt2.setString(11, campsite.getImageSource());

            stmt2.executeUpdate();

        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in addCampsite"
                    + "(Campsite campsite) campsite="+campsite+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return campsite;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return getCampsiteByCampsiteName(campsite.getCampsiteName());
    }
    
    
    /**
     * Returns a <code>Campsite</code> object with latest update, which is stored in
     * the database. 
     *
     * @param campsite A <code>Campsite</code> object which contains the latest
     * information.
     * @return A <code>Campsite</code> object with latest update.
     */
    @Override
    public Campsite updateCampsite(Campsite campsite){
        String sql="UPDATE campsite_information SET "
                + "campsite_name=?, campsite_number=?, campsite_section=?, max_length=?, "
                + "width=?, accepts_slide_out=?, pull_thru=?, campsite_type=?,"
                + "notes=?, image=?, image_path=? WHERE campsite_id=?";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt2 = null;
        try {
            stmt2 = conn.prepareStatement(sql);//Stop SQL insertion issues with prepared statements 
            stmt2.setString(1, campsite.getCampsiteName().trim());
            stmt2.setInt(2, campsite.getCampsiteNumber());
            stmt2.setString(3, String.valueOf(campsite.getCampsiteSection()));
            stmt2.setInt(4, campsite.getMaxLength());
            stmt2.setInt(5, campsite.getWidth());
            stmt2.setBoolean(6, campsite.getAcceptsSlideOut());
            stmt2.setBoolean(7, campsite.getPullThru());
            stmt2.setString(8, campsite.getSiteType().getSiteTypeName());
            stmt2.setString(9, campsite.getNotes().trim());
            stmt2.setBlob(10, campsite.getImage());
            stmt2.setString(11, campsite.getImageSource());
            stmt2.setInt(12, campsite.getCampsiteId());

            stmt2.executeUpdate();
            } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in updateCampsite"
                    + "(Campsite campsite) campsite="+campsite+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return campsite;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return getCampsiteWithNameNumberSection(campsite);
            
    }
    
    /**
     * Returns a <code>Campsite</code> object according to the given campsite ID.
     *
     * @param campsiteId The ID of a campsite that want to be exported.
     * @return A <code>Campsite</code> object with this campsite's ID.
     */
    @Override
    public Campsite getCampsiteById(int campsiteId){
        String sql="SELECT * FROM campsite_information WHERE campsite_id=?";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Campsite campsite = null;
        PreparedStatement stmt2=null;
        try{
            stmt2=conn.prepareStatement(sql);
            stmt2.setInt(1, campsiteId);
            
            rs = stmt2.executeQuery();
            if (rs.next() == false) {
                Web_MYSQL_Helper.closePreparedStatement(stmt2);
                Web_MYSQL_Helper.returnConnection(conn);
                return null;
            }
             campsite = SQLUtility.convertResultSetToCampsite(rs);
            rs.close();
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in getCampsiteById"
                    + "(int campsiteId) campsiteId="+campsiteId+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return null;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return campsite;
    }
    
    /**
     * Returns a <code>Campsite</code> object according to the given campsite name.
     *
     * @param campsiteName The name of the campsite that one wants exported
     * @return A <code>Campsite</code> object with this campsite name.
     */
    @Override
    public Campsite getCampsiteByCampsiteName(String campsiteName){
        String sql="SELECT * FROM campsite_information WHERE campsite_name=?";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Campsite campsite = null;
        PreparedStatement stmt2=null;
        try{
            stmt2=conn.prepareStatement(sql);
            stmt2.setString(1, campsiteName);
            
            rs = stmt2.executeQuery();
            if (rs.next() == false) {
                Web_MYSQL_Helper.closePreparedStatement(stmt2);
                Web_MYSQL_Helper.returnConnection(conn);
                return null;
            }
             campsite = SQLUtility.convertResultSetToCampsite(rs);
            rs.close();
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in getCampsiteByName"
                    + "(int campsiteName) campsiteName="+campsiteName+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return null;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return campsite;
    }
    
    /**
     * Returns a collection of <code>Campsite</code> object, which includes all the
     * campsites that are of the same <code>SiteType</code>
     *
     * @param siteType Type of site a campsite is
     * @return A collection of <code>Campsite</code> objects according to the given
     * site type.
     */
    @Override
    public Collection<Campsite> getAllCampsitesBySiteType(SiteType siteType){
        Collection<Campsite> campsites = new ArrayList<>();
        String sql = "SELECT * FROM campsite_information WHERE campsite_type = ?;";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Campsite campsite;
        PreparedStatement stmt2=null;
        try{
            stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, siteType.getSiteTypeName());
            
            rs=stmt2.executeQuery();
            if(rs == null){
                Web_MYSQL_Helper.closePreparedStatement(stmt2);
                Web_MYSQL_Helper.returnConnection(conn);
                return campsites;
            }
            while(rs.next()){
                campsite=SQLUtility.convertResultSetToCampsite(rs);
                campsites.add(campsite);
            }
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in getAllCampsitesBySiteType(SiteType siteType) "
                    + "siteType = "+siteType.getSiteTypeName(), ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return campsites;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return campsites;
    }
    
    @Override
    public Collection<Campsite> getUnassignedSitesByDateRange(LocalDateTime startDate,
            LocalDateTime endDate){
        Collection<Campsite> campsites = new ArrayList<>();
        String sql = "SELECT * FROM campsite_information WHERE NOT "
                + "campsite_id = ANY (SELECT campsite_id FROM assigned_site "
                + "WHERE ? BETWEEN arrival_date AND departure_date " 
                + "OR ? BETWEEN arrival_date AND departure_date " 
                + "OR campsite_id = ANY (SELECT campsite_id from assigned_site " 
                + "WHERE ? <= arrival_date AND ? <=departure_date));";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Campsite campsite;
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(sql);
            
            stmt.setTimestamp(1, Timestamp.valueOf(startDate));
            stmt.setTimestamp(2, Timestamp.valueOf(endDate));
            stmt.setTimestamp(3, Timestamp.valueOf(startDate));
            stmt.setTimestamp(4, Timestamp.valueOf(endDate));
            rs=stmt.executeQuery();
            if(rs == null || rs.next() == false){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return campsites;
            }
            do{
                campsite = SQLUtility.convertResultSetToCampsite(rs);
                campsites.add(campsite);
            }while(rs.next());
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in getUnassignedSitesByDateRange(LocalDateTime startDate," +
"            LocalDateTime endDate) "+ "startDate = "+startDate + ", endDate = "+endDate, ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return campsites;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return campsites;
    }
    
    /**
     * Retrieves all campsites within the database
     * @return A collection of all <code>Campsite</code> objects
     * within the database
     */
    @Override
    public Collection<Campsite> getAllCampsites(){
        Collection<Campsite> campsites = new ArrayList<>();
        String sql = "SELECT * FROM campsite_information;";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Campsite campsite;
        PreparedStatement stmt2=null;
        try{
            stmt2 = conn.prepareStatement(sql);
            
            rs=stmt2.executeQuery();
            if(rs == null){
                Web_MYSQL_Helper.closePreparedStatement(stmt2);
                Web_MYSQL_Helper.returnConnection(conn);
                return campsites;
            }
            while(rs.next()){
                campsite=SQLUtility.convertResultSetToCampsite(rs);
                campsites.add(campsite);
            }
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in getAllCampsites()", ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return campsites;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return campsites;
    }
    
    /**
     * Return ture if the given campsite have been deleted from the database.
     * Otherwise, return false.
     *
     * @param campsiteId The id of the campsite which is expected to be deleted 
     * from database
     * @return True if the given campsite have been deleted from the database
     * successfully.Otherwise, return false
     */
    @Override
    public boolean deleteCampsiteById(int campsiteId){
        String sql="DELETE FROM campsite_information WHERE campsite_id =?; ";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt2 = null;
        try{
            stmt2=conn.prepareStatement(sql);
            stmt2.setInt(1, campsiteId);
            stmt2.executeUpdate();
        }catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in deleteCampsiteById"
                    + "(int campsiteId) campsiteId="+campsiteId+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return false;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return true;
    }
    
    /**
     * Return ture if the given campsite have been deleted from the database.
     * Otherwise, return false.
     *
     * @param campsite The object which is expected to be deleted 
     * from database
     * @return Ture if the given campsite have been deleted from the database
     * successfully.Otherwise, return false
     */
    @Override
    public boolean deleteCampsite(Campsite campsite){
        if(campsite==null){return false;}
        return deleteCampsiteById(campsite.getCampsiteId());
    }
    
    /**
     * Returns a <code>Campsite</code> object according to the given campsite name, section, and number.
     * This is used in the to obtain the id of a recently created campsite object in which the
     * id is auto created by the database and unknown at creation.
     *
     * @param campsite the campsite containing the
     * @return A <code>Campsite</code> object with this campsite name.
     */
    @Override
    public Campsite getCampsiteWithNameNumberSection(Campsite campsite){
        String sql="SELECT * FROM campsite_information WHERE campsite_name=? AND campsite_section=? AND campsite_number=?;";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        PreparedStatement stmt2=null;
        try{
            stmt2=conn.prepareStatement(sql);
            stmt2.setString(1, campsite.getCampsiteName());
            stmt2.setString(2, String.valueOf(campsite.getCampsiteSection()));
            stmt2.setInt(3, campsite.getCampsiteNumber());
            
           
            rs = stmt2.executeQuery();
            if (rs.next() == false) {
                Web_MYSQL_Helper.closePreparedStatement(stmt2);
                Web_MYSQL_Helper.returnConnection(conn);
                return null;
            }
             campsite = SQLUtility.convertResultSetToCampsite(rs);
            rs.close();
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in getCampsiteByName"
                    + "(int campsiteName) campsiteName="+campsite.getCampsiteName()+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return null;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return campsite;
    }
}
