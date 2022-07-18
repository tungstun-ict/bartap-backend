package com.tungstun.bill.domain.bill;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Bartender {
    @Column(name = "bartender_id")
    private Long bartenderId;

    @Column(name = "bartender_username")
    private String userName;

    public Bartender() {
    }

    public Bartender(Long bartenderId, String userName) {
        this.bartenderId = bartenderId;
        this.userName = userName;
    }

    public Long getBartenderId() {
        return bartenderId;
    }

    public void setBartenderId(Long bartenderId) {
        this.bartenderId = bartenderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
