package com.innova.lawyerhiringsystem.model;
/*
* Model/POJO class
* Represents Case Entity
* The legal case detail entered by user
* user can have multiple cases
* a case can belong to only one user*/
public class Case {
    String title, city, budget, statement, courttype, lawyertype,email, hiredlawyer;
    String isopen; // default will be true until layer is assigned
    int applications;
    String case_id, user_id;

    public Case() {
    }

//    public Case(String title, String city, String budget, String statement, String courttype, String lawyertype, String email, String hiredlawyer, boolean isopen) {
//        this.title = title;
//        this.city = city;
//        this.budget = budget;
//        this.statement = statement;
//        this.courttype = courttype;
//        this.lawyertype = lawyertype;
//        this.email = email;
//        this.hiredlawyer = hiredlawyer;
//        this.isopen = isopen;
//    }

    public Case(String title, String city, String budget, String statement, String courttype, String lawyertype, String email, String isopen) {
        // will be put in use to create objects locally
        this.title = title;
        this.city = city;
        this.budget = budget;
        this.statement = statement;
        this.courttype = courttype;
        this.lawyertype = lawyertype;
        this.email = email;
        this.isopen = isopen;
    }

    public Case(String title, String city, String budget, String statement, String courttype, String lawyertype, String email, String hiredlawyer, String isopen, int applications, String case_id, String user_id) {
        this.title = title;
        this.city = city;
        this.budget = budget;
        this.statement = statement;
        this.courttype = courttype;
        this.lawyertype = lawyertype;
        this.email = email;
        this.hiredlawyer = hiredlawyer;
        this.isopen = isopen;
        this.applications = applications;
        this.case_id = case_id;
        this.user_id = user_id;
    }

    public int getApplications() {
        return applications;
    }

    public void setApplications(int applications) {
        this.applications = applications;
    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getCourttype() {
        return courttype;
    }

    public void setCourttype(String courttype) {
        this.courttype = courttype;
    }

    public String getLawyertype() {
        return lawyertype;
    }

    public void setLawyertype(String lawyertype) {
        this.lawyertype = lawyertype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHiredlawyer() {
        return hiredlawyer;
    }

    public void setHiredlawyer(String hiredlawyer) {
        this.hiredlawyer = hiredlawyer;
    }

    public String isIsopen() {
        return isopen;
    }

    public void setIsopen(String isopen) {
        this.isopen = isopen;
    }
}
