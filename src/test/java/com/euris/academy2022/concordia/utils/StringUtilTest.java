package com.euris.academy2022.concordia.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void splitString() {
        String input = "string1 string2  string3";

        List<String> result = List.of("string1", "string2", "string3");

        assertEquals(result.toString(), StringUtil.splitString(input).toString());
    }

    @Test
    void listToString() {
        String str1 = "";
        String str2 = "string1";
        String str3 = "string1 string2";

        List<String> list1 = new ArrayList<>();
        List<String> list2 = List.of("string1");
        List<String> list3 = List.of("string1", "string2");

        assertEquals(str1, StringUtil.listToString(list1));
        assertEquals(str2, StringUtil.listToString(list2));
        assertEquals(str3, StringUtil.listToString(list3));
    }

    @Test
    void getNameAndSurname() {
        String[] case0 = StringUtil.getNameAndSurname(" ");
        String[] case1 = StringUtil.getNameAndSurname(" string ");
        String[] caseDefault = StringUtil.getNameAndSurname(" string1 string2 ");

        assertEquals("---", case0[0]);
        assertEquals("---", case0[1]);
        assertEquals("string", case1[0]);
        assertEquals("---", case1[1]);
        assertEquals("string1", caseDefault[0]);
        assertEquals("string2", caseDefault[1]);
    }
}