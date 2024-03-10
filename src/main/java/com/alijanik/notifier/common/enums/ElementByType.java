package com.alijanik.notifier.common.enums;

import com.alijanik.notifier.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum ElementByType {

    ID,
    LINK_TEXT,
    XPATH,
    OTHER;

    private static final Map<String, ElementByType> TYPES_MAP = new HashMap<>();

    static {
        for (ElementByType type : ElementByType.values()) {
            TYPES_MAP.put(StringUtils.toAlphabetLowercase(type.name()), type);
        }
    }

    public static ElementByType getInstance(String text) {
        String normalizedName = StringUtils.toAlphabetLowercase(text);
        return TYPES_MAP.getOrDefault(normalizedName, ElementByType.OTHER);
    }
}