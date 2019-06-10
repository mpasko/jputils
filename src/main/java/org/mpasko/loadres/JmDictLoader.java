/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.loadres;

import org.mpasko.configuration.RuntimeSetup;
import org.mpasko.loadres.loaderfilters.IJmDictFilter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.util.StringUtils;
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class JmDictLoader {

    private static List<String> acceptable = Arrays.asList("keb", "reb", "gloss", "field");

    public static Dictionary loadDictionary() {
        final DefaultFilter filter = new DefaultFilter();
        Dictionary dict = new JmDictLoader().load(filter);
        return dict;
    }

    //private List<String> keb = new LinkedList<String>();
    //private List<String> reb = new LinkedList<String>();
    //private List<String> gloss = new LinkedList<String>();
    private Dictionary dict = new Dictionary();
    private Map<String, LinkedList<String>> map = new HashMap<String, LinkedList<String>>();

    public Dictionary load(DefaultFilter filter) {
        RuntimeSetup.configure();
        XMLEventReader eventReader = null;
        String tagContent = "";
        String lastTag = "";
        flush();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            FileInputStream file = new FileInputStream("xml/JMdict.xml");
            InputStreamReader isr = new InputStreamReader(file, "UTF-8");
            eventReader = factory.createXMLEventReader(isr);
            //new FileReader("xml/kanjidic2.xml", "UTF-8")
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                String name;
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        lastTag = newElementStarts(event);
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (acceptable.contains(lastTag)) {
                            tagContent = StringUtils.clear(event.asCharacters().getData().trim());
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        elementEnds(event, tagContent, filter);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(JmDictLoader.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        } finally {
            try {
                if (eventReader != null) {
                    eventReader.close();
                }
            } catch (XMLStreamException ex) {
                Logger.getLogger(JmDictLoader.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        }
        return dict;
    }

    private void flush() {
        //reb = new LinkedList<String>();
        //gloss = new LinkedList<String>();
        //keb = new LinkedList<String>();
        for (String x : acceptable) {
            map.put(x, new LinkedList<String>());
        }
    }

    private String newElementStarts(XMLEvent event) {
        StartElement startElement = event.asStartElement();
        String lastTag = startElement.getName().getLocalPart();
        if (lastTag.equalsIgnoreCase("entry")) {
            flush();
        }
        return lastTag;
    }

    private void elementEnds(XMLEvent event, String tagContent, IJmDictFilter filter) {
        String name;
        name = event.asEndElement().getName().getLocalPart();
        if (name.equalsIgnoreCase("entry")) {
            /*if (!map.get("field").isEmpty()) {
                System.out.println(map.get("field"));
            }*/
            if (filter.matches(map)) {
                for (String keb_item : map.get("keb")) {
                    dict.put(keb_item, Util.stringifyList(map.get("reb")), Util.stringifyList(map.get("gloss")));
                }
            }
        } else if (acceptable.contains(name)) {
            LinkedList<String> list = map.get(name);
            if (list == null) {
                throw new RuntimeException(String.format("List [%s] not found", name));
            }
            list.add(tagContent);
        }
    }

    public static class DefaultFilter implements IJmDictFilter {

        private Map<String, String> exactFilters = new HashMap<String, String>();

        public DefaultFilter addExactFilter(String key, String value) {
            exactFilters.put(key, value);
            return this;
        }

        public DefaultFilter addFieldFilter(String field) {
            exactFilters.put("field", field);
            return this;
        }

        public boolean matches(Map<String, LinkedList<String>> itemMap) {
            boolean matches = true;
            for (String key : exactFilters.keySet()) {
                String filterValue = exactFilters.get(key);
                LinkedList<String> itemProperty = itemMap.get(key);
                if (itemProperty == null) {
                    throw new RuntimeException(String.format("Filter key not found! [%s]", key));
                }
                boolean isAny = matchesAny(itemProperty, filterValue);
                matches &= isAny;
            }
            return matches;
        }

        private boolean matchesAny(LinkedList<String> itemProperty, String filterValue) {
            boolean isAny = false;
            for (String tag : itemProperty) {
                isAny |= tag.contains(filterValue);
            }
            return isAny;
        }
    }
}
