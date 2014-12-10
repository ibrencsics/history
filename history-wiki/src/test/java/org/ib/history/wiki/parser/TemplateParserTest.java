package org.ib.history.wiki.parser;

import junit.framework.Assert;
import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static junit.framework.Assert.*;

public class TemplateParserTest {

    private static final String EXP1 = "{{Infobox royalty| type = monarch| name = William III| image = King William III of England, (1650-1702).jpg| caption = William III by Sir [[Godfrey Kneller]]| succession = [[Principality of Orange|Prince of Orange]]| reign = 4 November 1650<ref name=OSNS/>&nbsp;-<br > 8 March 1702| predecessor = [[William II, Prince of Orange|William II]]| successor = [[John William Friso, Prince of Orange|John William Friso]]| succession1 = [[Stadtholder|Stadtholder of Holland, Zeeland, Utrecht, Gelderland and Overijssel]]| reign1 = July 1672&nbsp;- 8 March 1702| predecessor1 = [[William II, Prince of Orange|William II]]| successor1 = [[William IV, Prince of Orange|William IV]]| succession2 = [[List of English monarchs|King of England]], [[List of Scottish monarchs|Scotland]] and [[King of Ireland|Ireland]]| moretext2 = ([[Styles of English and Scottish sovereigns|more&nbsp;...]])| reign2 = 13 February 1689 -<br > 8 March 1702| coronation2 = 11 April 1689| predecessor2 = [[James II of England|James II & VII]]| successor2 = [[Anne, Queen of Great Britain|Anne]]| regent2 = [[Mary II of England|Mary II]]| reg-type2 = Co-monarch| spouse =[[Mary II of England]]| spouse-type = Spouse| house = [[House of Orange-Nassau]]| father =[[William II, Prince of Orange]]| mother = [[Mary, Princess Royal and Princess of Orange|Mary, Princess Royal]]| birth_date = {{birth date|df=yes|1650|11|4}}<br />{{small|[<nowiki />[[Old Style and New Style dates|N.S.]]: 14 November 1650<nowiki />]}}<ref name=OSNS>During William's lifetime, two calendars were in use in Europe: the Old Style [[Julian calendar]] in Britain and parts of Northern and Eastern Europe, and the New Style [[Gregorian calendar]] elsewhere, including William's birthplace in the Netherlands. At the time of William's birth, Gregorian dates were ten days ahead of Julian dates: thus William was born on 14 November 1650 by Gregorian reckoning, but on 4 November 1650 by Julian. At William's death, Gregorian dates were eleven days ahead of Julian dates. He died on 8 March 1702 by the standard Julian calendar, but on 19 March 1702 by the Gregorian calendar. (However, the English New Year fell on 25 March, so by English reckoning of the time, William died on 8 March 1701.) Unless otherwise noted, dates in this article follow the standard Julian calendar, in which the New Year falls on 1 January.</ref>| birth_place = [[Binnenhof]], [[The Hague]]| death_date = {{death date and age|df=yes|1702|3|8|1650|11|4}}<br /><small>[<nowiki />[[Old Style and New Style dates|N.S.]]: 19 March 1702<nowiki />]</small>| death_place = [[Kensington Palace]], London| place of burial = [[Westminster Abbey]], London| religion = [[Protestantism]]| signature = WilliamIII Sig.svg|}}";
    private static final String EXP2 = "{{Infobox royalty|name           =Charles V|image          =Titian - Portrait of Charles V Seated - WGA22964.jpg|caption        =Charles&nbsp;V by [[Titian]], 1548. [[Alte Pinakothek]], Munich, Germany|reign          =28 June 1519 – 27 August 1556<ref>Date of Charles's abdication; on 24 February 1558, the college of electors assembled at Frankfort accepted the instrument of Charles V's imperial resignation and declared the election of Ferdinand as emperor [http://books.google.es/books?id=DUwLAAAAIAAJ&lpg=PA716&dq=&pg=PA716#v=onepage&q=&f=false] [http://books.google.es/books?id=nPwQAAAAIAAJ&dq=&lr&as_brr=3&pg=PA182#v=onepage&q=&f=false]</ref>|coronation     = 26 October 1520 {{small|(Germany)}}<br>22 February 1530 {{small|(Italy)}}<br>24 February 1530 {{small|(imperial)}}|succession     =[[Holy Roman Emperor]];<br>[[List of German monarchs|King of Germany]];<br>[[King of Italy]]|predecessor    =[[Maximilian I, Holy Roman Emperor|Maximilian I]]|successor      =[[Ferdinand I, Holy Roman Emperor|Ferdinand I]]|succession1    = [[King of Spain]]<br>{{small|(alongside [[Joanna of Castile|Joanna]] until 1555)}}|reign1         ={{nowrap|23 January 1516 – 16 January 1556}}|predecessor1   =[[Joanna of Castile|Joanna]] and [[Ferdinand II of Aragon|Ferdinand II]]|successor1     =[[Philip II of Spain|Philip II]]|succession2    =[[Seventeen Provinces|Lord of the Netherlands]]; <br>[[List of counts of Burgundy|Count Palatine of Burgundy]]|reign2         =25 September 1506 –<br> 25 October 1555<ref>{{cite book|url=http://books.google.es/books?id=idjdQOYlK-4C&lpg=PA39&dq=&lr&as_brr=3&pg=PA39#v=onepage&q=&f=true |title=Abdication of Brussels |publisher=Books.google.es |accessdate=8 June 2012}}</ref>|predecessor2   =[[Philip I of Castile|Philip IV]]|successor2     =[[Philip II of Spain|Philip V]]|spouse         =[[Isabella of Portugal]]|issue          =[[Philip II of Spain]]<br />[[Maria of Spain|Maria, Holy Roman Empress]]<br />[[Joanna of Austria, Princess of Portugal|Joanna, Princess of Portugal]]<br />[[John of Austria]] ([[illegitimate]])<br />[[Margaret of Parma|Margaret, Duchess of Parma]] ([[illegitimate]])|house          =[[House of Habsburg]]|father         =[[Philip I of Castile]]|mother         =[[Joanna of Castile]]|birth_date     =24 February 1500|birth_place    =[[Ghent]], Flanders|death_date     =21 September 1558 (aged 58)|death_place    =[[Monastery of Yuste|Yuste]], Spain|place of burial=[[El Escorial]], [[San Lorenzo de El Escorial]], Spain|religion       =[[Roman Catholicism]]|signature      =Firma_Emperador_Carlos_V.svg|death_cause    = black plague}}";
    private static final String EXP3 = "{{Infobox royalty| name            = George II| image           = [[File:George II by Thomas Hudson.jpg|235px]]| caption         = Portrait by [[Thomas Hudson (painter)|Thomas Hudson]], 1744| alt             = George sitting on a throne| succession      = [[King of Great Britain]] [[King of Ireland|and Ireland]]<br />[[Electorate of Brunswick-Lüneburg|Elector of Hanover]]| moretext        = ([[Style of the British sovereign#Styles of British sovereigns|more...]])| reign           = 11/22{{ref|dates|O.S./N.S.}} June 1727&nbsp;-<br /> 25 October 1760| coronation      = 11/22{{ref|dates|O.S./N.S.}} October 1727| cor-type        = britain| predecessor     = [[George I of Great Britain|George I]]| successor       = [[George III of the United Kingdom|George III]]| reg-type        = [[Prime Minister of the United Kingdom|Prime&nbsp;Ministers]]| regent          = {{Collapsible list |title=''See list'' |{{plainlist|* [[Robert Walpole]]* [[Spencer Compton, 1st Earl of Wilmington|Lord Wilmington]]* [[Henry Pelham]]* [[Thomas Pelham-Holles, 1st Duke of Newcastle|The Duke of Newcastle]]* [[William Cavendish, 4th Duke of Devonshire|The Duke of Devonshire]]}}}}| spouse          = [[Caroline of Ansbach]]| issue           = {{plainlist|* [[Frederick, Prince of Wales]]* [[Anne, Princess Royal and Princess of Orange]]* [[Princess Amelia of Great Britain|Princess Amelia]]* [[Princess Caroline of Great Britain|Princess Caroline]]* [[Prince George William of Great Britain|Prince George William]]* [[Prince William, Duke of Cumberland]]* [[Princess Mary of Great Britain|Princess Mary, Landgravine of Hesse]]* [[Louise of Great Britain|Louisa, Queen of Denmark and Norway]]}}| issue-link      = #Issue| full name       = George Augustus<br >{{lang-de|link=no|Georg August}}| house           = [[House of Hanover]]| father          = [[George I of Great Britain|George I]]| mother          = [[Sophia Dorothea of Celle]]| religion        = [[Lutheran]]| birth_date      = 30 October / 9 November 1683{{ref|dates|O.S./N.S.}}| birth_place     = [[Herrenhausen Gardens|Herrenhausen Palace]],<ref name=cannon>Cannon.</ref> or [[Leineschloss|Leine Palace]],<ref>Thompson, p. 10.</ref> [[Hanover]], [[Electorate of Brunswick-Luneburg]], [[Holy Roman Empire]]| death_date      = {{Death date and age|1760|10|25|1683|11|9|df=yes}}| death_place     = [[Kensington Palace]], [[London]], [[England]], [[Kingdom of Great Britain]]| date of burial  = 11 November 1760| place of burial = [[Westminster Abbey]], London| signature       = Firma del Rey George II.svg|}}";

//    @Test
    public void testGetTemplateOffline() throws IOException {
        TemplateParser parser = new TemplateParser();

        Optional<String> template = parser.getTemplate(offlineTestPage("1.txt"), "Infobox royalty");
        System.out.println(template.get());

        template = parser.getTemplate(offlineTestPage("2.txt"), "Infobox royalty");
        System.out.println(template.get());

        template = parser.getTemplate(offlineTestPage("3.txt"), "Infobox royalty");
        System.out.println(template.get());
    }

//    @Test
    public void testGetTemplateOnline() throws IOException {
        TemplateParser parser = new TemplateParser();

        Optional<String> template = parser.getTemplate(testPage("William_III_of_England"), "Infobox royalty");
        System.out.println(template.get());

        template = parser.getTemplate(testPage("Charles_V,_Holy_Roman_Emperor"), "Infobox royalty");
        System.out.println(template.get());

        template = parser.getTemplate(testPage("George_II_of_Great_Britain"), "Infobox royalty");
        System.out.println(template.get());
    }

