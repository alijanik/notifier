package com.alijanik.notifier.config;

import com.alijanik.notifier.common.extractor.*;
import com.alijanik.notifier.common.enums.ExtractorType;
import com.alijanik.notifier.common.logger.Logger;

import javax.annotation.Nullable;

public class ConfigSiteItemExtract {

    public String name = "";
    public String type = "";
    public boolean show = false;

    public ExtractorType getExtractType() {
        return ExtractorType.getInstance(this.type);
    }

    @Nullable
    public Object extractValue(String baseUrl, String value) {
        final ExtractorInterface extractor;
        switch (getExtractType()) {
            case LINK -> extractor = new LinkExtractor(baseUrl);
            case AREA -> extractor = new AreaExtractor();
            case PRICE -> extractor = new PriceExtractor();
            case POSTAL_CODE -> extractor = new PostalCodeExtractor();
            default -> {
                Logger.log("Extractor is not found: %s", this.type);
                extractor = null;
            }
        }
        return extractor != null ? extractor.getValue(value) : null;
    }
}
