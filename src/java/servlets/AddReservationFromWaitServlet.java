package servlets;

import common.Customer;
import common.Reservation;
import database.CustomerManager;
import database.ReservationManager;
import database.WaitingListManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Takes inputs from ResFromWaitListModal and adds the reservation.
 *
 * @author Noah Young
 */
@WebServlet(name = "AddReservationFromWaitServlet", urlPatterns = {"/AddReservationFromWaitServlet"})
public class AddReservationFromWaitServlet extends HttpServlet {

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
        ReservationManager rm = database.Database.getDatabaseManagement().getReservationManager();
        WaitingListManager wm = database.Database.getDatabaseManagement().getWaitingListManager();
        int id = Integer.parseInt(request.getParameter("listId"));

        // get customer name from modal selection, find customer's id
        String customerName = request.getParameter("customer_name");
        Customer customer = cm.getCustomerByName(customerName);
        int customerId = customer.getCustomerId();
        
        // get arrival and departure date inputs
        String arrivalString = request.getParameter("arrival_date");
        arrivalString += " 14:00:00";               // default arrival time always 2:00 PM?
        String departureString = request.getParameter("departure_date");
        departureString += " 09:00:00";             // default departure time always 9:00 AM?
        // turn strings into LocalDateTime with DateTimeFormatter and default times
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime arrivalDate = LocalDateTime.parse(arrivalString, formatter);
        LocalDateTime departureDate = LocalDateTime.parse(departureString, formatter);
        // calculate number of nights between chosen dates
        int numberOfNights = (int) ChronoUnit.DAYS.between(arrivalDate, departureDate);
        
        // get the rest of the modal inputs
        int numberOfSitesRequested = Integer.parseInt(request.getParameter("sites_requested"));
        int numberOfSitesAssigned = 0;              // defualt number of assigned sites is 0
        LocalDateTime dateReservationMade = LocalDateTime.now();
        BigDecimal deposit = new BigDecimal(0.0);   // default deposit is $0
        String notes = request.getParameter("notes");
        
        // create reservation and add to database
        Reservation reservation = new Reservation(
                customerId, arrivalDate, departureDate, numberOfNights, numberOfSitesRequested, 
                numberOfSitesAssigned, dateReservationMade, deposit, notes);
        Reservation added = rm.addReservation(reservation);
        wm.deleteWaitingListById(id);

        response.sendRedirect("/Campground/WaitListManager");

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
