package com.haitomns.redsoil;

public class bloodFindTableModel {
    String donorId, donorName, donorGender , donorPhone , diseaseList , abo, rh, hiv, hcv, hbsag, vdrl, dateOfCreation;

    public bloodFindTableModel(String donorId, String donorName, String donorGender, String donorPhone, String diseaseList, String abo, String rh, String hiv, String hcb, String hbsag, String vdrl, String dateOfCreation) {
        this.donorId = donorId;
        this.donorName = donorName;
        this.donorGender = donorGender;
        this.donorPhone = donorPhone;
        this.diseaseList = diseaseList;
        this.abo = abo;
        this.rh = rh;
        this.hiv = hiv;
        this.hcv = hcb;
        this.hbsag = hbsag;
        this.vdrl = vdrl;
        this.dateOfCreation = dateOfCreation;
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

    public String getDiseaseList() {
        return diseaseList;
    }

    public void setDiseaseList(String diseaseList) {
        this.diseaseList = diseaseList;
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

    public String getHiv() {
        return hiv;
    }

    public void setHiv(String hiv) {
        this.hiv = hiv;
    }

    public String getHcv() {
        return hcv;
    }

    public void setHcv(String hcb) {
        this.hcv = hcb;
    }

    public String getHbsag() {
        return hbsag;
    }

    public void setHbsag(String hbsag) {
        this.hbsag = hbsag;
    }

    public String getVdrl() {
        return vdrl;
    }

    public void setVdrl(String vdrl) {
        this.vdrl = vdrl;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}
