package com.geppoextractor.atilla;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        for(Map.Entry<String, String> entry : Config.GetUrls().entrySet())
        {
            List<Dictionary<String,String>> toWriteMoveList = new ArrayList<>();

            try
            {
                // For testing using a file instead of pinging the webserver over and over
                //File file = new File("GanryuData.html");
                //Document doc = Jsoup.parse(file, "UTF-8", "");

                Document doc = Jsoup.connect(entry.getValue()).get();
                Elements tbodies = doc.getElementsByTag("tbody");

                //remove 10 hit combos for now
                tbodies.remove(tbodies.size() - 1);

                for(Element tbody : tbodies)
                {
                    for(Element tr : tbody.children())
                    {
                        if(tr.className().equals("title") || tr.child(0).className().equals("title") || tr.parent().parent().className().equals("left"))
                        {
                            continue;
                        }

                        Elements moveInformation = tr.children().get(0).getAllElements();

                        // Remove break elements
                        moveInformation.removeIf(element -> element.is("br"));
                        moveInformation.get(0).children().removeIf(element -> element.is("br"));

                        // Split up the elements of the labeled table element, this contains the move input and hit properties
                        Dictionary<String, String> labelInformation = ExtractLabelInformation(moveInformation.get(0).childNodes());

                        // Retrieve notes from the note table, and extra special notes
                        var rawNotes = tr.children().get(tr.children().size() - 1).childNodes();

                        String notes = LabelExtractor.ExtractNotes(rawNotes);
                        String moveName = moveInformation.select("span[class='en_name']").get(0).text();
                        String moveDamage = moveInformation.select("span[class='en_name']").get(1).text();;
                        String moveInput = labelInformation.get("moves");
                        String hitLevel = labelInformation.get("hitLevel");
                        String startup = tr.children().get(1).text();
                        String onBlock = tr.children().get(2).text();
                        String onHit = tr.children().get(3).text();
                        String onCounterHit = tr.children().get(4).text();
                        String totalFrames = tr.children().get(5).text();

                        Dictionary<String, String> toWriteInfo = new Hashtable<String, String>();

                        toWriteInfo.put("Name", moveName);
                        toWriteInfo.put("Command", moveInput);
                        toWriteInfo.put("Property", hitLevel);
                        toWriteInfo.put("Damage", moveDamage);
                        toWriteInfo.put("Startup", startup);
                        toWriteInfo.put("Block", onBlock);
                        toWriteInfo.put("Hit", onHit);
                        toWriteInfo.put("Counter Hit", onCounterHit);
                        toWriteInfo.put("Total", totalFrames);
                        toWriteInfo.put("Notes", notes);

                        toWriteMoveList.add(toWriteInfo);
                    }

                    JsonWriter.WriteJson(entry.getKey(), toWriteMoveList);
                }
            }
            catch (Exception e)
            {
                JsonWriter.WriteJson(entry.getKey(), toWriteMoveList);
                continue;
            }
        }
    }

    public static Dictionary<String, String> ExtractLabelInformation(List<Node> nodes)
    {
        var cleanNodes = GetCleanNodes(nodes);

        Dictionary<String, String> labelInformation = new Hashtable<>();
        List<Node> moveElements = new ArrayList<>();

        // Gets rid of the move name and move damage, since these are already retrieved
        for(int i = 1; i < cleanNodes.size() - 2; i++)
        {
            moveElements.add(cleanNodes.get(i));
        }

        String moveInput = LabelExtractor.ExtractMoves(moveElements);
        String hitLevel = "";

        try
        {
            hitLevel = cleanNodes.get(cleanNodes.size() - 2).toString();
            hitLevel = hitLevel.replace("/", "");
            hitLevel = hitLevel.replace(" ", "");
        }
        catch(Exception e)
        {
            hitLevel = " ";
        }

        labelInformation.put("moves", moveInput);
        labelInformation.put("hitLevel", hitLevel);

        return labelInformation;
    }

    private static List<Node> GetCleanNodes(List<Node> nodeList)
    {
        var cleanNodes = new ArrayList<Node>();

        for(Node node : nodeList)
        {
            if(node instanceof Element)
            {
                var element = (Element)node;
                if(element.is("br"))
                {
                    continue;
                }
            }

            if(node instanceof TextNode)
            {
                var textNode = (TextNode)node;
                if(textNode.isBlank())
                {
                    continue;
                }
            }

            cleanNodes.add(node);
        }

        return cleanNodes;
    }
}