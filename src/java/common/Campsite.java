package common;

import java.io.Serializable;
import java.sql.Blob;
import utilities.Debug;

/**
 * Represents a Campsite.
 * 
 * @author Noah Young (2021)
 */
public class Campsite implements Comparable<Campsite>, Serializable {
    private int campsiteId; // Primary key, auto incremented.
    private String campsiteName;
    private int campsiteNumber;
    private char campsiteSection;
    private int maxLength;
    private int width;
    private boolean acceptsSlideOut;
    private boolean pullThru;
    private SiteType campsiteType;
    private String notes;
    private Blob image;
    private String imageSource;
    
    /**
     * Constructs an empty Campsite
     */
    public Campsite () {}
    
    /**
     * Constructs a new Campsite with ID
     * @param campsiteId A number that represents this Campsite. This number is
     * the primary key in our database and is auto incremented.
     * @param campsiteName The name of the campsite.
     * @param campsiteNumber The number of the campsite. Similar to a hotel's
     * room number.
     * @param campsiteSection The section the campsite is located.
     * @param maxLength The max length of the campsite.
     * @param width The width of the campsite. Default is 25.
     * @param acceptsSlideOut Boolean that determines whether or not this
     * campsite accepts slide outs.
     * @param pullThru Boolean that determines whether or not this campsite is
     * a pull thru site.
     * @param campsiteType The type of campsite (Seasonal, monthly, daily)
     * @param notes The notes about this campsite.
     * @param image The sql Blob reference for the campsite image.
     * @param imageSource The source of the campsite image.
     */
    public Campsite (int campsiteId, String campsiteName, int campsiteNumber,
                     char campsiteSection, int maxLength, int width,
                     boolean acceptsSlideOut, boolean pullThru,
                     SiteType campsiteType, String notes, Blob image,
                     String imageSource) {
        this.campsiteId = campsiteId;
        this.campsiteName = campsiteName;
        this.campsiteNumber = campsiteNumber;
        this.campsiteSection = campsiteSection;
        this.maxLength = maxLength;
        this.width = width;
        this.acceptsSlideOut = acceptsSlideOut;
        this.pullThru = pullThru;
        this.campsiteType = campsiteType;
        this.notes = notes;
        this.image = image;
        this.imageSource = imageSource;
    }
    
    /**
     * Constructs a new Campsite
     * @param campsiteName The name of the campsite.
     * @param campsiteNumber The number of the campsite. Similar to a hotel's
     * room number.
     * @param campsiteSection The section the campsite is located.
     * @param maxLength The max length of the campsite.
     * @param width The width of the campsite. Default is 25.
     * @param acceptsSlideOut Boolean that determines whether or not this
     * campsite accepts slide outs.
     * @param pullThru Boolean that determines whether or not this campsite is
     * a pull thru site.
     * @param campsiteType The type of campsite (Seasonal, monthly, daily)
     * @param notes The notes about this campsite.
     * @param image The sql Blob reference for the campsite image.
     * @param imageSource The source of the campsite image.
     */
    public Campsite (String campsiteName, int campsiteNumber,
                     char campsiteSection, int maxLength, int width,
                     boolean acceptsSlideOut, boolean pullThru,
                     SiteType campsiteType, String notes, Blob image,
                     String imageSource) {
        this.campsiteName = campsiteName;
        this.campsiteNumber = campsiteNumber;
        this.campsiteSection = campsiteSection;
        this.maxLength = maxLength;
        this.width = width;
        this.acceptsSlideOut = acceptsSlideOut;
        this.pullThru = pullThru;
        this.campsiteType = campsiteType;
        this.notes = notes;
        this.image = image;
        this.imageSource = imageSource;
    }
    
    /**
     * Gets the campsite id of this campsite.
     * 
     * @return The campsite id of this campsite. If there is no campsite id,
     * null is returned.
     */
    public int getCampsiteId(){
        return campsiteId;
    }
    
    /**
     * Sets the campsite id of this campsite. No error checking is performed.
     *
     * @param campsiteId The campsite id that is set to this campsite.
     */
    public void setCampsiteId(int campsiteId){
        this.campsiteId = campsiteId;
    }
    
    /**
     * Gets the campsite name of this campsite.
     * 
     * @return The campsite name of this campsite. If there is no campsite name,
     * null is returned.
     */
    public String getCampsiteName(){
        return campsiteName;
    }
    
