package com.mrudul.tourandtravel.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Integer custId;

    @Column(name = "cust_name", nullable = false, length = 40)
    private String custName;

    @Column(name = "cust_email", nullable = false, unique = true, length = 40)
    private String custEmail;

    @Column(name = "cust_phone", nullable = false, unique = true, length = 10)
    private String custPhone;

    @Column(name = "cust_address", length = 50)
    private String custAddress;

    @Column(name = "cust_gender")
    private String custGender;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;


    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustGender() {
        return custGender;
    }

    public void setCustGender(String custGender) {
        this.custGender = custGender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
