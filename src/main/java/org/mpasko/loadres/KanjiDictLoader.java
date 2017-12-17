/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.loadres;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.mpasko.commons.KanjiDictionary;
import org.mpasko.commons.KanjiEntry;
import org.mpasko.util.NumericUtil;
import org.mpasko.util.SimpleUtils;
import org.mpasko.util.StringUtils;

/**
 *
 * @author marcin
 */
public class KanjiDictLoader {
    private static List<String> acceptable = Arrays.asList("literal","reading","meaning","grade","stroke_count","freq");
    public String tagContent;
    public String lastTag;
    public String attrContent;
    public StringBuilder currMeaning;
    public List<String> kunyomi;
    public List<String> onyomi;
    public List<String> pinyin;
    public int currGrade;
    public int currStroke;
    public int currFreq;
    public String currKanji;

    public KanjiDictionary load(Filter filter) {
        KanjiDictionary dict = new KanjiDictionary();
        XMLEventReader eventReader = null;
        tagContent = "";
        lastTag = "";
        attrContent = "";
        currKanji = "";
        initialize();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            FileInputStream file = new FileInputStream("xml/kanjidic2.xml");
            InputStreamReader isr = new InputStreamReader(file, "UTF-8");
            eventReader = factory.createXMLEventReader(isr);
            //new FileReader("xml/kanjidic2.xml", "UTF-8")
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                String name;
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        startElem(event);
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        characters(event);
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        endElem(event, filter, dict);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(KanjiDictLoader.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        } finally {
            try {
                if (eventReader != null) {
                    eventReader.close();
                }
            } catch (XMLStreamException ex) {
                Logger.getLogger(KanjiDictLoader.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        }
        return dict;
    }

    public void initialize() {
        currMeaning = new StringBuilder();
        kunyomi = new LinkedList<String>();
        onyomi = new LinkedList<String>();
        pinyin = new LinkedList<String>();
        currKanji = "";
        currGrade=0;
        currStroke=0;
        currFreq=0;
    }

    public void endElem(XMLEvent event, Filter filter, KanjiDictionary dict) {
        String name;
        name = event.asEndElement().getName().getLocalPart();
        if (name.equalsIgnoreCase("character")) {
            KanjiEntry kanji = new KanjiEntry();
            kanji.character = currKanji;
            kanji.onyomi = onyomi;
            kanji.kunomi = kunyomi;
            kanji.pinyin = pinyin; 
            kanji.meaning = currMeaning.toString();
            if (filter.matches(kanji, currGrade, currStroke, currFreq)){
                //dict.addItem(currKanji, currMeaning.toString(), onyomi, kunyomi);
                dict.put(kanji);
            }
        } else if (name.equalsIgnoreCase("literal")) {
            currKanji = tagContent;
        } else if (name.equalsIgnoreCase("reading")) {
            if (attrContent.equalsIgnoreCase("ja_on")){
                onyomi.add(tagContent);
            } else if (attrContent.equalsIgnoreCase("ja_kun")){
                kunyomi.add(tagContent);
            } else if (attrContent.equalsIgnoreCase("pinyin")){
                pinyin.add(tagContent);
            }
        } else if (name.equalsIgnoreCase("meaning")) {
            if (attrContent.isEmpty()) {
                currMeaning.append(tagContent).append(",");
            }
        } else if (name.equalsIgnoreCase("grade")) {
            currGrade = NumericUtil.parseIntegerDefault(tagContent, 0);
        } else if (name.equalsIgnoreCase("stroke_count")) {
            currStroke = NumericUtil.parseIntegerDefault(tagContent, 0);
        } else if (name.equalsIgnoreCase("freq")) {
            currFreq = NumericUtil.parseIntegerDefault(tagContent, 0);
        }
    }

    public void characters(XMLEvent event) {
        if (acceptable.contains(lastTag)) {
            tagContent = StringUtils.clear(event.asCharacters().getData().trim());
        }
    }

    public void startElem(XMLEvent event) {
        StartElement startElement = event.asStartElement();
        Iterator iterator = startElement.getAttributes();
        lastTag = startElement.getName().getLocalPart();
        attrContent="";
        while (iterator.hasNext()) {
            Attribute attr = (Attribute) iterator.next();
            attrContent=attr.getValue();
        }
        if (lastTag.equalsIgnoreCase("character")) {
            initialize();
        }
    }
    
    public static class Filter {
        public int gradeFrom = 0;
        public int gradeTo = 0;
        public int strokeFrom = 0;
        public int strokeTo = 0;
        public int freqFrom = 0;
        public int freqTo = 0;
        
        public boolean matches(KanjiEntry kanji, int grade, int strokes, int freq) {
            boolean matches = true;
            if (gradeFrom!=0) {matches &= grade >= gradeFrom;}
            if (gradeTo!=0) {matches &= grade <= gradeTo;}
            if (strokeFrom!=0) {matches &= grade >= strokeFrom;}
            if (strokeTo!=0) {matches &= grade <= strokeTo;}
            if (freqFrom!=0) {matches &= grade >= freqFrom;}
            if (freqTo!=0) {matches &= grade <= freqTo;}
            return matches;
        }
    }
}
