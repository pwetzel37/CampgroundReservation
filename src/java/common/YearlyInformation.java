
package common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import utilities.Debug;

/**
 * Represents a YearlyInformation Object. This contains all the information an
 * owner can set in the Owner Management section of the application.
 * @author Drew Wagner (2021)
 */
public class YearlyInformation implements Comparable<YearlyInformation>, Serializable {

    private int year; // Primary key
    private BigDecimal dailyRate;
    private BigDecimal weeklyRate;
    private BigDecimal monthlyRate;
    private BigDecimal seasonalRate;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private BigDecimal seasonalVisitorPassRate;
    private BigDecimal nightlyVisitorFee;
    private BigDecimal dailyVisitorFee;
    private BigDecimal earlyCheckInFeePerHour;
    private BigDecimal lateStayFeePerHour;
    private BigDecimal nightlyCancellationFee;
    private BigDecimal weeklyCancellationFee;
    private BigDecimal monthlyCancellationFee;
    private BigDecimal electricalRatePerKWH;
    
    /**
     * Constructs an empty yearly information object.
     */
    public YearlyInformation() {}
    
    /**
     * 
     * @param year The year in which the information about the campground is for.
     * This is the primary key in out database.
     * @param dailyRate The cost to stay at one site for a day.
     * @param weeklyRate The cost to stay at one site for a week.
     * @param monthlyRate The cost to stay at one site for a month.
     * @param seasonalRate The cost to stay at one site for the season.
     * @param openingDate The date in which the campground opens for the season.
     * This can be null for campgrounds are open year round.
     * @param closingDate The date in which the campground closes for the season.
     * This can be null for campgrounds are open year round.
     * @param seasonalVisitorPassRate The cost for a seasonal visitor pass.
     * @param nightlyVisitorFee The cost for a nightly visitor.
     * @param earlyCheckInFeePerHour The per hour cost for early check in.
     * @param lateStayFeePerHour The per hour cost for late checkout.
     * @param nightlyCancellationFee The cost for canceling a reservation for a single night.
     * @param weeklyCancellationFee The cost for canceling a reservation for a single week.
     * @param monthlyCancellationFee The cost for canceling a reservation for a single month.
     */
    public YearlyInformation (int year, BigDecimal dailyRate, BigDecimal weeklyRate,
     BigDecimal monthlyRate, BigDecimal seasonalRate, LocalDate openingDate, LocalDate closingDate, BigDecimal seasonalVisitorPassRate, 
     BigDecimal nightlyVisitorFee, BigDecimal dailyVisitorFee, BigDecimal earlyCheckInFeePerHour, BigDecimal lateStayFeePerHour, 
     BigDecimal nightlyCancellationFee, BigDecimal weeklyCancellationFee, BigDecimal monthlyCancellationFee,
     BigDecimal electricalRatePerKWH) {
        this.year = year; // Primary key
        this.dailyRate = dailyRate;
        this.weeklyRate = weeklyRate;
        this.monthlyRate = monthlyRate;
        this.seasonalRate = seasonalRate;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.seasonalVisitorPassRate = seasonalVisitorPassRate;
        this.nightlyVisitorFee = nightlyVisitorFee;
        this.dailyVisitorFee = dailyVisitorFee;
        this.earlyCheckInFeePerHour = earlyCheckInFeePerHour;
        this.lateStayFeePerHour = lateStayFeePerHour;
        this.nightlyCancellationFee = nightlyCancellationFee;
        this.weeklyCancellationFee = weeklyCancellationFee;
        this.monthlyCancellationFee = monthlyCancellationFee;
        this.electricalRatePerKWH= electricalRatePerKWH;
    }
    
    /**
     * Gets the year for the yearly information object
     * 
     * @return The year for the yearly information object
     */
    public int getYear(){ 
        return year;
    } 
    
    /**
     * Gets the daily rate for the yearly information object
     * 
     * @return The daily rate for the yearly information object
     */
    public BigDecimal getDailyRate(){ 
        return dailyRate;
    }
    
    /**
     * Gets the weekly rate for the yearly information object
     * 
     * @return The weekly rate for the yearly information object
     */
    public BigDecimal getWeeklyRate(){ 
        return weeklyRate;
    }
    
