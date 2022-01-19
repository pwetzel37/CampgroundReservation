/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import common.Campsite;
import database.CampsiteManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cms70143
 */
@WebServlet(name = "getAvailableSitesServlet", urlPatterns = {"/getAvailableSites"})
public class getAvailableSitesServlet extends HttpServlet {

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
        
        String start = request.getParameter("start") + " 00:00";
        String end = request.getParameter("end") + " 23:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        CampsiteManager cm = database.Database.getDatabaseManagement().getCampsiteManager();
        ArrayList<Campsite> campsites = new ArrayList<>(cm.getUnassignedSitesByDateRange(LocalDateTime.parse(start, formatter), LocalDateTime.parse(end, formatter)));
        if(!(campsites==null ||campsites.size()==0))
        {
            PrintWriter pw = response.getWriter();
            for (Campsite campsite : campsites) {
                pw.println("<option value=" + campsite.getCampsiteId() + ">"+ campsite.getCampsiteName() +"</option>");
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
