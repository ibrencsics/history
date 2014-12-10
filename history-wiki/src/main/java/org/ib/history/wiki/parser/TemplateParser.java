package org.ib.history.wiki.parser;

import org.ib.history.commons.tuples.Tuple2;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TemplateParser {

    private static final char[] TEMPLATE_START = {'{', '{'};
    private static final char[] TEMPLATE_END = {'}', '}'};
    private static final char[] LINK_START = {'[', '['};
    private static final char[] LINK_END = {']', ']'};
    private static final char DATA_SEPARATOR = '|';
    private static final char KEY_VALUE_SEPARATOR = '=';

//    Optional<String> getTemplate(String wikiText, String requestedTemplateName) throws IOException {
//
//        Stack<Tuple2<String,Integer>> templateStack = new Stack<>();
//
//        wikiText = wikiText.replaceAll("[\\r\\n]+", "");
//
//        for (int i=1; i<wikiText.length(); i++) {
//            char c1 = wikiText.charAt(i-1);
//            char c2 = wikiText.charAt(i);
//            char[] cc = {c1,c2};
//
//            if (Arrays.equals(TEMPLATE_START, cc)) {
//                int j = wikiText.indexOf(DATA_SEPARATOR, i+1);
//                String templateName = wikiText.substring(i+1, j);
//                templateStack.push(new Tuple2<String, Integer>(templateName, i-1));
//                i = j;
//            }
//
//            if (Arrays.equals(TEMPLATE_END, cc)) {
//                Tuple2<String,Integer> templateData = templateStack.pop();
//
//                if (templateData.element1().equals(requestedTemplateName)) {
//                    String template = wikiText.substring(templateData.element2(), i+1);
//                    return Optional.of(template);
//                }
//
//                i++;
//            }
//        }
//
//        return Optional.empty();
//    }

    Optional<String> getTemplate(String wikiText, String... requestedTemplateNames) {
        Stack<Tuple2<String,Integer>> templateStack = new Stack<>();

        wikiText = wikiText.replaceAll("[\\r\\n]+", "");

        for (int i=1; i<wikiText.length(); i++) {
            char c1 = wikiText.charAt(i-1);
            char c2 = wikiText.charAt(i);
            char[] cc = {c1,c2};

            if (Arrays.equals(TEMPLATE_START, cc)) {
                int j = wikiText.indexOf(DATA_SEPARATOR, i+1);
                String templateName = wikiText.substring(i+1, j).toLowerCase();
                templateStack.push(new Tuple2<String, Integer>(templateName, i-1));
                i = j;
            }

            if (Arrays.equals(TEMPLATE_END, cc)) {
                Tuple2<String,Integer> templateData = templateStack.pop();

                List<String> requestedTemplateNamesLowercase = Stream.of(requestedTemplateNames).map(s -> s.toLowerCase()).collect(Collectors.toList());
                if (requestedTemplateNamesLowercase.contains(templateData.element1())) {
//                if (templateData.element1().equals(requestedTemplateName)) {
                    String template = wikiText.substring(templateData.element2(), i+1);
                    return Optional.of(template);
                }

                i++;
            }
        }

        return Optional.empty();
    }

    Map<String,String> getTemplateData(String template) {
        Map<String,String> data = new HashMap<>();

        Stack<Tuple2<String,Integer>> tagStack = new Stack<>();
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
                level--;
                i++;
            }

            if (c1==DATA_SEPARATOR && level==1) {
                if (prevSeparator >= 0) {
                    String datum = template.substring(prevSeparator, i).trim();

                    int keyValueSeparatorPos = datum.indexOf(KEY_VALUE_SEPARATOR);
                    String key = datum.substring(0, keyValueSeparatorPos-1).trim();
                    String value = datum.substring(keyValueSeparatorPos+1, datum.length()-1).trim();

                    data.put(key, value);
                }
                prevSeparator = i;
            }
        }

        return data;
    }

    Royalty getRoyalty(Map<String,String> data) {
        RoyaltyParser royaltyParser = new RoyaltyParser();
        return royaltyParser.parse(data);
    }
}
