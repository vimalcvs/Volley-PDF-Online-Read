package com.vimalcvs.pdflist.helper;

public class PDFModel {

    private final String pdf_name;
    private final String pdf_url;
    private final String img_url;
    private final String read_time;

    public PDFModel(String pdf_name, String pdf_url, String img_url, String read_time) {
        this.pdf_name = pdf_name;
        this.pdf_url = pdf_url;
        this.img_url = img_url;
        this.read_time = read_time;
    }

    public String getPDFName() {
        return pdf_name;
    }

    public String getPDFUrl() {
        return pdf_url;
    }

    public String getPDFImage() {
        return img_url;
    }

    public String getPDFRead() {
        return read_time;
    }


}
