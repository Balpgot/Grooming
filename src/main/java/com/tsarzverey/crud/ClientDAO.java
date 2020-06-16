package com.tsarzverey.crud;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "CLIENT")
public class ClientDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "name")
    private String clientName;
    @Column(name = "phone")
    private String mobilePhone;
    @OneToMany(mappedBy = "client")
    @Column(name = "client_pets")
    private Set<PetDAO> clientPets;
    @Column(name = "local")
    private boolean isLocal;
    @Column(name = "registration")
    private LocalDate registration;
    @OneToMany(mappedBy = "client")
    @Column(name = "orders")
    private Set<NOrderDAO> clientOrders;


    public ClientDAO(){}

    public ClientDAO(String clientName, String mobilePhone, boolean isLocal, LocalDate registration) {
        this.clientName = clientName;
        this.mobilePhone = mobilePhone;
        this.isLocal = isLocal;
        this.registration = registration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public LocalDate getRegistration() {
        return registration;
    }

    public void setRegistration(LocalDate registration) {
        this.registration = registration;
    }

    public Set<PetDAO> getClientPets() {
        return clientPets;
    }

    public void setClientPets(Set<PetDAO> clientPets) {
        this.clientPets = clientPets;
    }

    public Set<NOrderDAO> getClientOrders() {
        return clientOrders;
    }

    public void setClientOrders(Set<NOrderDAO> clientOrders) {
        this.clientOrders = clientOrders;
    }

}