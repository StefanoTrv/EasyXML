package it.trv.easyxml;

import java.util.*;

/*
Class that represents an XML element with its text content, attributes and children. The tag name is immutable, all the other features can be modified.
 */
public class XMLElement {
    private final String tagName;
    private String textContent;
    private HashMap<String, String> attributesMap;
    private final ArrayList<XMLElement> children;

    /*
    Constructor for an XMLElement. It takes the tag name, which is immutable.
    Parameters: tagName is the tag name.
     */
    public XMLElement(String tagName){
        this.tagName=tagName;
        textContent="";
        attributesMap=new HashMap<>();
        children=new ArrayList<>();
    }

    /*
    Returns the tag name of this XMLElement.
    Returns: the tag name of this XMLElement
     */
    public String getTagName() {
        return tagName;
    }

    /*
    Returns the text content of this XMLElement.
    Returns: the text content of this XMLElement
     */
    public String getTextContent() {
        return textContent;
    }

    /*
    Replaces the text content of this XMLElement with the string passed as input.
    Parameters: content is the new text content of this XMLElement
     */
    public void setTextContent(String content) {
        this.textContent = content;
    }

    /*
    Appends the string passed as input to the text content of this XMLElement.
    Parameters: content is the new text that must be appended
     */
    public void appendTextContent(String content) {
        this.textContent = this.textContent + content;
    }

    /*
    Adds to this XMLElement a new attribute by specifying its name and value. If an attribute with the same name already exists, it is replaced.
    Parameters: name is the name of the attribute that must be added
                value is the value of the attribute that must be added
     */
    public void addAttribute(String name, String value){
        attributesMap.put(name,value);
    }

    /*
    Returns the value of the attribute that has a specific name.
    Parameters: name is the name of the attribute whose value must be returned
    Returns: the value of the attribute with that specific name
    Throws: NoSuchElementException if this XMLElement has no attribute with that name
     */
    public String getAttribute(String name) throws NoSuchElementException{
        String value = attributesMap.get(name);
        if (value==null){
            throw new NoSuchElementException("This XMLElement with tag name \""+tagName+"\" doesn't have an attribute with name \""+name+"\".");
        } else {
            return value;
        }
    }

    /*
    Removes a specific attribute from this XMLElement and return the value of that attribute. If that attribute does not exist, it return null.
    Parameters: name is the name of the attribute that must be removed
    Returns: the value of the attributes that is removed, null if the attribute was not present
     */
    public String removeAttributeByName(String name){
        return attributesMap.remove(name);
    }

    /*
    Adds an XMLElement to the children of this XMLElement.
    Parameters: child is the child that must be added
    Throws: IllegalArgumentException if the new child is an ancestor of this XMLElement or if this and child are the same object
     */
    public void addChild(XMLElement child) throws IllegalArgumentException{
        if (this==child) throw new IllegalArgumentException("You can't add an XMLElement to its own children.");
        if (child.isAncestorOf(this)) throw new IllegalArgumentException(child.getTagName()+" is an ancestor of "+this.getTagName()+", so it can't become its child.");
        children.add(child);
    }

    /*
    Returns: true if this is an ancestor of element, false otherwise
     */
    private boolean isAncestorOf(XMLElement element){
        for (XMLElement child : children){
            if(child.equals(element)) {
                return true;
            }else if(child.isAncestorOf(element)){
                return true;
            }
        }
        return false;
    }

    /*
    Removes an XMLElement from the children of this XMLElement. Only the direct children are removed, not the other descendants.
    Parametri: child is the children that must be removed
    Throws: NoSuchElementException if the specified element is not a child of this XMLElement.
     */
    public void removeChild(XMLElement child) throws NoSuchElementException{
        if(!children.remove(child)) throw new NoSuchElementException("Can't remove "+child.getTagName()+" from the children of "+this.getTagName()+" because "+child.getTagName()+" is not a child of "+this.getTagName()+".");
    }

    /*
    Returns the text corresponding to this XMLElement.
    Returns: the string representation of this XMLElement.
     */
    public String toString(){
        return this.toString("\t");
    }

    /*
    Returns the text corresponding to this XMLElement, with the possibility of choosing the tabulation.
    Parameters: tabulationCharacters is the tabulation that will be used
    Returns: the string representation of this XMLElement.
     */
    public String toString(String tabulationCharacters){
        StringBuilder s=new StringBuilder("<"+this.tagName);
        for (Map.Entry<String, String> attribute : attributesMap.entrySet()) {
            s.append(" ").append(attribute.getKey()).append("=\"").append(attribute.getValue()).append("\"");
        }
        s.append(">");
        if(!textContent.equals("")){
            s.append("\n").append(tabulationCharacters).append(this.textContent);
        }
        for(XMLElement child : children){
            s.append("\n");
            s.append(addTab(child.toString(),tabulationCharacters));
        }
        s.append("\n<\\").append(this.tagName).append(">");
        return s.toString();
    }

    /*
    Adds a string at the beginning of each line of another string. This is used to add a tabulation to each line.
    Parameters: s is the string to which the other string must be added at the beginning of each line.
                tabulationCharacters is the string that must be added at the begging of each line.
    Returns: the original string s with tabulationCharacters added at the beginning of each new line
     */
    private String addTab(String s, String tabulationCharacter){
        StringBuilder result=new StringBuilder(tabulationCharacter);
        for(int i=0;i<s.length();i++){
            result.append(s.charAt(i));
            if(s.charAt(i)=='\n'){
                result.append(tabulationCharacter);
            }
        }
        return result.toString();
    }
}