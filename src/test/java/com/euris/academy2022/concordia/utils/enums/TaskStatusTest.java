package com.euris.academy2022.concordia.utils.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class TaskStatusTest {

  @Test
  void getEnumByListIdTest_Succeed() {

    String[] listIdTest = {"6331a6227f9f991a315ca09e", "6331c2489132c20205bac3ab",
        "6331c24bce6fc400d92fe580"};

    assertEquals(TaskStatus.TO_DO, TaskStatus.getEnumByListId(listIdTest[0]));
    assertEquals(TaskStatus.IN_PROGRESS, TaskStatus.getEnumByListId(listIdTest[1]));
    assertEquals(TaskStatus.COMPLETED, TaskStatus.getEnumByListId(listIdTest[2]));

  }

  @Test
  void getEnumByListIdTest_Not_Found() {

    String listIdTest = "viausdhbfliagbogrfi";

    NoSuchElementException e = assertThrows(NoSuchElementException.class,
        () -> TaskPriority.getEnumByLabelId(listIdTest));

    assertEquals("No value present", e.getMessage());
  }
}
