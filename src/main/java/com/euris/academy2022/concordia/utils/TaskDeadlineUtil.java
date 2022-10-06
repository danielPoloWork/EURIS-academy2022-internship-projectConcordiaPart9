package com.euris.academy2022.concordia.utils;

import java.time.LocalDateTime;
import java.util.Optional;

public class TaskDeadlineUtil {

    private static String cleanDue(String due) {
        return due.replace("Z", "");
    }

   public static LocalDateTime parseDue(String due) {
        return due.equals("null") ? null : LocalDateTime.parse(cleanDue(due));
   }
}
