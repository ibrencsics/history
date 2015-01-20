package org.ib.history.wiki.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(value = Parameterized.class)
public class TemplateParserTestRemoveTag {

    private String input;
    private String expected;

    public TemplateParserTestRemoveTag(String input, String expected) {
        this.input = input;
        this.expected = expected;
    }

    @Test
    public void testRemoveTag() {
        TemplateParser parser = new TemplateParser();
        String noTags = parser.removeTags(input, "small", "br");
        assertThat(noTags, is(expected));
//        parser.removeLinks("[[King of Spain]]<br>{{small|(alongside [[Joanna of Castile|Joanna]] until 1555)}}");
    }

    @Parameterized.Parameters()
    public static Iterable<Object[]> data1() {
        return Arrays.asList(new Object[][]{
                {"[[King of Bohemia]]<br/><small>contested till 1471 by [[George of Poděbrady]], from 1471 by [[Vladislas II of Bohemia and Hungary|Vladislaus II]]</small>",
                        "[[King of Bohemia]]"},
                {"[[King of Spain]]<br>{{small|(alongside [[Joanna of Castile|Joanna]] until 1555)}}",
                        "[[King of Spain]]"},
        });
    }
}
