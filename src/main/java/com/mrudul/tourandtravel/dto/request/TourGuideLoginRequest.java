package com.mrudul.tourandtravel.dto.request;

public class TourGuideLoginRequest {

    private String tourGuideEmail;
    private String accessCode;

    public TourGuideLoginRequest(){}

    public String getTourGuideEmail() {
        return tourGuideEmail;
    }

    public void setTourGuideEmail(String tourGuideEmail) {
        this.tourGuideEmail = tourGuideEmail;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
