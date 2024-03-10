package com.alijanik.notifier.email;

import java.util.*;

public class EmailContent {

    private String subject;
    private String contentType;
    private String content;
    private String from;
    private final Set<String> to = new HashSet<>();;

    public EmailContent(String subject,
                        String content,
                        String from,
                        String to,
                        String contentType) {
        this.subject = subject;
        this.content = content;
        this.from = from;
        this.addTo(to);
        this.setContentType(contentType);
    }

    public EmailContent(String subject,
                        String content,
                        String from,
                        Set<String> to,
                        String contentType) {
        this(subject, content, from, (String) null, contentType);
        if (to != null && !to.isEmpty()) {
            to.forEach(this::addTo);
        }
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = (contentType != null && !contentType.isBlank()) ? contentType : "text/plain; charset=utf-8";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Set<String> getTo() {
        return to;
    }

    public void clearTo() {
        this.to.clear();
    }

    public boolean addTo(String to) {
        if (to == null || to.isBlank())
            return false;

        this.to.add(to);
        return true;
    }

    public String getToString() {
        return String.join(", ", this.to);
    }
}
