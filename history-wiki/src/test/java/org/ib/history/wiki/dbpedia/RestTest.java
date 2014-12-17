package org.ib.history.wiki.dbpedia;

import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class RestTest {

    private static final String QUERY = "http://dbpedia.org/sparql?default-graph-uri=http%3A%2F%2Fdbpedia.org&query=PREFIX+%3A+%3Chttp%3A%2F%2Fdbpedia.org%2Fresource%2F%3E%0D%0APREFIX+d%3A+%3Chttp%3A%2F%2Fdbpedia.org%2Fproperty%2F%3E%0D%0ASELECT+%3Fname+%3FbirthDate%0D%0AWHERE%0D%0A%7B%0D%0A%3AWilliam_III_of_England+d%3Aname+%3Fname+.%0D%0A%3AWilliam_III_of_England+d%3AdateOfBirth+%3FbirthDate+.%0D%0A%7D&format=text%2Fhtml&timeout=30000&debug=on";

    @Test
    public void test() {
        Client client = ClientBuilder.newBuilder().newClient();
        WebTarget target = client.target("http://dbpedia.org/sparql");
        target = target
                .queryParam("default-graph-uri", "http%3A%2F%2Fdbpedia.org")
                .queryParam("query", "PREFIX+%3A+%3Chttp%3A%2F%2Fdbpedia.org%2Fresource%2F%3E%0D%0APREFIX+d%3A+%3Chttp%3A%2F%2Fdbpedia.org%2Fproperty%2F%3E%0D%0ASELECT+%3Fname+%3FbirthDate%0D%0AWHERE%0D%0A%7B%0D%0A%3AWilliam_III_of_England+d%3Aname+%3Fname+.%0D%0A%3AWilliam_III_of_England+d%3AdateOfBirth+%3FbirthDate+.%0D%0A%7D&format=text%2Fhtml&timeout=30000&debug=on");

        Invocation.Builder builder = target.request();
        Response response = builder.get();
        System.out.println(response.getEntity());
    }

//    PREFIX d: <http://dbpedia.org/ontology/>
//    SELECT ?name ?birthDate
//            WHERE
//    {
//        :William_III_of_England dbpedia2:name ?name .
//            :William_III_of_England dbpedia2:dateOfBirth ?birthDate .
//    }

//    PREFIX : <http://dbpedia.org/resource/>
//    PREFIX d: <http://dbpedia.org/property/>
//    SELECT ?name ?birthDate
//            WHERE
//    {
//        :William_III_of_England d:name ?name .
//            :William_III_of_England d:dateOfBirth ?birthDate .
//    }
}
