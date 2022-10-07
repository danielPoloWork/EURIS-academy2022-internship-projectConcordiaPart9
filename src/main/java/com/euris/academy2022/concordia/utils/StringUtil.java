package com.euris.academy2022.concordia.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static String[] splitFullName(String fullName) {

        // string string string -> string string | string

        String[] nameAndSurname = new String[2];

        String regEx = "(.*)(?:\\s(.*))";

        Pattern pattern = Pattern.compile(regEx);

        Matcher matcher = pattern.matcher(fullName);

        if (matcher.matches()) {
            nameAndSurname[0] = matcher.group(1);
            nameAndSurname[1] = matcher.group(2);
        } else {
            nameAndSurname[0] = fullName;
            nameAndSurname[1] = "----";
        }

        return nameAndSurname;
    }
}
