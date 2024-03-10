package com.alijanik.notifier.common.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostalCodeExtractor implements ExtractorInterface<String> {

    private static final String POSTAL_REGEX = "\\d{4}\\s*\\w{2}";
    private static final Pattern POSTAL_PATTERN = Pattern.compile(POSTAL_REGEX);

    @Override
    public String getValue(String str) {
        return getPostalCode(str);
    }

    public static String getPostalCode(String postalCode) {
        if (postalCode == null || postalCode.isBlank())
            return null;

        Matcher matcher = POSTAL_PATTERN.matcher(postalCode);
        if (matcher.find()) {
            String result = matcher.group(0);
            return result.replaceAll("\\s*", "");
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getPostalCode("fd3544KPUtrecht (dsafa)"));
        System.out.println(getPostalCode("3544 KP Utrecht (dsafa)"));
    }
}
