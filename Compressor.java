package csiifinal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Compressor {

    //Do the actual compression algorithm
    public abstract String compress(String doc);

    public abstract String decompress(String doc);

    //Fill this out for use with all classes extending Compressor
    public String removePunctuation(String doc) {
        String str = "";
        String lowerCase = doc.toLowerCase();
        int space = 0;
        for (int i = 0; i < lowerCase.length(); i++) {
            if (Character.isLetter(lowerCase.charAt(i)) || lowerCase.charAt(i) == ' ' || lowerCase.charAt(i) == '\n') {
                if (lowerCase.charAt(i) == ' ') {
                    space++;
                } else {
                    space = 0;
                }
                if (space < 2) {
                    str += lowerCase.charAt(i);
                }
            } else {
                space++;
                if (space < 2) {
                    str += " ";
                }
            }
        }
        return str;
    }

    //The name of this compression
    public abstract String getName();

    //The unique suffix
    public abstract String getSuffix();

    public void saveFile(String text, File file) {
        //Save the text
        PrintWriter out = null;
        try {
            out = new PrintWriter(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Compressor.class.getName()).log(Level.SEVERE, null, ex);
        }
        out.println(text);
        out.close();
    }

    public String loadFile(File file) {
        String result = "";
        try {
            Scanner in = new Scanner(file);
            while (in.hasNextLine()) {
                result += in.nextLine();
                if(in.hasNextLine())
                    result+="\n";
            }
            return result;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Compressor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public abstract int getSize(String comp);
}
