package database;

import common.AssignedSite;
import common.Campsite;
import common.Customer;
import common.CustomerType;
import common.DatabaseProperty;
import common.ErrorLog;
import common.RVType;
import common.Reservation;
import common.User;
import common.UserRole;
import common.WaitingList;
import common.SiteType;
import common.YearlyInformation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import utilities.WebErrorLogger;


/**
 * A utility class designed to convert result set entries to  
 * Java objects. 
 * 
 * @author Curt Jones (2018) Conor Smetana(2021)
 */
public class SQLUtility {

    public static User convertResultSetToUser(ResultSet rs) {
        User user = new User();
        try {
            user.setUserNumber(rs.getInt("userNumber"));
            user.setUserPassword(rs.getString("userPassword"));
            user.setLoginName(rs.getString("loginName"));
            user.setEmailAddress(rs.getString("emailAddress"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setUserRole(UserRole.getUserRole(rs.getString("userRole")));
            LocalDateTime now = LocalDateTime.parse(rs.getString("lastLoginTime"));
            user.setLastLoginTime(now);
            if (rs.getString("LastAttemptedLoginTime") == null) {
                user.setLastAttemptedLoginTime(LocalDateTime.now());
            } else {
                user.setLastAttemptedLoginTime(LocalDateTime.parse(rs.getString("LastAttemptedLoginTime")));
            }
            int loginCount = rs.getInt("loginCount");
            user.setLoginCount(loginCount);
            user.setAttemptedLoginCount(rs.getInt("attemptedLoginCount"));
            user.setLocked(rs.getBoolean("locked"));
            user.setSalt(rs.getString("salt"));

        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in convertResultSetToUser()" + ex, ex);
            return null;
        }
        return user;
    }
    
    public static DatabaseProperty convertResultSetToDatabaseProperty(ResultSet rs) {
        DatabaseProperty property = new DatabaseProperty();
        try {
            property.setPropertyNumber(rs.getInt("propertyNumber"));
            property.setPropertyName(rs.getString("propertyName"));
            property.setPropertyValue(rs.getString("propertyValue"));
            property.setDescription(rs.getString("description"));
            property.setPreviousValue(rs.getString("previousValue"));
            property.setDefaultValue(rs.getString("defaultValue"));
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in convertResultSetToDatabaseProperty. " + ex, ex);
            return null;
        }
        return property;
    }

    public static ErrorLog convertResultSetToErrorLog(ResultSet rs) {
         ErrorLog errorLog = new ErrorLog();
         try {
            errorLog.setErrorLogID(rs.getInt("EVENT_ID"));
            //String date = rs.getString("EVENT_DATE");
            //LocalDateTime time = LocalDateTime.parse(date);
            errorLog.setErrorLogDateTime(rs.getTimestamp("EVENT_DATE").toLocalDateTime());
            errorLog.setErrLevel(rs.getString("LEVEL"));
            errorLog.setLoggerName(rs.getString("LOGGER"));
            errorLog.setErrorMessage(rs.getString("MSG"));
            errorLog.setException(rs.getString("THROWABLE"));
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in convertResultSetToErrorLog(ResultSet rs). " + ex, ex);
            return null;
        }
         
         return errorLog;
    }
    
    public static Customer convertResultSetToCustomer(ResultSet rs){
        Customer customer = new Customer();
        try{
            
            customer.setCustomerId(rs.getInt("customer_id"));
            customer.setCustomerInformationId(rs.getInt("customer_information_id"));
            customer.setCustomerType(CustomerType.getCustomerType(rs.getString("customer_type")));
            customer.setName(rs.getString("customer_name"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setSpouseFirstName(rs.getString("spouse_first_name"));
            customer.setSpouseLastName(rs.getString("spouse_last_name"));
            customer.setPrimaryPhone(rs.getString("primary_phone_number"));
            customer.setMobilePhone(rs.getString("mobile_phone"));
            customer.setSpousePhone(rs.getString("spouse_phone"));
            customer.setAddressLine1(rs.getString("address_line_1"));
            customer.setAddressLine2(rs.getString("address_line_2"));
            customer.setTown(rs.getString("town"));
            customer.setState(rs.getString("state"));
            customer.setZip(rs.getString("zip"));
            customer.setEmailAddress(rs.getString("email_address"));
            customer.setSpouseEmailAddress(rs.getString("spouse_email_address"));
            customer.setRvType(RVType.getRVType(rs.getString("rv_type")));
            customer.setRvLength(rs.getInt("rv_length"));
            customer.setNumberOfSlideOuts(rs.getInt("number_of_slide_outs"));
            customer.setNotes(rs.getString("notes"));
            customer.setFlagged(rs.getBoolean("flagged"));
            customer.setGroupId(rs.getInt("group_id"));
        }catch (SQLException ex) {
            System.out.println("the resultset is erroring");
             WebErrorLogger.log(Level.SEVERE, "SQLException in "
                     + "convertResultSetToCustomer(ResultSet rs). " + ex, ex);
            return null;
        }
        return customer;
    }
    
    public static Reservation convertResultSetToReservation(ResultSet rs){
        Reservation reservation = new Reservation();
        try{
            reservation.setReservationId(rs.getInt("reservation_id"));
            reservation.setCustomerId(rs.getInt("customer_id"));
            reservation.setArrivalDate(rs.getTimestamp("arrival_date").toLocalDateTime());
            reservation.setDepartureDate(rs.getTimestamp("departure_date").toLocalDateTime());
            reservation.setNumberOfNights(rs.getInt("number_of_nights"));
            reservation.setNumberOfSitesRequested(rs.getInt("number_of_sites_requested"));
            reservation.setNumberOfSitesAssigned(rs.getInt("number_of_sites_assigned"));
            reservation.setDateReservationMade(rs.getTimestamp("date_reservation_made").toLocalDateTime());
            reservation.setDeposit(rs.getBigDecimal("deposit"));
            reservation.setNotes(rs.getString("notes"));
        }catch (SQLException ex) {
             WebErrorLogger.log(Level.SEVERE, "SQLException in "
                     + "convertResultSetToReservation(ResultSet rs). " + ex, ex);
            return null;
        }
        return reservation;
    }
    
    public static Campsite convertResultSetToCampsite(ResultSet rs){
        Campsite campsite= new Campsite();
        
        try{
            campsite.setCampsiteId(rs.getInt("campsite_id"));
            campsite.setCampsiteName(rs.getString("campsite_name"));
            campsite.setCampsiteNumber(rs.getInt("campsite_number"));
            campsite.setCampsiteSection(rs.getString("campsite_section").charAt(0));
            campsite.setMaxLength(rs.getInt("max_length"));
            campsite.setWidth(rs.getInt("width"));
            campsite.setAcceptsSlideOut(rs.getBoolean("accepts_slide_out"));
            campsite.setPullThru(rs.getBoolean("pull_thru"));
            campsite.setSiteType(SiteType.getSiteType(rs.getString("campsite_type")));
            campsite.setNotes(rs.getString("notes"));
            campsite.setImage(rs.getBlob("image"));
            campsite.setImageSource("image_source");
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in convertResultSetToCampsite(ResultSet rs)", ex);
            return null;
        }
            return campsite;
    }
    
    public static AssignedSite convertResultSetToAssignedSite(ResultSet rs){
        AssignedSite assignedSite= new AssignedSite();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try{
            assignedSite.setAssignedSiteId(rs.getInt("assigned_site_id"));
            assignedSite.setReservationId(rs.getInt("reservation_id"));
            assignedSite.setCustomerId(rs.getInt("customer_id"));
            assignedSite.setCampsiteId(rs.getInt("campsite_id"));
            assignedSite.setLockSite(rs.getBoolean("lock_site"));
            assignedSite.setSiteDeposit(rs.getBigDecimal("deposit"));
            assignedSite.setCheckIn(rs.getBoolean("checked_in"));
            assignedSite.setArrivalDate(LocalDateTime.parse(rs.getString("arrival_date"), format));
            assignedSite.setDepartureDate(LocalDateTime.parse(rs.getString("departure_date"), format));
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in convertResultSetToAssignedSite(ResultSet rs)", ex);
            return null;
        }
            return assignedSite;
    }
    
    public static WaitingList convertResultSetToWaitingList(ResultSet rs){
        WaitingList waitingList= new WaitingList();

        try{
            waitingList.setWaitingListId(rs.getInt("waiting_list_id"));
            waitingList.setCustomerId(rs.getInt("customer_id"));
            waitingList.setDateOfRequest(rs.getDate("date_of_request").toLocalDate());
            waitingList.setArrivalDate(rs.getTimestamp("arrival_date").toLocalDateTime());
            waitingList.setDepartureDate(rs.getTimestamp("departure_date").toLocalDateTime());
            waitingList.setNumberOfNights((int)rs.getByte("number_of_nights"));
            waitingList.setNumberOfSites((int)rs.getByte("number_of_sites"));
            waitingList.setPriority(rs.getBoolean("priority"));
            waitingList.setNotes(rs.getString("notes"));
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in convertResultSetToWaitingList(ResultSet rs)", ex);
            return null;
        }
            return waitingList;
    }
    
    
        public static YearlyInformation convertResultSetToYearlyInformation(ResultSet rs){
        YearlyInformation yearlyInformation= new YearlyInformation();

        try{
            yearlyInformation.setYear(rs.getInt("current_year"));
            yearlyInformation.setDailyRate(rs.getBigDecimal("daily_rate"));
            yearlyInformation.setWeeklyRate(rs.getBigDecimal("weekly_rate"));
            yearlyInformation.setMonthlyRate(rs.getBigDecimal("monthly_rate"));
            yearlyInformation.setSeasonalRate(rs.getBigDecimal("seasonal_rate"));
            yearlyInformation.setOpeningDate(rs.getDate("opening_date").toLocalDate());
            yearlyInformation.setClosingDate(rs.getDate("closing_date").toLocalDate());
            yearlyInformation.setSeasonalVisitorPassRate(rs.getBigDecimal("seasonal_visitor_pass_rate"));
            yearlyInformation.setNightlyVisitorFee(rs.getBigDecimal("nightly_visitor_fee"));
            yearlyInformation.setDailyVisitorFee(rs.getBigDecimal("daily_visitor_fee"));
            yearlyInformation.setEarlyCheckInFeePerHour(rs.getBigDecimal("early_check_in_fee_per_hour"));
            yearlyInformation.setLateStayFeePerHour(rs.getBigDecimal("late_stay_fee_per_hour"));
            yearlyInformation.setNightlyCancellationFee(rs.getBigDecimal("nightly_cancellation_fee"));
            yearlyInformation.setWeeklyCancellationFee(rs.getBigDecimal("weekly_cancellation_fee"));
            yearlyInformation.setMonthlyCancellationFee(rs.getBigDecimal("monthly_cancellation_fee"));
            yearlyInformation.setElectricalRatePerKWH(rs.getBigDecimal("electrical_rate_per_KWH"));
            
        }catch(SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in convertResultSetToWaitingList(ResultSet rs)", ex);
            return null;
        }
            return yearlyInformation;
    }
}
