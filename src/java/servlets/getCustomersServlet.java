/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import common.Customer;
import common.CustomerType;
import database.CustomerManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sth68511
 */
@WebServlet(name = "getCustomersServlet", urlPatterns = {"/getCustomers"})
public class getCustomersServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CustomerManager cm = database.Database.getDatabaseManagement().getCustomerManager();
        Customer mainCustomer = cm.getCustomerByName(request.getParameter("mainCustomer"));
        ArrayList<Customer> groupMembers = null;
        if(mainCustomer.getCustomerType()!=CustomerType.Individual){
            groupMembers = new ArrayList<>(cm.getAllCustomersInGroup(mainCustomer.getGroupId()));
        }       
        ArrayList<Customer> otherCusts = new ArrayList<>(cm.getAllCustomers());
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(mainCustomer);
        if(groupMembers != null && !groupMembers.isEmpty()){customers.addAll(groupMembers);}
        if(!otherCusts.isEmpty()){
            for (Customer cust : otherCusts) {
                if (!customers.contains(cust)){customers.add(cust);}
            }
        }
        
        if(!customers.isEmpty()){
            PrintWriter pw = response.getWriter();
            for(Customer cust : customers){
                pw.println("<option value="+cust.getCustomerId()+">"+cust.getName()+"</option>");
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
