package com.geppoextractor.atilla;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Log4j2
public class Config {
    private Config(){}

    public static Map<String, String> getMoveConversionMap() {
        return loadFile("move_config.json");
    }

    public static Map<String, String> getCharacterLinks() {
        return loadFile("character_config.json");
    }

    private static Map<String, String> loadFile(String fileName) {
        log.info("Loading data from file: {}", fileName);

        var objectMapper = new ObjectMapper();
        ClassLoader classLoader = Config.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            return objectMapper.readValue(inputStream, Map.class);
        } catch (IllegalArgumentException | IOException e) {
            log.error("Error reading file. Error: {}", e.toString());
        }

        return null;
    }
}
