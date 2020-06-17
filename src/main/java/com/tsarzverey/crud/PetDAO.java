package com.tsarzverey.crud;

import javax.persistence.*;

@Table(name = "PET")
@Entity
public class PetDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "client_pets")
    private ClientDAO client;
    private String comment;
    private String type;
    public PetDAO() {
    }

    public PetDAO(String name, ClientDAO client, String comment, String type) {
        this.name = name;
        this.client = client;
        this.comment = comment;
        this.type = type;
    }

    public PetDAO(ClientDAO client) {
        this.name = null;
        this.client = client;
        this.comment = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientDAO getClient() {
        return client;
    }

    public void setClient(ClientDAO client) {
        this.client = client;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PetDAO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", client=" + client.getId() +
                ", comment='" + comment + '\'' +
                '}';
    }
}