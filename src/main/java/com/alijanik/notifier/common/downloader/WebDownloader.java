package com.alijanik.notifier.common.downloader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.alijanik.notifier.common.driver.WebDriverFactory;

import java.time.Duration;

public class WebDownloader implements DownloaderInterface {

    private WebDriver driver;
    private final String url;

    public WebDownloader(String fullUrlWithQueryParams) {
        this.url = fullUrlWithQueryParams;
    }

    private void open() {
        driver = WebDriverFactory.getDriver();
    }

    public void close() {
        try {
            driver.close();
        } catch (Exception ignored) {
        }
    }

    public String download() {
        this.open();
        this.driver.get(url);
        WebDriverWait myWaitVar = new WebDriverWait(this.driver, Duration.ofSeconds(5));
        try {
            myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(By.className("You_can_NOT_find_this:)))")));
        } catch (Exception ignored) {

        }
        String content = this.driver.getPageSource();
        this.close();
        return content;
    }
}
