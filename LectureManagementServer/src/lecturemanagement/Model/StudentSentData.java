/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Model;

public class StudentSentData {

    private String StudentIP;
    private String username;

    public StudentSentData(String username, String StudentIP) {
        this.StudentIP = StudentIP;
        this.username = username;
    }

    public String getStudentIP() {
        return StudentIP;
    }

    public void setStudentIP(String StudentIP) {
        this.StudentIP = StudentIP;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
