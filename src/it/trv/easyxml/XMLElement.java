package it.trv.easyxml;

import java.util.*;

/*
Class that represents an XML element with its text content, attributes and children. The tag name is immutable, all the other features can be modified.
 */
public class XMLElement implements Cloneable{
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
    Returns true if this instance of XMLElement has a specific attribute.
    Parameters: name is the name of the attribute
    Returns: true if this instance of XMLElement has an attribute named name, false otherwise
     */
    public boolean hasAttribute(String name){
        return attributesMap.get(name)!=null;
    }

    /*
    Removes a specific attribute from this XMLElement and returns the value of that attribute. If that attribute does not exist, it returns null.
    Parameters: name is the name of the attribute that must be removed
    Returns: the value of the attribute that is removed, null if the attribute was not present
     */
    public String removeAttributeByName(String name){
        return attributesMap.remove(name);
    }

    /*
    Returns a HashMap that contains all the attributes of this instance of XMLElement.
    Returns: a HashMap<String, String> containing all the attributes of this XMLElement
     */
    public HashMap<String, String> getAllAttributes(){
        return (HashMap<String, String>) attributesMap.clone();
    }

    /*
    Returns a fail-fast Iterator that iterates over all the attributes of this instance of XMLElement.
    Returns: an Iterator<Map.Entry<String, String>> that iterates over all the attributes of this XMLElement
     */
    public Iterator<Map.Entry<String, String>> getAttributesIterator(){
        return attributesMap.entrySet().iterator();
    }

    /*
    Adds an XMLElement to the children of this XMLElement.
    Parameters: child is the XMLElement that must be added to the children of this XMLElement
    Throws: IllegalArgumentException if child is an ancestor of this XMLElement or if this and child are the same object
     */
    public void addChild(XMLElement child) throws IllegalArgumentException{
        if (this==child) throw new IllegalArgumentException("You can't add an XMLElement to its own children.");
        if (child.isAncestorOf(this)) throw new IllegalArgumentException(child.getTagName()+" is an ancestor of "+this.getTagName()+", so it can't become its child.");
        children.add(child);
    }

    /*
    Adds an XMLElement to the children of this XMLElement at the specific position in the list of children.
    Shifts the child currently at that position (if any) and any subsequent children to the right (adds one to their indices).
    Parameters: i is the index at which the new child will be located
                child is the XMLElement that must be added to the children of this XMLElement
    Throws: IllegalArgumentException if child is an ancestor of this XMLElement or if this and child are the same object
     */
    public void addChild(int i, XMLElement child) throws IllegalArgumentException{
        if (this==child) throw new IllegalArgumentException("You can't add an XMLElement to its own children.");
        if (child.isAncestorOf(this)) throw new IllegalArgumentException(child.getTagName()+" is an ancestor of "+this.getTagName()+", so it can't become its child.");
        children.add(i,child);
    }

    /*
    Returns: true if this XMLElement is an ancestor of the XMLElement element, false otherwise
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
    Parameters: child is the children that must be removed
    Throws: NoSuchElementException if the specified element is not a child of this XMLElement.
     */
    public void removeChild(XMLElement child) throws NoSuchElementException{
        if(!children.remove(child)) throw new NoSuchElementException("Can't remove "+child.getTagName()+" from the children of "+this.getTagName()+" because "+child.getTagName()+" is not a child of "+this.getTagName()+".");
    }

    /*
    Removes the child at the specified position from the list of children of this XMLElement.
    Parameters: i is the index of the child that must be removed from the list of children of this XMLElement
     */
    public void removeChild(int i){
        children.remove(i);
    }

    /*
    Returns the ith child of this XMLElement.
    Parameters: i is the index of the child in the children of this XMLElement.
     */
    public XMLElement getChildAt(int i){
        return children.get(i);
    }

