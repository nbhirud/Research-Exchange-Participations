package assignment2.business;

import java.io.Serializable;
import java.sql.Date;


/**
 *
 * @author nbhirud
 */
public class Answer implements Serializable{
    String email,choice;
    int StudyCode,questionID;
    Date submissionDate;

    public int getStudyCode() {
        return StudyCode;
    }

    public void setStudyCode(int StudyCode) {
        this.StudyCode = StudyCode;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public Answer() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }
}
