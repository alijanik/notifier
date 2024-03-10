package com.alijanik.notifier.common.file;

import com.alijanik.notifier.common.logger.Logger;
import com.alijanik.notifier.common.utils.StringUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Filer {

    private final Path outputPath;

    public Filer(String outputDirectory, String fileNameWithExtension) {
        outputDirectory = StringUtils.dropLastChar(outputDirectory, '/');
        createDirectory(outputDirectory);
        String outputFile = String.format("%s/%s", outputDirectory, fileNameWithExtension);
        this.outputPath = Paths.get(outputFile);
    }

    public void createDirectory(String outputDirectory) {
        try {
            Files.createDirectories(Paths.get(outputDirectory));
        } catch (IOException e) {
            Logger.log("IOException@createDirectory %s: %s", outputDirectory, e.getMessage());
        }
    }

    @Nullable
    public String read() {
        try {
            return Files.readString(this.outputPath);
        } catch (IOException e) {
            Logger.log("IOException@read %s: %s", this.outputPath, e.getMessage());
            return null;
        }
    }

    public boolean write(String string) {
        try {
            Files.writeString(this.outputPath, string);
            return true;
        } catch (IOException e) {
            Logger.log("IOException@write %s: %s", this.outputPath, e.getMessage());
            return false;
        }
    }
}
