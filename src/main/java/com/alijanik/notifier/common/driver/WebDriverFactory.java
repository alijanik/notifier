package com.alijanik.notifier.common.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.alijanik.notifier.common.enums.BrowserType;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class WebDriverFactory {

  public static WebDriver getDriver() {
    WebDriver driver;
    BrowserType browserType = BrowserType.fromString(System.getProperty("browserType", "chrome"));

    switch (browserType) {
      case CHROME:
        ChromeOptions chromeOptions = new ChromeOptions();
        driver = new ChromeDriver(chromeOptions);
        break;
      case FIREFOX:
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        driver = new FirefoxDriver(firefoxOptions);
        break;
      // Add cases for other browsers as needed
      case EDGE: // Fall through to default case
        EdgeOptions edgeOptions = new EdgeOptions();
        driver = new EdgeDriver(edgeOptions);
        break;
      default:
        throw new IllegalArgumentException("Invalid browser type specified: " + browserType);
    }

    return driver;
  }
}
