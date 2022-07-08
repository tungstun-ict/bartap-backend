package com.tungstun.core.domain.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {
    private Session session;

    @BeforeEach
    void setUp() {
        session = Session.with(123L, "session");
    }

    @Test
    void sessionWithMethod_CreatesNewSession() {
        Long barId = 123L;
        String name = "session1";
        LocalDateTime now = ZonedDateTime.now().toLocalDateTime();

        Session session = Session.with(barId, name);

        assertEquals(barId, session.getBarId());
        assertEquals(name, session.getName());
        assertFalse(session.isEnded());
        assertFalse(session.isLocked());
        assertTrue(session.getBillIds().isEmpty());
        assertEquals(now.truncatedTo(ChronoUnit.SECONDS),
                session.getCreationDate().truncatedTo(ChronoUnit.SECONDS));
    }

    @SuppressWarnings("java:S2925")
    @Test
    void endSession_Successfully() throws InterruptedException {
        Thread.sleep(1); // Added to make sure session is not ended in the same millisecond as it is created
        session.endSession();

        assertNotNull(session.getEndDate());
        assertTrue(session.isEnded());
        assertTrue(session.getCreationDate().isBefore(session.getEndDate()));
    }

    @Test
    void endSession_WhenAlreadyEndedThrows() {
        session.endSession();

        assertThrows(
                IllegalStateException.class,
                () -> session.endSession()
        );
    }

    @Test
    void lockSession_Successfully() {
        session.endSession();

        session.lock();

        assertTrue(session.isLocked());
    }

    @Test
    void lockSession_WhenNotLockedThrows() {
        assertThrows(
                IllegalStateException.class,
                () -> session.lock()
        );
    }

    @Test
    void lockSession_WhenAlreadyLockedThrows() {
        session.endSession();
        session.lock();

        assertThrows(
                IllegalStateException.class,
                () -> session.lock()
        );
    }

    @Test
    void addBillWhenSessionIsActive_Successfully() {
        boolean isSuccess = session.addBill(123L);

        assertTrue(isSuccess);
    }

    @Test
    void addBillWhenSessionHasEnded_Successfully() {
        session.endSession();

        boolean isSuccess = session.addBill(123L);

        assertTrue(isSuccess);
    }

    @Test
    void addBillWhenSessionIsLocked_ThrowsIllegalStateException() {
        session.endSession();
        session.lock();

        assertThrows(
                IllegalStateException.class,
                () -> session.addBill(123L)
        );
    }

    @Test
    void removeBillWhenSessionIsActive_Successfully() {
        session.addBill(123L);

        boolean isSuccess = session.removeBill(123L);

        assertTrue(isSuccess);
    }

    @Test
    void removeBillWhenSessionHasEnded_Successfully() {
        session.addBill(123L);
        session.endSession();

        boolean isSuccess = session.removeBill(123L);

        assertTrue(isSuccess);
    }

    @Test
    void removeBillWhenSessionIsLocked_ThrowsIllegalStateException() {
        session.addBill(123L);
        session.endSession();
        session.lock();

        assertThrows(
                IllegalStateException.class,
                () -> session.removeBill(123L)
        );
    }

    @Test
    void changingGetBillIds_DoesNotChangeSessionInternalListOfBills() {
        session.addBill(123L);

        session.getBillIds().remove(123L);

        assertTrue(session.getBillIds().contains(123L));
    }
}