    @Test
    public void test1() throws Exception {
        TemplateParser parser = new TemplateParser();

        Optional<String> template = parser.getTemplate(testPage("William_III_of_England"), "Infobox royalty");
//        Optional<String> template = parser.getTemplate(testPage("Charles_V,_Holy_Roman_Emperor"), "Infobox royalty");
//        Optional<String> template = parser.getTemplate(testPage("George_II_of_Great_Britain"), "Infobox royalty");
//        Optional<String> template = parser.getTemplate(testPage("Winston_Churchill"), "Infobox prime minister");
        System.out.println(template.get());

        System.out.println();

        Map<String,String> data = parser.getTemplateData(template.get());
        for (Map.Entry<String,String> entry : data.entrySet()) {
            System.out.println("  " + entry.getKey() + " : " + entry.getValue());
        }

        System.out.println();

        Royalty royalty = parser.getRoyalty(data);
        System.out.println(royalty);
    }


    private String testPage(String page) {
        MediaWikiBot mediaWikiBot = new MediaWikiBot( "http://en.wikipedia.org/w/" );
        Article article = mediaWikiBot.getArticle(page);
        return article.getSimpleArticle().getText();
    }

    private String offlineTestPage(String file) {
        return getStringFromInputStream( this.getClass().getClassLoader().getResourceAsStream(file) );
    }

    private String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}
