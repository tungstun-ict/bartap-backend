package com.tungstun.security.domain.user;

import javax.persistence.*;

@Entity
@Table(name = "authorization")
public class Authorization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "bar_id")
    private String barId;

    @Column(name = "role")
    private String role;

    public Authorization() {
    }
    public Authorization(String barId, String role) {
        this.barId = barId;
        this.role = role;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarId() {
        return barId;
    }

    public void setBarId(String barId) {
        this.barId = barId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
