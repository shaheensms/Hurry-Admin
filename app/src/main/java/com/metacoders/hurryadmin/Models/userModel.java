package com.metacoders.hurryadmin.Models;

public class userModel {
    String userFined, phone, userTripCount, userProPic, userName, userTripList, userWallet, userIdState, userTotalSpent, userPhone;


    public userModel(String userFined, String userTripCount, String userProPic, String userName, String userTripList, String userWallet, String userIdState, String userTotalSpent) {
        this.userFined = userFined;
        this.userTripCount = userTripCount;
        this.userProPic = userProPic;
        this.userName = userName;
        this.userTripList = userTripList;
        this.userWallet = userWallet;
        this.userIdState = userIdState;
        this.userTotalSpent = userTotalSpent;
    }

    public userModel() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserTotalSpent() {
        return userTotalSpent;
    }

    public void setUserTotalSpent(String userTotalSpent) {
        this.userTotalSpent = userTotalSpent;
    }

    public String getUserFined() {
        return userFined;
    }

    public void setUserFined(String userFined) {
        this.userFined = userFined;
    }

    public String getUserTripCount() {
        return userTripCount;
    }

    public void setUserTripCount(String userTripCount) {
        this.userTripCount = userTripCount;
    }

    public String getUserProPic() {
        return userProPic;
    }

    public void setUserProPic(String userProPic) {
        this.userProPic = userProPic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTripList() {
        return userTripList;
    }

    public void setUserTripList(String userTripList) {
        this.userTripList = userTripList;
    }

    public String getUserWallet() {
        return userWallet;
    }

    public void setUserWallet(String userWallet) {
        this.userWallet = userWallet;
    }

    public String getUserIdState() {
        return userIdState;
    }

    public void setUserIdState(String userIdState) {
        this.userIdState = userIdState;
    }
}
