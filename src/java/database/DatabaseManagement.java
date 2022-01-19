
package database;

/**
 * <code>DatabaseManagement</code> is a <code>interface</code> designed to
 * specify the database managers  
 * along with high level operations available for the database we are 
 * using in this application. 
 * Examples include:
 * <pre>
 * public void CreateTables();
 * public void initializeDatabaseManagement();
 * public UserManager getUserManager();
 * </pre>
 * 
 * @author Curt Jones (2017)
 * @author Scott Harchar(2021)
 */
public interface DatabaseManagement {
    
    public void CreateTables();
    public void initializeDatabaseManagement();
    public UserManager getUserManager();
    public DatabasePropertyManager getDatabasePropertyManager();
    public DatabaseErrorLogManager getDatabaseErrorLogManager();
    public CustomerManager getCustomerManager();
    public ReservationManager getReservationManager();
    public CampsiteManager getCampsiteManager();
    public AssignedSiteManager getAssignedSiteManager();
    public WaitingListManager getWaitingListManager();
    public YearlyInformationManager getYearlyInformationManager();
}
