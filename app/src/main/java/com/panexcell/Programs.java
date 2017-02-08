package com.panexcell;

/**
 * Created by sujil on 06-01-2017.
 */

public class Programs {

    private String id;

    public Programs() {
    }

    private String title;
    private String date;
    private String payment;

    public Programs(String id, String title, String payment, String date) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.payment = payment;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
