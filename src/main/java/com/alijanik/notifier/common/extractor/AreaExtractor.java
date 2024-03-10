package com.alijanik.notifier.common.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AreaExtractor implements ExtractorInterface<Double> {

    private static  final  String AREA_REGEX = "(\\d+(?:\\.\\d+)?)\\s*m\\s*[²|2]";
    private static  final  Pattern AREA_PATTERN = Pattern.compile(AREA_REGEX);

    @Override
    public Double getValue(String str) {
        return getArea(str);
    }

    public static double getArea(String areaStr) {
        if (areaStr == null || areaStr.isBlank())
            return -1.0;

        Matcher matcher = AREA_PATTERN.matcher(areaStr);
        if (matcher.find()) {
            String resultStr = matcher.group(1);
            return Double.parseDouble(resultStr);
        }
        return -1.0;
    }

    public static void main(String[] args) {
        System.out.println(getArea("Fully furnished 21.5 m2 Single Indefinite Studio 4th floor"));
        System.out.println(getArea("75m² 2 B"));
        System.out.println(getArea("60 m² 2 rooms Furnished"));
    }
}
