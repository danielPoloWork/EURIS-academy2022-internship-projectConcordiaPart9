package com.euris.academy2022.concordia.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum TaskStatus {
    TO_DO(
            "TO_DO",
            "6331a6227f9f991a315ca09e",
            "Tasks to do"),
    IN_PROGRESS(
            "IN_PROGRESS",
            "6331c2489132c20205bac3ab",
            "Tasks in progress"),
    COMPLETED(
            "COMPLETED",
            "6331c24bce6fc400d92fe580",
            "Tasks completed");

    private final String label;
    private final String trelloListId;
    private final String trelloListName;
    private static Stream<TaskStatus> stream() {
        return Stream.of(TaskStatus.values());
    }
    public static TaskStatus getEnumByListId(String listId) {
        return TaskStatus.stream()
                .filter(val -> val.getTrelloListId().equals(listId))
                .findFirst().get();
    }
}