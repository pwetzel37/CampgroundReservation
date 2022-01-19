/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import database.YearlyInformationManager;
import common.YearlyInformation;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Michael Boyd (2021)
 */

@WebServlet(name = "AddYearlyInformationServlet", urlPatterns = {"/AddYearlyInformationServlet"})
public class AddYearlyInformationServlet extends HttpServlet {

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

        YearlyInformationManager ym = database.Database.getDatabaseManagement().getYearlyInformationManager();
        int year = Integer.parseInt(request.getParameter("information_year"));
        String openingString = request.getParameter("opening_date");
        String closingString = request.getParameter("closing_date");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        LocalDate openingDate = LocalDate.parse(openingString, formatter);
        LocalDate closingDate = LocalDate.parse(closingString, formatter);

        BigDecimal seasonalVisitorPassRate = new BigDecimal(request.getParameter("seasonal_visitor_pass_rt"));

        BigDecimal nightlyVisitorFee = new BigDecimal(request.getParameter("nightly_visitor_pass_rt"));
        BigDecimal dailyVisitorFee = new BigDecimal(request.getParameter("daily_visitor_rt"));
        BigDecimal earlyCheckInFeePerHour = new BigDecimal(request.getParameter("early_checkin_fee"));
        BigDecimal lateStayFeePerHour = new BigDecimal(request.getParameter("late_stay_fee"));
        BigDecimal nightlyCancellationFee = new BigDecimal(request.getParameter("night_cancel"));
        BigDecimal weeklyCancellationFee = new BigDecimal(request.getParameter("weekly_cancel"));
        BigDecimal monthlyCancellationFee = new BigDecimal(request.getParameter("monthly_cancel"));
        BigDecimal electricalRatePerKWH = new BigDecimal(request.getParameter("electric_fee"));
        BigDecimal dailyRate = new BigDecimal(request.getParameter("daily_rates"));
        BigDecimal weeklyRate = new BigDecimal(request.getParameter("weekly_rates"));
        BigDecimal monthlyRate = new BigDecimal(request.getParameter("monthly_rates"));
        BigDecimal seasonalRate = new BigDecimal(request.getParameter("seasonal_rates"));
        
        YearlyInformation yearlyInformation = new YearlyInformation(year,dailyRate,
        weeklyRate, monthlyRate, seasonalRate, openingDate,closingDate,seasonalVisitorPassRate,
        nightlyVisitorFee,dailyVisitorFee,earlyCheckInFeePerHour,lateStayFeePerHour,
        nightlyCancellationFee,weeklyCancellationFee,monthlyCancellationFee,electricalRatePerKWH);
        
        YearlyInformation added = ym.addYearlyInformation(yearlyInformation);
        response.sendRedirect("/Campground/OwnerManagement");
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
