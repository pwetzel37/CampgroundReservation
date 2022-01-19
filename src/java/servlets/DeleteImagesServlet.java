
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilities.FileUploadUtility;

/**
 *
 * @author dgw82363
 */
@WebServlet(name = "DeleteImagesServlet", urlPatterns = {"/DeleteImagesServlet"})
public class DeleteImagesServlet extends HttpServlet {

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
        List<String> imageArray = new LinkedList<>();
        String path = "C:/data/Campground";//request.getParameter("path");
        String[] imageNames = request.getParameterValues("imageNames");
        imageArray = Arrays.asList(imageNames);
        
        for(Iterator<String> it = imageArray.iterator(); it.hasNext();)
            FileUploadUtility.deleteFromRemote(path, it.next());
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
//String name;
        //for(int i=0, j=0; i<imageNames.length(); i++){
          //  if(imageNames.charAt(i)==','){
             //   name=imageNames.substring(j, i-1);
              //  imageArray.add(name);
               // j++;
           // }
       // }//