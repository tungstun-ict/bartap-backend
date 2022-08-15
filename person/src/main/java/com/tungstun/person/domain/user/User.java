package com.tungstun.person.domain.user;

import com.tungstun.common.phonenumber.PhoneNumber;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "\"user\"")
@SQLDelete(sql = "UPDATE \"user\" SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class User {
    @Column(name = "deleted", columnDefinition = "BOOLEAN default false")
    private final boolean deleted = Boolean.FALSE;

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

    public User(Long id, String username, String phoneNumber, String fullName) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        setPhoneNumber(phoneNumber);
    }

    public Long getId() {
        return id;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null) this.phoneNumber = new PhoneNumber(phoneNumber);
        else this.phoneNumber = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