    /**
     * Gets the monthly rate for the yearly information object
     * 
     * @return The monthly rate for the yearly information object
     */
    public BigDecimal getMonthlyRate(){ 
        return monthlyRate;
    }
    /**
     * Gets the seasonal rate for the yearly information object
     * 
     * @return The seasonal rate for the yearly information object
     */
    public BigDecimal getSeasonalRate(){ 
        return seasonalRate;
    }
    
    /**
     * Gets the opening date for the yearly information object
     * 
     * @return The opening date for the yearly information object
     */
    public LocalDate getOpeningDate(){ 
        return openingDate;
    }
    
    /**
     * Gets the closing date for the yearly information object
     * 
     * @return The closing date for the yearly information object
     */
    public LocalDate getClosingDate(){ 
        return closingDate;
    }
    
    /**
     * Gets the seasonal visitor pass rate for the yearly information object
     * 
     * @return The seasonal visitor pass rate for the yearly information object
     */
    public BigDecimal getSeasonalVisitorPassRate(){ 
        return seasonalVisitorPassRate;
    }
    
    /**
     * Gets the nightly visitor fee for the yearly information object
     * 
     * @return The nightly visitor fee for the yearly information object
     */
    public BigDecimal getNightlyVisitorFee(){ 
        return nightlyVisitorFee;
    }
    
    /**
     * Gets the daily visitor fee for the yearly information object
     * 
     * @return The daily visitor fee for the yearly information object
     */
    public BigDecimal getDailyVisitorFee(){ 
        return dailyVisitorFee;
    }
    
    /**
     * Gets the per hour early check in fee for the yearly information object
     * 
     * @return The per hour early check in fee for the yearly information object
     */
    public BigDecimal getEarlyCheckInFeePerHour(){ 
        return earlyCheckInFeePerHour;
    }
    
    /**
     * Gets the per hour late stay fee for the yearly information object
     * 
     * @return The per hour late stay fee for the yearly information object
     */
    public BigDecimal getLateStayFeePerHour(){ 
        return lateStayFeePerHour;
    }
    
    /**
     * Gets the nightly cancellation fee for the yearly information object
     * 
     * @return The nightly cancellation fee for the yearly information object
     */
    public BigDecimal getNightlyCancellationFee(){ 
        return nightlyCancellationFee;
    }
    
    /**
     * Gets the weekly cancellation fee for the yearly information object
     * 
     * @return The weekly cancellation fee for the yearly information object
     */
    public BigDecimal getWeeklyCancellationFee(){ 
        return weeklyCancellationFee;
    }
    
    /**
     * Gets the monthly cancellation fee for the yearly information object
     * 
     * @return The monthly cancellation fee for the yearly information object
     */
    public BigDecimal getMonthlyCancellationFee(){ 
        return monthlyCancellationFee;
    }
    
    /**
     * Gets the per KWH rate for electricity for the yearly information object
     * 
     * @return The per KWH rate for electricity for the yearly information object
     */
    public BigDecimal getElectricalRatePerKWH(){ 
        return electricalRatePerKWH;
    }
    
    
    /**
     * Sets the  of this yearly information object
     * 
     * @param year 
     */
    public void setYear(int year){ 
        this.year = year;
    } 
    
    /**
     * Sets the  of this yearly information object
     * 
     * @param dailyRate 
     */
    public void setDailyRate(BigDecimal dailyRate){ 
        this.dailyRate = dailyRate;
    }
    
    /**
     * Sets the  of this yearly information object
     * 
     * @param weeklyRate 
     */
    public void setWeeklyRate(BigDecimal weeklyRate){ 
        this.weeklyRate = weeklyRate;
    }
    
    /**
     * Sets the  of this yearly information object
     * 
     * @param seasonalRate 
     */
    public void setSeasonalRate(BigDecimal seasonalRate){ 
        this.seasonalRate = seasonalRate;
    }
    
    /**
     * Sets the  of this yearly information object
     * 
     * @param monthlyRate 
     */
    public void setMonthlyRate(BigDecimal monthlyRate){ 
        this.monthlyRate = monthlyRate;
    }
    
    /**
     * Sets the  of this yearly information object
     * 
     * @param openingDate 
     */
    public void setOpeningDate(LocalDate openingDate){ 
        this.openingDate = openingDate;
    }
    
    /**
     * Sets the  of this yearly information object
     * 
     * @param closingDate 
     */
    public void setClosingDate(LocalDate closingDate){ 
        this.closingDate = closingDate;
    }
    
