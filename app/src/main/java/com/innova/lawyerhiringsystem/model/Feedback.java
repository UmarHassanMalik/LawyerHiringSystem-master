package com.innova.lawyerhiringsystem.model;
/* Model POJO Class
*  In use by Firebase Database to store Feedback
*  Direct objects are pushed to node
*  Data is stored under 'feedback' node
* */
public class Feedback {
    String name, email, message;

    public Feedback() {
    }

    public Feedback(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