    /*
    Returns the index of the first occurrence of the specified XMLElement in the list of children of this XMLElement.
    Parameters: child is the child whose index must be returned
    Returns: the index of the first occurrence of child in the list of children of this XMLElement
    Throws: NoSuchElementException if the specified element is not a child of this XMLElement.
     */
    public int indexOfChild(XMLElement child) throws NoSuchElementException{
        int index = children.indexOf(child);
        if(index==-1){
            throw new NoSuchElementException(child.tagName+" is not a child of "+this.tagName);
        }else {
            return index;
        }
    }

    /*
    Returns the number of direct children of this instance of XMLElement.
    Returns: the number of children of this instance of XMLElement
     */
    public int getNumberOfChildren(){
        return children.size();
    }

    /*
    Returns an ArrayList that contains all the children of this instance of XMLElement.
    Returns: an ArrayList<XMLElement> containing all the children of this XMLElement
     */
    public ArrayList<XMLElement> getAllChildren(){
        return (ArrayList<XMLElement>) children.clone();
    }

    /*
    Returns a fail-fast Iterator that iterates over all the children of this instance of XMLElement.
    Returns: an Iterator<XMLElement> that iterates over all the children of this XMLElement
     */
    public Iterator<XMLElement> getChildrenIterator(){
        return children.iterator();
    }

    /*
    Swaps the positions of two children in the children list of this instance of XMLElement.
    Parameters: i and j are the indices of the two children that must be swapped
     */
    public void swapChildrenPosition(int i, int j){
        XMLElement temp = children.get(i);
        children.set(i,children.get(j));
        children.set(j,temp);
    }

    /*
    Moves a child in the children list of this instance of XMLElement "up" (that is, towards the beginning of the list) by a certain number of positions.
    Parameters: i is the index of the child that must be moved
                n is the number of positions that the child at i must be moved up
     */
    public void moveChildPositionUp(int i, int n){
        if(n<0){
            moveChildPositionDown(i,-n);
        }
        while(n>0&&i>0){
            swapChildrenPosition(i,i-1);
            n--;
            i--;
        }
    }

    /*
    Moves a child in the children list of this instance of XMLElement "down" (that is, towards the end of the list) by a certain number of positions.
    Parameters: i is the index of the child that must be moved
                n is the number of positions that the child at i must be moved down
     */
    public void moveChildPositionDown(int i, int n){
        if(n<0){
            moveChildPositionUp(i,-n);
        }
        while(n>0&&i<children.size()-1){
            swapChildrenPosition(i,i+1);
            n--;
            i++;
        }
    }


    /*
    Returns all the descendants of this XMLElement that have a specific tag name.
    Parameters: tagName is tag name of the descendants that must be returned
    Returns: an ArrayList<XMLElement> containing all the descendants of this XMLElement whose tag name is tagName.
    */
    public ArrayList<XMLElement> getDescendantsWithTag(String tagName) {
        ArrayList<XMLElement> result = new ArrayList<>();
        Stack<XMLElement> stack = new Stack<>();
        stack.push(this);
        XMLElement element;
        while(!stack.empty()){
            element = stack.pop();
            if(element.tagName.equals(tagName)){
                result.add(element);
            }
            for(int i=element.children.size()-1;i>=0;i--){
                stack.push(element.children.get(i));
            }
        }
        return result;
    }

    /*
        Returns the XML code corresponding to this XMLElement.
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
            s.append("\n").append(addTab(this.textContent,tabulationCharacters));
        }
        for(XMLElement child : children){
            s.append("\n");
            s.append(addTab(child.toString(tabulationCharacters),tabulationCharacters));
        }
        s.append("\n</").append(this.tagName).append(">");
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

    /*
    Returns a deep copy of this XMLElement instance.
     */
    public Object clone(){
        XMLElement copy = new XMLElement(this.tagName);
        copy.textContent=this.textContent;
        copy.attributesMap= (HashMap<String, String>) this.attributesMap.clone();
        Iterator i = this.children.iterator();
        while(i.hasNext()){
            copy.addChild((XMLElement) ((XMLElement) i.next()).clone());
        }
        return copy;
    }
}