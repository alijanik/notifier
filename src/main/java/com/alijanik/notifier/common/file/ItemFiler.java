package com.alijanik.notifier.common.file;

import com.alijanik.notifier.common.logger.Logger;
import com.alijanik.notifier.site.SiteItem;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class ItemFiler {

    private static final Gson gson = new Gson();
    private static final Type listType = new TypeToken<Set<SiteItem>>() {
    }.getType();

    private final String fileName;
    private final Filer filer;


    public ItemFiler(String outputDirectory, String fileName) {
        this.fileName = fileName;
        String fileNameWithExtension = String.format("%s.json", fileName);
        this.filer = new Filer(outputDirectory, fileNameWithExtension);
    }

    public Set<SiteItem> read() {
        final Set<SiteItem> items = new HashSet<>();
        final String json = this.filer.read();
        if (json != null) {
            Set<SiteItem> data = gson.fromJson(json, listType);
            Logger.log("%d data loaded for %s", data.size(), this.fileName);
            items.addAll(data);
        }
        return items;
    }


    public boolean save(Set<SiteItem> data) {
        String json = gson.toJson(data);
        boolean saved = this.filer.write(json);
        if (saved) {
            Logger.log("%d data saved for %s", data.size(), this.fileName);
        }
        return saved;
    }
}
