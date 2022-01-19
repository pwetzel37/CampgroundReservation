package servlets;

import common.User;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilities.Debug;
import utilities.EmailUtility;
import utilities.PropertyManager;

/**
 * 
 * For normal users, <code>LoginServlet</code> processes login requests before
 * it executes the <code>ControlServlet</code> page. If the request is denied,
 * the servlet returns back to the <code>loginScreen.jsp</code> to try again.
 *
 *
 * @author Joseph Picataggio
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        //Any initilization code goes here
    }

    @Override
    public void destroy() {
        // Reclaim any resources
        /*
         * Called by the servlet container to indicate to a servlet that the servlet 
         * is being taken out of service. 
         * This method is only called once all threads within the servlet's service 
         * method have exited or after a timeout period has passed. After the servlet 
         * container calls this method, it will not call the service method again on this servlet.
         *
         * This method gives the servlet an opportunity to clean up any resources 
         * that are being held (for example, memory, file handles, threads) and make 
         * sure that any persistent state is synchronized with the servlet's current 
         * state in memory. 
         *
         * Global resources are normally returned by a Context listener.
         *  In our case it is servlet.ContextListener.java
         */

    }

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

        HttpSession session = request.getSession(true); // Create a new session if one does not exists

        // Always resets the session attributes with a new login to avoid problems.
        // Always lock a session variable to be thread safe.
        final Object lock = session.getId().intern();
        synchronized(lock){
           session.setAttribute("user", null);
        }

        // Get the max number of Login Attempts
        int loginAttempts;
        try {
              loginAttempts = Integer.parseInt(PropertyManager.getProperty("LoginFailures"));
        } catch (NumberFormatException ex) {
                loginAttempts = 7; //assume 7 if the attempt to use the PropertyManager fails 
          }
        
        // Get the login reset time
        int loginResetMinutesTime;
        try {
              loginResetMinutesTime = Integer.parseInt(PropertyManager.getProperty("LoginResetMinutesTime"));
        } catch (NumberFormatException ex) {
                loginResetMinutesTime = 60; //assume 60 if the attempt to use the PropertyManager fails 
          }

       
        // Get IP Address
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();//IP address is used in an email to the user
 
        }
        //log("ipAddress is "+ ipAddress);

           database.UserManager um = database.Database.getDatabaseManagement().getUserManager();
        
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Debug.println(username + " " + password);

            String errorURL = "/jsp/loginScreen.jsp"; // url to redirect to if there is an error
            
            if (username == null || password == null) {
                request.setAttribute("errorMessage", "Please enter a username and password");

                getServletContext()
                        .getRequestDispatcher(errorURL)
                        .forward(request, response);
                return; //This return statement is needed because of the forwrd statement above.
            }

            // See if we have this user by asking for the salt associated with the user
            String salt = um.getSaltByLoginName(username);
            if (salt == null) { //user was not in database
                request.setAttribute("errorMessage", "Invalid username or password.");
                getServletContext()
                        .getRequestDispatcher(errorURL)
                        .forward(request, response);
                return;
            }

            // Sets a potential user from the requested username and runs
            // through a series of checks to validate it.

            User potentialUser = um.getUserByLoginName(username);
            if (potentialUser != null && !potentialUser.isLocked()) {
                if (potentialUser.getAttemptedLoginCount() < loginAttempts) {
                    User user = um.validateUser(username, security.Encryption.hashString(password + salt));
                    if (user != null) { //Password was valid for this user
                        //Thread Safe
                        synchronized(lock){
                          session.setAttribute("user", user);
                        }
                        //Action is still login

                        String url = "/ControlServlet";
                        request.getRequestDispatcher(url).forward(request, response);
                    }
                    // if the user is null then the password was not correct        
                    else {

                        if (potentialUser.getLastAttemptedLoginTime() == null) {
                            potentialUser.setLastAttemptedLoginTime(LocalDateTime.now());
                        }
                        // If the current time is less than the reset time period
                        // after the first time they tried to log in for this session, then
                        // increment their login count bringing them closer to the failure number from property file
                        if (LocalDateTime.now().isBefore(potentialUser.getLastAttemptedLoginTime().plusMinutes(loginResetMinutesTime))){
                            if (potentialUser.getAttemptedLoginCount() == 0) {
                                potentialUser.setLastAttemptedLoginTime(LocalDateTime.now());
                            }
                            potentialUser.setAttemptedLoginCount(potentialUser.getAttemptedLoginCount() + 1);
                            um.updateUser(potentialUser);
                        } else {
                            potentialUser.setLastAttemptedLoginTime(LocalDateTime.now());
                            potentialUser.setAttemptedLoginCount(1);
                            um.updateUser(potentialUser);
                        }

                        request.setAttribute("errorMessage", "Invalid password.");
                        request.setAttribute("username", username);

                        // Returns the user back to the login screen.
                        request.getServletContext()
                                .getRequestDispatcher(errorURL)
                                .forward(request, response);
                    }

                    // If your attempted Login count reaches its limit
                } else {
                    request.setAttribute("errorMessage", "You have been Locked out. Please contact your system administrator for access.");
                    potentialUser.setLocked(true);
                    potentialUser.setAttemptedLoginCount(0);
                    um.updateUser(potentialUser);
                    emailUser(potentialUser, ipAddress);

                    // Returns the user back to the login screen.
                    request.getServletContext()
                            .getRequestDispatcher(errorURL)
                            .forward(request, response);
                }

                // Returns the user back to the login screen.
            } else if (potentialUser != null) {
                request.setAttribute("errorMessage", "You have been Locked out. Please contact your system administrator for access.");
                request.getServletContext()
                        .getRequestDispatcher(errorURL)
                        .forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Username not found.");
                request.getServletContext()
                        .getRequestDispatcher(errorURL)
                        .forward(request, response);
            }
    }

    /**
     * Sends an email to the current user when they have exhausted their login
     * attempts for one session.
     *
     * @param user
     * @param ip
     */
    private void emailUser(common.User user, String ip) {
        String message = "OutsideLoginsOnlyTemplate.\r\n\r\n"
                + "To many login attempts for '" + user.getLoginName() + "' has locked out your account.\r\n"
                + "Please contact your system adminstrator to unlock your account.\r\n\r\n"
                + "Please do not reply to this email. This is an automatic generated email and you will not get a response.";
        EmailUtility.email(user.getEmailAddress(), message, "Account Locked");

        message = "OutsideLoginsOnlyTemplate.\r\n\r\n"
                + "To many login attempts for '" + user.getLoginName() + "' has locked out this account.\r\n"
                + "IP Address of Last Login Attempt is: " + ip + "\r\n\r\n"
                + "Please do not reply to this email. This is an automatic generated email and you will not get a response.";
        database.UserManager um = database.Database.getDatabaseManagement().getUserManager();
        Collection<String> emails = new ArrayList<>();
        Collection<User> adminList = um.getSystemAdmins();
        if (adminList == null) {
            emails.add(PropertyManager.getProperty("ADMIN_EMAIL"));
            EmailUtility.email(emails, message, user.getLoginName() + " Account Locked");
            return;
        }
        for (User admin : adminList) {
            emails.add(admin.getEmailAddress());
        }
        EmailUtility.email(emails, message, user.getLoginName() + " Account Locked");
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
        return "Process Login Requests";
    }// </editor-fold>

}
