package com.mrudul.tourandtravel.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tour_package")
public class TourPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private Integer packageId;

    @Column(name = "pack_name", nullable = false, unique = true)
    private String packName;

    @Column(name = "pack_description")
    private String packDescription;

    @Column(name = "pack_duration_day", nullable = false)
    private Integer packDurationDay;

    @Column(name = "pack_duration_night", nullable = false)
    private Integer packDurationNight;

    @Column(name = "pack_price", nullable = false)
    private Integer packPrice;

    @Column(name = "pack_max_capacity", nullable = false)
    private Integer packMaxCapacity;

    @Column(name = "pack_thumbnail")
    private String packThumbnail;

    @Column(name = "pack_status")
    private String packStatus;

    @Column(name = "pack_start_date")
    private LocalDate packStartDate;

    @Column(name = "pack_rating")
    private Float packRating;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "guide_id")
    private TourGuide guide;


    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getPackDescription() {
        return packDescription;
    }

    public void setPackDescription(String packDescription) {
        this.packDescription = packDescription;
    }

    public Integer getPackDurationDay() {
        return packDurationDay;
    }

    public void setPackDurationDay(Integer packDurationDay) {
        this.packDurationDay = packDurationDay;
    }

    public Integer getPackDurationNight() {
        return packDurationNight;
    }

    public void setPackDurationNight(Integer packDurationNight) {
        this.packDurationNight = packDurationNight;
    }

    public Integer getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(Integer packPrice) {
        this.packPrice = packPrice;
    }

    public Integer getPackMaxCapacity() {
        return packMaxCapacity;
    }

    public void setPackMaxCapacity(Integer packMaxCapacity) {
        this.packMaxCapacity = packMaxCapacity;
    }

    public String getPackThumbnail() {
        return packThumbnail;
    }

    public void setPackThumbnail(String packThumbnail) {
        this.packThumbnail = packThumbnail;
    }

    public String getPackStatus() {
        return packStatus;
    }

    public void setPackStatus(String packStatus) {
        this.packStatus = packStatus;
    }

    public LocalDate getPackStartDate() {
        return packStartDate;
    }

    public void setPackStartDate(LocalDate packStartDate) {
        this.packStartDate = packStartDate;
    }

    public Float getPackRating() {
        return packRating;
    }

    public void setPackRating(Float packRating) {
        this.packRating = packRating;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public TourGuide getGuide() {
        return guide;
    }

    public void setGuide(TourGuide guide) {
        this.guide = guide;
    }
}
