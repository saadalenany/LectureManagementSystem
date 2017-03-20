/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Client.ChatRoom.model;

public class Message {

    private char type; // Doctor -> d  , student -> s 
    private String message;

    public Message(String message, char type) {
        this.type = type;
        this.message = message;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}