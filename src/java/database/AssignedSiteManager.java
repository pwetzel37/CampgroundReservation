
package database;

import common.AssignedSite;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * A <code>interface</code> that specifies the allowable operations on assigned sites 
 * in the system.
 * @author Drew Wagner, Scott Harchar (2021)
 */
public interface AssignedSiteManager {
    public AssignedSite addAssignedSite(AssignedSite assignedSite);
    public boolean deleteAssignedSite(AssignedSite assignedSite);
    public AssignedSite updateAssignedSite(AssignedSite assignedSite);
    public AssignedSite getAssignedSiteById(int assignedSiteId);
    public Collection<AssignedSite> getAssignedSiteByReservationId(int reservationId);
    public Collection<AssignedSite> getAllAssignedSites();
    public boolean deleteAssignedSiteById(int assignedSiteId);
    public boolean sitesAvailableInDateRange(int campsiteID, LocalDateTime startDate, LocalDateTime endDate);
}
