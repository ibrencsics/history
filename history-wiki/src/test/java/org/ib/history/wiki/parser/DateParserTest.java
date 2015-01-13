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

        dates = dateParser.parseDateEnglishFormat("1066");
        assertEquals(new FlexibleDate.Builder().year(1066).noMonth().noDay().build(), dates.get(0));

        // Stephen,_King_of_England
        dates = dateParser.parseDateEnglishFormat("[[circa|c.]] 1092 or 1096");
        assertEquals(new FlexibleDate.Builder().year(1092).noMonth().noDay().build(), dates.get(0));
        assertEquals(new FlexibleDate.Builder().year(1096).noMonth().noDay().build(), dates.get(1));

        //
        dates = dateParser.parseDateEnglishFormat("10 September 1382 (aged 56)");
        assertEquals(new FlexibleDate.Builder().year(1382).month(9).day(10).build(), dates.get(0));
    }
}
