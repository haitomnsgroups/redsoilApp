package com.haitomns.redsoil;

public class bloodDonationTableModel {
    String donorId, donorName, donorGender, donorPhone, abo, rh, unit;

    public bloodDonationTableModel(String donorId, String donorName, String donorGender, String donorPhone, String abo, String rh, String unit) {
        this.donorId = donorId;
        this.donorName = donorName;
        this.donorGender = donorGender;
        this.donorPhone = donorPhone;
        this.abo = abo;
        this.rh = rh;
        this.unit = unit;
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

    public String getDonorGender() {
        return donorGender;
    }

    public void setDonorGender(String donorGender) {
        this.donorGender = donorGender;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
