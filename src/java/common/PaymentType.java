/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;

/**
 *
 *  ENUM('Cash', 'CreditCard', 'Check','AccountCredit')
 * @author Dr. Curt Jones
 */
public enum PaymentType implements Serializable{
    CASH("Cash"),
    CREDITCARD("Credit Card"),
    CHECK("Check"),
    ACCOUNTCREDIT("Account Credit");
    
    private String paymentType ;
    
    PaymentType(String paymentType){
        this.paymentType = paymentType.trim();
    }
    
    public static PaymentType getPaymentTypee (String paymentType) {
       PaymentType name = CASH; // default is Cash

        for (PaymentType e : PaymentType.values ()) {
            if (e.paymentType.equalsIgnoreCase(paymentType.trim())){
                name = e;
                break;
            }
        }
        return (name);
    }


    public String getPaymentTypeName() {
        return this.paymentType;
    }

    /**
     * Returns this <code> UserRole </code>'s name as a string.
     * 
     * @return The name of this <code> UserRole </code> as a string.
     */
    @Override
    public String toString() {
        return "PaymentType{" + "payment type = =" + this.paymentType + '}';
    }

}