package com.alijanik.notifier.site;


import javax.annotation.Nullable;
import java.util.*;

public class SiteItem {

    public String uniqueKey = UUID.randomUUID().toString();
    public String fromSite = null;
    public String duplicateOfSite = null;
    public boolean actionDone = false;
    public String link = null;

    public final Map<String, String> itemMap = new HashMap<>();
    public final List<String> orders = new ArrayList<>();

    public void addOverride(String key, String value, boolean show) {
        if (!itemMap.containsKey(key)) {
            if (show) {
                orders.add(key);
            }
        }
        itemMap.put(key, value);
    }

    public void remove(String key) {
        if(itemMap.remove(key) != null) {
            orders.remove(key);
        }
    }

    @Nullable
    public String getValue(String key) {
        key = key.replaceAll("items.", "");
        return itemMap.get(key);
    }

    public boolean isActionDone() {
        return actionDone;
    }

    public void setUniqueKey(List<String> keys) {
        StringBuilder htmlString = new StringBuilder();
        for (String key : keys) {
            String exist = getValue(key);
            if (exist != null) {
                if (!htmlString.isEmpty()) {
                    htmlString.append('#');
                }
                htmlString.append(key).append(':').append(exist);
            }
        }
        if (!htmlString.isEmpty()) {
            this.uniqueKey = htmlString.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder htmlString = new StringBuilder("<table>\n");
        htmlString.append(getRow("<tr><td>ActionDone</td><td>: %s</td></tr>", actionDone ? "Yes": "No"));
        if (fromSite != null && !fromSite.isBlank())
            htmlString.append(getRow("<tr><td>From</td><td>: %s</td></tr>", fromSite));
        if (duplicateOfSite != null && !duplicateOfSite.isBlank())
            htmlString.append(getRow("<tr><td>ExistOn</td><td>: %s</td></tr>", duplicateOfSite));
//        if (isRotsvast())
//            table.append("<tr><td>WARN</td><td>: DO NOT PLAN -> Rotsvast</td></tr>");
        if (link != null && !link.isBlank())
            htmlString.append(getRow("<tr><td>Link</td><td><a href=\"%s\">: Click to Open</a></td></tr>", link));
        for (String order : this.orders) {
            htmlString.append(getRow("<tr><td>%s</td><td>: %s</td></tr>", order, itemMap.get(order)));
        }
        htmlString.append("</table>");
        return htmlString.toString();
    }

    private String getRow(String format, Object ... args) {
        return String.format("\t" + format + "\n", args);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SiteItem siteItem = (SiteItem) o;
        return Objects.equals(itemMap, siteItem.itemMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemMap);
    }
}