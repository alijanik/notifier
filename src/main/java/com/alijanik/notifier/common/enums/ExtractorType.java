package com.alijanik.notifier.common.enums;

import com.alijanik.notifier.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum ExtractorType {

    LINK,
    AREA,
    POSTAL_CODE,
    PRICE,
    OTHER;

    private static final Map<String, ExtractorType> TYPES_MAP = new HashMap<>();

    static {
        for (ExtractorType type : ExtractorType.values()) {
            TYPES_MAP.put(StringUtils.toAlphabetLowercase(type.name()), type);
        }
    }

    public static ExtractorType getInstance(String text) {
        String normalizedName = StringUtils.toAlphabetLowercase(text);
        return TYPES_MAP.getOrDefault(normalizedName, ExtractorType.OTHER);
    }
}
