package org.ib.history.wiki.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(value = Parameterized.class)
public class TemplateParserTestRemoveLinks {

    private String input;
    private String expected;

    public TemplateParserTestRemoveLinks(String input, String expected) {
        this.input = input;
        this.expected = expected;
    }

    @Test
    public void testRemoveLinks() {
        TemplateParser parser = new TemplateParser();
        String noLinks = parser.removeLinks(input);
        assertThat(expected, is(noLinks));
//        parser.removeLinks("[[King of Spain]]<br>{{small|(alongside [[Joanna of Castile|Joanna]] until 1555)}}");
    }

    @Parameterized.Parameters()
    public static Iterable<Object[]> data1() {
        return Arrays.asList(new Object[][]{
                {"[[Principality of Orange|Prince of Orange]]",
                        "Prince of Orange"},
                {"[[List of English monarchs|King of England]]; [[List of Irish monarchs|Lord/King of Ireland]]",
                        "King of England; Lord/King of Ireland"},
        });
    }
}
