package com.euris.academy2022.concordia.utils.prints;

import java.util.Date;

public class BatchProcessingPrint {
    public static void printFetchExpiringTaskStart(String threadName) {
        System.out.println("       ───────────────────────────────────────────────────");
        System.out.println("       :: Concordia Scheduling ::         (FETCH EXPIRING)\n");

        System.out.printf("%s  [fetchExpiring ] executed at %s  INFO : STARTED..\n",
                threadName,
                new Date());
    }
    public static void printFetchExpiringTaskEnd(String threadName) {
        System.out.printf("%s  [fetchExpiring ] executed at %s  INFO : FINISHED\n",
                threadName,
                new Date());
    }
    public static void printFetchTrelloStart(String threadName) {
        System.out.println("       ───────────────────────────────────────────────────");
        System.out.println("       :: Concordia Scheduling ::           (FETCH TRELLO)\n");

        System.out.printf("%s  [fetchTrello   ] executed at %s  INFO : STARTED..\n",
                threadName,
                new Date());
    }
    public static void printFetchTrelloEnd(String threadName) {
        System.out.printf("%s  [fetchTrello   ] executed at %s  INFO : FINISHED\n",
                threadName,
                new Date());
    }
    public static void printFetchConcordiaStart(String threadName) {
        System.out.println("       ───────────────────────────────────────────────────");
        System.out.println("       :: Concordia Scheduling ::        (FETCH CONCORDIA)\n");

        System.out.printf("%s  [fetchConcordia] executed at %s  INFO : STARTED..\n",
                threadName,
                new Date());
    }
    public static void printFetchConcordiaEnd(String threadName) {
        System.out.printf("%s  [fetchConcordia] executed at %s  INFO : FINISHED\n",
                threadName,
                new Date());
    }
}
