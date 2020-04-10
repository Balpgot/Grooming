package com.tsarzverey.crud;

import javax.persistence.*;

@Table
@Entity
public class ServiceDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public ServiceDAO(String name) {
        this.name = name;
    }

    public ServiceDAO() {
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
}