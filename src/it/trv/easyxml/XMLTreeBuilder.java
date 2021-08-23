package it.trv.easyxml;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.ParseException;

/*
Class that provides functions to parse XML text into an XMLElement.
 */
public class XMLTreeBuilder {
    public static XMLElement buildFromString(String string) throws ParseException {
        try{
            return buildFromStream(new ByteArrayInputStream(string.getBytes()));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static XMLElement buildFromFile(String path) throws FileNotFoundException, ParseException {
        File file = new File(path);
        return buildFromFile(file);
    }

    public static XMLElement buildFromFile(File file) throws FileNotFoundException, ParseException {
        try{
            return buildFromStream(new FileInputStream(file));
        } catch (FileNotFoundException e){
            throw e;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static XMLElement buildFromStream(InputStream stream) throws ParseException, IOException {
        Document document;
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(stream);
        }catch (SAXException | ParserConfigurationException e){
            throw new ParseException(e.getLocalizedMessage(),-1);
        }

        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();

        return buildFromElement(root);
    }

    private static XMLElement buildFromElement(Element element){
        XMLElement result = new XMLElement(element.getTagName());
        NamedNodeMap attributesMap = element.getAttributes();
        //sets the attributes
        Attr attribute;
        for(int i=0;i<attributesMap.getLength();i++){
            attribute = (Attr) attributesMap.item(i);
            result.addAttribute(attribute.getName(),attribute.getValue());
        }
        //adds the children and reads the text content
        Node childNode = element.getFirstChild();
        while( childNode!=null ){
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                result.addChild(buildFromElement((Element) childNode));
            }else if(childNode.getNodeType() == Node.TEXT_NODE && childNode.getTextContent().trim().length()>0){
                result.appendTextContent(childNode.getTextContent());
            }
            childNode = childNode.getNextSibling();
        }
        return result;
    }
}
