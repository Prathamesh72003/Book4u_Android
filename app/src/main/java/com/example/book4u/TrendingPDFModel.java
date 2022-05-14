package com.example.book4u;

public class TrendingPDFModel {
    private String id;
    String pdfName;
    String imgName;

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}