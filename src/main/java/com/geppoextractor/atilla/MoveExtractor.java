package com.geppoextractor.atilla;

import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.*;

@Log4j2
public class MoveExtractor {
    private MoveExtractor(){}

    private static final Map<String, String> moveConversionMap = Config.getMoveConversionMap();

    public static List<Dictionary<String, String>> getCharacterMoveList(Document characterDocument) {
        List<Dictionary<String, String>> toReturnMoveList = new ArrayList<>();

        Elements moveElements = characterDocument.getElementsByTag("tbody");

        //remove 10 hit combos for now
        moveElements.remove(moveElements.size() - 1);

        for (Element moveElement : moveElements) {
            String typeOfMove = moveElement.child(0).child(0).text();

            for (Element moveLine : moveElement.children()) {
                try {
                    if (moveLine.className().equals("title") || moveLine.child(0).className().equals("title") || moveLine.parent().parent().className().equals("left")) {
                        continue;
                    }

                    Elements moveInformation = moveLine.children().get(0).getAllElements();

                    // Remove break elements
                    moveInformation.removeIf(element -> element.is("br"));
                    moveInformation.get(0).children().removeIf(element -> element.is("br"));

                    // Split up the elements of the labeled table element, this contains the move input and hit properties
                    Dictionary<String, String> labelInformation = extractLabelInformation(moveInformation.get(0).childNodes());

                    // Retrieve notes from the note table, and extra special notes
                    var rawNotes = moveLine.children().get(moveLine.children().size() - 1).childNodes();

                    String notes = LabelExtractor.extractNotes(rawNotes);
                    String moveName = moveInformation.select("span[class='en_name']").get(0).text();
                    String moveDamage = moveInformation.select("span[class='en_name']").get(1).text();
                    String moveInput = labelInformation.get("moves");
                    String hitLevel = labelInformation.get("hitLevel");
                    String startup = moveLine.children().get(1).text();
                    String onBlock = moveLine.children().get(2).text();
                    String onHit = moveLine.children().get(3).text();
                    String onCounterHit = moveLine.children().get(4).text();
                    String totalFrames = moveLine.children().get(5).text();

                    Dictionary<String, String> toWriteInfo = new Hashtable<>();

                    switch (typeOfMove) {
                        case "Rage Art" -> toWriteInfo.put("Name", "Rage Art");
                        case "Rage Drive" -> toWriteInfo.put("Name", "Rage Drive");
                        case "Throws" -> {
                            var throwBreak = fixNotesWithMoveInputs(moveLine.children().get(3));
                            toWriteInfo.put("Name", moveName);
                            toWriteInfo.put("Command", moveInput);
                            toWriteInfo.put("Damage", moveDamage);
                            toWriteInfo.put("Property", hitLevel);
                            toWriteInfo.put("BreakThrow", throwBreak);
                            toWriteInfo.put("AfterThrow", onBlock);
                            toWriteInfo.put("Notes", totalFrames);
                            toReturnMoveList.add(toWriteInfo);
                            continue;
                        }
                        default -> toWriteInfo.put("Name", moveName);
                    }

                    toWriteInfo.put("Command", moveInput);
                    toWriteInfo.put("Property", hitLevel);
                    toWriteInfo.put("Damage", moveDamage);
                    toWriteInfo.put("Startup", startup);
                    toWriteInfo.put("Block", onBlock);
                    toWriteInfo.put("Hit", onHit);
                    toWriteInfo.put("Counter Hit", onCounterHit);
                    toWriteInfo.put("Total", totalFrames);
                    toWriteInfo.put("Notes", notes);

                    toReturnMoveList.add(toWriteInfo);
                } catch (Exception e) {
                    log.error("Something went wrong retrieving a move, skipping...");
                }
            }
        }

        return toReturnMoveList;
    }

    private static String fixNotesWithMoveInputs(Element noteElement) {
        StringBuilder toReturnSB = new StringBuilder();

        for (var node : noteElement.childNodes()) {
            if (node.attr("tag", "img") != null && !(node instanceof TextNode)) {
                String move = Helper.getImageStringFromDocument(node);
                String translatedMove = moveConversionMap.get(move);

                if (translatedMove != null) {
                    toReturnSB.append(translatedMove);
                }
            } else {
                toReturnSB.append(((TextNode) node).text());
            }

        }
        return toReturnSB.toString();
    }

    private static Dictionary<String, String> extractLabelInformation(List<Node> nodes) {
        var cleanNodes = getCleanNodes(nodes);

        Dictionary<String, String> labelInformation = new Hashtable<>();
        List<Node> moveElements = new ArrayList<>();

        // Gets rid of the move name and move damage, since these are already retrieved
        for (int i = 1; i < cleanNodes.size() - 2; i++) {
            moveElements.add(cleanNodes.get(i));
        }

        String moveInput = LabelExtractor.extractMoves(moveElements);
        String hitLevel = "";

        try {
            hitLevel = cleanNodes.get(cleanNodes.size() - 2).toString();
            hitLevel = hitLevel.replace("/", "");
            hitLevel = hitLevel.replace(" ", "");
        } catch (Exception e) {
            hitLevel = " ";
        }

        labelInformation.put("moves", moveInput);
        labelInformation.put("hitLevel", hitLevel);

        return labelInformation;
    }

    private static List<Node> getCleanNodes(List<Node> nodeList) {
        var cleanNodes = new ArrayList<Node>();

        for (Node node : nodeList) {
            if (node instanceof Element) {
                var element = (Element) node;
                if (element.is("br")) {
                    continue;
                }
            }

            if (node instanceof TextNode) {
                var textNode = (TextNode) node;
                if (textNode.isBlank()) {
                    continue;
                }
            }

            cleanNodes.add(node);
        }

        return cleanNodes;
    }
}
