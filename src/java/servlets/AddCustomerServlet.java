package servlets;

import common.Customer;
import common.CustomerType;
import common.RVType;
import database.CustomerManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Takes inputs from AddCustomerModal and adds the Customer to the database.
 * 
 * Sets all Customer object variables with input from AddCustomerModal
 * Creates a Customer object with those variables
 * Creates CustomerManager object, uses methods to add customer to database
 * Returns to updated CustomerManager table
 * 
 * Still needs to check for valid inputs
 * 
 * @author Patrick Wetzel, Scott Harchar (2021)
 */
@WebServlet(name = "AddCustomerServlet", urlPatterns = {"/AddCustomerServlet"})
public class AddCustomerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        CustomerManager cm = database.Database.getDatabaseManagement().getCustomerManager();
        Customer customer;
        Customer added = null;
        Customer updated = null;
        
        if(request.getParameter("first_name")!=null && !request.getParameter("first_name").equals("")){
            // get customer type string from AddCustomerModal, set accordingly
            CustomerType customerType = null;
            switch (request.getParameter("customer_type")) {
                case "individual": customerType = CustomerType.Individual; break;
                case "group": customerType = CustomerType.Group; break;
                case "club": customerType = CustomerType.Club; break;
                default: break;
            }
            // get rv type string from AddCustomerModal, set accordingly
            RVType rvType = null;
            switch (request.getParameter("rv_type")) {
                case "travel_trailer": rvType = RVType.TravelTrailer; break;
                case "fifth_wheel": rvType = RVType.FifthWheel; break;
                case "motor_home": rvType = RVType.MotorHome; break;
                case "truck_camper": rvType = RVType.TruckCamper; break;
                case "van": rvType = RVType.Van; break;
                case "pop_up":rvType = RVType.PopUp; break;
                default: break;
            }
            // set strings from AddCustomerModal
            String name = request.getParameter("customer_name");
            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            String spouseFirstName = request.getParameter("spouse_first_name");
            String spouseLastName = request.getParameter("spouse_last_name");
            String primaryPhone = request.getParameter("mobile_phone");
            String mobilePhone = request.getParameter("mobile_phone"); 
            String spousePhone = request.getParameter("spouse_phone");
            String addressLine1 = request.getParameter("address_line_1");
            String addressLine2 = request.getParameter("address_line_2");
            String town = request.getParameter("city");
            String state = request.getParameter("state");
            String zip = request.getParameter("zip_code");
            String emailAddress = request.getParameter("email_1"); 
            String spouseEmailAddress = request.getParameter("email_2");
            String notes = request.getParameter("notes");
            // set integers from AddCustomerModal
            int rvLength = Integer.parseInt(request.getParameter("rv_length")); 
            int numberOfSlideOuts = Integer.parseInt(request.getParameter("rv_slide_outs"));
            // flagged should always be false on new customer
            boolean flagged = false;
            // create Customer object with variables
            customer = new Customer(
            customerType, name, firstName, lastName, 
            spouseFirstName, spouseLastName, 
            primaryPhone, mobilePhone,  spousePhone, 
            addressLine1, addressLine2, town, state,
            zip, emailAddress, spouseEmailAddress, 
            rvType, rvLength, numberOfSlideOuts, flagged, notes
            );
        
            if(request.getParameter("group_id") != null){
                customer.setGroupId(Integer.parseInt(request.getParameter("group_id")));
            }
            added = cm.addCustomer(customer);
        }else{
            customer = cm.getCustomerByName(request.getParameter("attach_customer_name"));
            customer.setGroupId(Integer.parseInt(request.getParameter("group_id")));
            updated = cm.updateCustomer(customer);
        }
        
        int pageNum = Integer.parseInt(request.getParameter("page_num")); 
        
        if (added == null && updated == null) {
            try (PrintWriter out = response.getWriter()) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Customer unsuccessfully Added');");
                out.println("location='/Campground/CustomerManager';");
                out.println("</script>");
            }
        } else {
            if (pageNum == 0) {
                response.sendRedirect("/Campground/CustomerManager");
            }
            if (pageNum == 1) {
                response.sendRedirect("/Campground/ReservationManager");
            }
        }
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
