package assignment2.servlet;

import assignment2.business.TempUser;
import assignment2.business.User;
import assignment2.data.MailUtil;
import assignment2.data.TempUserDB;
import assignment2.data.UserDB;
import assignment2.util.HashUtil;
import assignment2.util.JavaVariables;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nbhirud
 */
public class UserController extends HttpServlet {
    String action;
    int flag=0;

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url="/main.jsp";
        action=request.getParameter("action");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        User theUser=(User)session.getAttribute("theUser");
        if (theUser != null){
            if (action.equals("about")){
                url = "/aboutl.jsp";
            }
            else if (action.equals("how")){
                url = "/main.jsp";
            }
            else if (action.equals("home")){
                flag++;
                url = "/main.jsp";
            }
            else if(action.equals("main"))
            {
                url="/main.jsp";
            }
            else if(action.equals("login"))
            {
                url="/login.jsp";
            }
            else if (action.equals("logout")){
                session.invalidate();
                url="/home.jsp";
            }
        }
        else {
            if(action.equals("about"))
            {
                 url="/about.jsp";
            }
            if(action.equals("how"))
            {
                url="/how.jsp";
            }
            if(action.equals("home"))
            {
                 url="/home.jsp";
            }
            if(action.equals("main"))
            {
                url="/login.jsp";
            }
             if(action.equals("login"))
            {
                
                url="/login.jsp";
            }
        }
        if(action.equals("activate")){
            String token = request.getParameter("token");
            TempUser tu = null;
            tu = TempUserDB.getUser(token);
            if(tu != null){
                System.out.println(tu.toString());
                User u = new User();
                u.setName(tu.getUsername());
                u.setEmail(tu.getEmail());
                u.setType("Participant");
                u.setNumCoins(0);
                u.setParticipants(0);
                u.setParticipation(0);
                u.setStudies(0);
                String password = tu.getPassword();
                UserDB.addUser(u, password);
                TempUserDB.DeleteTempUser(tu.getEmail(), token);
                session = request.getSession();
                session.setAttribute("theUser", u);
                url = "/main.jsp";
            }
            else{
                String message;
                message = "user activation failed. please signup again";
                request.setAttribute("message", message);
                url = "/signup.jsp";
            }
            
        }
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url="/main.jsp";
        Object message;
        action = request.getServletPath();
        PrintWriter writer = response.getWriter();
        String action_name = request.getParameter("action");
        HttpSession session = request.getSession();
        
        if(action_name.equals("signup"))
        {
            //writer.println("inside  :");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String cpass = request.getParameter("confirm_password");
            if(password.equals(cpass))
            {
                if(UserDB.getUser(email)==null)
                {
                    String hash_password  = "";
                try {
                     hash_password = HashUtil.hashAndSaltPassword(password+JavaVariables.salt);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    String uuid = UUID.randomUUID().toString();
                    TempUser tu = new TempUser();
                    tu.setUsername(name);
                    tu.setEmail(email);
                    tu.setPassword(hash_password);
                    tu.setUuid(uuid);
                    if (TempUserDB.addTempUser(tu)){
                            String activation_url;                        try {

                            activation_url = "http://openshiftdemo-nbhirud.rhcloud.com/NikhilBhirud_Assg4/UserController?action=activate&token="+uuid;
                            String mail_body = "Click the following link to activate\n " + activation_url;
                            MailUtil.sendMail(email, "nbadnikhilbhirud@gmail.com", "Resea                            url = \"/login.jsp\";\n" +
"rch Portal activation", mail_body, true);
                            String msg = "User Created Successfully. Lookout for an activation mail from us. You can login once the account is activated.";
                            request.setAttribute("msg", msg);
                        } catch (MessagingException ex) {
                            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                else
                {
                    message = "Email address already exists!!";
                    request.setAttribute("message", message);
                    url="/signup.jsp";
                }
            }
            else
            {
                writer.println("Error");
                message = "Confirm Password doesnot match";
                request.setAttribute("message", message);
                url="/signup.jsp";
            }      
        }
        

        else if(action_name.equals("login"))
        {
            User userLogin; 
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            userLogin = UserDB.getUser(email);
            if(userLogin == null)
            {
                message = "No found email address : "+email;
                request.setAttribute("message", message);
                url="/login.jsp";           
            }
            else
            {
                String hash_password  = "";
                try {
                     hash_password = HashUtil.hashAndSaltPassword(password+JavaVariables.salt);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
               userLogin = UserDB.validateUser(email,hash_password);
               if(userLogin != null)
               {
                   if (session.getAttribute("theUser")!= null) { 
                        session.invalidate();
                    }
                    session = request.getSession();
                    session.setAttribute("theUser", userLogin);
                    url="/main.jsp";
               }
               else
               {
                    message = "Password is incorrect!!";
                    url="/login.jsp";
                    request.setAttribute("message", message);
               }   
            }            
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
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
