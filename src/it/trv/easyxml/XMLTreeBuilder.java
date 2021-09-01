package it.trv.easyxml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

/*
Class that provides functions to parse XML code into an XMLElement.
 */
public class XMLTreeBuilder {

    /*
    Parses into an XMLElement the XML code contained in a string.
    Parameters: string contains the XML code
    Returns: the parsed XMLElement containing the whole XML tree
    Throws: ParseException if there's an error in the XML code
     */
    public static XMLElement buildFromString(String string) throws ParseException {
        try{
            return buildFromStream(new ByteArrayInputStream(string.getBytes()));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    /*
    Parses into an XMLElement the XML code contained in a file.
    Parameters: path contains the path of the file
    Returns: the parsed XMLElement containing the whole XML tree
    Throws: ParseException if there's an error in the XML code
            FileNotFoundException if the file in the specified path does not exist
     */
    public static XMLElement buildFromFile(String path) throws FileNotFoundException, ParseException {
        File file = new File(path);
        return buildFromFile(file);
    }


    /*
    Parses into an XMLElement the XML code contained in a file.
    Parameters: file is the file
    Returns: the parsed XMLElement containing the whole XML tree
    Throws: ParseException if there's an error in the XML code
            FileNotFoundException if the file does not exist
     */
    public static XMLElement buildFromFile(File file) throws FileNotFoundException, ParseException {
        try{
            return buildFromStream(new FileInputStream(file));
        } catch (FileNotFoundException e){
            throw e;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }



    /*
    Parses into an XMLElement the XML code read from an InputStream.
    Parameters: stream is the InputStream
    Returns: the parsed XMLElement containing the whole XML tree
    Throws: ParseException if there's an error in the XML code
            IOException if there's an error while reading from the InputStream
     */
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

    /*
    Takes an Element and creates its corresponding XMLElement.
     */
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
        String[] temp;
        StringBuilder content;
        while( childNode!=null ){
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                result.addChild(buildFromElement((Element) childNode));
            }else if(childNode.getNodeType() == Node.TEXT_NODE && childNode.getTextContent().trim().length()>0){
                temp = childNode.getTextContent().trim().split("\n");
                content = new StringBuilder();
                for(int i=0;i<temp.length;i++){
                    content.append(temp[i].trim()).append("\n");
                }
                if(content.length()>0){
                    result.appendTextContent(content.toString().substring(0,content.length()-1));
                }
            }
            childNode = childNode.getNextSibling();
        }
        return result;
    }
}
