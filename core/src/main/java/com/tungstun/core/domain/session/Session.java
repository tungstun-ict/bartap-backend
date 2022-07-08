package com.tungstun.core.domain.session;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "bar_id")
    private Long barId;

    @Column(name = "name")
    private String name;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "locked", nullable = false)
    private boolean isLocked;

    @ElementCollection
    @OrderColumn(name = "bill_index")
    private List<Long> billIds;

    public static Session with(Long barId, String name) {
        return new Session(barId, name, ZonedDateTime.now().toLocalDateTime(), new ArrayList<>());
    }

    public Session() {
    }

    private Session(Long barId, String name, LocalDateTime creationDate, List<Long> billIds) {
        this.barId = barId;
        this.name = name;
        this.creationDate = creationDate;
        this.isLocked = false;
        this.billIds = billIds;
    }

    public boolean endSession() {
        if (this.endDate != null) throw new IllegalStateException("Session already ended");
        this.endDate = ZonedDateTime.now().toLocalDateTime();
        return true;
    }

    public void lock() {
        if (endDate == null || isLocked)
            throw new IllegalStateException("Cannot lock session that is stil ongoing or is already locked");
        isLocked = true;
    }

    public boolean addBill(Long billId) {
        if (isLocked) throw new IllegalStateException("Cannot add bills when session is already locked");
        return billIds.add(billId);
    }

    public boolean removeBill(Long billId) {
        if (isLocked) throw new IllegalStateException("Cannot add bills when session is already locked");
        return billIds.remove(billId);
    }

    public List<Long> getBillIds() {
        return new ArrayList<>(billIds);
    }

    public boolean isEnded() {
        return endDate != null;
    }

    public boolean isLocked() {
        return isLocked;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setName(String name) {
        this.name = name;
    }

}
