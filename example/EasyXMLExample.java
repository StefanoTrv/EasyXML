import it.trvi.easyxml.*;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;

public class EasyXMLExample{
    public static void main(String[] args) throws FileNotFoundException, ParseException, UnsupportedEncodingException {
        
        
        
        /*
        First of all, we parse the XML file into an XMLElement. To do that, we need only the following line of code.
        */
        XMLElement root = XMLTreeBuilder.buildFromFile("example.xml");
        
        

        /*
        Now we want to print all the names and relative addresses that appear in the file.
        We use the function "getDescendantsWithTag" to extract from the file all the names and all the addresses.
        Since the results of "getDescendantsWithTag" maintain the order that they have in the original XML code,
        we can easily associate the names with their addresses.
        */
        ArrayList<XMLElement> names = root.getDescendantsWithTag("name");
        ArrayList<XMLElement> addresses = root.getDescendantsWithTag("address");
        for(int i=0;i< names.size();i++){
            System.out.println(names.get(i).getTextContent()+":\t"+addresses.get(i).getTextContent());
        }
        
        

        /*
        Now we want to append some text to the body.
        To access the body element, we use the fact that we know its position in the children of the root.
        */
        root.getChildAt(2).appendTextContent("\nWhat did you have for dinner?");
        
        

        /*
        Let's pretend that Tom Bombadil's email address is wrong: how do we change it?
        This time we access the address element using "getDescendantsWithTag" instead of its position.
        We know that it's the second address in the document, and we exploit this fact.
        */
        ArrayList<XMLElement> addresses2 = root.getDescendantsWithTag("address");
        addresses2.get(0).setTextContent("tombombadil@mail.me");
        
        

        /*
        Now we want to create an "attachment" to add at the end of the document.
        We create it from scratch and it has its own children and attributes.
        */
        XMLElement attachment = new XMLElement("attachment");
        attachment.addAttribute("size","4MB");
        attachment.addAttribute("type","binary");
        XMLElement name = new XMLElement("name");
        name.setTextContent("important_document");
        XMLElement content = new XMLElement("file_content");
        content.setTextContent("010011100110111101110100001000000110000101101100011011000010000001110100011010000110111101110011011001010010000001110111011010000110111100100000011101110110000101101110011001000110010101110010001000000110000101110010011001010010000001101100011011110111001101110100");
        attachment.addChild(name);
        attachment.addChild(content);
        root.addChild(attachment);
        
        

        /*
        We notice that this email is missing a subject.
        We decide to create one and add it in the right place of the document, just before the body.
        */
        XMLElement subject = new XMLElement("subject");
        subject.setTextContent("About a peculiar neurosurgeon");
        root.addChild(2,subject);
        
        

        /*
        The last thing we do is printing the modified XML code to a new file "out.xml".
        */
        PrintWriter writer = new PrintWriter("out.xml", "UTF-8");
        writer.print(root.toString());
        writer.close();
    }
}