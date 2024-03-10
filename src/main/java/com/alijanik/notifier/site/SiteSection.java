package com.alijanik.notifier.site;


import com.alijanik.notifier.common.models.Pair;

import javax.annotation.Nullable;

public class SiteSection {

    public final String html;
    public final boolean memorize;
    public final String start;
    public final String finish;
    private int finishIndex = 0;

    public SiteSection(String html, boolean memorize, String start, String finish) {
        this.html = html;
        this.start = start;
        this.finish = finish;
        this.memorize = memorize;
    }

    public SiteSection(String html, boolean memorize) {
        this(html, memorize, "START_NOT_FOUND", "FINISH_NOT_FOUND");
    }

    @Nullable
    public String next() {
        return this.next(this.start, this.finish);
    }

    @Nullable
    public String next(String start, String finish) {
        String subHtml = null;
        int fromIndex = this.memorize ? this.finishIndex : 0;
        Pair<String, Integer> htmlIndexPair = nextHtmlIndexPair(this.html, start, finish, fromIndex);
        if (htmlIndexPair != null) {
            subHtml = htmlIndexPair.key;
            if (this.memorize) {
                this.finishIndex = htmlIndexPair.value;
            }
        }
        return subHtml;
    }

    @Nullable
    private static Pair<String, Integer> nextHtmlIndexPair(String html, String start, String finish, int fromIndex) {
        String subHtml = null;
        int finishIndex = -1;
        int startIndex = html.indexOf(start, fromIndex);
        if (startIndex >= 0) {
            finishIndex = html.indexOf(finish, startIndex + start.length());
            if (finishIndex > startIndex) {
                finishIndex += finish.length();
                subHtml = html.substring(startIndex, finishIndex).trim();
            }
        }
        return (subHtml == null) ? null : new Pair<>(subHtml, finishIndex);
    }
}
