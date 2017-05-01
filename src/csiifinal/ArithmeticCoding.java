/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csiifinal;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Michael
 */
public class ArithmeticCoding extends Compressor {

    @Override
    public String compress(String doc) {
        
        doc = this.removePunctuation(doc);
        
        ArrayList<Character> characters = new ArrayList<Character>();
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        String prep = doc;
        String tokens[] = prep.split(" ");
        String ready[] = new String[tokens.length];
        for (int x = 0; x < tokens.length; x++) {
            ready[x] = tokens[x] + "^";
        }
        //	System.out.println(ready[1]+ " " + tokens[1]);

        String readyS = "";
        for (int x = 0; x < tokens.length; x++) {
            readyS += ready[x] + "";
        }

//VVVV COUNT OCCURANCES FOR PROBABILIY 		
        String test = readyS;
        for (int x = 0; x < test.length(); x++) {
            if (characters.contains(test.charAt(x))) {
                numbers.set(characters.indexOf(test.charAt(x)), numbers.get(characters.indexOf(test.charAt(x))) + 1);

                //numbers.add(characters.indexOf(test.charAt(x)), temp+1);
            } else {
                characters.add(test.charAt(x));
                numbers.add(1);
            }
        }

//		System.out.println( characters + " " + numbers);
//^^^^^ COUNT OCCURANCES FOR PROBABILIY 		
//vv probability	
        ArrayList<Character> charList = new ArrayList<Character>();
        ArrayList<Double> doubList = new ArrayList<Double>();

        doubList.add(0.0); //this was SO important, it represents the lowest range (no index out of bound exceptions)

        for (int x = 1; x < numbers.size() + 1; x++) {
            charList.add(characters.get(x - 1)); //for the remainder of cases
            doubList.add(doubList.get(x - 1) + ((double) (numbers.get(x - 1)) / (test.length())));
            //		System.out.println(doubList+" "+(double)(numbers.get(x-1))/(test.length()));
        }

//^^ probability		
        readyS = "";
        for (int x = 0; x < tokens.length; x++) {
            readyS += ready[x] + " "; //Have to throw in some spaces to be split up again.
        }
        String thingsToCompress[] = readyS.split(" ");
        double compressedThings[] = new double[thingsToCompress.length];

        for (int y = 0; y < thingsToCompress.length; y++) {

            String input = thingsToCompress[y]; //Here is what we will COMPRESS.

//VVVVV COMPRESS		
            double compressedInput = 1;
            double low = 0;

            double high = 1;    //doubList.get(   charList.indexOf(input.charAt(0))      );

            double range;
            for (int sizeOfWord = 0; sizeOfWord < input.length(); sizeOfWord++) {
                range = high - low; //at the beginning of the for loop, the range is 1-0, or 1..
                high = low + range * doubList.get(charList.indexOf(input.charAt(sizeOfWord)) + 1);	//the high becomes the low + the high*the range
                low = low + range * doubList.get(charList.indexOf(input.charAt(sizeOfWord)));    //the low becomes the low + the low*the range

                //	System.out.println("The low value is "+low + " and the high value is "+high);
            }
	//At this point, the number is a decimal number that anything in the range can be decompressed to make the word
            //this works because

            //Let me take the average between the low and high values (because ANY value in that range will be the input).
            compressedInput = low;
            compressedThings[y] = compressedInput + (thingsToCompress[y].length() - 1); //instead of 0-1, i add the length of the string that was encoded, for later use!
            //System.out.println("\n\nGreat Success! " + compressedInput+" " );

        }

//^^^^^^ COMPRESS		
        //*******************************************************************
        //TIME TO MAKE THE KEY that goes at the beginning of the file
        String cKey = "";
        for (int x = 0; x < numbers.size(); x++) {
            cKey += charList.get(x) + " " + doubList.get(x + 1) + " ";
        }
        //********************************************************************

        String compressedFile = cKey + "\n";
        for (int y = 0; y < thingsToCompress.length; y++) {
            compressedFile += compressedThings[y] + " ";
        }

        return compressedFile;
    }

    @Override
    public String decompress(String doc) {
        ArrayList<Character> dcharList = new ArrayList<Character>();
        ArrayList<Double> ddoubList = new ArrayList<Double>();

        ddoubList.add(0.0);

        int index = doc.lastIndexOf('\n');
        String fileDecompress = doc;
        ArrayList<Double> num2Decomp = new ArrayList<Double>();

        Scanner sc = new Scanner(fileDecompress.substring(index+1));
        String dKey = fileDecompress.substring(0, index);
        int i=0;
        while (i<dKey.length()) {
            char temp = dKey.charAt(i);
            i+=2;
            dcharList.add(temp);
            String doub = "";
            while((temp=dKey.charAt(i++))!=' '){
                doub += temp;
            }
            ddoubList.add(Double.parseDouble(doub));
        }
        
        while (sc.hasNext()) {
            num2Decomp.add(sc.nextDouble());
        }
        sc.close();
//        System.out.println(dcharList + "\n" + ddoubList);
		//Time to decompress!

					//Okay now that I FINALLY have an easy way to decompress.
        //it would be nice to make 1 data structure but here's what i have.
        String decompressedFile = "";

        for (int x = 0; x < num2Decomp.size(); x++) {
            String decompressedInput = "";

            ArrayList<Double> d1 = new ArrayList<Double>();
            d1 = ddoubList; //this has 0, their probabilities, up to 1
            //System.out.println(d1);
            //	System.out.println( d1);
            int sizeOfWord = num2Decomp.get(x).intValue();
            //System.out.println("The length of word is: " + sizeOfWord);//RETURNS A MOTHERFUCKING INT VALUE ARE YOU KIDDING ME?!
            double num = num2Decomp.get(x) - sizeOfWord; //too long to type, shortened it.
            double Range = 1;

            int counter = 0;
            while (counter < sizeOfWord) //If the number is in the range for end character, exit.
            {

				//	System.out.println(num + "  " + decompressedInput);
                for (int y = 0; y < ddoubList.size(); y++) {
                    if (counter == sizeOfWord) //THESE TWO
                    {
                        break;				//LINES FIXED MY PROGRAM HOLY CRAP!!!!!!!!!!!!
                    }
                    if ((num) > d1.get(y) && (num) < d1.get(y + 1)) //if the number is less than the range for 'a'
                    {

                        Range = ddoubList.get(y + 1) - ddoubList.get(y);
                        num = num - ddoubList.get(y);
                        num = (num / Range);

                        if (dcharList.get(y) != '^') {
                            counter++;
                            decompressedInput += dcharList.get(y);
                        }

                        //System.out.println("Counter is: " + counter);
                    }

                }

            }

            decompressedFile += decompressedInput + " ";
        }
        return decompressedFile;
    }

    @Override
    public String getName() {
        return "Arithmetic Coding";
    }

    @Override
    public String getSuffix() {
        return ".ac";
    }

    @Override
    public int getSize(String comp) {
        int index = comp.lastIndexOf('\n');
        String dKey = comp.substring(0, index);
        int i = 0;
        int bytes = 0;
        while (i<dKey.length()) {
            char temp = dKey.charAt(i);
            i+=2;
            bytes+=1;
            String doub = "";
            while((temp=dKey.charAt(i++))!=' '){
                doub += temp;
            }
            bytes+=8;
        }
        i=0;
        dKey = comp.substring(index);
        bytes++;
        while(i<dKey.length()){
            while((dKey.charAt(i++))!=' ');
            bytes+=8;
        }
        return bytes;
    }

}
