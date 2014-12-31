package org.ib.history.wiki.batch;

import org.ib.history.wiki.domain.Royalty;
import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.domain.WikiSuccession;
import org.ib.history.wiki.service.WikiService;
import org.ib.history.wiki.wikipedia.WikiServiceWikipedia;

import java.io.*;
import java.util.stream.Collectors;

public class ExportEnglishKings {

    public static void main(String[] args) throws IOException {
        File file = new File("/tmp/english-kings.txt");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        PrintWriter writer = new PrintWriter(fos);

        WikiService service = new WikiServiceWikipedia();

        WikiPerson king = service.getPerson("William_the_Conqueror");
        writer.append(king.toString());
        writer.append("\n\n");

        for (int i=0; i<4; i++) {
            WikiSuccession succession = king.getSuccessions().stream()
                    .filter(s -> s.getCountry().equalsIgnoreCase("England"))
                    .collect(Collectors.toList()).get(0);

            king = service.getPerson(succession.getSuccessor().getLocalPart());

            writer.append(king.toString());
            writer.append("\n\n");
        }

        writer.close();
        fos.close();
    }
}
