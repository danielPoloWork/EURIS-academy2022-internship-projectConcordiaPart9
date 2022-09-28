package com.euris.academy2022.concordia.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TaskPriority {
  HIGH(
      "HIGH",
      "6331a62237ccc99fc3e4b2ec",
      "High Priority",
      "red"),
  EXPIRING(
      "EXPIRING",
      "6331a62237ccc99fc3e4b2e5",
      "Expiring Priority",
      "orange"),
  MEDIUM(
      "MEDIUM",
      "6331a62237ccc99fc3e4b2e1",
      "Medium Priority",
      "yellow"),
  LOW(
      "LOW",
      "6331a62237ccc99fc3e4b2eb",
      "Low Priority",
      "blue"),
  DONE(
      "DONE",
      "6331a62237ccc99fc3e4b2e3",
      "Done",
      "green"),
  ARCHIVED(
      "ARCHIVED",
      "6331a62237ccc99fc3e4b2ed",
      "Archived",
      "purple");

  private final String label;
  private final String trelloLabelId;
  private final String trelloName;
  private final String trelloColor;
}