package com.tungstun.person.domain.user;

import com.tungstun.common.phonenumber.PhoneNumber;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    private Long id;

    @Column(name = "phone_number")
    private PhoneNumber phoneNumber;

    @Column(name = "username")
    private String username;

    @Column(name = "full_name")
    private String fullName;

    public User() {

    }
    public User(Long id, String phoneNumber, String username, String fullName) {
        this.id = id;
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.username = username;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }
}
