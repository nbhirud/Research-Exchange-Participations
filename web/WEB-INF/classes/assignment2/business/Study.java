package assignment2.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author nbhirud
 */
public class Study implements Serializable {
    String studyName,email,question,imageURL,description;
    int studyCode, questionID;
    Date dateCreated;
    int requestedParticipants, numOfParticipants;
    String status, answerType;
    ArrayList<String> answerList;
    
    public Study() {
        answerList = new ArrayList<>();
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public int getStudyCode() {
        return studyCode;
    }

    public void setStudyCode(int studyCode) {
        this.studyCode = studyCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getRequestedParticipants() {
        return requestedParticipants;
    }

    public void setRequestedParticipants(int requestedParticipants) {
        this.requestedParticipants = requestedParticipants;
    }

    public int getNumOfParticipants() {
        return numOfParticipants;
    }

    public void setNumOfParticipants(int numOfParticipants) {
        this.numOfParticipants = numOfParticipants;
    }

    public ArrayList<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(ArrayList<String> answerList) {
        this.answerList = answerList;
    }
    
    public float getAverage(){
     
        return 0.0f;
    }
    
    public float getMinimum(){
        return 0.0f;
    }
    
    public float getMaximum(){
        return 0.0f;
    }
    
    public float getSD(){
        
        return 0.0f;
    }
}
