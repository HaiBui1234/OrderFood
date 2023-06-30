package com.example.orderfood.allModel;

public class FeedbackModel {
    private String id;
    private String nameFeedBack;
    private int sDT;
    private String emailFeedBack;
    private String comment;

    public FeedbackModel() {
    }

    public FeedbackModel(String id, String nameFeedBack, int sDT, String emailFeedBack, String comment) {
        this.id = id;
        this.nameFeedBack = nameFeedBack;
        this.sDT = sDT;
        this.emailFeedBack = emailFeedBack;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameFeedBack() {
        return nameFeedBack;
    }

    public void setNameFeedBack(String nameFeedBack) {
        this.nameFeedBack = nameFeedBack;
    }

    public int getsDT() {
        return sDT;
    }

    public void setsDT(int sDT) {
        this.sDT = sDT;
    }

    public String getEmailFeedBack() {
        return emailFeedBack;
    }

    public void setEmailFeedBack(String emailFeedBack) {
        this.emailFeedBack = emailFeedBack;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
