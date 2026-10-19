package com.mrudul.tourandtravel.dto.request;

public class CustomerLoginRequest {

    private String custEmail;
    private String password;

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
