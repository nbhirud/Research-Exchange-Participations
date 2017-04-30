/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2.business;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author nbhirud
 */
public class Reported implements Serializable {
    int QuestionID, StudyID, NumParticipants;
    Date reported_date;
    String status, UserName, question;
    public Reported() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int QuestionID) {
        this.QuestionID = QuestionID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
    

    public int getStudyID() {
        return StudyID;
    }

    public void setStudyID(int StudyID) {
        this.StudyID = StudyID;
    }

    public int getNumParticipants() {
        return NumParticipants;
    }

    public void setNumParticipants(int NumParticipants) {
        this.NumParticipants = NumParticipants;
    }

    public Date getReported_date() {
        return reported_date;
    }

    public void setReported_date(Date reported_date) {
        this.reported_date = reported_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
