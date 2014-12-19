package org.ib.history.wiki.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.Test;

import java.net.URISyntaxException;

public class WikiResourceTest {

    @Test
    public void testFromURIString() throws URISyntaxException {
        String testVector = "http://dbpedia.org/resource/Henry_VIII_of_England";
        WikiResource resource = WikiResource.fromURIString(testVector);
        assertThat(resource.getFullDbpedia().toString(), is(testVector));
    }
}
