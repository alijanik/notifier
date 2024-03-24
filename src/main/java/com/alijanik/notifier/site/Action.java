package com.alijanik.notifier.site;

import com.alijanik.notifier.config.ConfigSiteAction;
import com.alijanik.notifier.config.ConfigSiteActionPart;
import com.alijanik.notifier.common.driver.WebDriverFactory;
import com.alijanik.notifier.common.logger.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Action {

    private WebDriver driver;
    private final ConfigSiteAction action;

    public Action(ConfigSiteAction action) {
        this.action = action;
    }

    public void open() {
        this.driver = WebDriverFactory.getDriver();
    }

    public void close() {
        try {
            driver.close();
        } catch (Exception ignored) {
        }
    }

    public boolean doAction(String url) {
        Logger.log("Going to apply actions for %s...", url);
        driver.get(url);

        this.waitFor();
        boolean finalActionDone = false;
        if (this.doActions(this.action.startWith)) {
            boolean mainActions = this.doActions(this.action.thenDo);
            if (!mainActions) {
                mainActions = this.doActions(this.action.elseDo);
            }
            if (mainActions) {
                finalActionDone = this.doActions(this.action.finishWith);
            }
        }
        Logger.log("Action status for %s was: %s", url, finalActionDone);
        return finalActionDone;
    }

    private void waitFor() {
        if (this.action.waitFor != null) {
            this.waitFor(this.action.waitFor);
        }
    }

    private void waitFor(ConfigSiteActionPart waitFor) {
        try {
            WebDriverWait myWaitVar = new WebDriverWait(this.driver, Duration.ofSeconds(waitFor.waitSeconds));
            myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(waitFor.getElementBy()));
        } catch (Exception ignored) {
            Logger.log("We couldn't wait for %s", waitFor.name);
        }
    }

    private boolean doActions(List<ConfigSiteActionPart> parts) {
        boolean allDone = true;
        for (ConfigSiteActionPart part : parts) {
            if (!doAction(part) && !part.skipWhenNotFound) {
                allDone = false;
                break;
            }
        }
        return allDone;
    }

    private boolean doAction(ConfigSiteActionPart part) {
        try {
            WebElement webElement = driver.findElement(part.getElementBy());
            part.doAction(webElement, part.value);
            Thread.sleep(1000L * part.waitSeconds);
            return true;
        } catch (Exception ignored) {
            Logger.log("We couldn't do action for %s", part.findElement);
            return false;
        }
    }

}
