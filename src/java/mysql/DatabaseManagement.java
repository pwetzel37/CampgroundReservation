package mysql;

import database.DatabaseErrorLogManager;
import database.DatabasePropertyManager;

/**
 * Represents a MySQL database that implements the <code>DatabaseManagement</code>
 * interface.
 *
 * @author Curt Jones (2018)
 * @author Scott Harchar(2021)
 */
public class DatabaseManagement implements database.DatabaseManagement {

    private database.UserManager userManager; 
    private database.DatabasePropertyManager databasePropertyManager;
    private database.DatabaseErrorLogManager databaseErrorLogManager;
    private database.CustomerManager customerManager;
    private database.ReservationManager reservationManager;
    private database.CampsiteManager campsiteManager;
    private database.AssignedSiteManager assignedSiteManager;
    private database.WaitingListManager waitingListManager;
    private database.YearlyInformationManager yearlyInformationManager;


    @Override
    public void initializeDatabaseManagement() {

    }

    @Override
    public void CreateTables() {
        throw new UnsupportedOperationException("Not yet a supported operation!"); 
    }

    /**
     * Returns a <code>UserManager</code> object for this
     * <code>DatabaseManagement</code>.
     *
     * @return A <code>UserManager</code>  object.
     * @see common.User 
     */
    @Override
    public database.UserManager getUserManager() {
        if (userManager == null) userManager = new mysql.UserManager();
        return userManager;
    }

    @Override
    public database.DatabasePropertyManager getDatabasePropertyManager() {
        if (databasePropertyManager == null) databasePropertyManager = new mysql.DatabasePropertyManager();
        return databasePropertyManager;
    }

    @Override
    public DatabaseErrorLogManager getDatabaseErrorLogManager() {
        if(databaseErrorLogManager==null) {
            databaseErrorLogManager = new mysql.DatabaseErrorLogManager();
        }
        return  databaseErrorLogManager;
    }
    
    @Override
    public database.CustomerManager getCustomerManager() {
        if(customerManager==null) {
            customerManager = new mysql.CustomerManager();
        }
        return customerManager;
    }
    
    @Override
    public database.ReservationManager getReservationManager() {
        if(reservationManager==null) {
            reservationManager = new mysql.ReservationManager();
        }
        return reservationManager;
    }
    
    @Override
    public database.CampsiteManager getCampsiteManager() {
        if(campsiteManager==null) {
            campsiteManager = new mysql.CampsiteManager();
        }
        return campsiteManager;
    }
    
    @Override
    public database.AssignedSiteManager getAssignedSiteManager() {
        if(assignedSiteManager==null) {
            assignedSiteManager = new mysql.AssignedSiteManager();
        }
        return assignedSiteManager;
    }
    
    @Override
    public database.WaitingListManager getWaitingListManager() {
        if(waitingListManager==null) {
            waitingListManager = new mysql.WaitingListManager();
        }
        return waitingListManager;
    }
    
    @Override
    public database.YearlyInformationManager getYearlyInformationManager() {
        if(yearlyInformationManager==null) {
            yearlyInformationManager = new mysql.YearlyInformationManager();
        }
        return yearlyInformationManager;
    }

 }
