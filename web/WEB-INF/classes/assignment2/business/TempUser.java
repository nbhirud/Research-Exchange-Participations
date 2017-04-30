/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2.business;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author nbhirud
 */
public class TempUser implements Serializable{
    String username, email, password, uuid;
    Date date;

    public TempUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TempUser{" + "username=" + username + ", email=" + email + ", password=" + password + ", uuid=" + uuid + ", date=" + date + '}';
    }
    
    
}
