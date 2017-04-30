/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2.data;

import assignment2.business.TempUser;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nbhirud
 */
public class TempUserDB {
    public static TempUser getUser(String token){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet rs = null;
        TempUser tu = null;
        PreparedStatement ps = null;
        try {
            String get_tu = "SELECT * FROM tempuser where Token = ?";
            ps = connection.prepareStatement(get_tu);
            ps.setString(1, token);
            rs = ps.executeQuery();
            if(rs.next()){
                tu = new TempUser();
                tu.setUsername(rs.getString("UName"));
                tu.setEmail(rs.getString("Email"));
                tu.setPassword(rs.getString("Password"));
                tu.setUuid(rs.getString("Token"));
            }
            return tu;
        } catch (SQLException ex) {
            Logger.getLogger(TempUserDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        
    }
    
    public static boolean addTempUser(TempUser tu){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String add_tu = "INSERT INTO TempUser (UName, Email, Password, IssueDate, Token) VALUES (?,?,?,?,?)";
            java.util.Date myDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
            ps = connection.prepareStatement(add_tu);
            ps.setString(1, tu.getUsername());
            ps.setString(2, tu.getEmail());
            ps.setString(3, tu.getPassword());
            ps.setDate(4, sqlDate);
            ps.setString(5, tu.getUuid());
            int i = ps.executeUpdate();
            return i == 1;
        } catch (SQLException ex) {
            Logger.getLogger(TempUserDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }        
    }
    
    public static boolean DeleteTempUser(String email, String token){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            System.out.println("Before Delete");
            String del_tu = "DELETE FROM TempUser WHERE Email = ? and Token = ?";
            ps = connection.prepareStatement(del_tu);
            ps.setString(1, email);
            ps.setString(2, token);
            int i = ps.executeUpdate();
            System.out.println("After Delete");
            return i == 1;
        } catch (SQLException ex) {
            Logger.getLogger(TempUserDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }        
    }
}
