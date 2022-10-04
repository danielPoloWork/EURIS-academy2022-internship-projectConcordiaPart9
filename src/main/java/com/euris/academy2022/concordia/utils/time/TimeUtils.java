package com.euris.academy2022.concordia.utils.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class TimeUtils {

    public static boolean isExpiring(LocalDateTime dateTime) {
        Period period = Period.between(LocalDate.now(), LocalDate.from(dateTime));
        return period.getDays() < 5;
    }
}
