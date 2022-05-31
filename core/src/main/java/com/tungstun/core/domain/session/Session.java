package com.tungstun.core.domain.session;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "locked", nullable = false)
    private boolean isLocked;

    public static Session with(String name) {
        return new Session(name, ZonedDateTime.now().toLocalDateTime());
    }

    public Session() {
    }

    private Session(String name, LocalDateTime creationDate) {
        this.name = name;
        this.creationDate = creationDate;
        this.isLocked = false;
    }

    public boolean endSession() {
        if (this.endDate != null) throw new IllegalStateException("Session already ended");
        this.endDate = ZonedDateTime.now().toLocalDateTime();
        return true;
    }

    public void lock() {
        if (endDate == null) throw new IllegalStateException("Cannot lock session that is stil ongoing");
        isLocked = true;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public boolean isEnded() {
        return endDate != null;
    }

    public boolean isLocked() {
        return isLocked;
    }
}
