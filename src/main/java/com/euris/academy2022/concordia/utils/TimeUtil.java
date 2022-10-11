package com.euris.academy2022.concordia.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class TimeUtil {

    public static boolean isExpiring(LocalDateTime dateTime) {
        Period period = Period.between(LocalDate.now(), LocalDate.from(dateTime));
        return period.getDays() < 5;
    }

    private static String cleanString(String dateTime) {
        return dateTime.replace("Z", "");
    }
    public static LocalDateTime parseDue(String due) {
        return due.equals("null") || due.equals("") ? null : LocalDateTime.parse(cleanString(due)).truncatedTo(ChronoUnit.SECONDS);
    }

    public static LocalDateTime parseToLocalDateTime(String dateTime) {
        return LocalDateTime.parse(cleanString(dateTime)).truncatedTo(ChronoUnit.SECONDS);
    }
}
