package org.ib.history.wiki.parser.old;

import org.ib.history.commons.tuples.Tuple2;
import org.ib.history.wiki.parser.old.RoyaltyInfoboxParser;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class RoyaltyInfoboxParserTest {

    private static final String vector1 = "[[Principality of Orange|Prince of Orange]]";
    private static final String vector2 = "[[Stadtholder|Stadtholder of Holland, Zeeland, Utrecht, Gelderland and Overijssel]]";
    private static final String vector3 = "[[List of English monarchs|King of England]], [[List of Scottish monarchs|Scotland]] and [[King of Ireland|Ireland]]";

//    @Test
    public void testGetTags() {
        RoyaltyInfoboxParser parser = new RoyaltyInfoboxParser();
        List<String> ret;

        ret = parser.getTags(vector1);
        System.out.println(ret);

        ret = parser.getTags(vector2);
        System.out.println(ret);

        ret = parser.getTags(vector3);
        System.out.println(ret);
    }

//    @Test
    public void testParseTags() {
        RoyaltyInfoboxParser parser = new RoyaltyInfoboxParser();
        List<Tuple2<String,String>> ret;

        System.out.println( parser.parseTags(vector1).stream().map(t -> t.element2()).collect(Collectors.joining(", ")) );
        System.out.println( parser.parseTags(vector2).stream().map(t -> t.element2()).collect(Collectors.joining(", ")) );
        System.out.println( parser.parseTags(vector3).stream().map(t -> t.element2()).collect(Collectors.joining(", ")) );
    }

//    @Test
    public void testSentenceToWords() {
        RoyaltyInfoboxParser parser = new RoyaltyInfoboxParser();
        System.out.println(parser.sentenceToWords("Prince of Orange"));
        System.out.println(parser.sentenceToWords("Stadtholder of Holland, Zeeland, Utrecht, Gelderland and Overijssel"));
        System.out.println(parser.sentenceToWords("King of England, Scotland, Ireland"));
    }

    @Test
    public void testParseReferenceList() {
        RoyaltyInfoboxParser parser = new RoyaltyInfoboxParser();
        parser.parseReferenceList(vector1);
        parser.parseReferenceList(vector2);
        parser.parseReferenceList(vector3);
    }
}
