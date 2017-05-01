package csiifinal;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Huffman extends Compressor {

    @Override
    public String compress(String doc) {
        
        doc = removePunctuation(doc);//remove punctuation
        
        
        //Find the number of each character
        ArrayList<CharCounter> number = new ArrayList<>();
        HashMap<Character, CharCounter> counters = new HashMap<>();
        for (int i = 0; i < doc.length(); i++) {
            char c = doc.charAt(i);
            CharCounter count = null;
            if ((count = counters.get(c)) == null) {
                count = new CharCounter(c);
                counters.put(c, count);
                number.add(count);
            } else {
                count.count++;
            }
        }

        //Sort the TreeSet
        Collections.sort(number);

        
        //Generate a key
        Object[] countsArray = number.toArray();
        HashMap<Character, String> key = new HashMap<>();
        for (int i = 0; i < countsArray.length; i++) {
            CharCounter c = ((CharCounter) countsArray[i]);
            String code = "";
            for (int k = 0; k < i; k++) {
                code += "0";
            }
            if (i < countsArray.length - 1) {
                code += "1";
            }
            key.put(c.character, code);
        }

        
        //Generate the new compressed String
        String compress = "";

        //Place the key into the String
        for (Object o : countsArray) {
            CharCounter c = (CharCounter) o;
            compress += c.character;
        }
        compress += "00000000";

        //Do the actual compression
        for (int i = 0; i < doc.length(); i++) {
            char c = doc.charAt(i);
            compress += key.get(c);
        }

        //Return
        return compress;
    }

    @Override
    public String decompress(String doc) {
        //Get the key
        int index = doc.indexOf("00000000");
        String charArray = doc.substring(0, index);
        char[] chars = charArray.toCharArray();
        TreeMap<String, Character> key = new TreeMap<>();
        for (int i = 1; i < charArray.length()+1; i++) {
            String code = "";
            for (int k = 0; k < i - 1; k++) {
                code += "0";
            }
            if (i < charArray.length() ) {
                code += "1";
            }
            key.put(code, chars[i-1]);
        }

        
        //Remove the key from the doc
        doc = doc.substring(index + 8);

        
        
        //Decode the document
        String decompress = "";
        index = 0;
        while (index < doc.length()) {
            String code = "";
            if (doc.charAt(index) == '0' || doc.charAt(index) == '1') {
                while (!code.endsWith("1") && code.length() < key.keySet().size() - 1) {
                    code += doc.charAt(index++);
                }
                decompress += key.get(code);
            } else {
                decompress += doc.charAt(index++);
            }
        }
        return decompress;
    }

    @Override
    public String getName() {
        return "Huffman";
    }

    @Override
    public String getSuffix() {
        return ".huff";
    }

    //This is a custom save method to save the document as a binary file
    @Override
    public void saveFile(String text, File file){
        
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        
        int index = text.indexOf("00000000");       //Find the separating byte
        String one = text.substring(0, index);      //Separate the output
        String two = text.substring(index+8);       
        for(int i=0;i<one.length();i++){            //Write the key into the file
            stream.write(one.charAt(i));
        }
        
        stream.write(0);                            //Write the null char into the file
        
        String byteString = "";
        for(int i=0;i<two.length();i++){            //Split binary into bytes and write it
            byteString += two.charAt(i);
            if(byteString.length() == 8){
                stream.write(Integer.parseInt(byteString,2));
                byteString = "";
            }
        }
        if(byteString.length() != 0){
            while(byteString.length()<8){
                byteString+=0;
            }
            stream.write(Integer.parseInt(byteString,2));
        }
        try {
            //Output the two byte arrays
            stream.writeTo(new FileOutputStream(file));
        } catch (IOException ex) {
            Logger.getLogger(Huffman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //This is a custom load method to load the binary document
    @Override
    public String loadFile(File file){
        String ret = "";
        BufferedInputStream stream = null;
        try {
            stream = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Huffman.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int in;
        int keySize = 0;
        try {
            while((in=stream.read()) != 0){         //Load in the key
                ret += (char)in;
                keySize++;
            }
            ret+="00000000";
            while(stream.available() >0){           //Load in the binary portion and conver to string
                int input = stream.read();
                String temp = Integer.toBinaryString(input);
                while(temp.length()<8){
                    temp = "0"+temp;
                }
                ret+=temp;
            }
        } catch (IOException ex) {
            Logger.getLogger(Huffman.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        int index = ret.length()-1;
        while(ret.charAt(index)=='0')index--;      //Remove all extra zeros
        
        
        while(ret.length()-index>=keySize-1){
            index+=keySize;
        }
        index++;
        ret = ret.substring(0, index);

        return ret;
    }
    
    @Override
    public int getSize(String comp) {
        int size = 0;
        int index = comp.indexOf("00000000");
        size += comp.substring(0, index).length();
        int tempSize = comp.substring(index).length();
        if(tempSize%8 != 0){
            tempSize+=tempSize%8;
        }
        tempSize/=8;
        size += tempSize;
        return size;
    }

    
}

class CharCounter implements Comparable {

    public char character;
    public int count;

    public CharCounter(char c) {
        character = c;
        count = 1;
    }

    @Override
    public int compareTo(Object o) {
        CharCounter c = (CharCounter) o;
        int val = -(count - c.count);
        if (val == 0) {
            val++;
        }
        return val;
    }

    @Override
    public String toString() {
        return "[char:" + character + " count:" + count + "]";
    }
}

class MyString implements Comparable{

    String str;
    public MyString(String s){
        str = s;
    }
    
    @Override
    public int compareTo(Object o) {
        if(o instanceof MyString){
            return -str.compareTo(((MyString)o).str);
        }
        return -str.compareTo((String)o);
    }
    
    @Override
    public String toString(){
        return str;
    }
}