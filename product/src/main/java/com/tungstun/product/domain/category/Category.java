package com.tungstun.product.domain.category;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "deleted")
    private final boolean deleted = Boolean.FALSE;

    @Column(name = "name")
    private String name;

    @Column(name = "bar_id")
    private Long barId;

    public Category() {
    }

    public Category(String name, Long barId) {
        this.name = name;
        this.barId = barId;
    }

    public Long getId() {
        return id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBarId() {
        return barId;
    }

    public void setBarId(Long barId) {
        this.barId = barId;
    }
}
