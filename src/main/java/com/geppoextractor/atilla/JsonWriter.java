package com.geppoextractor.atilla;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.List;

public class JsonWriter {

    public static void WriteJson(String fileName, List<Dictionary<String,String>> moveInformation)
    {
        CreateDirIfNotExists();

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        File toWriteFile = new File(".\\extracted_json\\" + fileName + ".json");

        try
        {
            writer.writeValue(toWriteFile, moveInformation);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void CreateDirIfNotExists()
    {
        Path path = Paths.get(".\\extracted_json\\");

        if(Files.notExists(path))
        {
            try
            {
                Files.createDirectory(path);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
