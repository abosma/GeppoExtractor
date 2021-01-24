package com.geppoextractor.atilla;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.*;

@Log4j2
public class LabelExtractor {

    private LabelExtractor(){}

    private static final Map<String, String> moveConversionMap = Config.getMoveConversionMap();

    public static String extractMoves(List<Node> elements) {
        List<String> moveInput = new ArrayList<>();

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

            String move = Helper.getImageStringFromDocument(input);
            String translatedMove = moveConversionMap.get(move);

            moveInput.add(translatedMove);
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (String move : moveInput) {
            stringBuilder.append(move);
        }

        var toReturnString = stringBuilder.toString();

        // Replace inputs between 【】 to normal 1~1 slide input format
        if (toReturnString.contains("【") || toReturnString.contains("】")) {
            toReturnString = replaceSlideInput(toReturnString);
        }

        toReturnString = Helper.replaceNeutralInput(toReturnString);

        return toReturnString;
    }

    private static String replaceSlideInput(String toReplaceString) {
        var movesBeforeBrackets = StringUtils.substringBefore(toReplaceString, "【").trim();
        var movesBetweenBrackets = StringUtils.substringsBetween(toReplaceString, "【", "】");
        var movesAfterBrackets = StringUtils.substringAfterLast(toReplaceString, "】").trim();

        List<String> moves = new ArrayList<>();
        String toReturnMoveInput = "";

        // TODO: Find out how to fix this after removing the , from the move in extractMoves
        // Replaces slide inputs from 1, 2 to 1~2
        for (String movesBetweenBracket : movesBetweenBrackets) {
            var toAddString = movesBetweenBracket;

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
        if (!StringUtils.isBlank(movesBeforeBrackets)) {
            toReturnMoveInput = movesBeforeBrackets + " " + toReturnMoveInput;
        }

        // Adds moves after the slide inputs
        if (!StringUtils.isBlank(movesAfterBrackets)) {
            toReturnMoveInput = toReturnMoveInput + " " + movesAfterBrackets;
        }

        return toReturnMoveInput;
    }

    public static String extractNotes(List<Node> elements) {
        StringBuilder toReturnInformation = new StringBuilder();

        for (Node node : elements) {
            if (node instanceof Element) {
                var element = (Element) node;
                var tagName = element.tag().getName();

                if (tagName.equals("br")) {
                    toReturnInformation.append("\n");
                    continue;
                }

                if (tagName.equals("span") && element.hasClass("icon")) {
                    toReturnInformation.append(element.childNode(0).toString()).append(" ");
                    continue;
                }

                if (tagName.equals("span") && element.hasClass("patch") || tagName.equals("div")) {
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
