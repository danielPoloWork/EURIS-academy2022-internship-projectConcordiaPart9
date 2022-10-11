package com.euris.academy2022.concordia.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class TimeUtilTest {

    @Test
    void isExpiring() {
        LocalDateTime notExpiringDate = LocalDateTime.now().plusDays(5);

        LocalDateTime expiringDate = LocalDateTime.now();

        Assertions.assertFalse(TimeUtil.isExpiring(notExpiringDate));
        Assertions.assertTrue(TimeUtil.isExpiring(expiringDate));
    }
}