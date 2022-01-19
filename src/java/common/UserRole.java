
package common;

import java.io.Serializable;
import utilities.Debug;

/**
 * Represents the role with which a <code> User </code> in this system. Different
 * users have different access levels and different levels of functionality and
 * responsibility within the system.  
 * 
 * @author Curt Jones (2017)
 */
public enum UserRole implements Serializable{
    
    /**
     * The owner of the campground, has complete control of the
     * system. 
     */
    Owner("Owner"),
    
    /**
     * The Systems administrator, will get error messages and fix bugs in the program. 
     * Systems administrator does have has complete control of the system as well as the owner. 
     * system. 
     */
    SystemAdmin("SystemAdmin"),
    
    
    /**
     * Office staff in the campground. 
     */
    OfficeStaff("Office Staff");
    
    private final String roleName;
    
    /**
     * Constructs a <code> UserRole </code> given a role name. A user role
     * represents their position in the college and does not require them to be
     * a member of the <code> Faculty </code>.
     * 
     * @param roleName The name of this <code> UserRole </code> position.
     */
    UserRole(String roleName){
        this.roleName=roleName.trim();
    }
    
    /**
     * Returns the <code> UserRole </code> of the given role name. If the role name
     * given is not one of the roles in the enumerated type, the default is set to "Guest."
     * 
     * @param roleName The name of this role, preferably one already in the enumerated type
     * (SystemAdmin, Administrator, Chair, Advisor, Instructor, Staff, or Student).
     * 
     * @return The <code> UserRole </code> corresponding to the role name given.
     */
     public static UserRole getUserRole (String roleName) {
       UserRole name = OfficeStaff; // default is Office Staff 

        for (UserRole e : UserRole.values ()) {
            if (e.roleName.equalsIgnoreCase(roleName.trim())){
                name = e;
                break;
            }
        }
        return (name);
    }

     /**
     * Returns the role name of this <code> UserRole </code>.
     * 
     * @return This <code> UserRole </code>'s name.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Returns this <code> UserRole </code>'s name as a string.
     * 
     * @return The name of this <code> UserRole </code> as a string.
     */
    @Override
    public String toString() {
        return "UserRole{" + "roleName=" + roleName + '}';
    }
    
    /**
     * Tests the <code>UserRole</code> class. Reviews the toString(), 
     * getRoleName(), getEnum(), and UserRole().
     * 
     * @param args Command line arguments.
     */
    public static void main (String args[]){
        utilities.Debug.setEnabled(true);
        Debug.println(getUserRole("Office Staff"));
        Debug.println(getUserRole("C").toString());
        
        Debug.println(Owner.getRoleName());
        Debug.println(OfficeStaff.getRoleName());

  
        
        
        Debug.println();
    }
}
