package com.alijanik.notifier.common.downloader;

import com.alijanik.notifier.common.logger.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpDownloader implements DownloaderInterface {

    private final String url;
    private final Map<String, String> queryParams = new HashMap<>();

    public HttpDownloader(String url, @Nullable Map<String, String> queryParams) {
        this.url = url;
        if (queryParams != null) {
            this.queryParams.putAll(queryParams);
        }
    }

    public String download() {
        try {
            Logger.log("Downloading: %s", this.url);
            Connection connection = Jsoup
                    .connect(this.url)
                    .method(Connection.Method.GET)
                    .header("User-Agent", USER_AGENT)
                    .postDataCharset("UTF-8");
            for (Map.Entry<String, String> keyValue: this.queryParams.entrySet()) {
                connection = connection.data(keyValue.getKey(), keyValue.getValue());
            }
            Connection.Response response = connection.execute();
            return response.body();
        } catch (IOException e) {
            Logger.log("IOException @getUrl(%s): %s", this.url, e.getMessage());
        }
        return null;
    }
}
