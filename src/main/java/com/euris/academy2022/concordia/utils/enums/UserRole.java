package com.euris.academy2022.concordia.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {
  /* Application roles */
  ADMIN("ADMIN","IT Admin"),

  MEMBER_A1("MEMBER_A1","Scientific for astrophysics"),
  MEMBER_A2("MEMBER_A2","Scientific for chemistry and glaciology"),
  MEMBER_A3("MEMBER_A3","Scientific for atmospheric physics"),
  MEMBER_B1("MEMBER_B1","Qualified doctor emergency area"),
  MEMBER_C1("MEMBER_C1","Electronic engineer"),
  MEMBER_C2("MEMBER_C2","ICT/Radio Technician"),

  /* UserDetailsManager.class roles */
  MANAGER("MANAGER","User detail manager");

  private final String label;
  private final String name;
}