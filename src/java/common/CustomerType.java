
package common;

import java.io.Serializable;

/**
 * Needs Comments 
 * @author cjones
 */
public enum CustomerType implements Serializable {
    Individual("Individual"),
    Group("Group"),
    Club("Club");
    
    private final String customerType;
    
    CustomerType(String customerType){
        this.customerType=customerType.trim();
    }
    
    public static CustomerType getCustomerType (String type) {
       CustomerType customerType = Individual; 

        for (CustomerType e : CustomerType.values ()) {
            if (e.customerType.equalsIgnoreCase(type.trim())){
                customerType = e;
                break;
            }
        }
        return (customerType);
    }
    public String getCustomerTypeName(){
        return customerType;
    }
    
   @Override
    public String toString() {
        return customerType;
    }
}
