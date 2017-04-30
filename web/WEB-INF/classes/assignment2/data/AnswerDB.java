/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2.data;

import assignment2.business.Answer;
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
public class AnswerDB {
    
    public static boolean isUserAnswerExists(String email, int StudyID, int QuestionID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String query = "SELECT * FROM answer WHERE StudyId = ? and QuestionID = ? and UserName = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, StudyID);
            ps.setInt(2, QuestionID);
            ps.setString(3, email);
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
    
    public static boolean addAnswer(Answer answer){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String insert_query = "INSERT INTO answer (StudyID, QuestionID, UserName, Choice, DateSubmitted) VALUES (?,?,?,?,?)";
            System.out.println("In Add Answer Query");
            ps = connection.prepareStatement(insert_query);
            ps.setInt(1, answer.getStudyCode());
            ps.setInt(2, answer.getQuestionID());
            ps.setString(3, answer.getEmail());
            ps.setString(4, answer.getChoice());
            ps.setDate(5,new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            int i = ps.executeUpdate();
            System.out.println("Value of execute: " + i);
            return i == 1;
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }   
    }
    
    public static boolean updateAnswer(Answer answer){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String insert_query = "UPDATE answer SET Choice = ?, DateSubmitted = ? WHERE StudyID = ? , QuestionID = ?, UserName = ?, ";
            ps = connection.prepareStatement(insert_query);
            ps.setString(1, answer.getChoice());
            ps.setDate(2, (Date) answer.getSubmissionDate());
            ps.setInt(3, answer.getStudyCode());
            ps.setInt(4, answer.getQuestionID());
            ps.setString(5, answer.getEmail());
            int i = ps.executeUpdate();
            return i == 1;
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }   
        
    }
    
    public static ArrayList<Answer> getAnswersFor(int StudyCode){
        
        ArrayList<Answer> answers = new ArrayList<>();
        try {
            
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            ResultSet rs = null;
            PreparedStatement ps = null;
            String query = "SELECT * FROM answer where StudyCode = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, StudyCode);
            rs = ps.executeQuery();
            while (rs.next()){
                Answer a = new Answer();
                a.setStudyCode(rs.getInt("StudyID"));
                a.setQuestionID(rs.getInt("QuestionID"));
                a.setEmail(rs.getString("UserName"));
                a.setChoice(rs.getString("Choice"));
                a.setSubmissionDate(rs.getDate("DateSubmitted"));
                answers.add(a);
            }
            return answers;
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDB.class.getName()).log(Level.SEVERE, null, ex);
            return answers;
        }
    }
    
    public static ArrayList<Answer> getAnswersFor(String Email){
        
        ArrayList<Answer> answers = new ArrayList<>();
        try {
            
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            ResultSet rs = null;
            PreparedStatement ps = null;
            String query = "SELECT * FROM answer where UserName = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, Email);
            rs = ps.executeQuery();
            while (rs.next()){
                Answer a = new Answer();
                a.setStudyCode(rs.getInt("StudyID"));
                a.setQuestionID(rs.getInt("QuestionID"));
                a.setEmail(rs.getString("UserName"));
                a.setChoice(rs.getString("Choice"));
                a.setSubmissionDate(rs.getDate("DateSubmitted"));
                answers.add(a);
            }
            return answers;
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDB.class.getName()).log(Level.SEVERE, null, ex);
            return answers;
        }
    } 
}
