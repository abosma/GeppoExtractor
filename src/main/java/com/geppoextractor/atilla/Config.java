package com.geppoextractor.atilla;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Config {
    public static Map<String, String> GetMoveConversionMap()
    {
        return LoadFile("move_config.json");
    }

    public static Map<String, String> GetCharacterLinks()
    {
        return LoadFile("character_config.json");
    }

    private static Map<String, String> LoadFile(String fileName)
    {
        var objectMapper = new ObjectMapper();
        ClassLoader classLoader = Config.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            return objectMapper.readValue(inputStream, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
