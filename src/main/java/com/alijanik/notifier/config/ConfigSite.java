package com.alijanik.notifier.config;

import com.alijanik.notifier.common.utils.StringUtils;

import javax.annotation.Nullable;
import java.util.*;

public class ConfigSite {

    public String name = "";
    public boolean useDriver = false;

    public String url = "";
    public String path = null;
    public Map<String, String> queryParams = null;
    public String start = "";
    public String finish = "";
    public List<String> createUniqueKeyFrom = new ArrayList<>();
    public String doActionIf = null;
    public Set<ConfigSiteItem> items = new HashSet<>();
    public ConfigSiteAction action = null;

    public String getFullUrl() {
        String fullUrl = StringUtils.dropLastChar(this.url, '/');
        if (this.path != null && !this.path.isBlank()) {
            String urlPath = StringUtils.dropLastChar(this.path, '/');
            fullUrl += urlPath;
        }
        return fullUrl;
    }

    public String getFullUrlWithQueryParams() {
        String fullUrl = getFullUrl();
        String queryParamsString = getQueryParamsString();
        if (queryParamsString != null && !queryParamsString.isEmpty()) {
            fullUrl += "?" + queryParamsString;
        }
        return fullUrl;
    }

    @Nullable
    public String getQueryParamsString() {
        StringBuilder queryParamsString = new StringBuilder();
        if (this.queryParams != null && !this.queryParams.isEmpty()) {
            for (Map.Entry<String, String> query: this.queryParams.entrySet()) {
                if (!queryParamsString.isEmpty()) {
                    queryParamsString.append('&');
                }
                queryParamsString.append(query.getKey()).append('=').append(query.getValue()); // todo: encode value, handle set!
            }
        }
        return queryParamsString.isEmpty() ? null : queryParamsString.toString();
    }
}
