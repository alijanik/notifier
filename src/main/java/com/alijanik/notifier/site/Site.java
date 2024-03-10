package com.alijanik.notifier.site;

import com.alijanik.notifier.common.downloader.DownloaderInterface;
import com.alijanik.notifier.common.downloader.HttpDownloader;
import com.alijanik.notifier.common.file.ItemFiler;
import com.alijanik.notifier.common.logger.Logger;
import com.alijanik.notifier.config.ConfigSite;
import com.alijanik.notifier.config.ConfigSiteItem;
import com.alijanik.notifier.config.ConfigSiteItemExtract;
import com.alijanik.notifier.common.downloader.WebDownloader;
import com.alijanik.notifier.common.enums.ExtractorType;
import org.jsoup.Jsoup;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Site {

    private final ConfigSite configSite;
    private final ItemFiler itemFiler;
    private DownloaderInterface downloader;
    private Action action;

    private final Set<SiteItem> allItems = new HashSet<>();
    private final List<SiteItem> latestItems = new ArrayList<>();
    private boolean itemsUpdatedAndNotSaved = false;

    public Site(ConfigSite configSite,
                String outputDirectory) {
        this.configSite = configSite;
        this.itemFiler = new ItemFiler(outputDirectory, configSite.name);
        this.initAction();
        this.initDownloader();
        this.loadItems();
    }

    private void initDownloader() {
        this.downloader = (configSite.useDriver) ?
                new WebDownloader(configSite.getFullUrlWithQueryParams()) :
                new HttpDownloader(configSite.getFullUrl(), configSite.queryParams);
    }

    private void initAction() {
        this.action = (this.configSite.action != null) ?
                new Action(this.configSite.action) :
                null;
    }

    private void loadItems() {
        this.allItems.addAll(this.itemFiler.read());
    }

    public Set<SiteItem> getAllItems() {
        return allItems;
    }

    public List<SiteItem> getLatestItems() {
        return latestItems;
    }

    public List<SiteItem> getNewItems() {
        String htmlContent = this.downloader.download();
        Logger.log("Parsing data of %s", this.configSite.url);
        List<SiteItem> items = getSiteItems(htmlContent);
        Logger.log("New item size is: %d", items.size());
        this.saveAllItems();
        this.setLatestItems(items);
        return items;
    }

    private List<SiteItem> getSiteItems(String contentHtml) {
        List<SiteItem> items = new ArrayList<>();
        SiteSection siteSection = new SiteSection(contentHtml, true, this.configSite.start, this.configSite.finish);
        String sectionHtml = siteSection.next();
        while (sectionHtml != null) {
            SiteItem item = getSiteItem(sectionHtml);
            if (!this.allItems.contains(item)) {
                item.fromSite = this.configSite.name;
                items.add(item);
                this.allItems.add(item);
                this.itemsUpdatedAndNotSaved = true;
            }
            sectionHtml = siteSection.next();
        }

        return items;
    }

    private SiteItem getSiteItem(String sectionHtml) {
        SiteItem siteItem = new SiteItem();
        SiteSection siteSection = new SiteSection(sectionHtml, false);
        for (ConfigSiteItem itemConfig : this.configSite.items) {
            String itemHtml = siteSection.next(itemConfig.start, itemConfig.finish);
            String itemValue = getValue(itemHtml);
            if (itemValue != null && !itemValue.isBlank()) {
                siteItem.addOverride(itemConfig.name, itemValue, true);
                extractValues(siteItem, itemHtml, itemConfig);
            }
        }
        siteItem.setUniqueKey(this.configSite.createUniqueKeyFrom);
        return siteItem;
    }

    @Nullable
    private static String getValue(String itemHtml) {
        String value = null;
        if (itemHtml != null && !itemHtml.isBlank()) {
            value = Jsoup.parse(itemHtml).text().trim();
        }
        return value != null && !value.isBlank() ? value : null;
    }

    private void extractValues(SiteItem item, String itemValue, ConfigSiteItem itemConfig) {
        for (ConfigSiteItemExtract extractConfig : itemConfig.extracts) {
            ExtractorType type = extractConfig.getExtractType();
            if (type == ExtractorType.LINK) {
                item.remove(extractConfig.name);
            }
            Object value = extractConfig.extractValue(this.configSite.url, itemValue);
            if (value != null) {
                if (type == ExtractorType.LINK) {
                    item.link = String.valueOf(value);
                } else {
                    item.addOverride(extractConfig.name, String.valueOf(value), extractConfig.show);
                }
            }
        }
    }

    private void saveAllItems() {
        if (this.itemsUpdatedAndNotSaved) {
            this.itemFiler.save(this.allItems);
            this.itemsUpdatedAndNotSaved = true;
        }
    }

    private void setLatestItems(List<SiteItem> items) {
        this.latestItems.clear();
        this.latestItems.addAll(items);
    }

    public void doActionForLatestItems() {
        if (this.action != null) {
            this.doAction(this.getLatestItems());
        }
    }

    private void doAction(List<SiteItem> items) {
        this.action.open();
        for (SiteItem item : items) {
            String url = getUrlFromItem(item);
            if (url != null) {
                item.actionDone = this.action.doAction(url);
            }
        }
        this.action.close();
    }

    private String getUrlFromItem(SiteItem item) {
        String link = item.link;
        if (link == null) {
            String urlFrom = this.configSite.action.urlFrom;
            link = (urlFrom != null) ? item.getValue(urlFrom) : null;
        }
        return link;
    }
}
