package com.example.telegabot.XMLParser;

import com.example.telegabot.Currency.Currency;
import org.apache.commons.io.FileUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class SaxXMLParser {

    private static ArrayList<Currency> curList = new ArrayList<>();

    public static String getInfo(String curr) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        URL url = new URL("https://www.nationalbank.kz/rss/rates_all.xml");
        File file = new File("rates.xml");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileUtils.copyURLToFile(url, file);

        AdvancedXMLHandler handler = new AdvancedXMLHandler();
        parser.parse(file, handler);

        for (Currency currency : curList) {
            if (currency.getTitle().equals(curr))
               return String.format("Валюта: %s, дата публикации: %s, значение в тенге: %s, источник: nationalbank.kz", currency.getTitle(), currency.getPubDate(), currency.getValue());
        }
        return null;
    }

    private static class AdvancedXMLHandler extends DefaultHandler {
        private String title, pubDate, lastElementName, description;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            lastElementName = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            String information = new String(ch, start, length);

            information = information.replace("\n", "").trim();

            if (!information.isEmpty()) {


                    if (lastElementName.equals("title") && information.length() == 3)
                        title = information;
                    if (lastElementName.equals("pubDate"))
                        pubDate = information;
                    if (lastElementName.equals("description") && information.length() < 8)
                        description = information;

            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if ( (title != null && !title.isEmpty()) && (pubDate != null && !pubDate.isEmpty()) && (description != null && !description.isEmpty()) ) {
                curList.add(new Currency(title, pubDate, description));
                title = null;
                pubDate = null;
                description = null;
            }
        }
    }
}

