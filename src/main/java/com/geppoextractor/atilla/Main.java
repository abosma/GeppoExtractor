package com.geppoextractor.atilla;

import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

@Log4j2
public class Main {

    public static final boolean TESTING = false;
    private static final Map<String, String> characterLinks = Config.getCharacterLinks();

    public static void main(String[] args) {
        for (Map.Entry<String, String> characterLink : characterLinks.entrySet()) {
            Document characterDocument;

            try {
                characterDocument = Helper.getCharacterDocument(characterLink.getValue());
            } catch (IOException e) {
                log.error(e.getMessage());
                continue;
            }

            List<Dictionary<String, String>> characterMoveList = MoveExtractor.getCharacterMoveList(characterDocument);

            JsonWriter.writeJson(characterLink.getKey(), characterMoveList);
        }
    }
}
