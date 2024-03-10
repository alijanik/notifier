package com.alijanik.notifier.common.timer;

import com.alijanik.notifier.common.logger.Logger;
import com.alijanik.notifier.config.ConfigTime;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Sleeper {

    private final ConfigTime configTime;

    public static Sleeper getInstance(ConfigTime configTime) {
        return new Sleeper(configTime);
    }

    private Sleeper(ConfigTime configTime) {
        this.configTime = configTime;
    }

    public static void sleepTilNearestMinute() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMinute = now.withNano(0)
                .withSecond(0).plusMinutes(1);
        long difference = getTimeDifferenceInMillis(now, nextMinute);
        Logger.log("Sleeping for %d (ms) ...", difference);
        Thread.sleep(difference);
    }

    public static void sleepTilNearestFiveMinutes() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        int nextFive = (65 - now.getMinute()) % 5;
        if (nextFive == 0) nextFive = 5;
        LocalDateTime nearestFiveMinute = now.withSecond(0)
                .withNano(0).plusMinutes(nextFive);
        long difference = getTimeDifferenceInMillis(now, nearestFiveMinute);
        Logger.log("Sleeping for %d (ms) ...", difference);
        Thread.sleep(difference);
    }

    private static void sleepTilNearestHour() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nearestHour = now.withNano(0)
                .withSecond(0).withMinute(0).plusHours(1);
        long difference = getTimeDifferenceInMillis(now, nearestHour);
        Logger.log("Sleeping for %d (ms-hour) ...", difference);
        Thread.sleep(difference);
    }

    private static long getTimeDifferenceInMillis(LocalDateTime past, LocalDateTime future) {
        return ChronoUnit.MILLIS.between(past, future);
    }

    private boolean mustBeSlower() {
        int currentHour = TimeFormatter.getCurrentHour();
        return (currentHour >= this.configTime.slowerFromHour || currentHour <= this.configTime.slowerToHour);
    }

    private boolean sleepingOver(long differenceInMills) {
        int differenceInSeconds = (int) (differenceInMills / 1000);
        if (mustBeSlower()) {
            return differenceInSeconds >= this.configTime.runSlowerEveryXSeconds;
        } else {
            return differenceInSeconds >= this.configTime.runEveryXSeconds;
        }
    }

    public void sleepIntelligent() throws InterruptedException {
        final LocalDateTime start = LocalDateTime.now();
        long differenceInMills;
        do {
            sleepTilNearestMinute();
            differenceInMills = getTimeDifferenceInMillis(start, LocalDateTime.now());
        } while (!sleepingOver(differenceInMills));
    }
}
