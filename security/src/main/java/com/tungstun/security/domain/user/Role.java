package com.tungstun.security.domain.user;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
public class Role {
    public static final Role OWNER = new Role(0L, "OWNER", Collections.emptyList());
    public static final Role BARTENDER = new Role(1L, "OWNER", Collections.emptyList());
    public static final Role CUSTOMER = new Role(2L, "OWNER", Collections.emptyList());
    public static final Role role = new Role(0L, "OWNER", Collections.emptyList());

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

    public Role() {
    }
    public Role(Long id, String name, Collection<Privilege> privileges) {
        this.id = id;
        this.name = name;
        this.privileges = privileges;
    }
}
