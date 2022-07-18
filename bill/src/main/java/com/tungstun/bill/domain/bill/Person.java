package com.tungstun.bill.domain.bill;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Person {
    @Column(name = "person_id")
    private Long personId;

    @Column(name = "person_username")
    private String username;

    public Person() {
    }

    public Person(Long personId, String username) {
        this.personId = personId;
        this.username = username;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long bartenderId) {
        this.personId = bartenderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }
}
