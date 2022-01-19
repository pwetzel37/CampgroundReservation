
package database;
import common.Campsite;
import common.SiteType;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * A <code>interface</code> that specifies the allowable operations on campsites 
 * in the system.
 * 
 * @author Drew Wagner, Scott Harchar(2021)
 */
public interface CampsiteManager {
    public Campsite addCampsite(Campsite campsite);
    public boolean deleteCampsite(Campsite campsite);
    public Campsite updateCampsite(Campsite campsite);
    public Campsite getCampsiteById(int campsiteId);
    public Campsite getCampsiteByCampsiteName(String campsiteName);
    public Collection<Campsite> getAllCampsitesBySiteType(SiteType siteType);
    public Collection<Campsite> getUnassignedSitesByDateRange(LocalDateTime startDate,
            LocalDateTime endDate);
    public Collection<Campsite> getAllCampsites();
    public boolean deleteCampsiteById(int campsiteId);
    public Campsite getCampsiteWithNameNumberSection(Campsite campsite);

}
