package servlets;

import common.WaitingList;
import database.WaitingListManager;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "EditWaitListServlet", urlPatterns = {"/EditWaitListServlet"})
public class EditWaitListServlet extends HttpServlet {
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
        
        int id = Integer.parseInt(request.getParameter("listId"));
        WaitingListManager wm = database.Database.getDatabaseManagement().getWaitingListManager();
        WaitingList wl = wm.getWaitingListById(id);
        int customerId = wl.getCustomerId();
        
        // Gets Campsite information from AddCampsiteModal
        boolean priority = true;
        if("No".equals(request.getParameter("priorityRadio"))) {
            priority = false;
        }
        
        // get arrival, departure, and request date inputs
        String requestString = request.getParameter("request_date");
        String arrivalString = request.getParameter("arrival_date");
        arrivalString += " 14:00:00";
        String departureString = request.getParameter("departure_date");
        departureString += " 09:00:00";
        
        // turn strings into LocalDateTime with DateTimeFormatter and default times
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate requestDate = LocalDate.parse(requestString, formatter2);
        LocalDateTime arrivalDate = LocalDateTime.parse(arrivalString, formatter);
        LocalDateTime departureDate = LocalDateTime.parse(departureString, formatter);
        
        int nights = Integer.parseInt(request.getParameter("number_of_nights"));
        int sites = Integer.parseInt(request.getParameter("number_of_sites"));
        String notes = request.getParameter("notes");
       
        WaitingList waitingList = new WaitingList (id, customerId, nights, sites,
                notes, requestDate, arrivalDate, departureDate, priority);
        
        WaitingList update = wm.updateWaitingList(waitingList);
        
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


