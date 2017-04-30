package assignment2.servlet;

import assignment2.business.Answer;
import assignment2.business.Reported;
import assignment2.business.Study;
import assignment2.business.User;
import assignment2.data.AnswerDB;
import assignment2.data.MailUtil;
import assignment2.data.ReportedDB;
import assignment2.data.StudyDB;
import assignment2.data.UserDB;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.Part;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.mail.*;


/**
 *
 * @author nbhirud
 */

@MultipartConfig(fileSizeThreshold=0, // 0MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50)   // 50MB
public class StudyController extends HttpServlet {
    private static final String SAVE_DIR = "images";
    
    ArrayList<Study> all_studies;
    ArrayList<String> reportedQuestions = new ArrayList<>();
    ArrayList<Answer> answer_list = new ArrayList<>();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StudyController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudyController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url ="";
        response.setContentType("text/html");
        String applicationPath = request.getSession().getServletContext().getRealPath("/images");
        System.out.println(applicationPath);
        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();   
        HttpSession session = request.getSession();
        User theUser = (User) session.getAttribute("theUser");
        // Participate Action Code
        if (action.equalsIgnoreCase("participate")){
            out.println("Inside participate action!");
            if (theUser == null){
                url = "/login.jsp";
            }
        else{
            String studyCode = request.getParameter("studyCode");
                if (studyCode == null){
                    all_studies = StudyDB.getStudies("Start", theUser.getEmail());
                    request.setAttribute("allStudies", all_studies);
                    url = "/participate.jsp";
                }
                else{
                    Study study = new Study();
                    study = StudyDB.getStudy(studyCode);
                    request.setAttribute("study", study);
                    url = "/question.jsp";
                }
            }
        }

//Edit Action Code        
        else if (action.equalsIgnoreCase("edit")){
            if (theUser == null){
                url = "/login.jsp";
            }
            else if(theUser.getType().equals("Participant")){
                String studyCode = request.getParameter("StudyCode");
                Study study = StudyDB.getStudy(studyCode);
                request.setAttribute("study", study);
                url = "/editstudy.jsp";
            }
        }
        
    //Page for sharing on facebook
        else if (action.equalsIgnoreCase("share")){
            if (theUser == null){
                url = "/login.jsp";
                System.out.println("Empty theuser");
            }
            else if(theUser.getType().equals("Participant")){
                System.out.println("full theuser");
                String studyCode = request.getParameter("scode");
                System.out.println(studyCode);
                Study study = StudyDB.getStudy(studyCode);
                request.setAttribute("studyshare", study);
                url = "/shareStudy.jsp";
            }
        }
 //Report action Code
        else if (action.equalsIgnoreCase("report")){
            if (theUser == null){
                url = "/login.jsp";
            }
            else if(theUser.getType().equals("Participant")){
                String studyCode = request.getParameter("studyCode");
                String questionID = request.getParameter("QuestionID");
                String UserName = request.getParameter("ReporterEmail");
                String Status = "Pending";
                if (studyCode == null){
                    ArrayList<Reported> userReportedQuestions = new ArrayList<>();
                    userReportedQuestions  = ReportedDB.getUserReportedQuestions(theUser.getEmail());
                    request.setAttribute("reportedQuestions", userReportedQuestions);
                    // Code to request the report history of the user.
                    url = "/reporth.jsp";
                }
                
                else{
                    
                    if (ReportedDB.isUserReportExists(UserName, Integer.parseInt(studyCode), Integer.parseInt(questionID))){
                    String msg = "You have already reported this question";
                    request.setAttribute("msg", msg );
                    all_studies = StudyDB.getStudies("Start", theUser.getEmail());
                    request.setAttribute("allStudies", all_studies);
                    url = "/participate.jsp";
                    }
                    else{
                        Reported r = new Reported();
                        r.setNumParticipants(0);
                        r.setQuestionID(Integer.parseInt(questionID));
                        r.setStudyID(Integer.parseInt(studyCode));
                        r.setUserName(UserName);
                        r.setStatus(Status);
                        ReportedDB.addReport(r);
                    url = "/confirmrep.jsp";
                    }
                    
                    }    // Code to add report record to the database.
                    
                }
        }
//Approve Action Code : Admin

        else if(action.equalsIgnoreCase("approve")){
            if (theUser != null && theUser.getType().equalsIgnoreCase("Administrator")){
                String StudyCode = request.getParameter("StudyCode");
                String QuestionID = request.getParameter("QuestionID");
                System.out.println("In Approve tab");
                ReportedDB.updateReportStatus( Integer.parseInt(QuestionID),Integer.parseInt(StudyCode), "Approved");
                ArrayList<Reported> list = ReportedDB.getAllPendingReports();
                request.setAttribute("reportedQuestions", list);
                url = "/reportques.jsp";
            }
            else{
                url = "/login.jsp";
            }
        }
//Disapprove action code : Admin

        else if(action.equalsIgnoreCase("disapprove")){
            if (theUser != null && theUser.getType().equalsIgnoreCase("Administrator")){
                String StudyCode = request.getParameter("StudyCode");
                String QuestionID = request.getParameter("QuestionID");
                ReportedDB.updateReportStatus(Integer.parseInt(QuestionID),Integer.parseInt(StudyCode), "Disapproved");
                ArrayList<Reported> list = ReportedDB.getAllPendingReports();
                request.setAttribute("reportedQuestions", list);
                url = "/reportques.jsp";
            }
            else{
                url = "/login.jsp";
            }
        }
//Update study action code        
        
        else if (action.equalsIgnoreCase("update")){
            if (theUser == null){
                url = "/login.jsp";
            }
            else if(theUser.getType().equals("Participant")){
                String StudyCode = request.getParameter("StudyCode");
                String studyName = request.getParameter("study_name");
                String question = request.getParameter("question_text");
                String participants = request.getParameter("participants");
                String [] answers = request.getParameterValues("DynamicTextBox");
                //String imageURL = request.getParameter("imageURL");
                //System.out.println(imageURL);
                
                String fileName = "";
                    OutputStream os = null;
                    InputStream is = null;
                   for (Part part : request.getParts()) {
                       if(part.getName().equals("imageURL")){
                        fileName = getFileName(part);
                        is = part.getInputStream();
                        os = new FileOutputStream(new File(applicationPath+File.separator+fileName));
                       //os = new FileOutputStream(System.getenv("OPENSHIFT_DATA_DIR") + fileName);
                        int read = 0;
                        final byte[] bytes = new byte[1024];
                        while ((read=is.read(bytes)) != -1 ){
                            os.write(bytes, 0, read);
                        }
                       }
                    }
                   
                ArrayList<String> ans = new ArrayList<>();
                ans.addAll(Arrays.asList(answers));
                String description = request.getParameter("description");
                Study s = StudyDB.getStudy(StudyCode);
                s.setStudyName(studyName);
                s.setQuestion(question);
                s.setNumOfParticipants(Integer.parseInt(participants));
                s.setDescription(description);
                s.setAnswerList(ans);
                s.setImageURL(fileName);
                StudyDB.updateStudy(s);
                all_studies = StudyDB.getUserStudies(theUser.getEmail());
                request.setAttribute("userStudies", all_studies);
                url = "/studies.jsp";
            }
        }
// Add action code        
        else if(action.equalsIgnoreCase("add")){
            if (theUser == null){
                url = "/login.jsp";
            }
            else if(theUser.getType().equals("Participant")){
                Study study = new Study();
                String studyname = request.getParameter("study_name");
                String questiontext = request.getParameter("question_text");
                String numpart = request.getParameter("participant_text");
                String description = request.getParameter("description");
                String ans[] = request.getParameterValues("DynamicTextBox");
                ArrayList<String> list = new ArrayList<String>(); 
                list.addAll(Arrays.asList(ans));
                study.setAnswerList(list);
                study.setStudyName(studyname);
                study.setDescription(description);
                study.setQuestion(questiontext);
                study.setRequestedParticipants(Integer.parseInt(numpart));
                study.setEmail(theUser.getEmail());
                study.setStatus("Stop");
                OutputStream os = null;
                InputStream is = null;
                   for (Part part : request.getParts()) {
                       if(part.getName().equals("imageURL")){
                        String imageURL = getFileName(part);
                        is = part.getInputStream();
                        //os = new FileOutputStream(new File(applicationPath+File.separator+imageURL));
                        os = new FileOutputStream(System.getenv("OPENSHIFT_DATA_DIR") + imageURL);
                        int read = 0;
                        final byte[] bytes = new byte[1024];
                        while ((read=is.read(bytes)) != -1 ){
                            os.write(bytes, 0, read);
                        }
                        study.setImageURL(imageURL);
                       }
                    }
                StudyDB.addStudy(study);
                UserDB.updateUserStudiesCount(theUser.getEmail(), theUser.getStudies()+1);
                all_studies = StudyDB.getUserStudies(theUser.getEmail());
                request.setAttribute("userStudies", all_studies);
                url = "/studies.jsp";
            }
        }
        
//Start Action code
        else if (action.equalsIgnoreCase("Start")){
            if (theUser == null){
                url = "/login.jsp";
            }
            
            else if(theUser.getType().equals("Participant")){
                String StudyCode = request.getParameter("StudyCode");
                Study study = StudyDB.getStudy(StudyCode);
                System.out.println(study.getStatus());
                study.setStatus("Start");
                StudyDB.updateStudy(study);
                all_studies = StudyDB.getUserStudies(theUser.getEmail());
                request.setAttribute("userStudies", all_studies);
                url = "/studies.jsp";
            }
            
        }
//Stop Action Code
        else if (action.equalsIgnoreCase("stop")){
            if (theUser == null){
                url = "/login.jsp";
            }
            else if(theUser.getType().equals("Participant")){
                String StudyCode = request.getParameter("StudyCode");
                Study study = StudyDB.getStudy(StudyCode);
                study.setStatus("Stop");
                StudyDB.updateStudy(study);
                all_studies = StudyDB.getUserStudies(theUser.getEmail());
                request.setAttribute("userStudies", all_studies);
                url = "/studies.jsp";
            }
        }
//Answers Action Code
        else if (action.equalsIgnoreCase("answer")){
            if (theUser == null){
                url = "/login.jsp";
            }
            else if(theUser.getType().equals("Participant")){
                
                String StudyCode = request.getParameter("StudyCode");
                String QuestionID = request.getParameter("QuestionID");
                if (AnswerDB.isUserAnswerExists(theUser.getEmail(), Integer.parseInt(StudyCode), Integer.parseInt(QuestionID))){
                    String msg = "Already answered the Question. You cannot participate";
                    all_studies = StudyDB.getStudies("Start", theUser.getEmail());
                    request.setAttribute("allStudies", all_studies);
                    request.setAttribute("msg", msg);
                    url = "/participate.jsp";
                }
                else{
                    String choice = request.getParameter("choice");
                Answer ans = new Answer();
                ans.setChoice(choice);
                ans.setEmail(theUser.getEmail());
                ans.setStudyCode(Integer.parseInt(StudyCode));
                ans.setQuestionID(Integer.parseInt(QuestionID));
                // Code to add the answer to DB
                if (AnswerDB.addAnswer(ans)){
                all_studies = StudyDB.getStudies("Start", theUser.getEmail());
                request.setAttribute("allStudies", all_studies);
                theUser.setParticipation(theUser.getParticipation() + 1);
                theUser.setNumCoins(theUser.getNumCoins() + 1);
                UserDB.updateUserParticipationCoins(theUser);
                Study s = StudyDB.getStudy(StudyCode);
                String owner_email = s.getEmail();
                User u = UserDB.getUser(owner_email);
                u.setNumCoins(u.getNumCoins()+ 1);
                UserDB.updateUserParticipationCoins(u);
                }
                all_studies = StudyDB.getStudies("Start", theUser.getEmail());
                    request.setAttribute("allStudies", all_studies);
                    //request.setAttribute("msg", " ");
                    url = "/participate.jsp";
                    
                }
                
                
            }
        }

//Studies Action Code
        else if(action.equalsIgnoreCase("studies")){
            if (theUser == null){
                url = "/login.jsp";
            }
            else if(theUser.getType().equals("Participant")){
                all_studies = StudyDB.getUserStudies(theUser.getEmail());
                
                if (all_studies.size() > 0){
                    request.setAttribute("userStudies",all_studies);
                }
                else{
                    request.setAttribute("userStudies", null);
                }
                url = "/studies.jsp";
            }
        }
        
        
        else if(action.equalsIgnoreCase("adminreport")){
            if(theUser!=null && theUser.getType().equals("Administrator")){
                ArrayList <Reported> reportedQuestions = ReportedDB.getAllPendingReports();
                request.setAttribute("reportedQuestions", reportedQuestions);
                url = "/reportques.jsp";
            }
            else{
                url = "/login.jsp";
            }
        }
        else if (action.equalsIgnoreCase("recommend")){
            if(theUser!=null && theUser.getType().equals("Participant")){
                String email = request.getParameter("email");
                String friend_email = request.getParameter("friend_email");
                String subject = "Recommend";
                String body = request.getParameter("message");
                
              try{
                     MailUtil.sendMail(friend_email,email, subject, body, false);
                     url = "/confirmr.jsp";
                } catch (MessagingException ex) {
                    Logger.getLogger(StudyController.class.getName()).log(Level.SEVERE, null, ex);
                    url = "/recommend.jsp";
                }
            }
            else
            {
                    url = "/recommend.jsp";
            }
                
            }
        
        
        else if (action.equalsIgnoreCase("contact")){
            if(theUser!=null && theUser.getType().equals("Participant")){
                String name = request.getParameter("study_name");
                String email = request.getParameter("email");
                String subject = "Contact " + name ;
                String body = request.getParameter("message");
                try{
                     MailUtil.sendMail("sravan1707@gmail.com", email, subject, body, false);
                     url = "/confirmc.jsp";
                } catch (MessagingException ex) {
                    Logger.getLogger(StudyController.class.getName()).log(Level.SEVERE, null, ex);
                    url = "/contact.jsp";
                }
                
            }
            else
            {
                    url = "/contact.jsp";
            }
            
        }
        
//Default action   
        else{
            if (theUser == null){
                url = "/home.jsp";
            }
            else if(theUser.getType().equals("Admin")){
                url = "/admin.jsp";
            }
            else if (theUser.getType().equals("Participant")){
                url = "/main.jsp";
            }
        }
        
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String name = part.getName();
        System.out.println("partName = "+name);
        System.out.println("content-disposition header= "+contentDisp);
        String filename ="";
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            System.out.println("inside for ");
            if (token.trim().startsWith("filename")) {
                System.out.println("inside if");
                filename = token.substring(token.indexOf("=") + 2, token.length()-1);
                  System.out.println("inside if file name = "+filename);
                return filename;
            }
            
        }
        return filename;
    }
}
