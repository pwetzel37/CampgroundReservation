/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import common.Paths;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import utilities.FileUploadUtility;
import utilities.WebErrorLogger;

/**
 *
 * @author Micahel Boyd, Noah Young (2021)
 */
@WebServlet(name = "AddImagesToGallaryServlet", urlPatterns = {"/AddImagesToGallaryServlet"})
@MultipartConfig(maxFileSize = 16177215)
public class AddImagesToGallaryServlet extends HttpServlet {

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

        //get file part from form 
        Part part = request.getPart("validatedCustomFile");

        //get campsite id
        int id = Integer.parseInt(request.getParameter("campsite_id"));
        //dir
        String dir = Paths.IMAGEDIRECTORY;
        String[] fileNames = FileUploadUtility.getAllFileNames(dir);
        List<String> fileList = new ArrayList<>();
        int index = 0;
        for(String name : fileNames) {
            index = name.indexOf(".");
            fileList.add(name.substring(0, index));
        }
        

        //if the part is not empty continue else there has been an error that has 
        //occured
        if (!FileUploadUtility.isEmptyPart(part)) {
            int count = 1;
            // The save as name of the initial subimage should be
            // campsite_id(the id of campsite)_1
            String saveAs = "campsite_" + id + "_subimage" + count;
            
            // If there hasn't already been a sub image uploaded, save as
            // subimage1. If there has already been sub images saved to this
            // campsite id we need to rename the image then upload it.
            while (true) {
                if(fileList.contains(saveAs)) {
                    count++;
                    saveAs = "campsite_" + id + "_subimage" + count;
                } else {
                    break;
                }
            }
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