    /**
     * Sets the campsite name of this campsite. No error checking is performed.
     *
     * @param campsiteName The campsite name that is set to this campsite.
     */
    public void setCampsiteName(String campsiteName){
        this.campsiteName = campsiteName;
    }
    
    /**
     * Gets the campsite number of this campsite.
     * 
     * @return The campsite number of this campsite. If there is no campsite
     * number, null is returned.
     */
    public int getCampsiteNumber(){
        return campsiteNumber;
    }
    
    /**
     * Sets the campsite number of this campsite. No error checking is performed.
     *
     * @param campsiteNumber The campsite number that is set to this campsite.
     */
    public void setCampsiteNumber(int campsiteNumber){
        this.campsiteNumber = campsiteNumber;
    }
    
    /**
     * Gets the campsite section of this campsite.
     * 
     * @return The campsite section of this campsite. If there is no campsite
     * section, null is returned.
     */
    public char getCampsiteSection(){
        return campsiteSection;
    }
    
    /**
     * Sets the campsite section of this campsite. No error checking is performed.
     *
     * @param campsiteSection The campsite section that is set to this campsite.
     */
    public void setCampsiteSection(char campsiteSection){
        this.campsiteSection = campsiteSection;
    }
    
    /**
     * Gets the max length of this campsite.
     * 
     * @return The max length of this campsite. If there is no max
     * length, null is returned.
     */
    public int getMaxLength(){
        return maxLength;
    }
    
    /**
     * Sets the max length of this campsite. No error checking is performed.
     *
     * @param maxLength The max length that is set to this campsite.
     */
    public void setMaxLength(int maxLength){
        this.maxLength = maxLength;
    }
    
    /**
     * Gets the width of this campsite.
     * 
     * @return The width of this campsite. If there is no width,
     * null is returned.
     */
    public int getWidth(){
        return width;
    }
    
    /**
     * Sets the width of this campsite. No error checking is performed.
     *
     * @param width The width that is set to this campsite.
     */
    public void setWidth(int width){
        this.width = width;
    }
    
    /**
     * Gets the boolean value of whether or not the campsite accepts slide outs.
     * 
     * @return True if the campsite accepts slide outs, false otherwise.
     */
    public boolean getAcceptsSlideOut(){
        return acceptsSlideOut;
    }
    
    /**
     * Sets the boolean value of whether or not the campsite accepts slide outs.
     *
     * @param acceptsSlideOut Whether or not the campsite accepts slide outs.
     */
    public void setAcceptsSlideOut(boolean acceptsSlideOut){
        this.acceptsSlideOut = acceptsSlideOut;
    }
    
    /**
     * Gets the boolean value of whether or not the campsite is pull thru.
     * 
     * @return True if the campsite is pull thru, false otherwise.
     */
    public boolean getPullThru(){
        return pullThru;
    }
    
    /**
     * Sets the boolean value of whether or not the campsite is pull thru.
     *
     * @param pullThru Whether or not the campsite is pull thru.
     */
    public void setPullThru(boolean pullThru){
        this.pullThru = pullThru;
    }
    
    /**
     * Gets the campsite type of this campsite.
     * 
     * @return The campsite type of this campsite. If there is no campsite type,
     * null is returned.
     */
    public SiteType getSiteType(){
        return campsiteType;
    }
    
    /**
     * Sets the campsite type of this campsite. No error checking is performed.
     *
     * @param campsiteType The campsite type that is set to this campsite.
     */
    public void setSiteType(SiteType campsiteType){
        this.campsiteType = campsiteType;
    }
    
    /**
     * Gets the notes for this campsite.
     * 
     * @return The notes for this campsite. If there are no campsite notes,
     * null is returned.
     */
    public String getNotes(){
        return notes;
    }
    
    /**
     * Sets the campsite notes of this campsite. No error checking is performed.
     *
     * @param notes The campsite notes that are set to this campsite.
     */
    public void setNotes(String notes){
        this.notes = notes;
    }
    
    /**
     * Gets the image for this campsite.
     * 
     * @return The image for this campsite. If there is no campsite image,
     * null is returned.
     */
    public Blob getImage(){
        return image;
    }
    
    /**
     * Sets the campsite image of this campsite. No error checking is performed.
     *
     * @param image The campsite image that is set to this campsite.
     */
    public void setImage(Blob image){
        this.image = image;
    }
    
