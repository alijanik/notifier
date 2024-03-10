package com.alijanik.notifier.email;


import com.alijanik.notifier.common.timer.TimeFormatter;
import com.alijanik.notifier.config.ConfigEmail;
import com.alijanik.notifier.site.SiteItem;

import java.util.Comparator;
import java.util.List;

public class ItemsSender {

    private final EmailSenderr emailSender;
    private final ConfigEmail configEmail;

    public static ItemsSender getInstance(ConfigEmail configEmail) {
        return new ItemsSender(configEmail);
    }

    private ItemsSender(ConfigEmail configEmail) {
        this.configEmail = configEmail;
        this.emailSender = new EmailSenderr(configEmail);
    }

    public void send(List<SiteItem> items) {
        List<SiteItem> doneFirst = items.stream()
                .sorted(Comparator.comparing(SiteItem::isActionDone))
                .toList();

        String emailTitle = String.format("%s: %s", this.configEmail.subject, TimeFormatter.getTimeString());
        String contentHeadline = String.format("<h4>%s:</h4><br>", this.configEmail.content);
        StringBuilder content = new StringBuilder(contentHeadline);
        for (SiteItem item : doneFirst) {
            content.append(item).append("<br><br>");
        }
        EmailContent emailContent = new EmailContent(
                emailTitle,
                content.toString(),
                this.configEmail.username,
                this.configEmail.to,
                ConfigEmail.CONTENT_TYPE
        );
        this.emailSender.send(emailContent);
    }
}
