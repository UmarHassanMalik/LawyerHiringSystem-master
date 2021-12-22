package com.innova.lawyerhiringsystem.model;
/* This is model class for 'Lawyer & Client' role.
*  Will be used to define variables associated with user roles
*  Principally, used to retrieving data from Firebase Realtime database
* */
public class Lawyer {
    String name;
    String email;
    String password;
    String mobile;
    String city;
    String profession;
    String lawyerId;
    String experience;
    String address;
    String lawyerType;
    String courtType;
    String user_id;

//    public Lawyer(String name, String email, String password, String mobile, String city, String profession, String lawyerId, String experience, String address, String lawyerType) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.mobile = mobile;
//        this.city = city;
//        this.profession = profession;
//        this.lawyerId = lawyerId;
//        this.experience = experience;
//        this.address = address;
//        this.lawyerType= lawyerType;
//    }

    public Lawyer() {
    }

    public Lawyer(String name, String email, String password, String mobile, String city, String profession, String lawyerId, String experience, String address, String lawyerType, String courtType, String user_id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.city = city;
        this.profession = profession;
        this.lawyerId = lawyerId;
        this.experience = experience;
        this.address = address;
        this.lawyerType = lawyerType;
        this.courtType = courtType;
        this.user_id = user_id;
    }

    public String getCourtType() {
        return courtType;
    }

    public void setCourtType(String courtType) {
        this.courtType = courtType;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(String lawyerId) {
        this.lawyerId = lawyerId;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLawyerType() {
        return lawyerType;
    }

    public void setLawyerType(String lawyerType) {
        this.lawyerType = lawyerType;
    }
}
