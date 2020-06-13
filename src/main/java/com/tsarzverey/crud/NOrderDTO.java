package com.tsarzverey.crud;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class NOrderDTO {
    private String clientPhone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String startTime;
    private String finishTime;
    private String price;

    public NOrderDTO() {
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "NOrderDTO{" +
                "clientPhone='" + clientPhone + '\'' +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
