package com.innova.lawyerhiringsystem.model;
/* POJO class
*  In fulfillment of Firebase Database requirements
*  Used to save and retrieve articles created by Lawyers*/
public class Article {
    String title, content, article_id, lawyer_id,layer_name;

    public Article() {
    }

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Article(String title, String content, String article_id, String lawyer_id, String layer_name) {
        this.title = title;
        this.content = content;
        this.article_id = article_id;
        this.lawyer_id = lawyer_id;
        this.layer_name = layer_name;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getLawyer_id() {
        return lawyer_id;
    }

    public void setLawyer_id(String lawyer_id) {
        this.lawyer_id = lawyer_id;
    }

    public String getLayer_name() {
        return layer_name;
    }

    public void setLayer_name(String layer_name) {
        this.layer_name = layer_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
