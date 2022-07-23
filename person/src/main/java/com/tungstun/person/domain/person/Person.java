package com.tungstun.person.domain.person;

import com.tungstun.person.domain.user.User;

import javax.persistence.*;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "bar_id")
    private Long barId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private User user;

    public Person() {
    }

    public Person(Long barId, String name, User user) {
        this.barId = barId;
        this.name = name;
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public Long getBarId() {
        return barId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }


}
