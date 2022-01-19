package database;

import common.Customer;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An interface that specifies the allowable operations on customers in
 * the system. 
 * 
 * 
 * @author Scott Harchar (2021)
 */
public interface CustomerManager {
    public Customer addCustomer(Customer customer); 
    public Customer addOnlyCustomer(Customer customer);
    public Customer updateCustomer(Customer customer);
    public boolean deleteCustomer(Customer customer);
    public boolean deleteCustomerbyID(int customerID);
    public Customer getCustomerByID(int customerID); 
    public Customer getCustomerByName(String name);
    public Customer getGroupByCustomer(int customerID);
    public Collection<Customer> getAllCustomersInGroup(int groupID);
    public Collection<Customer> getAllCustomersWithEmailAddress(String emailAddress);
    public Collection<Customer> getAllCustomersWithPhoneNumber(String phoneNumber);
    public Collection<Customer> getAllCustomersWithFilter(ArrayList<String> filter);
    public Collection<Customer> getAllCustomers();
    public Collection<Integer> checkPreexisting(Customer customer);
}

