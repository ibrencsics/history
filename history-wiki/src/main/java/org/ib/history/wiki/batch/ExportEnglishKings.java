package org.ib.history.wiki.batch;

import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.tuples.Tuple2;
import org.ib.history.wiki.domain.Royalty;
import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.domain.WikiSuccession;
import org.ib.history.wiki.service.WikiService;
import org.ib.history.wiki.wikipedia.WikiServiceWikipedia;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ExportEnglishKings {

    static WikiService service = new WikiServiceWikipedia();
    static PrintWriter writer;

    static List<KingCache> kingCache = new LinkedList<>();
    static List<String> kingNameCache = new LinkedList<>();

    static final String[] STARTING_POINTS = {"William_the_Conqueror", "Henry_II_of_England", "Edward_V_of_England", "George_V", "Edward_VIII"};

    public static void main(String[] args) throws IOException {
        File file = new File("/tmp/english-kings.txt");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        writer = new PrintWriter(fos);

        try {
            for (int i=0; i<STARTING_POINTS.length; i++)
                getKing(STARTING_POINTS[i]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        writer.close();
        fos.close();
    }

    private static void getKing(String personName) {

        if (kingNameCache.contains(personName)) {
            return;
        }
        kingNameCache.add(personName);

        WikiPerson person = service.getPerson(personName);


//        writer.append(person.toString());
//        writer.append("\n\n");

        List<WikiSuccession> sucs = person.getSuccessions().stream()
                .filter(s -> (s.getTitle().toLowerCase().contains("england") ||
                        s.getTitle().toLowerCase().contains("great britain") ||
                        s.getTitle().toLowerCase().contains("united kingdom")
                ))
                .collect(Collectors.toList());

        for (WikiSuccession suc : sucs) {
            writer.append(person.getName());
            writer.append(": ");
            writer.append(suc.getFrom().toString());
            writer.append(" - ");
            writer.append(suc.getTo().toString());
            writer.append("\n");

            if (suc.getSuccessor()==null) continue;
            getKing(suc.getSuccessor().getLocalPart());
        }
    }

    static class KingCache {
        FlexibleDate from, to;
        String pageName;

        KingCache(FlexibleDate from, FlexibleDate to, String pageName) {
            this.from = from;
            this.to = to;
            this.pageName = pageName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            KingCache kingCache = (KingCache) o;

            if (!from.equals(kingCache.from)) return false;
            if (!pageName.equals(kingCache.pageName)) return false;
            if (!to.equals(kingCache.to)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = from.hashCode();
            result = 31 * result + to.hashCode();
            result = 31 * result + pageName.hashCode();
            return result;
        }
    }
}
