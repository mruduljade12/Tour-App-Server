package com.mrudul.tourandtravel.dto.request;

public class DriverLoginRequest {

    private String driverEmail;
    private String accessCode;

    public DriverLoginRequest() {
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
