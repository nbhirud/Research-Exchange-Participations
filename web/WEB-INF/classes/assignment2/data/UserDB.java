package assignment2.data;

import assignment2.business.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author nbhirud
 */
public class UserDB {   
    public static HashMap<String,User> getUsers(){
        
        HashMap<String,User> all_users = new HashMap<>();
        all_users.clear();
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM user";
        try{
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            User user = new User();
            while (rs.next()) {
                user = new User();
                user.setName(rs.getString("Name"));
                user.setEmail(rs.getString("Username"));
                user.setNumCoins(rs.getInt("Coins"));
                user.setStudies(rs.getInt("Studies"));
                user.setParticipation(rs.getInt("Participation"));
                user.setType(rs.getString("Type"));
                user.setParticipants(UserDB.getUserParticipants(user.getEmail()));
                all_users.put(user.getEmail(), user);
            }
            return all_users;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static User getUser(String email){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM user where username = '" + email+"';";
        try{
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            User user = new User();
            if (rs.next()) {
                user = new User();
                user.setName(rs.getString("Name"));
                user.setEmail(rs.getString("Username"));
                user.setNumCoins(rs.getInt("Coins"));
                user.setStudies(rs.getInt("Studies"));
                user.setParticipation(rs.getInt("Participation"));
                user.setType(rs.getString("Type"));
                user.setParticipants(UserDB.getUserParticipants(user.getEmail()));
                return user;
            }
            else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static User validateUser(String email, String password){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM user where username = '" + email+"';";
        try{
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("Password").equals(password))
                    return UserDB.getUser(email);
                return null;
            }
            else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        
    }
    
    public static int addUser(User user, String password) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query
                = "INSERT INTO user (Name, Username, Password, Type, Studies, Participation, Coins) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, password);
            ps.setString(4, user.getType());
            ps.setInt(5, user.getStudies());
            ps.setInt(6, user.getParticipation());
            ps.setInt(7, user.getNumCoins());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static boolean updateUserParticipationCoins(User user){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "UPDATE user SET Participation = ? , Coins = ? WHERE UserName = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, user.getParticipation());
            ps.setInt(2, user.getNumCoins());
            ps.setString(3, user.getEmail());            
            int i = ps.executeUpdate();
            return i == 1;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static int getUserParticipants(String username){
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String query = "select count(*) from answer where StudyID in (SELECT StudyID from study where Username = ?)";
            ps = connection.prepareStatement(query);
            ps.setString(1,username);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
            else{
                return 0;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(StudyDB.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
          finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static int getUserStudies(String username){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String query = "select count(*) from studies where Username = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1,username);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
            else{
                return 0;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(StudyDB.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
          finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static boolean updateUserStudiesCount(String email, int count){
               ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "UPDATE user SET Studies = ? WHERE UserName = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, count);
            ps.setString(2, email);
            int i = ps.executeUpdate();
            return i == 1;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        
    }
    
    
}
