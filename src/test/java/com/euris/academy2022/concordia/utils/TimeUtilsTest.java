package com.euris.academy2022.concordia.utils;

import com.euris.academy2022.concordia.utils.TimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class TimeUtilsTest {

    @Test
    void isExpiring() {
        LocalDateTime notExpiringDate = LocalDateTime.now().plusDays(5);

        LocalDateTime expiringDate = LocalDateTime.now();

        Assertions.assertFalse(TimeUtils.isExpiring(notExpiringDate));
        Assertions.assertTrue(TimeUtils.isExpiring(expiringDate));
    }
}