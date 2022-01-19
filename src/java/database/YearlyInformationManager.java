
package database;

import common.YearlyInformation;
import java.util.Collection;

/**
 *
 * @author Drew Wagner (2021)
 */
public interface YearlyInformationManager {
    public YearlyInformation addYearlyInformation(YearlyInformation yearlyInformation);
    public boolean deleteYearlyInformation(YearlyInformation yearlyInformation);
    public YearlyInformation updateYearlyInformation(YearlyInformation yearlyInformation);
    public YearlyInformation getYearlyInformationByYear(int year);;
    public Collection<YearlyInformation> getAllYearlyInformations();
    public boolean deleteYearlyInformationByYear(int year);
}
