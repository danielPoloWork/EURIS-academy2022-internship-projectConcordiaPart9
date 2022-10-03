package com.euris.academy2022.concordia.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberRole {
    /* Researcher roles */
    A1("A1", "Scientific for astrophysics"),
    A2("A2", "Scientific for chemistry and glaciology"),
    A3("A3", "Scientific for atmospheric physics"),
    B1("B1", "Qualified doctor emergency area"),
    C1("C1", "Electronic engineer"),
    C2("C2", "ICT/Radio Technician"),

    /* Application roles */
    ADMIN("ADMIN", "IT Admin"),

    /* UserDetailsManager roles */
    MANAGER("MANAGER", "User detail manager");

    private final String label;
    private final String name;
}