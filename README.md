# EasyXML
A Java library to easily parse, unparse and manipulate XML.

# Index
* [Release notes](#release-notes)
* [Introduction](#introduction)
* [Documentation](#documentation)
  * [XMLElement](#xmlelement)
    * [XMLElement(String tagName)](#xmlelementstring-tagname)
    * [String getTagName()](#string-gettagname)
    * [String getTextContent()](#string-gettextcontent)
    * [void setTextContent(String content)](#void-settextcontentstring-content)
    * [void appendTextContent(String content)](#void-appendtextcontentstring-content)
    * [void addAttribute(String name, String value)](#void-addattributestring-name-string-value)
    * [String getAttribute(String name)](#string-getattributestring-name)
    * [boolean hasAttribute(String name)](#boolean-hasattributestring-name)
    * [String removeAttributeByName(String name)](#string-removeattributebynamestring-name)
    * [HashMap&lt;String, String&gt; getAllAttributes()](#hashmapstring-string-getallattributes)
    * [Iterator&lt;Map.Entry&lt;String, String&gt;&gt; getAttributesIterator()](#iteratormapentrystring-string-getattributesiterator)
    * [void addChild(XMLElement child)](#void-addchildxmlelement-child)
    * [void addChild(int i, XMLElement child)](#void-addchildint-i-xmlelement-child)
    * [void removeChild(XMLElement child)](#void-removechildxmlelement-child)
    * [void removeChild(int i)](#void-removechildint-i)
    * [XMLElement getChildAt(int i)](#xmlelement-getchildatint-i)
    * [int indexOfChild(XMLElement child)](#int-indexofchildxmlelement-child)
    * [int getNumberOfChildren()](#int-getnumberofchildren)
    * [ArrayList&lt;XMLElement&gt; getAllChildren()](#arraylistxmlelement-getallchildren)
    * [Iterator&lt;XMLElement&gt; getChildrenIterator()](#iteratorxmlelement-getchildreniterator)
    * [void swapChildrenPosition(int i, int j)](#void-swapchildrenpositionint-i-int-j)
    * [void moveChildPositionUp(int i, int n)](#void-movechildpositionupint-i-int-n)
    * [void moveChildPositionDown(int i, int n)](#void-movechildpositiondownint-i-int-n)
    * [ArrayList&lt;XMLElement&gt; getDescendantsWithTag(String tagName)](#arraylistxmlelement-getdescendantswithtagstring-tagname)
    * [String toString()](#string-tostring)
    * [String toString(String tabulationCharacters)](#string-tostringstring-tabulationcharacters)
    * [Object clone()](#object-clone)
  * [XMLTreeBuilder](#xmltreebuilder)
    * [XMLElement buildFromString(String string)](#xmlelement-buildfromstringstring-string)
    * [XMLElement buildFromFile(String path)](#xmlelement-buildfromfilestring-path)
    * [XMLElement buildFromFile(File file)](#xmlelement-buildfromfilefile-file)
    * [XMLElement buildFromStream(InputStream stream)](#xmlelement-buildfromstreaminputstream-stream)
* [Examples](#examples)
* [Conclusion](#conclusion)

# Release notes
`none yet`

# Introduction
The scope of this library is to provide an instrument to easily parse, unparse and manipulate XML code. Unlike other Java libraries that focus mostly on high speed parsing, this library focuses on ease of use and maximum flexibility. This library is thus ideal for projects that don't need extremely high performance XML parsing, but instead need a simple and ready-to-use parser of XML and/or the ability to freely inspect and manipulate XML items.

The parser in this library is based on the Java DOM parser.

All the classes in this library are contained in the package "`it.trv.easyxml`".

# Documentation
This library is composed by two classes: `XMLElement` represents a single XML element and provides methods to access and modify its data and its children; `XMLTreeBuilder` provides various functions to parse XML code into an XMLElement.  
Both these classes are contained in the package "`it.trv.easyxml`".

## XMLElement
The XMLElement class represents an XML element with its text content, attributes and children. The tag name is immutable and is set at the moment of the object initialization, all the other features can be modified.  
XMLElement implements Cloneable.

### XMLElement(String tagName)
Constructor for an XMLElement. It takes the tag name, which is immutable.  
Parameters:  
&ensp;&ensp;`tagName` is the tag name.

### String getTagName()
Returns the tag name of this XMLElement.  
Returns:  
&ensp;&ensp;the tag name of this XMLElement

### String getTextContent()
Returns the text content of this XMLElement.  
Returns:  
&ensp;&ensp;the text content of this XMLElement

### void setTextContent(String content)
Replaces the text content of this XMLElement with the string passed as input.  
Parameters:  
&ensp;&ensp;`content` is the new text content of this XMLElement

### void appendTextContent(String content)
Appends the string passed as input to the text content of this XMLElement.  
Parameters:  
&ensp;&ensp;`content` is the new text that must be appended

### void addAttribute(String name, String value)
Adds to this XMLElement a new attribute by specifying its name and value. If an attribute with the same name already exists, it is replaced.  
Parameters:  
&ensp;&ensp;`name` is the name of the attribute that must be added  
&ensp;&ensp;`value` is the value of the attribute that must be added

### String getAttribute(String name)
Returns the value of the attribute that has a specific name.  
Parameters:  
&ensp;&ensp;`name` is the name of the attribute whose value must be returned  
Returns:  
&ensp;&ensp;the value of the attribute with that specific name  
Throws:  
&ensp;&ensp;`NoSuchElementException` if this XMLElement has no attribute with that name

### boolean hasAttribute(String name)
Returns true if this instance of XMLElement has a specific attribute.  
Parameters:  
&ensp;&ensp;`name` is the name of the attribute  
Returns:  
&ensp;&ensp;true if this instance of XMLElement has an attribute named `name`, false otherwise

### String removeAttributeByName(String name)
Removes a specific attribute from this XMLElement and returns the value of that attribute. If that attribute does not exist, it returns null.  
Parameters:  
&ensp;&ensp;`name` is the name of the attribute that must be removed  
Returns:  
&ensp;&ensp;the value of the attribute that is removed, null if the attribute was not present

### HashMap&lt;String, String&gt; getAllAttributes()
Returns a HashMap that contains all the attributes of this instance of XMLElement.  
Returns:  
&ensp;&ensp;a HashMap&lt;String, String&gt; containing all the attributes of this XMLElement

### Iterator&lt;Map.Entry&lt;String, String&gt;&gt; getAttributesIterator()
Returns a fail-fast Iterator that iterates over all the attributes of this instance of XMLElement.  
Returns:  
&ensp;&ensp;an Iterator&lt;Map.Entry&lt;String, String&gt;&gt; that iterates over all the attributes of this XMLElement

### void addChild(XMLElement child)
Adds an XMLElement to the children of this XMLElement.  
Parameters:  
&ensp;&ensp;`child` is the XMLElement that must be added to the children of this XMLElement  
Throws:  
&ensp;&ensp;`IllegalArgumentException` if `child` is an ancestor of this XMLElement or if `this` and `child` are the same object

### void addChild(int i, XMLElement child)
Adds an XMLElement to the children of this XMLElement at the specific position in the list of children.  
Shifts the child currently at that position (if any) and any subsequent children to the right (adds one to their indices).  
Parameters:  
&ensp;&ensp;`i` is the index at which the new child will be located  
&ensp;&ensp;`child` is the XMLElement that must be added to the children of this XMLElement  
Throws:  
&ensp;&ensp;`IllegalArgumentException` if child is an ancestor of this XMLElement or if this and child are the same object

### void removeChild(XMLElement child)
Removes an XMLElement from the children of this XMLElement. Only the direct children are removed, not the other descendants.  
Parameters:  
&ensp;&ensp;`child` is the children that must be removed  
Throws:  
&ensp;&ensp;`NoSuchElementException` if `child` is not a child of this XMLElement.

### void removeChild(int i)
Removes the child at the specified position from the list of children of this XMLElement.  
Parameters:  
&ensp;&ensp;`i` is the index of the child that must be removed from the list of children of this XMLElement

### XMLElement getChildAt(int i)
Returns the child located at the specified position in the list of the children of this XMLElement.  
Parameters:  
&ensp;&ensp;`i` is the index of the child in the list of the children of this XMLElement.

### int indexOfChild(XMLElement child)
Returns the index of the first occurrence of the specified XMLElement in the list of children of this XMLElement.  
Parameters:  
&ensp;&ensp;`child` is the child whose index must be returned  
Returns:  
&ensp;&ensp;the index of the first occurrence of `child` in the list of children of this XMLElement  
Throws:  
&ensp;&ensp;`NoSuchElementException` if the specified element is not a child of this XMLElement.

### int getNumberOfChildren()
Returns the number of direct children of this instance of XMLElement.  
Returns:  
&ensp;&ensp;the number of children of this instance of XMLElement

### ArrayList&lt;XMLElement&gt; getAllChildren()
Returns an ArrayList that contains all the children of this instance of XMLElement.  
Returns:  
&ensp;&ensp;an ArrayList&lt;XMLElement&gt; containing all the children of this XMLElement

### Iterator&lt;XMLElement&gt; getChildrenIterator()
Returns a fail-fast Iterator that iterates over all the children of this instance of XMLElement.  
Returns:  
&ensp;&ensp;an Iterator&lt;XMLElement&gt; that iterates over all the children of this XMLElement

### void swapChildrenPosition(int i, int j)
Swaps the positions of two children in the children list of this instance of XMLElement.  
Parameters:  
&ensp;&ensp;`i` and `j` are the indices of the two children that must be swapped

### void moveChildPositionUp(int i, int n)
Moves a child in the children list of this instance of XMLElement "up" (that is, towards the beginning of the list) by a certain number of positions.  
Parameters:  
&ensp;&ensp;`i` is the index of the child that must be moved  
&ensp;&ensp;`n` is the number of positions that the child at i must be moved up

### void moveChildPositionDown(int i, int n)
Moves a child in the children list of this instance of XMLElement "down" (that is, towards the end of the list) by a certain number of positions.  
Parameters:  
&ensp;&ensp;`i` is the index of the child that must be moved  
&ensp;&ensp;`n` is the number of positions that the child at i must be moved down

### String toString()
Returns the XML code corresponding to this XMLElement.  
Returns:  
&ensp;&ensp;the string representation of this XMLElement.

### ArrayList&lt;XMLElement&gt; getDescendantsWithTag(String tagName)
Returns all the descendants of this XMLElement that have a specific tag name.  
Parameters:  
&ensp;&ensp;`tagName` is tag name of the descendants that must be returned  
Returns:  
&ensp;&ensp;an ArrayList&lt;XMLElement&gt; containing all the descendants of this XMLElement whose tag name is `tagName`.

### String toString(String tabulationCharacters)
Returns the text corresponding to this XMLElement, with the possibility of choosing the tabulation.  
Parameters:  
&ensp;&ensp;`tabulationCharacters` is the tabulation that will be used  
Returns:  
&ensp;&ensp;the string representation of this XMLElement.

### Object clone()
Returns a deep copy of this XMLElement instance.

## XMLTreeBuilder
The XMLTreeBuilder class provides static methods to parse XML code into an XMLElement.

### XMLElement buildFromString(String string)
Static function that parses into an XMLElement the XML code contained in a string.  
Parameters:  
&ensp;&ensp;`string` contains the XML code  
Returns:  
&ensp;&ensp;the parsed XMLElement containing the whole XML tree  
Throws:  
&ensp;&ensp;`ParseException` if there's an error in the XML code

### XMLElement buildFromFile(String path)
Static function that parses into an XMLElement the XML code contained in a file.  
Parameters:  
&ensp;&ensp;`path` contains the path of the file  
Returns:  
&ensp;&ensp;the parsed XMLElement containing the whole XML tree  
Throws:  
&ensp;&ensp;`ParseException` if there's an error in the XML code  
&ensp;&ensp;`FileNotFoundException` if the file in the specified path does not exist

### XMLElement buildFromFile(File file)
Static function that parses into an XMLElement the XML code contained in a file.  
Parameters:  
&ensp;&ensp;`file` is the file  
Returns:  
&ensp;&ensp;the parsed XMLElement containing the whole XML tree  
Throws:  
&ensp;&ensp;`ParseException` if there's an error in the XML code  
&ensp;&ensp;`FileNotFoundException` if the file does not exist

### XMLElement buildFromStream(InputStream stream)
Static function that parses into an XMLElement the XML code read from an InputStream.  
Parameters:  
&ensp;&ensp;`stream` is the InputStream  
Returns:  
&ensp;&ensp;the parsed XMLElement containing the whole XML tree  
Throws:  
&ensp;&ensp;`ParseException` if there's an error in the XML code  
&ensp;&ensp;`IOException` if there's an error while reading from the InputStream

# Examples
Coming soon...

# Conclusion
This is everything you needed to know about the EasyXML library.  
Please contact me if you find any bugs, errors in the documentation, missing features or have feedback or suggestions on how to improve the library.

If you are feeling generous, consider making a donation using one of the methods listed at the end of this document.

*Stefano Travasci*

---

Paypal: [![Donate](https://www.paypalobjects.com/en_US/IT/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/donate?hosted_button_id=9HMMFAZE248VN)

Curecoin: BTLPs7AD8fhJpVbS1a4ddvC6JMqJxVTwip

Bitcoin: bc1qf79e5jxwgvpwm59yzw3ff0fsjh8r08h04luzwg

<sub>*let me know if your favorite donation method is not in this list*</sub>
