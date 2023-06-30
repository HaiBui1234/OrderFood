package com.example.orderfood.allModel;

import java.util.ArrayList;

public class BillModel {
       private String idBill;
       private String idUser;
       private String emailBill;
       private String nameUser;
       private String paymentMethodBill;
       private int phoneBill;
       private float moneyBill;
       private String adressBill;
       private ArrayList<CartModel> cartModelArrayList;
       private String dateBill;
       private Boolean CheckBill;

    public BillModel() {
    }

    public BillModel(ArrayList<CartModel> cartModelArrayList) {
        this.cartModelArrayList = cartModelArrayList;
    }

    public BillModel(String idBill, String idUser, String emailBill, String nameUser, String paymentMethodBill, int phoneBill, float moneyBill, String adressBill, ArrayList<CartModel> cartModelArrayList, String dateBill, Boolean checkBill) {
        this.idBill = idBill;
        this.idUser = idUser;
        this.emailBill = emailBill;
        this.nameUser = nameUser;
        this.paymentMethodBill = paymentMethodBill;
        this.phoneBill = phoneBill;
        this.moneyBill = moneyBill;
        this.adressBill = adressBill;
        this.cartModelArrayList = cartModelArrayList;
        this.dateBill = dateBill;
        CheckBill = checkBill;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public String getEmailBill() {
        return emailBill;
    }

    public void setEmailBill(String emailBill) {
        this.emailBill = emailBill;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPaymentMethodBill() {
        return paymentMethodBill;
    }

    public void setPaymentMethodBill(String paymentMethodBill) {
        this.paymentMethodBill = paymentMethodBill;
    }

    public int getPhoneBill() {
        return phoneBill;
    }

    public void setPhoneBill(int phoneBill) {
        this.phoneBill = phoneBill;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public float getMoneyBill() {
        return moneyBill;
    }

    public void setMoneyBill(float moneyBill) {
        this.moneyBill = moneyBill;
    }

    public String getAdressBill() {
        return adressBill;
    }

    public void setAdressBill(String adressBill) {
        this.adressBill = adressBill;
    }

    public ArrayList<CartModel> getCartModelArrayList() {
        return cartModelArrayList;
    }

    public void setCartModelArrayList(ArrayList<CartModel> cartModelArrayList) {
        this.cartModelArrayList = cartModelArrayList;
    }

    public String getDateBill() {
        return dateBill;
    }

    public void setDateBill(String dateBill) {
        this.dateBill = dateBill;
    }

    public Boolean getCheckBill() {
        return CheckBill;
    }

    public void setCheckBill(Boolean checkBill) {
        CheckBill = checkBill;
    }

    public String getProductBill(){
        if (cartModelArrayList.isEmpty()){
            return "";
        }
        StringBuilder builder=new StringBuilder();
        for (int i=0;i<cartModelArrayList.size();i++){
            CartModel cartModel=cartModelArrayList.get(i);
            if (builder.length()>0){
                builder.append("\n");
            }
            builder.append("- "+cartModel.getNameFood()+"("+cartModel.getPriceFood()+") - Số lượng:"+cartModel.getQuantityFood());

        }
        return builder.toString();
    }
}
