package servlets;

import common.AssignedSite;
import common.Reservation;
import database.AssignedSiteManager;
import database.CustomerManager;
import database.ReservationManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Updates reservation information in database when Update button clicked in EditReservationModal
 *
 * @author Patrick Wetzel (2021)
 */
@WebServlet(name = "EditReservationServlet", urlPatterns = {"/EditReservationServlet"})
public class EditReservationServlet extends HttpServlet {

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
        
        // get reservation and customer manager, reservationId and customerId
        ReservationManager rm = database.Database.getDatabaseManagement().getReservationManager();
        CustomerManager cm = database.Database.getDatabaseManagement().getCustomerManager();
        int reservationId = Integer.parseInt(request.getParameter("reservation_id"));
        int customerId = rm.getReservationByID(reservationId).getCustomerId();
        
        // get LocalDateTimes from EditReservationModal datepickers
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        String arrivalString = request.getParameter("arrival_date");
        arrivalString += " 14:00:00";               // default arrival time always 2:00 PM?
        String departureString = request.getParameter("departure_date");
        departureString += " 09:00:00";             // default departure time always 9:00 AM?
        LocalDateTime arrivalDate = LocalDateTime.parse(arrivalString, dateFormat);
        LocalDateTime departureDate = LocalDateTime.parse(departureString, dateFormat);
        int numberOfNights = (int) ChronoUnit.DAYS.between(arrivalDate, departureDate);
        
        // get rest of values
        int numberOfSitesRequested = Integer.parseInt(request.getParameter("sites_requested"));
        int numberOfSitesAssigned = Integer.parseInt(request.getParameter("sites_assigned"));
        LocalDateTime dateReservationMade = rm.getReservationByID(reservationId).getDateReservationMade();
        BigDecimal deposit = rm.getReservationByID(reservationId).getDeposit();

        String notes = request.getParameter("notes");
        
        // create reservation used for updating
        Reservation reservation = new Reservation(
                reservationId, customerId, arrivalDate, departureDate, 
                numberOfNights, numberOfSitesRequested, numberOfSitesAssigned, 
                dateReservationMade, deposit, notes
        );
        
        // update reservation
        Reservation updated = rm.updateReservation(reservation);
        
        AssignedSiteManager asm = database.Database.getDatabaseManagement().getAssignedSiteManager();
        reservationId = updated.getReservationId();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        ArrayList<AssignedSite> existingSites = new ArrayList<>(asm.getAssignedSiteByReservationId(reservationId));
        
        existingSites.forEach((existingSite) -> {
            asm.deleteAssignedSite(existingSite);
        });
        int sitesAssignedSuccessfully = 0;
        for(int i = 0; i < numberOfSitesRequested; i++)
        {
            // get arrival and departure date inputs
            String arrivalStringSite = request.getParameter("arrival_"+i);
            String departureStringSite = request.getParameter("departure_"+i);
            String campsiteString = request.getParameter("site_"+i);
            int otherCustomerId = Integer.parseInt(request.getParameter("customer_"+i));

            
            if (arrivalStringSite!=null && departureStringSite!=null 
                    && !arrivalStringSite.equals("") && !departureStringSite.equals("")
                    && campsiteString!=null && !campsiteString.equals(""))
            {
                int campsiteId = Integer.parseInt(campsiteString);
                arrivalStringSite += " 14:00:00";               // default arrival time always 2:00 PM?
                departureStringSite += " 09:00:00";             // default departure time always 9:00 AM?
                // turn strings into LocalDateTime with DateTimeFormatter and default times
                LocalDateTime arrivalDateSite = LocalDateTime.parse(arrivalStringSite, formatter);
                LocalDateTime departureDateSite = LocalDateTime.parse(departureStringSite, formatter);
                if (asm.sitesAvailableInDateRange(campsiteId, arrivalDateSite, departureDateSite))
                {
                    AssignedSite as = new AssignedSite(reservationId, otherCustomerId, campsiteId, 
                                true, deposit, false, arrivalDateSite, departureDateSite);
                    asm.addAssignedSite(as);
                    sitesAssignedSuccessfully++;
                }
            }
                    
        }
        if (sitesAssignedSuccessfully!=numberOfSitesAssigned)
        {
            reservation.setNumberOfSitesAssigned(sitesAssignedSuccessfully);
            rm.updateReservation(reservation);
        }
        
        if (updated == null) {
            try (PrintWriter out = response.getWriter()) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Reservation unsuccessfully Updated');");
                out.println("location='/Campground/ReservationManager';");
                out.println("</script>");
            }
        } else {
            response.sendRedirect("/Campground/ReservationManager");
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
