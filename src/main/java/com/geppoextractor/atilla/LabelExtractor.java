package com.geppoextractor.atilla;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.*;

public class LabelExtractor {

    public static String ExtractMoves(List<Node> elements) {
        List<String> moveInput = new ArrayList<>();
        var moveHashtable = Config.GetMoveConversionMap();

        for (Node input : elements) {
            if (input instanceof TextNode) {
                var extraText = ((TextNode) input).text().trim();

                // Replaced just frame unicode with the better known : input
                if (extraText.contains("Ｊ")) {
                    extraText = extraText.replace("Ｊ", ":");
                    moveInput.add(extraText);
                    continue;
                }

                if (extraText.contains("just")) {
                    extraText = extraText.replace("just", ":");
                    moveInput.add(extraText);
                    continue;
                }

                extraText = String.format(" %s ", extraText);

                moveInput.add(extraText);
                continue;
            }

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

            moveInput.add(moveHashtable.get(move));
        }

        StringBuffer stringBuffer = new StringBuffer();

        for (String move : moveInput) {
            stringBuffer.append(move);
        }

        var toReturnString = stringBuffer.toString();

        // Replace inputs between 【】 to normal 1~1 slide input format
        if (toReturnString.contains("【") || toReturnString.contains("】")) {
            toReturnString = ReplaceSlideInput(toReturnString);
        }

        // Replace ☆ neutral input with ,n,
        if (toReturnString.contains("☆")) {
            toReturnString = toReturnString.replace(" ☆ ", "n");
        }

        return toReturnString;
    }

    private static String ReplaceSlideInput(String toReplaceString) {
        var movesBeforeBrackets = StringUtils.substringBefore(toReplaceString, "【").trim();
        var movesBetweenBrackets = StringUtils.substringsBetween(toReplaceString, "【", "】");
        var movesAfterBrackets = StringUtils.substringAfterLast(toReplaceString, "】").trim();

        List<String> moves = new ArrayList<>();
        String toReturnMoveInput = "";

        // Replaces slide inputs from 1, 2 to 1~2
        for (int i = 0; i < movesBetweenBrackets.length; i++) {
            var toAddString = movesBetweenBrackets[i];

            toAddString = toAddString.replace(", ", "~");
            toAddString = toAddString.trim();

            moves.add(toAddString);
        }

        if (moves.size() > 1) {
            for (int i = 0; i < moves.size(); i++) {
                // If it's not the last move in the list
                if (i != moves.size() - 1) {
                    toReturnMoveInput += moves.get(i) + " or ";
                } else {
                    toReturnMoveInput += moves.get(i);
                }
            }
        } else {
            toReturnMoveInput += moves.get(0);
        }

        // Adds moves before the slide inputs
        if (!StringIsEmpty(movesBeforeBrackets)) {
            toReturnMoveInput = movesBeforeBrackets + " " + toReturnMoveInput;
        }

        // Adds moves after the slide inputs
        if (!StringIsEmpty(movesAfterBrackets)) {
            toReturnMoveInput = toReturnMoveInput + " " + movesAfterBrackets;
        }

        return toReturnMoveInput;
    }

    private static boolean StringIsEmpty(String toCheckString) {
        return toCheckString.isBlank() || toCheckString.isEmpty();
    }

    public static String ExtractNotes(List<Node> elements) {
        StringBuffer toReturnInformation = new StringBuffer();

        for (Node node : elements) {
            if (node instanceof Element) {
                var element = (Element) node;
                var tagName = element.tag().getName();

                if (tagName.equals("br")) {
                    toReturnInformation.append("\n");
                    continue;
                }

                if (tagName.equals("span") && element.hasClass("icon")) {
                    toReturnInformation.append(element.childNode(0).toString() + " ");
                    continue;
                }

                if (tagName.equals("span") && element.hasClass("patch")) {
                    continue;
                }

                if (tagName.equals("div")) {
                    continue;
                }
            }

            var nodeString = node.toString();

            nodeString = nodeString.replace("\r", "");
            nodeString = nodeString.replace("\n", "");
            nodeString = nodeString.trim();

            toReturnInformation.append(nodeString);
        }

        return toReturnInformation.toString();
    }
}
