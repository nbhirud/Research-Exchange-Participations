/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2.data;

import assignment2.business.Reported;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nbhirud
 */
public class ReportedDB {
    
    public static boolean isUserReportExists(String UserName, int StudyID, int QuestionID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String query = "SELECT * FROM reported WHERE StudyId = ? and QuestionID = ? and UserName = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, StudyID);
            ps.setInt(2, QuestionID);
            ps.setString(3, UserName);
            rs = ps.executeQuery();
            return rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static boolean addReport(Reported r){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            String query = "INSERT INTO reported VALUES (?,?,?,?,?,?)";
            ps = connection.prepareStatement(query);
            ps.setInt(1, r.getQuestionID());
            ps.setInt(2, r.getStudyID());
            ps.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            ps.setInt(4,  r.getNumParticipants());
            ps.setString(5, r.getStatus());
            ps.setString(6,r.getUserName());
            int i = ps.executeUpdate();
            return i==1;
            
        } catch (SQLException ex) {
            Logger.getLogger(ReportedDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);            
        }
        
    }
    
    public static boolean updateReportStatus(int QuestionID, int StudyID, String Status){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            String query = "UPDATE reported SET Status = ? WHERE QuestionID = ? and StudyID = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, Status);
            ps.setInt(2, QuestionID);
            ps.setInt(3, StudyID); 
            System.out.println(ps.toString());
            int i = ps.executeUpdate();
            return i >= 0;
        } catch (SQLException ex) {
            Logger.getLogger(ReportedDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);            
        }
    }
    
    public static ArrayList<Reported> getAllPendingReports(){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        ArrayList<Reported> pending_reports = new ArrayList<>();
        try{
            String query = "SELECT * FROM reported, question where Status = 'Pending' "
                    + " and reported.QuestionID = question.QuestionID and "
                    + "question.StudyID = reported.StudyID GROUP BY question.QuestionID, reported.StudyID";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                Reported r = new Reported();
                r.setQuestionID(rs.getInt("QuestionID"));
                r.setStudyID(rs.getInt("StudyID"));
                r.setReported_date((java.util.Date)rs.getDate("Date"));
                r.setNumParticipants(rs.getInt("NumParticipants"));
                r.setStatus(rs.getString("Status"));
                r.setUserName(rs.getString("UserName"));
                r.setQuestion(rs.getString("Question"));
                pending_reports.add(r);
            }
            return  pending_reports;
        } catch (SQLException ex) {
            Logger.getLogger(ReportedDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pending_reports;
    }
    
    public static ArrayList<Reported> getUserReportedQuestions(String email){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        ArrayList<Reported> user_reports = new ArrayList<>();
        try{
            String query = "SELECT * FROM reported,question where UserName = ? "
                    + "and reported.QuestionID = question.QuestionID and "
                    + "question.StudyID = question.StudyID";
            ps = connection.prepareStatement(query);
            ps.setString(1,email);
            rs = ps.executeQuery();
            while (rs.next()){
                Reported r = new Reported();
                r.setQuestionID(rs.getInt("QuestionID"));
                r.setStudyID(rs.getInt("StudyID"));
                r.setReported_date((java.util.Date)rs.getDate("Date"));
                r.setNumParticipants(rs.getInt("NumParticipants"));
                r.setStatus(rs.getString("Status"));
                r.setUserName(rs.getString("UserName"));
                r.setQuestion(rs.getString("Question"));
                user_reports.add(r);
            }
            return  user_reports;
        } catch (SQLException ex) {
            Logger.getLogger(ReportedDB.class.getName()).log(Level.SEVERE, null, ex);
        }
          finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return user_reports;
    }
}