package org.ib.history.wiki.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ib.history.wiki.domain.WikiNamedResource;
import org.ib.history.commons.tuples.Tuple2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TemplateParser {

    private static Logger logger = LogManager.getLogger(TemplateParser.class);

    private static final char TEMPLATE_START_CHAR = '{';
    private static final char TEMPLATE_END_CHAR = '}';
    private static final char[] TEMPLATE_START = {TEMPLATE_START_CHAR, TEMPLATE_START_CHAR};
    private static final char[] TEMPLATE_END = {TEMPLATE_END_CHAR, TEMPLATE_END_CHAR};
    private static final char[] LINK_START = {'[', '['};
    private static final char[] LINK_END = {']', ']'};
    private static final char DATA_SEPARATOR = '|';
    private static final char KEY_VALUE_SEPARATOR = '=';

    Optional<String> getTemplate(String wikiText, String... requestedTemplateNames) {
        Stack<Tuple2<String,Integer>> templateStack = new Stack<>();

        wikiText = wikiText.replaceAll("[\\r\\n]+", "");

        for (int i=1; i<wikiText.length(); i++) {
            char cprev = wikiText.charAt(i-1);
            char ci = wikiText.charAt(i);
            char[] cc = {cprev,ci};

            if (Arrays.equals(TEMPLATE_START, cc)) {
                int next_separator = wikiText.indexOf(DATA_SEPARATOR, i+1);
                int next_template_end = wikiText.indexOf(TEMPLATE_END_CHAR, i+1);
                if ((next_separator > next_template_end) || (next_separator == -1)) {
                    // not a template
                    // e.g. {{Good article}}
                    i = next_template_end + 1;
                    continue;
                }

                String templateName = wikiText.substring(i+1, next_separator).toLowerCase();
                logger.trace("start template: {}", templateName);
                templateStack.push(new Tuple2<String, Integer>(templateName, i-1));
                i = next_separator;

                i = next_separator;
            }

            if (Arrays.equals(TEMPLATE_END, cc)) {
                Tuple2<String,Integer> templateData = templateStack.pop();
                logger.trace("end template: {}", templateData.element1());

                List<String> requestedTemplateNamesLowercase = Stream.of(requestedTemplateNames).map(s -> s.toLowerCase()).collect(Collectors.toList());
                if (requestedTemplateNamesLowercase.contains(templateData.element1().trim())) {
                    String template = wikiText.substring(templateData.element2(), i+1);
                    logger.trace("template: {}", template);
                    return Optional.of(template);
                }

                i++;
            }
        }

        return Optional.empty();
    }

    Map<String,String> getTemplateDataMap(String template) {
        Map<String,String> data = new LinkedHashMap<>();

        getTemplateData(template)
                .stream()
                .forEach( datum -> {
                            logger.trace("datum={}", datum);
                            int keyValueSeparatorPos = datum.indexOf(KEY_VALUE_SEPARATOR);
                            if (keyValueSeparatorPos != -1) {
                                String key = datum.substring(0, keyValueSeparatorPos /*- 1*/).trim();
                                logger.trace("key={}", key);
                                String value = datum.substring(keyValueSeparatorPos + 1, datum.length()).trim();
                                data.put(key, value);
                                logger.trace("put: {}={}", key, value);
                            }
                        }
                );

        return data;
    }

    List<String> getTemplateData(String template) {
        List<String> data = new ArrayList<>();

        int level=0;

        int prevSeparator = -1;

        for (int i=1; i<template.length(); i++) {
            char c1 = template.charAt(i-1);
            char c2 = template.charAt(i);
            char[] cc = {c1,c2};

            if (Arrays.equals(TEMPLATE_START, cc) || Arrays.equals(LINK_START, cc)) {
                level++;
                i++;
            }

            if (Arrays.equals(TEMPLATE_END, cc) || Arrays.equals(LINK_END, cc)) {
                if (level==1) {
                    String datum = template.substring(prevSeparator, i-1).trim();
                    data.add(datum);
//                    System.out.println("datum: " + datum);
                }

                level--;
                i++;
            }

            if (c1==DATA_SEPARATOR && level==1) {
                if (prevSeparator >= 0) {
                    String datum = template.substring(prevSeparator, i-1).trim();
                    data.add(datum);
//                    System.out.println("datum: " + datum);
                }
                prevSeparator = i;
            }
        }

        return data;
    }

    WikiNamedResource parseLink(String link) {
        link = link.replace("[", "").replace("]", "");
        String[] tokens = link.split("\\|");
        if (tokens.length==1) {
            return new WikiNamedResource(link);
        }

        return new WikiNamedResource(tokens[0], tokens[1]);
    }

    List<WikiNamedResource> getLinks(String data) {
        List<WikiNamedResource> links = new ArrayList<>(3);

        // maybe
        // [\p{Latin}\p{Punctuation}\p{Math_Symbol}]
        // \p{M}
        Pattern pattern = Pattern.compile("\\[\\[[\\p{L}|\\s|\\d|\\||,|\\-|\\(|\\)|–|#|\\&|/|\\.]+\\]\\]");
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            String link = matcher.group();
            WikiNamedResource pageLink = parseLink(link);
            links.add(pageLink);
        }

        return links;
    }

    public String removeLinks(String withLinks) {
        List<WikiNamedResource> links = getLinks(withLinks);

        StringBuilder sb = new StringBuilder();
        boolean isLink = false;
        int linkPos = 0;

        for (int i=1; i<withLinks.length(); i++) {
            char c1 = withLinks.charAt(i-1);
            char c2 = withLinks.charAt(i);
            char[] cc = {c1,c2};


            if (Arrays.equals(LINK_START, cc)) {
                isLink = true;
                i++;
                continue;
            }

            if (Arrays.equals(LINK_END, cc)) {
                isLink = false;
                sb.append(links.get(linkPos++).getDisplayText());
                i++;
                continue;
            }

            if (!isLink) {
                sb.append(c1);

                if (i == withLinks.length() - 1) {
                    sb.append(c2);
                }
            }
        }

        return sb.toString();
    }

    public String removeTags(String text, String... tagNames) {
        String temp=text;

        for (String tagName : tagNames) {
            temp = removeTemplate(temp, tagName);
            temp = removeXml(temp, tagName);
        }

        return temp;
    }

    public String removeTemplate(String text, String templateName) {
        String pattern = "\\{\\{" + templateName + ".*\\}\\}";
        return text.replaceAll(pattern, "");
    }

    public String removeXml(String text, String xmlTag) {
        String temp = text;
        String pattern;

        pattern = "<" + xmlTag + ">.*/" + xmlTag + ">";
        temp = temp.replaceAll(pattern, "");

        pattern = "<" + xmlTag + ">";
        temp =  temp.replaceAll(pattern, "");

        pattern = "<" + xmlTag + "/>";
        temp =  temp.replaceAll(pattern, "");

        return temp;
    }

    public String removeRef(String text) {
        int startPos = text.indexOf("<ref>");
        return (startPos>-1) ? text.substring(0,startPos) : text;
    }


    public List<String> sentenceToWords(String sentence) {
        String[] words = sentence.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            // You may want to check for a non-word character before blindly
            // performing a replacement
            // It may also be necessary to adjust the character class
            words[i] = words[i].replaceAll("[^\\w]", "");
        }
        return Arrays.asList(words);
    }
}
