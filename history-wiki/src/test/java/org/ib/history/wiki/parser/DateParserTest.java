package org.ib.history.wiki.parser;

import junit.framework.Assert;
import org.ib.history.commons.data.FlexibleDate;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class DateParserTest {

    @Test
    public void testParseDateEnglishFormat() {
        DateParser dateParser = new DateParser();

        List<FlexibleDate> dates = dateParser.parseDateEnglishFormat("25&nbsp;December 1066");
        assertEquals(new FlexibleDate.Builder().year(1066).month(12).day(25).build(), dates.get(0));
    }
}
