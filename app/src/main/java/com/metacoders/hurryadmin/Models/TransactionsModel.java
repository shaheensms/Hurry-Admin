package com.metacoders.hurryadmin.Models;

import java.io.Serializable;

public class TransactionsModel implements Serializable {
    String  userUid , amountPaid ,trxID , tripId, time , payment_type , status ;

    public TransactionsModel() {
    }

    public TransactionsModel(String userUid, String amountPaid, String trxID, String tripId, String time, String payment_type, String status) {
        this.userUid = userUid;
        this.amountPaid = amountPaid;
        this.trxID = trxID;
        this.tripId = tripId;
        this.time = time;
        this.payment_type = payment_type;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getTrxID() {
        return trxID;
    }

    public void setTrxID(String trxID) {
        this.trxID = trxID;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }
}
