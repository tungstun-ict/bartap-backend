package com.tungstun.security.domain.user;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Privilege {
//    public static final Privilege MANAGE_BAR = new Privilege(0L, "MANAGE_BAR");
//    public static final Privilege READ = new Privilege(1L, "READ");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public Privilege() { }
    public Privilege(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
