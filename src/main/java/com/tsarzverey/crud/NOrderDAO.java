package com.tsarzverey.crud;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NORDER")
public class NOrderDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "clientOrders", nullable = false)
    private ClientDAO client;
    private Date date;
    private Date startTime;
    private Date finishTime;
    private Integer price;

    public NOrderDAO(){}

    public NOrderDAO(ClientDAO client, Date date, Date startTime, Date finishTime, Integer price) {
        this.client = client;
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientDAO getClient() {
        return client;
    }

    public void setClient(ClientDAO client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date time) {
        this.startTime = time;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}