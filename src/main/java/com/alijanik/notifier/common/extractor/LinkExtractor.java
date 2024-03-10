package com.alijanik.notifier.common.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class LinkExtractor implements ExtractorInterface<String> {

    private final String baseUrl;

    public LinkExtractor(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String getValue(String html) {
        return getLink(html, this.baseUrl);
    }

    public static String getLink(String html, String baseUrl) {
        if (html == null || html.isBlank())
            return null;

        Document document = Jsoup.parse(html, baseUrl);
        Elements links = document.select("a[href]");
        String link = null;
        if (!links.isEmpty()) {
            link = links.first().attr("abs:href").trim();
        }
        return link != null && !link.isBlank() && link.startsWith(baseUrl) ? link : null;
    }
}
