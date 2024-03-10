package com.alijanik.notifier.common.enums;

import com.alijanik.notifier.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum ElementActionType {

    CLICK,
    SUBMIT,
    FILL,
    OTHER;

    private static final Map<String, ElementActionType> TYPES_MAP = new HashMap<>();

    static {
        for (ElementActionType type : ElementActionType.values()) {
            TYPES_MAP.put(StringUtils.toAlphabetLowercase(type.name()), type);
        }
    }

    public static ElementActionType getInstance(String text) {
        String normalizedName = StringUtils.toAlphabetLowercase(text);
        return TYPES_MAP.getOrDefault(normalizedName, ElementActionType.OTHER);
    }
}