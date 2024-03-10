package com.alijanik.notifier.config;

import com.alijanik.notifier.common.enums.ElementActionType;
import com.alijanik.notifier.common.enums.ElementByType;
import com.alijanik.notifier.common.logger.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;

public class ConfigSiteActionPart {

    public String name = "";
    public boolean skipWhenNotFound = false;
    public String findElement = "";
    public String by = "";
    public int waitSeconds = 5;
    public String action = "";
    public String value = "";


    @Nullable
    public By getElementBy() {
        final By elementBy;
        final ElementByType type = ElementByType.getInstance(this.by);
        switch (type) {
            case ID -> elementBy = By.id(this.findElement);
            case LINK_TEXT -> elementBy = By.linkText(this.findElement);
            case XPATH -> elementBy = By.xpath(this.findElement);
            default -> {
                Logger.log("By is not found: %s", this.by);
                elementBy = null;
            }
        }
        return elementBy;
    }

    public void doAction(WebElement element, CharSequence... keysToSend) {
        final ElementActionType type = ElementActionType.getInstance(this.action);
        switch (type) {
            case CLICK -> element.click();
            case SUBMIT -> element.submit();
            case FILL -> element.sendKeys(keysToSend);
            default -> Logger.log("Action is not found: %s", this.by);
        }
    }
}