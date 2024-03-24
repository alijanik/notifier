package com.alijanik.notifier.common.enums;

public enum BrowserType {
  CHROME,
  FIREFOX,
  EDGE;

  public String toString() {
    return switch (this) {
      case CHROME -> "chrome";
      case FIREFOX -> "firefox";
      case EDGE -> "edge";
    };
  }

  public static BrowserType fromString(String browserType) {
    return switch (browserType.toLowerCase()) {
      case "chrome" -> CHROME;
      case "firefox" -> FIREFOX;
      case "edge" -> EDGE;
      default -> throw new IllegalArgumentException("Invalid browser type specified: " + browserType);
    };
  }
}
