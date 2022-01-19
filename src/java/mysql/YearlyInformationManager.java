
package mysql;

import common.YearlyInformation;
import database.SQLUtility;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import utilities.WebErrorLogger;

/**
 * Represents a manager of the database, which implements
 * <code>YearlyInformationManager</code> interface. This class can be used to modify and
 * extract the yearly information data which is stored in the database.
 * @author Drew Wagner (2021)
 */
public class YearlyInformationManager implements database.YearlyInformationManager {
    /**
     * Returns this <code>YearlyInformation</code> object which has been added to the 
     * owner management table successfully.
     *
     * @param yearlyInformation An <code>YearlyInformation</code> object which will be used to add 
     * to the database.
     * @return An <code>YearlyInformation</code> object which has been added to the 
     * owner management table
     */
    @Override
    public YearlyInformation addYearlyInformation(YearlyInformation yearlyInformation){
        String sql = "INSERT INTO yearly_campground_information "
                + "(current_year, daily_rate, weekly_rate, monthly_rate, seasonal_rate, "
                + "opening_date, closing_date, seasonal_visitor_pass_rate, "
                + "nightly_visitor_fee, daily_visitor_fee, early_check_in_fee_per_hour, "
                + "late_stay_fee_per_hour, nightly_cancellation_fee, "
                + "weekly_cancellation_fee, monthly_cancellation_fee, electrical_rate_per_KWH) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); ";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt2 = null;
        try {
            stmt2 = conn.prepareStatement(sql);//Stop SQL insertion issues with prepared statements 
            stmt2.setInt(1, yearlyInformation.getYear());
            stmt2.setBigDecimal(2, yearlyInformation.getDailyRate());
            stmt2.setBigDecimal(3, yearlyInformation.getWeeklyRate());
            stmt2.setBigDecimal(4, yearlyInformation.getMonthlyRate());
            stmt2.setBigDecimal(5, yearlyInformation.getSeasonalRate());
            stmt2.setDate(6, Date.valueOf(yearlyInformation.getOpeningDate()));
            stmt2.setDate(7, Date.valueOf(yearlyInformation.getClosingDate()));
            stmt2.setBigDecimal(8, yearlyInformation.getSeasonalVisitorPassRate());
            stmt2.setBigDecimal(9, yearlyInformation.getNightlyVisitorFee());
            stmt2.setBigDecimal(10, yearlyInformation.getDailyVisitorFee());
            stmt2.setBigDecimal(11, yearlyInformation.getEarlyCheckInFeePerHour());
            stmt2.setBigDecimal(12, yearlyInformation.getLateStayFeePerHour());
            stmt2.setBigDecimal(13, yearlyInformation.getNightlyCancellationFee());
            stmt2.setBigDecimal(14, yearlyInformation.getWeeklyCancellationFee());
            stmt2.setBigDecimal(15, yearlyInformation.getMonthlyCancellationFee());
            stmt2.setBigDecimal(16, yearlyInformation.getElectricalRatePerKWH());

            stmt2.executeUpdate();

        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in addYearlyInformation"
                    + "(YearlyInformation yearlyInformation) yearlyInformation="+yearlyInformation+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return yearlyInformation;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return getYearlyInformationByYear(yearlyInformation.getYear());
    }
    
    
    /**
     * Returns an <code>YearlyInformation</code> object with latest update, which is stored in
     * the database. 
     *
     * @param yearlyInformation A <code>YearlyInformation</code> object which contains the latest
     * information.
     * @return A <code>YearlyInformation</code> object with latest update.
     */
    @Override
    public YearlyInformation updateYearlyInformation(YearlyInformation yearlyInformation){
        String sql = "UPDATE yearly_campground_information SET "
                + "daily_rate=?, weekly_rate=?, monthly_rate=?, seasonal_rate=?, "
                + "opening_date=?, closing_date=?, seasonal_visitor_pass_rate=?, "
                + "nightly_visitor_fee=?, daily_visitor_fee=?, early_check_in_fee_per_hour=?, "
                + "late_stay_fee_per_hour=?, nightly_cancellation_fee=?, "
                + "weekly_cancellation_fee=?, monthly_cancellation_fee=?, electrical_rate_per_KWH=? "
                + "WHERE current_year=?";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt2 = null;
        try {
            stmt2 = conn.prepareStatement(sql);//Stop SQL insertion issues with prepared statements 
            stmt2.setBigDecimal(1, yearlyInformation.getDailyRate());
            stmt2.setBigDecimal(2, yearlyInformation.getWeeklyRate());
            stmt2.setBigDecimal(3, yearlyInformation.getMonthlyRate());
            stmt2.setBigDecimal(4, yearlyInformation.getSeasonalRate());
            stmt2.setDate(5, Date.valueOf(yearlyInformation.getOpeningDate()));
            stmt2.setDate(6, Date.valueOf(yearlyInformation.getClosingDate()));
            stmt2.setBigDecimal(7, yearlyInformation.getSeasonalVisitorPassRate());
            stmt2.setBigDecimal(8, yearlyInformation.getNightlyVisitorFee());
            stmt2.setBigDecimal(9, yearlyInformation.getDailyVisitorFee());
            stmt2.setBigDecimal(10, yearlyInformation.getEarlyCheckInFeePerHour());
            stmt2.setBigDecimal(11, yearlyInformation.getLateStayFeePerHour());
            stmt2.setBigDecimal(12, yearlyInformation.getNightlyCancellationFee());
            stmt2.setBigDecimal(13, yearlyInformation.getWeeklyCancellationFee());
            stmt2.setBigDecimal(14, yearlyInformation.getMonthlyCancellationFee());
            stmt2.setBigDecimal(15, yearlyInformation.getElectricalRatePerKWH());
            stmt2.setInt(16, yearlyInformation.getYear());

            stmt2.executeUpdate();
            } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in updateYearlyInformation"
                    + "(YearlyInformation yearlyInformation) yearlyInformation="+yearlyInformation+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return yearlyInformation;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return getYearlyInformationByYear(yearlyInformation.getYear());
    }
    
    /**
     * Returns a <code>YearlyInformation</code> object according to the given year.
     *
     * @param year The ID of an yearly information object that is to be exported.
     * @return A <code>YearlyInformation</code> object with this yearly information object's ID.
     */
    @Override
    public YearlyInformation getYearlyInformationByYear(int year){
        String sql="SELECT * FROM yearly_campground_information WHERE current_year=?";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        YearlyInformation yearlyInformation = null;
        PreparedStatement stmt2=null;
        try{
            stmt2=conn.prepareStatement(sql);
            stmt2.setInt(1, year);
            
            rs = stmt2.executeQuery();
            if (rs.next() == false) {
                Web_MYSQL_Helper.closePreparedStatement(stmt2);
                Web_MYSQL_Helper.returnConnection(conn);
                return null;
            }
             yearlyInformation = SQLUtility.convertResultSetToYearlyInformation(rs);
            rs.close();
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in getYearlyInformationByYear"
                    + "(int year) year="+year+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return null;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return yearlyInformation;
    }
    
 
    
    /**
     * Retrieves all years of YearlyInformation within the database
     * @return A collection of all <code>YearlyInformation</code> objects
     * within the database
     */
    @Override
    public Collection<YearlyInformation> getAllYearlyInformations(){
        Collection<YearlyInformation> yearlyInformations = new ArrayList<>();
        String sql = "SELECT * FROM yearly_campground_information;";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        YearlyInformation yearlyInformation;
        PreparedStatement stmt2=null;
        try{
            stmt2 = conn.prepareStatement(sql);
            
            rs=stmt2.executeQuery();
            if(rs == null){
                Web_MYSQL_Helper.closePreparedStatement(stmt2);
                Web_MYSQL_Helper.returnConnection(conn);
                return yearlyInformations;
            }
            while(rs.next()){
                yearlyInformation=SQLUtility.convertResultSetToYearlyInformation(rs);
                yearlyInformations.add(yearlyInformation);
            }
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in getAllYearlyInformations()", ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return yearlyInformations;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return yearlyInformations;
    }
    
    /**
     * Return ture if the given yearly information object has been deleted from the database.
     * Otherwise, return false.
     *
     * @param year The id of the yearly information object which is expected to be deleted 
     * from database
     * @return True if the given yearly information object has been deleted from the database
     * successfully. Otherwise, return false.
     */
    @Override
    public boolean deleteYearlyInformationByYear(int year){
        String sql="DELETE FROM yearly_campground_information WHERE current_year =?; ";
        Connection conn=mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt2 = null;
        try{
            stmt2=conn.prepareStatement(sql);
            stmt2.setInt(1, year);
            stmt2.executeUpdate();
        }catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in deleteYearlyInformationByYear"
                    + "(int year) year="+year+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return false;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return true;
    }
    
    /**
     * Return ture if the given yealy information object has been deleted from the database.
     * Otherwise, return false.
     *
     * @param yearlyInformation The object which is expected to be deleted 
     * from database
     * @return true if the given yearly information object have been deleted from the database
     * successfully. Otherwise, return false
     */
    @Override
    public boolean deleteYearlyInformation(YearlyInformation yearlyInformation){
        if(yearlyInformation==null){return false;}
        return deleteYearlyInformationByYear(yearlyInformation.getYear());
    }
    
}
