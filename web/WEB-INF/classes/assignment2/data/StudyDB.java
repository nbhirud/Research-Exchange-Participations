package assignment2.data;

import assignment2.business.Study;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nbhirud
 */
public class StudyDB{
    
    public static Study getStudy(String studyCode){
        ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
        try {
            
            Study study = null;
            String query = "SELECT * FROM study, question WHERE study.StudyID = " + studyCode + " and study.StudyID = question.StudyID";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()){
                study = createStudyfromResult(rs);
                return study;
            }
                return null;
        } catch (SQLException ex) {
            Logger.getLogger(StudyDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<Study> getStudies(String status_code, String email){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Study> all_studies = new ArrayList<>();
        try {
            Study study = null;
            String query = "SELECT * FROM study, question WHERE SStatus = ? and study.StudyID = question.StudyID and "
                    + "study.Username <> ? and study.StudyID NOT IN (SELECT StudyID from reported where Status = 'Approved')";
            String query2 = "SELECT * FROM study, question WHERE study.StudyID = question.StudyID";
            if (status_code.equalsIgnoreCase("Start") || status_code.equalsIgnoreCase("Stop")){
                ps = connection.prepareStatement(query);
                ps.setString(1, status_code); 
                ps.setString(2,email);
                System.out.println(ps.toString());
            }
            else{
                ps = connection.prepareStatement(query2);
            }
            rs = ps.executeQuery();
            while (rs.next()){
                study = createStudyfromResult(rs);
                all_studies.add(study);
            }
            return all_studies;
        } 
        catch (SQLException ex) {
            Logger.getLogger(StudyDB.class.getName()).log(Level.SEVERE, null, ex);
            return all_studies;
        }
        finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<Study> getUserStudies(String email){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Study> user_studies = new ArrayList<>();
        try {
            Study study = null;
            String query = "SELECT * FROM study,question WHERE Username =  '"+email+"' and study.StudyID = question.StudyID";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                study = createStudyfromResult(rs);
                user_studies.add(study);
            }
            return user_studies;
        } 
        catch (SQLException ex) {
            Logger.getLogger(StudyDB.class.getName()).log(Level.SEVERE, null, ex);
            return user_studies;
        }
        finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        
    }
    
    public  static boolean addStudy(Study study){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ArrayList<String> ans = study.getAnswerList();
            String option1 = ans.get(0);
            String option2 = ans.get(1);
            String option3 = ans.get(2);
            String option4 = "";
            String option5 = "";
            if (ans.size() == 4){
                option4 = ans.get(4);
            }
            
            if (ans.size() == 5){
                option4 = ans.get(4);
                option5 = ans.get(5);
            }
            
            String study_query = "INSERT INTO study (StudyName, Description, Username, DateCreated, ImageURL, ReqParticipants, ActParticipants, SStatus) VALUES (?,?,?,?,?,?,?,?);";
            System.out.println(study_query);
            ps = connection.prepareStatement(study_query,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, study.getStudyName());
            ps.setString(2, study.getDescription());
            ps.setString(3,study.getEmail());
            ps.setDate(4, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            ps.setString(5, study.getImageURL());
            ps.setInt(6, study.getRequestedParticipants());
            ps.setInt(7, study.getNumOfParticipants());
            ps.setString(8, study.getStatus());
            
            
            int i = ps.executeUpdate();
            if (i < 1){
                return false;
            }
            rs = ps.getGeneratedKeys();
            rs.next();
            int StudyID = rs.getInt(1);
            System.out.println(StudyID);
            System.out.println("Study ID " + StudyID);
            
            ps.close();
            String AnswerType;
            if (isNumeric(option5) && isNumeric(option4) && isNumeric(option3) && isNumeric(option2) && isNumeric(option1)){
                AnswerType = "Numeric";
            }
            else
            {
                AnswerType = "Text";
            }
            
            String question_query = "INSERT INTO question "
                    + "(StudyID, Question, AnswerType, Option1, Option2, Option3, Option4, Option5) VALUES (?,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(question_query);
            
            ps.setInt(1, StudyID);
            ps.setString(2, study.getQuestion());
            ps.setString(3, AnswerType);
            ps.setString(4, option1);
            ps.setString(5, option2);
            ps.setString(6, option3);
            ps.setString(7, option4);
            ps.setString(8, option5);
            i = ps.executeUpdate();
            return i >= 1;
        } catch (SQLException ex) {
            Logger.getLogger(StudyDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        catch (Exception e){
            Logger.getLogger(StudyDB.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static boolean updateStudy(Study study){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ArrayList<String> ans = study.getAnswerList();
            String option1 = ans.get(0);
            String option2 = ans.get(1);
            String option3 = ans.get(2);
            String option4 = "";
            String option5 = "";
            if (ans.size() == 4){
                option4 = ans.get(3);
            }
            
            if (ans.size() == 5){
                option4 = ans.get(3);
                option5 = ans.get(4);
            }
            
            String study_query = "UPDATE study set StudyName = ? , Description = ?, Username = ?, DateCreated = ?, ImageURL = ?, ReqParticipants = ?, ActParticipants = ?, SStatus = ? WHERE StudyID = ?";
            ps = connection.prepareStatement(study_query);
            
            ps.setString(1, study.getStudyName());
            ps.setString(2, study.getDescription());
            ps.setString(3,study.getEmail());
            ps.setDate(4, (java.sql.Date) study.getDateCreated());
            ps.setString(5, study.getImageURL());
            ps.setInt(6, study.getRequestedParticipants());
            ps.setInt(7, study.getNumOfParticipants());
            ps.setString(8, study.getStatus());
            ps.setInt(9,study.getStudyCode());
            ps.executeUpdate();
            
           
           String AnswerType;
            AnswerType = getAnswerType(option1, option2, option3, option4, option5);
            String question_query = "UPDATE question SET "
                    + "Question = ?, AnswerType = ?, Option1 = ? , Option2 = ?, Option3 = ?, Option4 = ?, Option5 = ? WHERE StudyID = ? and QuestionID = ?";
            ps = connection.prepareStatement(question_query);
            System.out.println(ps.toString());
            ps.setString(1, study.getQuestion());
            ps.setString(2, AnswerType);
            ps.setString(3, option1);
            ps.setString(4, option2);
            ps.setString(5, option3);
            ps.setString(6, option4);
            ps.setString(7, option5);
            ps.setInt(8, study.getStudyCode());
            ps.setInt(9,study.getQuestionID());
            int i = ps.executeUpdate(question_query);
            return i >=0;
        } catch (SQLException ex) {
            Logger.getLogger(StudyDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        catch (Exception e){
            Logger.getLogger(StudyDB.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static Study createStudyfromResult(ResultSet rs){
        try {
            Study study = new Study();
            study.setStudyCode(rs.getInt("StudyID"));
            study.setStudyName(rs.getString("StudyName"));
            study.setDescription(rs.getString("Description"));
            study.setEmail(rs.getString("Username"));
            study.setImageURL(rs.getString("ImageURL"));
            study.setRequestedParticipants(rs.getInt("ReqParticipants"));
            study.setNumOfParticipants(rs.getInt("ActParticipants"));
            study.setStatus(rs.getString("SStatus"));
            ArrayList<String> answers = new ArrayList<>();
            study.setQuestion(rs.getString("Question"));
            study.setAnswerType(rs.getString("AnswerType"));
            study.setQuestionID(rs.getInt("QuestionID"));
            answers.add(rs.getString("Option1"));
            answers.add(rs.getString("Option2"));
            answers.add(rs.getString("Option3"));
            if (!rs.getString("Option4").equals("")){
                answers.add(rs.getString("Option4"));
            }
            if (!rs.getString("Option5").equals("")){
                answers.add(rs.getString("Option5"));
            }
            study.setAnswerList(answers);
            return study;
        } catch (Exception ex) {
            Logger.getLogger(StudyDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }

    private static String getAnswerType(String a, String b, String c , String d , String e) {
        try{
            Double.parseDouble(a);
            Double.parseDouble(b);
            Double.parseDouble(c);
            Double.parseDouble(d);
            Double.parseDouble(e);
            return "Number";
        }
        catch(Exception ex){
           System.out.println("One of the parameter is not a Number");
           return "Text";
        }
    }
    
    public static boolean isNumeric(String s) {  
        return s.matches("[-+]?\\d*\\.?\\d+");  
    }
}
