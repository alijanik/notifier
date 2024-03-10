package com.alijanik.notifier;

import com.alijanik.notifier.config.Config;

import java.io.IOException;

public class Run {

    public static void main(String[] args) throws InterruptedException, IOException {
        Config config = Config.getInstance(null);
        new MainFlow(config).runForever();
    }
}