    /**
     * Sets the  of this yearly information object
     * 
     * @param seasonalVisitorPassRate 
     */
    public void setSeasonalVisitorPassRate(BigDecimal seasonalVisitorPassRate){ 
        this.seasonalVisitorPassRate = seasonalVisitorPassRate;
    }
    
    /**
     * Sets the  of this yearly information object
     * 
     * @param nightlyVisitorFee 
     */
    public void setNightlyVisitorFee(BigDecimal nightlyVisitorFee){ 
        this.nightlyVisitorFee = nightlyVisitorFee;
    }
    
    /**
     * Sets the  of this yearly information object
     * 
     * @param dailyVisitorFee 
     */
    public void setDailyVisitorFee(BigDecimal dailyVisitorFee){ 
        this.dailyVisitorFee = dailyVisitorFee;
    }
    
    /**
     * Sets the  of this yearly information object
     * 
     * @param earlyCheckInFeePerHour 
     */
    public void setEarlyCheckInFeePerHour(BigDecimal earlyCheckInFeePerHour){ 
        this.earlyCheckInFeePerHour = earlyCheckInFeePerHour;
    }
    
    /**
     * Sets the  of this yearly information object
     * 
     * @param lateStayFeePerHour 
     */
    public void setLateStayFeePerHour(BigDecimal lateStayFeePerHour){ 
        this.lateStayFeePerHour = lateStayFeePerHour;
    }
    
    
    /**
     * Sets the  of this yearly information object
     * 
     * @param nightlyCancellationFee 
     */
    public void setNightlyCancellationFee(BigDecimal nightlyCancellationFee){ 
        this.nightlyCancellationFee = nightlyCancellationFee;
    }
    
    /**
     * 
     * @param weeklyCancellationFee 
     */
    public void setWeeklyCancellationFee(BigDecimal weeklyCancellationFee){ 
        this.weeklyCancellationFee = weeklyCancellationFee;
    }
    
    /**
     * Sets the  of this yearly information object
     * 
     * @param monthlyCancellationFee 
     */
    public void setMonthlyCancellationFee(BigDecimal monthlyCancellationFee){ 
        this.monthlyCancellationFee = monthlyCancellationFee;
    }
    
    /**
     * Sets the  of this yearly information object
     * 
     * @param electricalRatePerKWH 
     */
    public void setElectricalRatePerKWH(BigDecimal electricalRatePerKWH){ 
        this.electricalRatePerKWH = electricalRatePerKWH;
    }
    
    /**
     * Overrides the equals method to compare two <code> AssignedSite </code>s.
     * If the object parameter is not an <code> AssignedSite </code> or is null
     * return false. Otherwise, if the <code> AssignedSite </code> is equal to
     * this <code> AssignedSite </code>, return true.
     *
     * @param obj The <code> AssignedSite </code> object to be compared to.
     * @return Whether or not the <code> AssignedSite </code> given is equal to
     * this <code> AssignedSite </code>.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final YearlyInformation other = (YearlyInformation) obj;
        return this.year == other.year;
    }

    /**
     * Returns the hash value of this assigned site Id.
     *
     * @return The hash value of this assigned site Id.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.year;
        return hash;
    }
    
    /**
     * Returns the order of a <code> AssignedSite </code> compared to this
     * <code> AssignedSite </code>.
     *
     * @param o The <code> AssignedSite </code> to compare to this <code> 
     * AssignedSite </code>.
     * @return The order of the given <code> AssignedSite </code> relative to
     * this <code> AssignedSite </code>
     * Positive for before, zero for equal, and negative for after.
     */
    @Override
    public int compareTo(YearlyInformation o) {
        int order = Integer.compare(year, o.year);
        return order;
    }
    
