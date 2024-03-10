package com.alijanik.notifier.common.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceExtractor implements ExtractorInterface<Integer> {

    private static  final  String PRICE_REGEX = "[\\d,.]+";
    private static  final  Pattern PRICE_PATTERN = Pattern.compile(PRICE_REGEX);

    @Override
    public Integer getValue(String str) {
        return getPrice(str);
    }

    public static int getPrice(String price) {
        if (price == null || price.isBlank())
            return -1;

        Matcher matcher = PRICE_PATTERN.matcher(price);
        if (matcher.find()) {
            String result = matcher.group(0).replaceAll("[,.]", "");
            return Integer.parseInt(result);
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(getPrice("€sds 1.134 /maand."));
        System.out.println(getPrice("€sdf1,495 per month"));
    }
}
