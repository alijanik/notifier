package com.alijanik.notifier.config;

import com.alijanik.notifier.common.enums.BrowserType;
import com.alijanik.notifier.common.logger.Logger;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Config {
    public ConfigTime timer = new ConfigTime();
    public String outputDirectory = null;
    public String webDriverType = null;
    public Map<String, String> webDrivers = null;
    public BrowserType browserType = null;
    public String webDriverAddress = null;
    public ConfigEmail email = new ConfigEmail();
    public List<ConfigSite> sites = new ArrayList<>();

    public static Config getInstance(String address) throws IOException {
        if (address == null || address.isBlank()) {
            address = "properties.json";
        }
        String json = Files.readString(Paths.get(address));
        Config config = new Gson().fromJson(json, Config.class);
        config.prepareForWebDriver();
        Logger.log("Config form <%s> loaded :)", address);
        return config;
    }

    public void prepareForWebDriver() {
        System.setProperty("browserType", browserType.toString());
        for (Map.Entry<String, String> entry : webDrivers.entrySet()) {
            System.setProperty(entry.getKey(), entry.getValue());
        }
    }

    public static void main(String[] args) throws IOException {
        Config config = getInstance(null);
        System.out.println(config.sites.size());
    }
}
