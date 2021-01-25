package com.metacoders.hurryadmin.Models;

import java.io.Serializable;

public class ambulanceModel  implements Serializable {
    String post_id , req_date , status , user_id , user_ph;

    public ambulanceModel() {
    }

    public ambulanceModel(String post_id, String req_date, String status, String user_id) {
        this.post_id = post_id;
        this.req_date = req_date;
        this.status = status;
        this.user_id = user_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public String getUser_ph() {
        return user_ph;
    }

    public void setUser_ph(String user_ph) {
        this.user_ph = user_ph;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getReq_date() {
        return req_date;
    }

    public void setReq_date(String req_date) {
        this.req_date = req_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
