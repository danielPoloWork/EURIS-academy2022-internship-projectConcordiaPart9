package com.euris.academy2022.concordia.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static List<String> splitFullName(String fullName) {

        List<String> stringList = new ArrayList<>();

        String regExNew = "(\\S+)";

        Pattern pattern = Pattern.compile(regExNew);
        Matcher matcher = pattern.matcher(fullName);

        while (matcher.find()) {
            stringList.add(matcher.group());
        }

        return stringList;
    }

    public static String listToString(List<String> stringList) {
        StringBuilder result = new StringBuilder();
        for (String str : stringList) {
            result.append(" ").append(str);
        }
        return result.toString().trim();
    }

    public static String[] getNameAndSurname(String fullName) {

        List<String> stringList = splitFullName(fullName);

        String[] group = new String[2];

        switch (stringList.size()) {
            case 0:
                group[0] = "---";
                group[1] = "---";
                return group;
            case 1:
                group[0] = stringList.get(0);
                group[1] = "---";
                return group;
            default:
                group[0] = stringList.get(0);
                stringList.remove(0);
                group[1] = listToString(stringList);
                return group;
        }

    }
}
