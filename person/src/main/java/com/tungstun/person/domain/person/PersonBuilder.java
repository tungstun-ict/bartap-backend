package com.tungstun.person.domain.person;

import com.tungstun.person.domain.user.User;

public class PersonBuilder {
    private final Long barId;
    private String name;
    private PreferredNaming preferredNaming;
    private User user;

    public PersonBuilder(Long barId) {
        this.barId = barId;
        this.name = "";
        this.preferredNaming = PreferredNaming.PERSON_NAME;
    }

    public PersonBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PersonBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public PersonBuilder setPreferredNaming(PreferredNaming preferredNaming) {
        this.preferredNaming = preferredNaming;
        return this;
    }

    public Person build() {
        return new Person(
                barId,
                name,
                user
        );
    }
}
