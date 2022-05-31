package com.tungstun.security.domain.user;

import javax.persistence.*;

@Entity
public class BarRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "bar_id")
    private Long bar;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    private Role role;

    public BarRole() { }
    public BarRole(Long bar, User user, Role role) {
        this.bar = bar;
        this.user = user;
        this.role = role;
    }

    public Long getId() { return id; }

    public Long getBar() { return bar; }

    public User getUser() { return user; }

    public Role getRole() { return role; }

    public void setRole(Role role) {
        this.role = role;
    }
}
