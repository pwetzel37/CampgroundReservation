package mysql;

import common.Customer;
import common.CustomerType;
import database.SQLUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import utilities.WebErrorLogger;

/**
 * Represents a manager of the customer database, which implements the 
 * CustomerManager interface. This class can be used to modify and extract the 
 * customer's data which is stored in the database.
 * 
 * @author Scott Harchar (2021)
 */
public class CustomerManager implements database.CustomerManager {
    
    /**
     * Adds a Customer to the customers and customer_information databases 
     * and returns this Customer object which has been added successfully.
     * 
     * @param customer A customer object which will be used to add to the 
     * customers and customer information databases.
     * 
     * @return A customer object which has been added to the customers and 
     * customer information databases.
     */
    @Override
    public Customer addCustomer(Customer customer){
        String sql = "INSERT INTO customers (customer_id, customer_type, customer_name, "
                + "primary_phone_number, customer_information_id, notes, group_id) "
                + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?);";
        String sql2 = "INSERT INTO customer_information (customer_information_id,"
                + "first_name, last_name, spouse_first_name, spouse_last_name,"
                + "mobile_phone, spouse_phone, address_line_1, address_line_2,"
                + "town, state, zip, email_address, spouse_email_address,"
                + "rv_type, rv_length, number_of_slide_outs, flagged)"
                + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?);";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement customerStatement = null;
        PreparedStatement informationStatement = null;
        ResultSet keys = null;
        try {
            informationStatement = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
            informationStatement.setString(1, customer.getFirstName().trim());
            informationStatement.setString(2, customer.getLastName().trim());
            informationStatement.setString(3, customer.getSpouseFirstName().trim());
            informationStatement.setString(4, customer.getSpouseLastName().trim());
            informationStatement.setString(5, customer.getMobilePhone().trim());
            informationStatement.setString(6, customer.getSpousePhone().trim());
            informationStatement.setString(7, customer.getAddressLine1());
            informationStatement.setString(8, customer.getAddressLine2());
            informationStatement.setString(9, customer.getTown());
            informationStatement.setString(10, customer.getState());
            informationStatement.setString(11, customer.getZip());
            informationStatement.setString(12, customer.getEmailAddress().trim());
            informationStatement.setString(13, customer.getSpouseEmailAddress().trim());
            informationStatement.setString(14, customer.getRvType().toString());
            informationStatement.setInt(15, customer.getRvLength());
            informationStatement.setInt(16, customer.getNumberOfSlideOuts());
            informationStatement.setBoolean(17, customer.isFlagged());
            informationStatement.executeUpdate();
            keys= informationStatement.getGeneratedKeys();
            keys.next();
            int customerInformationId = keys.getInt(1);
            customer.setCustomerInformationId(customerInformationId);
            
            customerStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            customerStatement.setString(1, customer.getCustomerType().toString());
            customerStatement.setString(2, customer.getName().trim());
            customerStatement.setString(3, customer.getPrimaryPhone().trim());
            customerStatement.setInt(4, customerInformationId);
            customerStatement.setString(5, customer.getNotes());
            if(customer.getCustomerType() != CustomerType.Individual){
                customerStatement.setInt(6, customerInformationId);
            }else if(customer.getGroupId() == 0){
                customerStatement.setNull(6, java.sql.Types.INTEGER);
            }else{
                customerStatement.setInt(6, customer.getGroupId());
            }
            customerStatement.executeUpdate();
            keys= customerStatement.getGeneratedKeys();
            keys.next();
            int customerId = keys.getInt(1);
            customer.setCustomerId(customerId);
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in addCustomer"
                    + "(Customer customer) customer="+customer+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(customerStatement);
            Web_MYSQL_Helper.closePreparedStatement(informationStatement);
            Web_MYSQL_Helper.returnConnection(conn);
            return customer;
        }
        Web_MYSQL_Helper.closePreparedStatement(customerStatement);
        Web_MYSQL_Helper.closePreparedStatement(informationStatement);
        Web_MYSQL_Helper.returnConnection(conn);
        return getCustomerByID(customer.getCustomerId());
    }
    
    /**
     * Only adds customer to customers database, linked to preexisting 
     * information in the customer_information database. 
     * 
     * @param customer Customer to be added.
     * @return Customer successfully added.
     */
    @Override
    public Customer addOnlyCustomer(Customer customer){
        String sql = "INSERT INTO customers (customer_id, customer_type, customer_name, "
                + "primary_phone_number, customer_information_id, notes, group_id) "
                + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?);";
        
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement customerStatement = null;
        ResultSet keys = null;
        try{
            customerStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            customerStatement.setString(1, customer.getCustomerType().toString());
            customerStatement.setString(2, customer.getName().trim());
            customerStatement.setString(3, customer.getPrimaryPhone().trim());
            customerStatement.setInt(4, customer.getCustomerInformationId());
            customerStatement.setString(5, customer.getNotes());
            customerStatement.setInt(6, customer.getGroupId());
            customerStatement.executeUpdate();
            keys= customerStatement.getGeneratedKeys();
            keys.next();
            int customerId = keys.getInt(1);
            customer.setCustomerId(customerId);
        }catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in addOnlyCustomer"
                    + "(Customer customer) customer="+customer+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(customerStatement);
            Web_MYSQL_Helper.returnConnection(conn);
            return customer;
        }
        Web_MYSQL_Helper.closePreparedStatement(customerStatement);
        Web_MYSQL_Helper.returnConnection(conn);
        return getCustomerByID(customer.getCustomerId());
    }
    
    /**
     * Updates a Customer's information the customers and customer_information 
     * databases and returns this Customer object which has been updated 
     * successfully.
     * 
     * @param customer A customer object which will be used to update the 
     * customers and customer information databases.
     * 
     * @return A customer object which has been updated in the customers and 
     * customer information databases.
     */
    @Override
    public Customer updateCustomer(Customer customer){
        String sql = "UPDATE customers SET customer_type=?, "
                + "customer_name=?, primary_phone_number=?, notes=?, group_id=? WHERE customer_id=?;";
        String sql2 = "UPDATE customer_information SET first_name=?, last_name=?, "
                + "spouse_first_name=?, spouse_last_name=?, mobile_phone=?, "
                + "spouse_phone=?, address_line_1=?, address_line_2=?, town=?, "
                + "state=?, zip=?, email_address=?, spouse_email_address=?, "
                + "rv_type=?, rv_length=?, number_of_slide_outs=?, flagged=? "
                + "WHERE customer_information_id=?;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, customer.getCustomerType().toString());
            stmt.setString(2, customer.getName().trim());
            stmt.setString(3, customer.getPrimaryPhone().trim());
            stmt.setString(4, customer.getNotes());
            stmt.setInt(5, customer.getGroupId());
            stmt.setInt(6, customer.getCustomerId());
            stmt.executeUpdate();
            
            stmt2 = conn.prepareStatement(sql2);
            stmt2.setString(1, customer.getFirstName().trim());
            stmt2.setString(2, customer.getLastName().trim());
            stmt2.setString(3, customer.getSpouseFirstName().trim());
            stmt2.setString(4, customer.getSpouseLastName().trim());
            stmt2.setString(5, customer.getMobilePhone().trim());
            stmt2.setString(6, customer.getSpousePhone().trim());
            stmt2.setString(7, customer.getAddressLine1());
            stmt2.setString(8, customer.getAddressLine2());
            stmt2.setString(9, customer.getTown());
            stmt2.setString(10, customer.getState());
            stmt2.setString(11, customer.getZip());
            stmt2.setString(12, customer.getEmailAddress().trim());
            stmt2.setString(13, customer.getSpouseEmailAddress().trim());
            stmt2.setString(14, customer.getRvType().toString());
            stmt2.setInt(15, customer.getRvLength());
            stmt2.setInt(16, customer.getNumberOfSlideOuts());
            stmt2.setBoolean(17, customer.isFlagged());
            stmt2.setInt(18, customer.getCustomerInformationId());
            stmt2.executeUpdate();
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in updateCustomer"
                    + "(Customer customer) customer="+customer+" error: "+ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return customer;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.closePreparedStatement(stmt2);
        Web_MYSQL_Helper.returnConnection(conn);
        return getCustomerByID(customer.getCustomerId());
    }
    
    /**
     * Deletes a Customer from the customers database and deletes the Customer's
     * information, provided the information is not linked to another customer.
     * 
     * @param customer A customer object which will be used to delete from the 
     * customers and customer information databases.
     * 
     * @return True if the Customer was deleted successfully.
     */
    @Override
    public boolean deleteCustomer(Customer customer){
        String sql = "DELETE FROM customers WHERE customer_id=?;";
        String sql2 = "DELETE FROM customer_information WHERE customer_information_id=?;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        try {
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, customer.getCustomerId());
                stmt.executeUpdate();

                if(!customerExists(customer.getCustomerInformationId())){
                    stmt2 = conn.prepareStatement(sql2);
                    stmt2.setInt(1, customer.getCustomerInformationId());
                    stmt2.executeUpdate();
                }
            } catch (SQLException ex) {
                WebErrorLogger.log(Level.SEVERE, "SQLException in deleteCustomer"
                        + "(Customer customer) " + customer + " error: " + ex);
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.closePreparedStatement(stmt2);
                Web_MYSQL_Helper.returnConnection(conn);
                return false;
            }
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
            return !isCustomer(customer);
    }
    
    /**
     * Deletes Customer by referencing its customerId.
     * 
     * @param customerID The id of the customer to be deleted
     * 
     * @return True if the Customer was deleted successfully.
     */
    @Override
    public boolean deleteCustomerbyID(int customerID){
        Customer customer = getCustomerByID(customerID);
        if(customer == null){return false;}
        return deleteCustomer(customer);
    }
    
    /**
     * Determines if a customer information id is still linked to a different 
     * customer.
     * Example: A customer is entered once as an Individual, then added again as
     * a Group. This will ensure that upon deletion of one version of the customer,
     * the customer information is still kept for the other instance.
     * 
     * @param customerInformationId The customer information id to reference.
     * 
     * @return True if a link still exists.
     */
    private static boolean customerExists(int customerInformationId) {
        String sql = "SELECT customer_id FROM customers WHERE "
                + "customer_information_id=? LIMIT 1;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerInformationId);
            rs = stmt.executeQuery();
            if(rs == null || rs.next() == false){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return false;
            }else {
                return true;
            }
        }catch (SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in customerExists"
                    + "(int customerInformationId) " + customerInformationId + 
                    " error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return false;
        }
    }
    
    /**
     * Determines if a reservation is linked to the customer.
     * 
     * Example: A customer cannot be deleted if they have a reservation.
     * 
     * @param customerId The customer id to reference.
     * 
     * @return True if a reservation exists.
     */
    private static boolean reservationExists(int customerId) {
        String sql = "SELECT reservation_id FROM reservations WHERE "
                + "customer_id=? LIMIT 1;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            rs = stmt.executeQuery();
            if(rs == null || rs.next() == false){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return false;
            }else{
               return true;}
        }catch (SQLException ex){
            WebErrorLogger.log(Level.SEVERE, "SQLException in reservationExists"
                    + "(int customerId) " + customerId + 
                    " error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return false;
        }
    }
    
    /**
     * Returns true if the given customer is a customer in the customers database.
     */
    private boolean isCustomer(Customer customer){
        return getCustomerByID(customer.getCustomerId()) != null;
    }
    
    /**
     * Gets a customer object from the query results, searched by customer id.
     * 
     * @param customerID The customer id of the customer to find.
     * 
     * @return The customer found.
     */
    @Override
    public Customer getCustomerByID(int customerID){
        String sql = "SELECT * FROM customers JOIN customer_information WHERE "
                + "customers.customer_information_id=customer_information.customer_information_id "
                + "AND customer_id=?;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Customer customer = null;
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerID);
            rs = stmt.executeQuery();
            if(rs == null){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return null;
            }
            rs.next();
            customer = SQLUtility.convertResultSetToCustomer(rs);
            rs.close();
        }catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in getCustomerByID"
                    + "(int customerID) customerID="+customerID + " error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return null;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return customer;
    }
    
    /**
     * Gets a customer object from the query results, searched by customer name.
     * 
     * @param name The customer name of the customer to find.
     * 
     * @return The customer found.
     */
    @Override
    public Customer getCustomerByName(String name){
        String sql = "SELECT * FROM customers JOIN customer_information WHERE "
                + "customers.customer_information_id=customer_information.customer_information_id "
                + "AND customer_name=? LIMIT 1;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Customer customer = null;
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            if(rs == null){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return null;
            }
            rs.next();
            customer = SQLUtility.convertResultSetToCustomer(rs);
            rs.close();
        }catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in getCustomerByID"
                    + "(String name) name="+ name + " error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return null;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return customer;
    }
    
    /**
     * Gets the group information of the group a customer is in.
     * 
     * @param customerID The customer ID of the customer.
     * 
     * @return The group found
     */
    @Override
    public Customer getGroupByCustomer(int customerID){
        String sql = "SELECT * FROM customers JOIN customer_information WHERE "
                + "customers.customer_information_id=customer_information.customer_information_id "
                + "AND group_id=? AND (customer_type=club OR customer_type=group) LIMIT 1;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Customer group = null;
        PreparedStatement stmt = null;
        int groupID = getCustomerByID(customerID).getGroupId();
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, groupID);
            rs = stmt.executeQuery();
            if(rs == null){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return null;
            }
            rs.next();
            group = SQLUtility.convertResultSetToCustomer(rs);
            rs.close();
        }catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in getGroupByCustomer"
                    + "(int customerID) customerID="+ customerID + " error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return null;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return group;
    }
    
    /**
     * Gets a collection of customer objects from the query results, searched by
     * group ID
     * 
     * @param groupID The ID of the group to get the customers of.
     * 
     * @return The customers found
     */
    @Override
    public Collection<Customer> getAllCustomersInGroup(int groupID){
        Collection<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers JOIN customer_information WHERE "
                + "customers.customer_information_id=customer_information.customer_information_id "
                + "AND group_id=? AND customer_type='Individual';";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Customer customer = null;
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, groupID);
            rs = stmt.executeQuery();
            if(rs == null || rs.next() == false){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return customers;
            }
            do{
                customer = SQLUtility.convertResultSetToCustomer(rs);
                customers.add(customer);
            }while(rs.next());
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in "
                    + "getAllCustomersInGroup(int groupID) groupID="+groupID+" error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return customers;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return customers;
    }
    
    /**
     * Gets a collection of customer objects from the query results, searched 
     * by email address.
     * 
     * @param emailAddress The email address of the customers to find.
     * 
     * @return The customers found.
     */
    @Override
    public Collection<Customer> getAllCustomersWithEmailAddress(String emailAddress){
        Collection<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers JOIN customer_information WHERE "
                + "customers.customer_information_id=customer_information.customer_information_id "
                + "AND (email_address=? OR spouse_email_address=?);";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Customer customer = null;
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, emailAddress);
            stmt.setString(2, emailAddress);
            rs = stmt.executeQuery();
            if(rs == null || rs.next() == false){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return customers;
            }
            do{
                customer = SQLUtility.convertResultSetToCustomer(rs);
                customers.add(customer);
            }while(rs.next());
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in "
                    + "getAllCustomersWithEmailAddress(String emailAddress) "
                    + "emailAddress="+emailAddress+" error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return customers;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return customers;
    }
    
    /**
     * Gets a collection of customer objects from the query results, searched 
     * by phone number.
     * 
     * @param phoneNumber The phone number of the customers to find.
     * 
     * @return The customers found.
     */
    @Override
    public Collection<Customer> getAllCustomersWithPhoneNumber(String phoneNumber){
     Collection<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers JOIN customer_information WHERE "
                + "customers.customer_information_id=customer_information.customer_information_id "
                + "AND (primary_phone_number=? OR mobile_phone=? OR spouse_phone=?);";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Customer customer = null;
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, phoneNumber);
            stmt.setString(2, phoneNumber);
            stmt.setString(3, phoneNumber);
            rs = stmt.executeQuery();
            if(rs == null || rs.next() == false){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return customers;
            }
            do{
                customer = SQLUtility.convertResultSetToCustomer(rs);
                customers.add(customer);
            }while(rs.next());
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in "
                    + "getAllCustomersWithPhoneNumber(String phoneNumber) "
                    + "phoneNumber="+phoneNumber+" error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return customers;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return customers;
    }
    
    /**
     * Gets a collection of  customer objects from the query results, filtered 
     * by last name, zip code, phone number, and/or customer type.
     * 
     * @param filter The constraint by which customers are found.
     * 
     * @return The customers found.
     */
    @Override
    public Collection<Customer> getAllCustomersWithFilter(ArrayList<String> filter){
        Collection<Customer> customers = new ArrayList<>();
        String params[] = new String[7];
        int numParams = 0;
        boolean isFirst = true;
        boolean usedSearch = false;
        boolean name = false;
        String sql = "SELECT * FROM customers JOIN customer_information WHERE "
                + "customers.customer_information_id=customer_information.customer_information_id AND ";
        if(filter.get(0) != null){
            isFirst = false;
            sql += "last_name LIKE ?";
            params[numParams] = filter.get(0);
            numParams++;
            usedSearch=true;
            name=true;
        }
        if(filter.get(1) != null){
            if(!isFirst){sql += " AND ";}
            else{isFirst = false;}
            sql += "zip=?";
            params[numParams] = filter.get(1);
            numParams++;
            usedSearch=true;
        }
        if(filter.get(2) != null){
            if(!isFirst){sql += " AND ";}
            else{isFirst = false;}
            sql += "(mobile_phone=? OR spouse_phone=?)";
            params[numParams] = filter.get(2);
            numParams++;
            params[numParams] = filter.get(2);
            numParams++;
            usedSearch=true;
        }
        
        //customer type filters
        if(filter.get(3) != null || filter.get(4) != null || filter.get(5) != null){
            if(!isFirst){sql += " AND (";}else{sql += "(";}
            isFirst = true;
            if(filter.get(3) != null){
                isFirst = false;
                sql += "customer_type=?";
                params[numParams] = filter.get(3);
                numParams++;
            }
            if(filter.get(4) != null){
                if(!isFirst){sql += " OR ";}
                else{isFirst = false;}
                sql += "customer_type=?";
                params[numParams] = filter.get(4);
                numParams++;
            }
            if(filter.get(5) != null){
                if(!isFirst){sql += " OR ";}
                else{isFirst = false;}
                sql += "customer_type=?";
                params[numParams] = filter.get(5);
                numParams++;
            }
            sql += ")";
        }
        sql += ";";    
        
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Customer customer = null;
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            for (int i = 1; i <= numParams; i++) {
                if(i==1 && usedSearch && name){
                    stmt.setString(i, "%"+params[i-1]+"%");
                    name=false;
                } else{
                    stmt.setString(i, params[i-1]);
                }
            }
            rs = stmt.executeQuery();
            if(rs == null || rs.next() == false){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return customers;
            }
            do{
                customer = SQLUtility.convertResultSetToCustomer(rs);
                customers.add(customer);
            }while(rs.next());
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in "
                    + "getAllCustomersWithFilter(String[][] filter) "
                    + "filter="+filter+" error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return customers;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return customers;
    }
    
    /**
     * Returns a collection of customer objects for all customers in the database.
     */
    @Override
    public Collection<Customer> getAllCustomers(){
        Collection<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers JOIN customer_information WHERE "
                + "customers.customer_information_id=customer_information.customer_information_id;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        Customer customer = null;
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs == null || rs.next() == false){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return customers;
            }
            do{
                customer = SQLUtility.convertResultSetToCustomer(rs);
                customers.add(customer);
            }while(rs.next());
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in "
                    + "getAllCustomers() error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return customers;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return customers;
    }
    
    /**
     * Gets the customer IDs of potential preexisting versions of the given 
     * customer. This method used customer name and phone number to reference
     * against other customers.
     * 
     * @param customer The customer object used to compare.
     * 
     * @return The customer IDs of potential duplicates. 
     */
    public Collection<Integer> checkPreexisting(Customer customer){
        Collection<Integer> customerIds = new ArrayList<>();
        String sql = "SELECT customer_id FROM customers WHERE customer_name=?"
                + " AND primary_phone_number=?;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs;
        int id;
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPrimaryPhone());
            rs = stmt.executeQuery();
            if(rs == null || rs.next() == false){
                Web_MYSQL_Helper.closePreparedStatement(stmt);
                Web_MYSQL_Helper.returnConnection(conn);
                return customerIds;
            }
            do{
                id = rs.getInt(1);
                customerIds.add(id);
            }while(rs.next());
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in "
                    + "getAllCustomers() error: " + ex);
            Web_MYSQL_Helper.closePreparedStatement(stmt);
            Web_MYSQL_Helper.returnConnection(conn);
            return customerIds;
        }
        Web_MYSQL_Helper.closePreparedStatement(stmt);
        Web_MYSQL_Helper.returnConnection(conn);
        return customerIds;
    }
}

