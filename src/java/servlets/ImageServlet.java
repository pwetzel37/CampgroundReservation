package servlets;

import common.Paths;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Serves images to the client that are located on the external file server.
 * Right now the path is hardcoded. How you use it is using a link like:
 * ImageServlet?file=filename
 *
 * @author Andrew Bower (2021)
 */
@WebServlet(name = "ImageServlet", urlPatterns = {"/ImageServlet"})
public class ImageServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String filename = request.getParameter("file");

        if (filename != null) {
            ServletContext context = request.getServletContext();

            String filepath = Paths.IMAGEDIRECTORY + "/" + filename;

            String mime = context.getMimeType(filepath);
            if (mime != null) {
                response.setContentType(mime);
                File temp = new File(filepath); // Loading the file

                response.setContentLength((int) temp.length());
                response.setHeader("ETAG", temp.getName() + temp.getTotalSpace() + temp.lastModified()); // Used for caching
                response.setDateHeader("Last-Modified", temp.lastModified()); // Used for caching

                try (FileInputStream in = new FileInputStream(temp); OutputStream out = response.getOutputStream()) {

                    byte[] buffer = new byte[1024];
                    int count;

                    while ((count = in.read(buffer)) >= 0) {
                        out.write(buffer, 0, count); // Outputting the file
                    }

                    out.close();
                    in.close();
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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