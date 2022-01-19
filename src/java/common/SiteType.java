package common;

import java.io.Serializable;

/**
 * @author Noah Young (2021)
 */
public enum SiteType implements Serializable{
    Seasonal("Seasonal"),
    Monthly("Monthly"),
    Weekly("Weekly"),
    Daily("Daily");

    private final String siteType;
    
    SiteType(String siteType){
        this.siteType = siteType.trim();
    }
    public static SiteType getSiteType (String type) {
      SiteType siteType = Seasonal; 
        for (SiteType s : SiteType.values ()) {
            if (s.siteType.equalsIgnoreCase(type.trim())){
                siteType = s;
                break;
            }
        }
        return (siteType);
    }
    public String getSiteTypeName(){
        return siteType;
    }
    
   @Override
    public String toString() {
        return siteType;
    }
}
