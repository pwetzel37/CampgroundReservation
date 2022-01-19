package servlets;

import common.Campsite;
import common.Paths;
import common.SiteType;
import database.CampsiteManager;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;
import utilities.FileUploadUtility;
import utilities.WebErrorLogger;

/**
 *
 * @author Michael Boyd (2021)
 */
@WebServlet(name = "UpdateCampsiteServlet", urlPatterns = {"/UpdateCampsiteServlet"})
@MultipartConfig(maxFileSize = 16177215)
public class UpdateCampsiteServlet extends HttpServlet {

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

        // Gets Campsite information from AddCampsiteModal
        boolean pullThru = true;
        if ("No".equals(request.getParameter("pullThruRadio"))) {
            pullThru = false;
        }

        boolean acceptsSlideOut = false;
        if ("Yes".equals(request.getParameter("accept_slideouts"))) {
            acceptsSlideOut = true;
        }

        SiteType siteType = null;
        switch (request.getParameter("cmpsType")) {
            case "seasonal":
                siteType = SiteType.Seasonal;
                break;
            case "daily":
                siteType = SiteType.Daily;
                break;
            case "weekly":
                siteType = SiteType.Weekly;
                break;
            case "monthly":
                siteType = SiteType.Monthly;
                break;
            default:
                break;
        }

        // Currently still working on getting the uploaded image from the model. 
        Part imagePart = request.getPart("validatedCustomFile");
        InputStream stream = imagePart.getInputStream();
        Blob image = null;

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        for (int length = 0; (length = stream.read(buffer)) > 0;) {
            output.write(buffer, 0, length);
        }

        try {
            image = new SerialBlob(output.toByteArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String name = request.getParameter("campsite_name");
        char section = request.getParameter("campsite_section").charAt(0);
        int number = Integer.parseInt(request.getParameter("campsite_number"));
        int max_length = Integer.parseInt(request.getParameter("max_length"));
        String notes = request.getParameter("notes");

        // Default Campsite Information (Not received from modal)
        int width = Integer.parseInt(request.getParameter("width"));
        int id = Integer.parseInt(request.getParameter("campsite_id"));
        Campsite campsite = new Campsite(id, name, number, section, max_length,
                width, acceptsSlideOut, pullThru, siteType, notes, image, "file/path");

        CampsiteManager cm = database.Database.getDatabaseManagement().getCampsiteManager();

        Campsite added = cm.updateCampsite(campsite);

        //get file part from form 
        Part part = request.getPart("validatedCustomFile");
        //directory
        String dir = Paths.IMAGEDIRECTORY;

        //update the image inside of the directory 
        if (!FileUploadUtility.isEmptyPart(part)) {
            
            File d = new File(dir);
            //if the directory doesnt exist create it 
            if (!d.exists()) {
                boolean makeDir = FileUploadUtility.makeRemoteDirectory(dir);
            }
            
            String saveAs = "campsite_" + id + "_mainImg";
            FileUploadUtility.uploadToRemote(dir, saveAs, part);
            
        } else {
            WebErrorLogger.log(Level.SEVERE, "File Part is empty");
        }
        response.sendRedirect("/Campground/CampsiteManager");

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