    /**
     * Gets the image source for this campsite.
     * 
     * @return The image source for this campsite. If there is no campsite image
     * source, null is returned.
     */
    public String getImageSource(){
        return imageSource;
    }
    
    /**
     * Sets the campsite image source of this campsite. No error checking is
     * performed.
     *
     * @param imageSource The campsite image source that is set to this campsite.
     */
    public void setImageSource(String imageSource){
        this.imageSource = imageSource;
    }
    
    /**
     * Overrides the equals method to compare two <code> Campsite </code>s. If
     * the object parameter is not a <code> Campsite </code> or is null return
     * false. Otherwise, if the <code> Campsite </code> is equal to this
     * <code> Campsite </code>, return true.
     *
     * @param obj The <code> Campsite </code> object to be compared to.
     * @return Whether or not the <code> Campsite </code> given is equal to this
     * <code> Campsite </code>.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Campsite other = (Campsite) obj;
        return this.campsiteId == other.campsiteId;
    }

    /**
     * Returns the hash value of this campsite Id.
     *
     * @return The hash value of this campsite Id.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.campsiteId;
        return hash;
    }
    
    /**
     * Returns the order of a <code> Campsite </code> compared to this <code> 
     * Campsite </code>.
     *
     * @param o The <code> Campsite </code> to compare to this <code> Campsite </code>.
     * @return The order of the given <code> Campsite </code> relative to this
     * <code> Campsite </code>
     * Positive for before, zero for equal, and negative for after.
     */
    @Override
    public int compareTo(Campsite o) {
        int order = campsiteName.compareToIgnoreCase(o.campsiteName);
        return order;
    }
    
    @Override
    public String toString() {
        return "Campsite{" + "campsiteId=" + campsiteId
                + ", campsiteName=" + campsiteName 
                + ", campsiteNumber=" + campsiteNumber
                + ", campsiteSection=" + campsiteSection
                + ", maxLength=" + maxLength
                + ", width="  + width 
                + ", acceptsSlideOut=" + acceptsSlideOut 
                + ", pullThru=" + pullThru 
                + ", campsiteType=" + campsiteType
                + ", notes=" + notes 
                + ", image=" + image
                + ", imageSource=" + imageSource + "}";
    }
    
    /**
     * Tests the Campsite class.
     *
     * @param args Command line arguments.
     */
    public static void main(String args[]) {
        utilities.Debug.setEnabled(true);
        Blob img1 = null;
        Blob img2 = null;
        
        Campsite c1 = new Campsite();
        c1.setCampsiteId(1);
        c1.setCampsiteName("Mountain View");
        c1.setCampsiteNumber(101);
        c1.setCampsiteSection('A');
        c1.setMaxLength(60);
        c1.setWidth(25);
        c1.setAcceptsSlideOut(true);
        c1.setPullThru(false);
        c1.setSiteType(SiteType.Seasonal);
        c1.setNotes("A wonderful campsite with gorgeous mountain scenery!");
        c1.setImage(img1);
        c1.setImageSource("../images/MountainView1.jpg");

        Campsite c2 = new Campsite(2, "Lake View", 102, 'B', 50, 25, false,
                true, SiteType.Monthly, "A delightful campsite with a lake view!", 
                img2, "../images/Campsite1.jpg");

        Debug.println("CAMP ID: " + c1.getCampsiteId());
        Debug.println("CAMP NAME: " + c1.getCampsiteName());
        Debug.println("CAMP NUMBER: " + c1.getCampsiteNumber());
        Debug.println("CAMP SECTION: " + c1.getCampsiteSection());
        Debug.println("MAX LENGTH: " + c1.getMaxLength());
        Debug.println("WIDTH: " + c1.getWidth());
        Debug.println("ACCEPTS SLIDE OUT: " + c1.getAcceptsSlideOut());
        Debug.println("PULL THRU: " + c1.getPullThru());
        Debug.println("CAMP TYPE: " + c1.getSiteType());
        Debug.println("NOTES: " + c1.getNotes());
        Debug.println("IMAGE: " + c1.getImage());
        Debug.println("IMAGE SRC: " + c1.getImageSource());

        Debug.println();
        
        Debug.println(c2.toString());
        
        Debug.println();
        
        Debug.println(c1.equals(c1));
        Debug.println(c1.equals(c2));
        Debug.println(c1.compareTo(c1));
        Debug.println(c1.compareTo(c2));
        Debug.println(c2.compareTo(c1));
    }
}
