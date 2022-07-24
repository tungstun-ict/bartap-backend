package com.tungstun.core.domain.bar;

import com.tungstun.common.phonenumber.PhoneNumber;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "bar")
@SQLDelete(sql = "UPDATE category SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Bar {

    @Column(name = "deleted", columnDefinition = "BOOLEAN default false")
    private final boolean deleted = Boolean.FALSE;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private BarDetails details;

    public Bar() {
    }
    public Bar(String name, String address, String mail, String phoneNumber) {
        this.details = new BarDetails(name, address, mail, phoneNumber);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return details.getName();
    }

    public void setName(String name) {
        this.details.setName(name);
    }

    public String getAddress() {
        return details.getAddress();
    }

    public void setAddress(String address) {
        details.setAddress(address);
    }

    public String getMail() {
        return details.getMail();
    }

    public void setMail(String mail) {
        details.setMail(mail);
    }

    public PhoneNumber getPhoneNumber() {
        return details.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        details.setPhoneNumber(phoneNumber);
    }
}
