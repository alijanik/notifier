package com.alijanik.notifier.config;

import java.util.HashSet;
import java.util.Set;

public class ConfigEmail {

    public static final String CONTENT_TYPE = "text/html; charset=utf-8";

    public String subject = "";
    public String content = "";
    public String host = "smtp.gmail.com";
    public int port = 465;
    public String username = "";
    public String password = "";
    public Set<String> to = new HashSet<>();
}
