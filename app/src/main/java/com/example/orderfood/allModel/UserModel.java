package com.example.orderfood.allModel;

import java.io.Serializable;
import java.util.ArrayList;

public class UserModel implements Serializable {
    private String id_User;
    private String email_User;
    private boolean active;
    private ArrayList<BillModel> billModelArrayList;
    public UserModel() {
    }

    public UserModel(String id_User, String email_User, boolean active, ArrayList<BillModel> billModelArrayList) {
        this.id_User = id_User;
        this.email_User = email_User;
        this.active = active;
        this.billModelArrayList = billModelArrayList;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getEmail_User() {
        return email_User;
    }

    public void setEmail_User(String email_User) {
        this.email_User = email_User;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayList<BillModel> getBillModelArrayList() {
        return billModelArrayList;
    }

    public void setBillModelArrayList(ArrayList<BillModel> billModelArrayList) {
        this.billModelArrayList = billModelArrayList;
    }
}
