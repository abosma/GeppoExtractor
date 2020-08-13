package com.geppoextractor.atilla;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Config {
    private static Hashtable<String, String> MoveHashtable = new Hashtable<String, String>();
    private static Hashtable<String, String> characterUrls = new Hashtable<String, String>();

    public static Hashtable<String, String> GetMoveHashtable()
    {
        MoveHashtable.put("lp", "1");
        MoveHashtable.put("rp", "2");
        MoveHashtable.put("lk", "3");
        MoveHashtable.put("rk", "4");
        MoveHashtable.put("p", "1+2");
        MoveHashtable.put("l", "1+3");
        MoveHashtable.put("lpk", "1+4");
        MoveHashtable.put("rpk", "2+3");
        MoveHashtable.put("r", "2+4");
        MoveHashtable.put("k", "3+4");
        MoveHashtable.put("wplk", "1+2+3");
        MoveHashtable.put("wprk", "1+2+4");
        MoveHashtable.put("lpwk", "1+3+4");
        MoveHashtable.put("a", "1+2+3+4");
        MoveHashtable.put("c", ", ");

        MoveHashtable.put("6", "f");
        MoveHashtable.put("3", "df");
        MoveHashtable.put("2", "d");
        MoveHashtable.put("1", "db");
        MoveHashtable.put("4", "b");
        MoveHashtable.put("7", "ub");
        MoveHashtable.put("8", "u");
        MoveHashtable.put("9", "uf");

        MoveHashtable.put("6'", "f");
        MoveHashtable.put("3'", "df");
        MoveHashtable.put("2'", "d");
        MoveHashtable.put("1'", "db");
        MoveHashtable.put("4'", "b");
        MoveHashtable.put("7'", "ub");
        MoveHashtable.put("8'", "u");
        MoveHashtable.put("9'", "uf");

        MoveHashtable.put("1_002", "db");
        MoveHashtable.put("2_002", "d");
        MoveHashtable.put("3_002", "df");
        MoveHashtable.put("4_002", "b");
        MoveHashtable.put("6_002", "f");
        MoveHashtable.put("7_002", "ub");
        MoveHashtable.put("8_002", "u");
        MoveHashtable.put("9_002", "uf");

        return MoveHashtable;
    }

    public static Hashtable<String, String> GetUrls()
    {
        characterUrls.put("akuma", "http://geppopotamus.info/game/tekken7fr/gouki/data_en.htm");
        characterUrls.put("alisa", "http://geppopotamus.info/game/tekken7fr/alisa/data_en.htm");
        characterUrls.put("anna", "http://geppopotamus.info/game/tekken7fr/anna/data_en.htm");
        characterUrls.put("asuka", "http://geppopotamus.info/game/tekken7fr/asuka/data_en.htm");
        characterUrls.put("amking", "http://geppopotamus.info/game/tekken7fr/amking/data_en.htm");
        characterUrls.put("bob", "http://geppopotamus.info/game/tekken7fr/bob/data_en.htm");
        characterUrls.put("bryan", "http://geppopotamus.info/game/tekken7fr/bryan/data_en.htm");
        characterUrls.put("claudio", "http://geppopotamus.info/game/tekken7fr/claudio/data_en.htm");
        characterUrls.put("devil", "http://geppopotamus.info/game/tekken7fr/devil/data_en.htm");
        characterUrls.put("dragunov", "http://geppopotamus.info/game/tekken7fr/dragunov/data_en.htm");
        characterUrls.put("eddy", "http://geppopotamus.info/game/tekken7fr/eddy/data_en.htm");
        characterUrls.put("eliza", "http://geppopotamus.info/game/tekken7fr/eliza/data_en.htm");
        characterUrls.put("fahkumram", "http://geppopotamus.info/game/tekken7fr/fahkumram/data_en.htm");
        characterUrls.put("feng", "http://geppopotamus.info/game/tekken7fr/feng/data_en.htm");
        characterUrls.put("ganryu", "http://geppopotamus.info/game/tekken7fr/ganryu/data_en.htm");
        characterUrls.put("geese", "http://geppopotamus.info/game/tekken7fr/geese/data_en.htm");
        characterUrls.put("gigas", "http://geppopotamus.info/game/tekken7fr/gigas/data_en.htm");
        characterUrls.put("heihachi", "http://geppopotamus.info/game/tekken7fr/heihachi/data_en.htm");
        characterUrls.put("hwoarang", "http://geppopotamus.info/game/tekken7fr/hwoarang/data_en.htm");
        characterUrls.put("jack7", "http://geppopotamus.info/game/tekken7fr/jack7/data_en.htm");
        characterUrls.put("jin", "http://geppopotamus.info/game/tekken7fr/jin/data_en.htm");
        characterUrls.put("josie", "http://geppopotamus.info/game/tekken7fr/josie/data_en.htm");
        characterUrls.put("julia", "http://geppopotamus.info/game/tekken7fr/julia/data_en.htm");
        characterUrls.put("katarina", "http://geppopotamus.info/game/tekken7fr/katarina/data_en.htm");
        characterUrls.put("kazumi", "http://geppopotamus.info/game/tekken7fr/kazumi/data_en.htm");
        characterUrls.put("kazuya", "http://geppopotamus.info/game/tekken7fr/kazuya/data_en.htm");
        characterUrls.put("king", "http://geppopotamus.info/game/tekken7fr/king/data_en.htm");
        characterUrls.put("kuma", "http://geppopotamus.info/game/tekken7fr/kuma/data_en.htm");
        characterUrls.put("lars", "http://geppopotamus.info/game/tekken7fr/lars/data_en.htm");
        characterUrls.put("law", "http://geppopotamus.info/game/tekken7fr/law/data_en.htm");
        characterUrls.put("lee", "http://geppopotamus.info/game/tekken7fr/lee/data_en.htm");
        characterUrls.put("lei", "http://geppopotamus.info/game/tekken7fr/lei/data_en.htm");
        characterUrls.put("leo", "http://geppopotamus.info/game/tekken7fr/leo/data_en.htm");
        characterUrls.put("leroy", "http://geppopotamus.info/game/tekken7fr/leroy/data_en.htm");
        characterUrls.put("lili", "http://geppopotamus.info/game/tekken7fr/lili/data_en.htm");
        characterUrls.put("lucky", "http://geppopotamus.info/game/tekken7fr/lucky/data_en.htm");
        characterUrls.put("marduk", "http://geppopotamus.info/game/tekken7fr/marduk/data_en.htm");
        characterUrls.put("raven", "http://geppopotamus.info/game/tekken7fr/raven/data_en.htm");
        characterUrls.put("miguel", "http://geppopotamus.info/game/tekken7fr/miguel/data_en.htm");
        characterUrls.put("negan", "http://geppopotamus.info/game/tekken7fr/negan/data_en.htm");
        characterUrls.put("noctis", "http://geppopotamus.info/game/tekken7fr/noctis/data_en.htm");
        characterUrls.put("nina", "http://geppopotamus.info/game/tekken7fr/nina/data_en.htm");
        characterUrls.put("paul", "http://geppopotamus.info/game/tekken7fr/paul/data_en.htm");
        characterUrls.put("shaheen", "http://geppopotamus.info/game/tekken7fr/shaheen/data_en.htm");
        characterUrls.put("steve", "http://geppopotamus.info/game/tekken7fr/steve/data_en.htm");
        characterUrls.put("xiaoyu", "http://geppopotamus.info/game/tekken7fr/xiaoyu/data_en.htm");
        characterUrls.put("yoshimitsu", "http://geppopotamus.info/game/tekken7fr/yoshimitsu/data_en.htm");
        characterUrls.put("zafina", "http://geppopotamus.info/game/tekken7fr/zafina/data_en.htm");

        return characterUrls;
    }
}
