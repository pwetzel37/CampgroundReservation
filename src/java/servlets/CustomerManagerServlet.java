package servlets;

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
 * @author Conor Smetana, Scott Harchar (2021)
 */
@WebServlet(name = "CustomerManagerServlet", urlPatterns = {"/CustomerManager"})
public class CustomerManagerServlet extends HttpServlet {

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
        
        if(request.getParameter("useFilter") != null && request.getParameter("useFilter").equals("yes")){
            ArrayList<String> params = new ArrayList<>();
            String filter = request.getParameter("filter");
            switch(filter){
                case "last_name":{
                    if(request.getParameter("search").equals("")){
                        params.add(null);
                    }else{
                        params.add(request.getParameter("search"));
                    }
                    params.add(null);
                    params.add(null);
                    break;
                }
                case "zip_code":{
                    params.add(null);
                    if(request.getParameter("search").equals("")){
                        params.add(null);
                    }else{
                        params.add(request.getParameter("search"));
                    }
                    params.add(null);
                    break;
                }
                case "phone_number":{
                    params.add(null);
                    params.add(null);
                    if(request.getParameter("search").equals("")){
                        params.add(null);
                    }else{
                        params.add(request.getParameter("search"));
                    }
                    break;
                }
            }
            params.add(request.getParameter("individual"));
            params.add(request.getParameter("group"));
            params.add(request.getParameter("club"));

            request.setAttribute("filterList", params);
            request.getRequestDispatcher("/jsp/CustomerManager.jsp").forward(request, response);
        } else
        //Load ReservationManager.jsp to the page
        request.getServletContext()
                    .getRequestDispatcher("/jsp/CustomerManager.jsp")
                .forward(request, response);
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
