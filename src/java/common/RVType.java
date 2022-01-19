
package common;

import java.io.Serializable;

/**
 *
 * @author cjones
 */
public enum RVType implements Serializable{
    TravelTrailer("Travel Trailer"),
    FifthWheel("Fifth Wheel"),
    MotorHome("Motor Home"),
    TruckCamper("Truck Camper"),
    Van("Van"),
    PopUp("PopUp");

    private final String rvType;
    
    RVType(String rvType){
        this.rvType = rvType.trim();
    }
    public static RVType getRVType (String type) {
      RVType rvType = TravelTrailer; 
        for (RVType e : RVType.values ()) {
            if (e.rvType.equalsIgnoreCase(type.trim())){
                rvType = e;
                break;
            }
        }
        return (rvType);
    }
    public String getRVTypeName(){
        return rvType;
    }
    
   @Override
    public String toString() {
        return rvType;
    }
}
