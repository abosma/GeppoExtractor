package com.geppoextractor.atilla;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

import java.io.File;
import java.io.IOException;

public class Helper {
    private Helper(){}

    public static Document getCharacterDocument(String characterLink) throws IOException {
        Document toReturnDocument;

        try {
            if (Main.TESTING) {
                File file = new File("GanryuData.html");
                toReturnDocument = Jsoup.parse(file, "UTF-8", "");
            } else {
                toReturnDocument = Jsoup.connect(characterLink).get();
            }
        } catch (IOException ignored) {
            throw new IOException("Could not connect to the page: " + characterLink);
        }

        return toReturnDocument;
    }

    public static String getImageStringFromDocument(Node input) {
        String imgSrc = input.attr("src");
        String move = "";

        try {
            if (Main.TESTING) {
                move = imgSrc.substring(imgSrc.indexOf("/") + 1, imgSrc.indexOf(".dib"));
            } else {
                move = imgSrc.substring(imgSrc.indexOf("/") + 1, imgSrc.indexOf(".bmp"));
            }
        } catch (Exception e) {
            move = " ";
        }

        return move;
    }

    public static String replaceNeutralInput(String moveString) {
        if (moveString.contains("☆")) {
            return moveString.replace(" ☆ ", "n");
        }

        return moveString;
    }
}
