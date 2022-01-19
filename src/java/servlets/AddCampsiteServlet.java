package servlets;

import common.Campsite;
import common.Paths;
import common.SiteType;
import database.CampsiteManager;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.servlet.ServletContext;
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

@WebServlet(name = "AddCampsiteServlet", urlPatterns = {"/AddCampsiteServlet"})
@MultipartConfig(maxFileSize = 16177215)
public class AddCampsiteServlet extends HttpServlet {

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
        switch (request.getParameter("siteTypeRadio")) {
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

        /**
         * String directory = "c:/data/campgroud"; String saveAs =
         * FileUploadUtility.getFileName(imagePart);
         * FileUploadUtility.makeRemoteDirectory(directory);
         * FileUploadUtility.uploadToRemote(directory, saveAs, imagePart);
         *
         */
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

        Campsite campsite = new Campsite(name, number, section, max_length,
                width, acceptsSlideOut, pullThru, siteType, notes, image, "");

        CampsiteManager cm = database.Database.getDatabaseManagement().getCampsiteManager();
        Campsite added = cm.addCampsite(campsite);

        added = cm.getCampsiteWithNameNumberSection(campsite);

        int id = added.getCampsiteId();

        /**
        try (PrintWriter out = response.getWriter()) {

            Part part = request.getPart("validatedCustomFile");

            String cmpsName = request.getParameter("campsite_name");
            String dir = "C:/data/campground/Campsite" + id + "/mainImage";

            File d = new File(dir);

            if (!d.exists()) {
                boolean made = d.mkdirs();

                if (!made) {
                    out.println("there was an error making this directory");
                }
            }

            String fileName = part.getSubmittedFileName();
            String path = dir
                    + File.separator + fileName;

            InputStream is = part.getInputStream();

            boolean succs = uploadFile(is, path);

            if (!succs) {
                out.println("error");
            }
        }
        **/
         
         Part part = request.getPart("validatedCustomFile");
         String dir = Paths.IMAGEDIRECTORY;
         
         if(!FileUploadUtility.isEmptyPart(part)){
         File d = new File(dir);
         
          if (!d.exists()) {
              boolean makeDir = FileUploadUtility.makeRemoteDirectory(dir);
          }
  
          String saveAs = "campsite_"+id+"_mainImg";
          
          FileUploadUtility.uploadToRemote(dir, saveAs, part);
          
         }
         else{
            WebErrorLogger.log(Level.SEVERE, "File Part is empty"); 
         }
          //campsite.setImageSource(dir + saveAs + "." + 
             //     FileUploadUtility.getFileNameExtension(part));
         
        

        response.sendRedirect("/Campground/CampsiteManager");

    }

    public boolean uploadFile(InputStream is, String path) {
        boolean test = false;

        try {
            byte[] byt = new byte[is.available()];
            is.read();
            FileOutputStream fops = new FileOutputStream(path);
            fops.write(byt);
            fops.flush();
            fops.close();

            test = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return test;
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
