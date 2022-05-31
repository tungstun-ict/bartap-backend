package com.tungstun.core.domain.bar;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BarDetails {
    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "mail")
    private String mail;

    @Column(name = "phone_number")
    private String phoneNumber;

    public BarDetails() {
    }
    public BarDetails(String name, String address, String mail, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
