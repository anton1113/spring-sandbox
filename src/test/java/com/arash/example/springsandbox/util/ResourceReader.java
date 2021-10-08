package com.arash.example.springsandbox.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;

@UtilityClass
public class ResourceReader {

    @SneakyThrows
    public static String readFile(String filename) {
        return IOUtils.resourceToString(filename, StandardCharsets.UTF_8);
    }
}
