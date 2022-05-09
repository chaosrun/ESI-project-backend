package esi.project.ils.requests;

import esi.project.ils.users.User;

import java.util.Date;

public class LoanRequestDto {
    private int id;
    private Date startDate;
    private Date endDate;
    private String status;
    private Date createdDate;
    private Date updatedDate;
    private String locationAddress;
    private String locationCity;
    private String locationZipCode;
    private String userName;
    private String userEmail;
    private String materialTitle;
    private String materialAuthor;
    private String materialCallNumber;
    private String materialPublishedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getLocationZipCode() {
        return locationZipCode;
    }

    public void setLocationZipCode(String locationZipCode) {
        this.locationZipCode = locationZipCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMaterialTitle() {
        return materialTitle;
    }

    public void setMaterialTitle(String materialTitle) {
        this.materialTitle = materialTitle;
    }

    public String getMaterialAuthor() {
        return materialAuthor;
    }

    public void setMaterialAuthor(String materialAuthor) {
        this.materialAuthor = materialAuthor;
    }

    public String getMaterialCallNumber() {
        return materialCallNumber;
    }

    public void setMaterialCallNumber(String materialCallNumber) {
        this.materialCallNumber = materialCallNumber;
    }

    public String getMaterialPublishedAt() {
        return materialPublishedAt;
    }

    public void setMaterialPublishedAt(String materialPublishedAt) {
        this.materialPublishedAt = materialPublishedAt;
    }
}
