package assignment2.business;

import java.io.Serializable;

/**
 *
 * @author nbhirud
 */
public class User implements Serializable {
    String name, email, type;
    int numCoins, studies, participation,participants;

    public int getStudies() {
        return studies;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }
    

    public void setStudies(int studies) {
        this.studies = studies;
    }

 
    public int getParticipation() {
        return participation;
    }

    public void setParticipation(int participation) {
        this.participation = participation;
    }

    public User() {
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumCoins() {
        return numCoins;
    }

    public void setNumCoins(int numCoins) {
        
        this.numCoins = numCoins;
    }
    
    
}
