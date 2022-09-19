package com.haitomns.redsoil;

public class bloodFindTableModel {
    String donorId, donorName, donorPhone, abo, rh, dateOfCreation, bloodExpiryDate;

    public bloodFindTableModel(String donorId, String donorName, String donorPhone, String abo, String rh, String dateOfCreation, String bloodExpiryDate) {
        this.donorId = donorId;
        this.donorName = donorName;
        this.donorPhone = donorPhone;
        this.abo = abo;
        this.rh = rh;
        this.dateOfCreation = dateOfCreation;
        this.bloodExpiryDate = bloodExpiryDate;
    }

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getDonorPhone() {
        return donorPhone;
    }

    public void setDonorPhone(String donorPhone) {
        this.donorPhone = donorPhone;
    }

    public String getAbo() {
        return abo;
    }

    public void setAbo(String abo) {
        this.abo = abo;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getBloodExpiryDate() {
        return bloodExpiryDate;
    }

    public void setBloodExpiryDate(String bloodExpiryDate) {
        this.bloodExpiryDate = bloodExpiryDate;
    }
}
