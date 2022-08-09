package com.tungstun.bill.domain.person;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "person")
@SQLDelete(sql = "UPDATE person SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Person {
    @Column(name = "deleted", columnDefinition = "BOOLEAN default false")
    private final boolean deleted = Boolean.FALSE;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "bar_id")
    private Long barId;

    @Column(name = "username")
    private String username;

    public Person() {
    }

    public Person(Long id, Long barId, String username) {
        this.id = id;
        this.barId = barId;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public Long getBarId() {
        return barId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }
}
