package com.euris.academy2022.concordia.utils.enums;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class TaskPriorityTest {

  @Test
  void getEnumByLabelIdTest_Succeed() {

    String[] idTaskPriorityTest = {"6331a62237ccc99fc3e4b2ec", "6331a62237ccc99fc3e4b2e5", "6331a62237ccc99fc3e4b2e1", "6331a62237ccc99fc3e4b2eb", "6331a62237ccc99fc3e4b2e3", "6331a62237ccc99fc3e4b2ed"};

    assertEquals(TaskPriority.HIGH, TaskPriority.getEnumByLabelId(idTaskPriorityTest[0]));
    assertEquals(TaskPriority.EXPIRING, TaskPriority.getEnumByLabelId(idTaskPriorityTest[1]));
    assertEquals(TaskPriority.MEDIUM, TaskPriority.getEnumByLabelId(idTaskPriorityTest[2]));
    assertEquals(TaskPriority.LOW, TaskPriority.getEnumByLabelId(idTaskPriorityTest[3]));
    assertEquals(TaskPriority.DONE, TaskPriority.getEnumByLabelId(idTaskPriorityTest[4]));
    assertEquals(TaskPriority.ARCHIVED, TaskPriority.getEnumByLabelId(idTaskPriorityTest[5]));

  }

  @Test
  void getEnumByLabelIdTest_Not_Found() {

    String idTaskPriorityTest = "ssaiuhggvpasu235as";
    
    NoSuchElementException e = assertThrows(NoSuchElementException.class,
        () -> TaskPriority.getEnumByLabelId(idTaskPriorityTest));

    assertEquals("No value present", e.getMessage());

  }
}