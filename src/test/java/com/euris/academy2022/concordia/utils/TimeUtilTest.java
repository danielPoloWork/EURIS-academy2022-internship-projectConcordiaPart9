package com.euris.academy2022.concordia.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class TimeUtilTest {

    @Test
    void isExpiring() {

        LocalDateTime notExpiringDate = LocalDateTime.now().plusDays(5);
        LocalDateTime expiringDate = LocalDateTime.now();

        assertFalse(TimeUtil.isExpiring(notExpiringDate));
        assertTrue(TimeUtil.isExpiring(expiringDate));
    }

    @Test
    void parseDueTest() {
        String date1 = "";
        String date2 = "null";
        String date3 = LocalDateTime.now() + "Z";

        LocalDateTime expectedDate3 = LocalDateTime.now().plusHours(2).truncatedTo(ChronoUnit.SECONDS);

        assertNull(TimeUtil.parseDue(date1));
        assertNull(TimeUtil.parseDue(date2));
        assertEquals(expectedDate3, TimeUtil.parseDue(date3));
    }
}