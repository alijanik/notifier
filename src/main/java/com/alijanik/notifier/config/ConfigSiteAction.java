package com.alijanik.notifier.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigSiteAction {

    public String urlFrom = "items.link";
    public ConfigSiteActionPart waitFor = null;
    public List<ConfigSiteActionPart> startWith = new ArrayList<>();
    public List<ConfigSiteActionPart> thenDo = new ArrayList<>();
    public List<ConfigSiteActionPart> elseDo = new ArrayList<>();
    public List<ConfigSiteActionPart> finishWith = new ArrayList<>();
}
