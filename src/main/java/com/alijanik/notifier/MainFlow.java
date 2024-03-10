package com.alijanik.notifier;

import com.alijanik.notifier.config.Config;
import com.alijanik.notifier.config.ConfigSite;
import com.alijanik.notifier.common.logger.Logger;
import com.alijanik.notifier.common.timer.Sleeper;
import com.alijanik.notifier.common.timer.TimeFormatter;
import com.alijanik.notifier.email.ItemsSender;
import com.alijanik.notifier.site.Site;
import com.alijanik.notifier.site.SiteItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainFlow {

    private final ItemsSender itemsSender;
    private final Sleeper sleeper;
    private final List<Site> sites;

    public MainFlow(Config config) {
        this.itemsSender = ItemsSender.getInstance(config.email);
        this.sleeper = Sleeper.getInstance(config.timer);
        this.sites = getSites(config);
    }

    private static List<Site> getSites(Config config) {
        final List<Site> sites = new ArrayList<>();
        for (ConfigSite configSite : config.sites) {
            Site site = new Site(configSite, config.outputDirectory);
            sites.add(site);
        }
        return sites;
    }

    public void runForever() throws InterruptedException {
        Sleeper.sleepTilNearestMinute();

        while (true) {
            Logger.log("Time: %s", TimeFormatter.getTimeString());
            List<SiteItem> newItemsFromAllSites = this.getNewItemsOfAllSites();
            if (!newItemsFromAllSites.isEmpty()) {
                detectDuplicateItems();
                sendNewItems(newItemsFromAllSites);
                doActionForNewItems();
            }
            sleeper.sleepIntelligent();
        }
    }

    private List<SiteItem> getNewItemsOfAllSites() {
        final List<SiteItem> newItems = new ArrayList<>();
        for (Site site : this.sites) {
            newItems.addAll(site.getNewItems());
        }
        return newItems;
    }

    private List<SiteItem> getAllItemsOfAllSites() {
        final List<SiteItem> allItems = new ArrayList<>();
        for (Site site : this.sites) {
            allItems.addAll(site.getAllItems());
        }
        return allItems;
    }

    private void detectDuplicateItems() {
        final List<SiteItem> allItems = getAllItemsOfAllSites();
        final Map<String, SiteItem> existence = new HashMap<>();
        for (SiteItem item : allItems) {
            SiteItem exist = existence.get(item.uniqueKey);
            if (exist == null) {
                existence.put(item.uniqueKey, item);
            } else {
                makeEachOtherDuplication(item, exist);
            }
        }
    }

    private void makeEachOtherDuplication(SiteItem firstItem, SiteItem secondItem) {
        firstItem.duplicateOfSite = secondItem.fromSite;
        secondItem.duplicateOfSite = firstItem.fromSite;
        Logger.log("Duplicate items found for %s: %s, %s", firstItem.uniqueKey, firstItem.fromSite, secondItem.fromSite);
    }

    private void sendNewItems(final List<SiteItem> newItems) {
        itemsSender.send(newItems);
    }

    private void doActionForNewItems() {
        for (Site site : this.sites) {
            site.doActionForLatestItems();
        }
    }
}