    @Override
    public String toString() {
        return "YearlyInformation{" + "year=" + year
                + ", dailyRate="  + dailyRate
                + ", weeklyRate=" + weeklyRate 
                + ", monthlyRate=" + monthlyRate
                + ", seasonalRate=" + seasonalRate 
                + ", openingDate=" + openingDate
                + ", closingDate=" + closingDate
                + ", seasonalVisitorPassRate=" + seasonalVisitorPassRate
                + ", nightlyVisitorFee=" + nightlyVisitorFee
                + ", dailyVisitorFee=" + dailyVisitorFee
                + ", earlyCheckInFeePerHour=" + earlyCheckInFeePerHour
                + ", lateStayFeePerHour=" + lateStayFeePerHour
                + ", nightlyCancellationFee=" + nightlyCancellationFee
                + ", weeklyCancellationFee=" + weeklyCancellationFee
                + ", monthlyCancellationFee=" + monthlyCancellationFee
                + ", electricalRatePerKWH=" + electricalRatePerKWH + "}";
    }
    
    
    /**
     * Tests the YearlyInformation class.
     *
     * @param args Command line arguments.
     */
    public static void main(String args[]) {
        utilities.Debug.setEnabled(true);
        
        YearlyInformation y1 = new YearlyInformation();
        y1.setYear(2021);
        y1.setDailyRate(BigDecimal.valueOf(60.00));
        y1.setWeeklyRate(BigDecimal.valueOf(350.00));
        y1.setMonthlyRate(BigDecimal.valueOf(850.00));
        y1.setSeasonalRate(BigDecimal.valueOf(2200.00));
        y1.setOpeningDate(LocalDate.now());
        y1.setClosingDate(LocalDate.now());
        y1.setSeasonalVisitorPassRate(BigDecimal.valueOf(50.00));
        y1.setNightlyVisitorFee(BigDecimal.valueOf(5.00));
        y1.setDailyVisitorFee(BigDecimal.valueOf(3.00));
        y1.setEarlyCheckInFeePerHour(BigDecimal.valueOf(5.00));
        y1.setLateStayFeePerHour(BigDecimal.valueOf(8.00));
        y1.setNightlyCancellationFee(BigDecimal.valueOf(15.00));
        y1.setWeeklyCancellationFee(BigDecimal.valueOf(15.00));
        y1.setMonthlyCancellationFee(BigDecimal.valueOf(50.00));
        y1.setElectricalRatePerKWH(BigDecimal.valueOf(0.13));

        YearlyInformation y2 = new YearlyInformation(2020, BigDecimal.valueOf(.00), BigDecimal.valueOf(.00),
        BigDecimal.valueOf(.00), BigDecimal.valueOf(.00), LocalDate.now(), LocalDate.now(),
        BigDecimal.valueOf(.00), BigDecimal.valueOf(.00), BigDecimal.valueOf(.00), BigDecimal.valueOf(.00),
        BigDecimal.valueOf(.00), BigDecimal.valueOf(.00), BigDecimal.valueOf(.00), BigDecimal.valueOf(.00),
        BigDecimal.valueOf(.00));

        Debug.println("YEAR: " + y1.getYear());
        Debug.println("DAILY RATE: " + y1.getDailyRate());
        Debug.println("WEEKLY RATE: " + y1.getWeeklyRate());
        Debug.println("MONTHLY RATE: " + y1.getMonthlyRate());
        Debug.println("SEASONAL RATE: " + y1.getSeasonalRate());
        Debug.println("OPENING DATE: " + y1.getOpeningDate());
        Debug.println("CLOSING DATE: " + y1.getClosingDate());
        Debug.println("SEASONAL VISITOR PASS RATE: " + y1.getSeasonalVisitorPassRate());
        Debug.println("NIGHTLY VISITOR FEE: " + y1.getNightlyVisitorFee());
        Debug.println("DAILY VISITOR FEE: " + y1.getDailyVisitorFee());
        Debug.println("EARLY CHECK IN FEE PER HOUR: " + y1.getEarlyCheckInFeePerHour());
        Debug.println("LATE CHECK IN FEE PER HOUR: " + y1.getLateStayFeePerHour());
        Debug.println("NIGHTLY CANCELLATION FEE: " + y1.getNightlyCancellationFee());
        Debug.println("WEEKLY CANCELLATION FEE: " + y1.getWeeklyCancellationFee());
        Debug.println("MONTHLY CANCELLATION FEE: " + y1.getMonthlyCancellationFee());
        Debug.println("ELECTRICAL RATE PER KWH: " + y1.getElectricalRatePerKWH());

        Debug.println();
        
        Debug.println(y2.toString());
        
        Debug.println();
        
        Debug.println(y1.equals(y1));
        Debug.println(y1.equals(y2));
        Debug.println(y1.compareTo(y1));
        Debug.println(y1.compareTo(y2));
        Debug.println(y2.compareTo(y1));
    }
}
