package servlets;

import common.YearlyInformation;
import database.CustomerManager;
import database.YearlyInformationManager;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Conor Smetana, Scott Harchar (2021)
 */
@WebServlet(name = "ReservationManagerServlet", urlPatterns = {"/ReservationManager"})
public class ReservationManagerServlet extends HttpServlet {

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
        
        //Redirect to login if there is no user logged in
        if (request.getSession().getAttribute("user")==null){
            response.sendRedirect("/Campground/");
            return;
        }
        
        Date start = new Date();
        if(request.getParameter("start_date")!=null && !request.getParameter("start_date").equals(""))
        {
            //If start date was provided set it to that
            try {
                start = new SimpleDateFormat( "MM/dd/yyyy" ).parse(request.getParameter("start_date"));
            } catch (ParseException ex) {
                Logger.getLogger(ReservationManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            //If no date start date provided set it to the current date
            start = new Date(System.currentTimeMillis());
            start.setHours(0);
            start.setMinutes(0);
            start.setSeconds(0);
            YearlyInformationManager ym = database.Database.getDatabaseManagement().getYearlyInformationManager();
            YearlyInformation yearInfo = ym.getYearlyInformationByYear(start.getYear()+1900);
            Date temp = Date.from(yearInfo.getOpeningDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (temp.after(start))
                start=temp;
        }
        
        //Always display have date range include one day prior to start date.
        start.setDate(start.getDate()-1);
        
        Date end = new Date();
        if(request.getParameter("end_date")!=null && !request.getParameter("end_date").equals(""))
        {
            try {
                end = new SimpleDateFormat( "MM/dd/yyyy" ).parse(request.getParameter("end_date"));
            } catch (ParseException ex) {
                Logger.getLogger(ReservationManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            YearlyInformationManager ym = database.Database.getDatabaseManagement().getYearlyInformationManager();
            YearlyInformation yearInfo = ym.getYearlyInformationByYear(start.getYear()+1900);
            end = Date.from(yearInfo.getClosingDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            end.setHours(23);
            end.setMinutes(59);
            end.setSeconds(59);
        }
        request.getSession().setAttribute("startDate", start);
        request.getSession().setAttribute("endDate", end);
        if(request.getParameter("custID")!= null){
            
            ArrayList<String> params = new ArrayList<>();
            String filter = request.getParameter("filter");
            params.add(null);
            params.add(null);
            CustomerManager cm = database.Database.getDatabaseManagement().getCustomerManager(); 
            params.add(cm.getCustomerByID(Integer.parseInt(request.getParameter("custID"))).getName());
            
            request.getSession().setAttribute("filterList", params);
            request.getRequestDispatcher("/jsp/ReservationManager.jsp?viewState=table").forward(request, response);
        }else if(request.getParameter("useFilter") != null && request.getParameter("useFilter").equals("yes")){
            ArrayList<String> params = new ArrayList<>();
            String filter = request.getParameter("filter");
            
            if(request.getParameter("start_date").equals("")){
                params.add(null);
            }else{
                params.add(request.getParameter("start_date"));
            }
            
            if(request.getParameter("end_date").equals("")){
                params.add(null);
            }else{
                params.add(request.getParameter("end_date"));
            }
            
            if(request.getParameter("customer").equals("")){
                params.add(null);
            }else if(request.getParameter("custName") != null){
                params.add(request.getParameter("custName"));
            }else{
                params.add(request.getParameter("customer"));
            }
            request.getSession().setAttribute("filterList", params);
            request.getRequestDispatcher("/jsp/ReservationManager.jsp").forward(request, response);
        } else{
        request.getSession().removeAttribute("filterList");
        //Load ReservationManager.jsp to the page
        request.getServletContext()
                    .getRequestDispatcher("/jsp/ReservationManager.jsp")
                .forward(request, response);
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
