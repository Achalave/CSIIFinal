package csiifinal;
import java.util.Hashtable;

public class RunLengthEncoding extends Compressor {
	Hashtable<String,String> mapping = new Hashtable<String,String>();
	String[] words;
	public String decompress(String str){
		String digits = "";//will be used for storing all of the numbers generated after compression
		String end = "";//result after decompression is complete.
		
		for(int i =0; i<str.length(); i++){
			//if it's a digit, store the digit because we might need to work off of multiple digits.
			if(Character.isDigit(str.charAt(i))){
				digits=digits +""+str.charAt(i);
			}
			else{
				//if this character is a letter, we can stop storing digits.
				digits = "";
			}
			// This is a loop to filter certain bugs. mostly it adds the character as many times as the number before it says.
			if((!digits.equals("") && hasDigits(digits)) && (i<str.length()-1 && !Character.isDigit(str.charAt(i+1)))){
				for(int k = 1; k<Integer.valueOf(digits); k++){
					end+=str.charAt(i+1);
				}			
			}
			else{
				//this is for single characters. Some single characters don't have a number before them when compressed
				if(!Character.isDigit(str.charAt(i))){
					end+=str.charAt(i);
				}
			}
			
		}
		return end;
		
	}
	public boolean hasDigits(String s){
		//returns false if the string has ANY letters. only returns true for only digits.
		char[] charArray = s.toCharArray();
		for(int i = 0; i<s.length(); i++){
			if (!Character.isDigit(charArray[i]))
				return false;
		}
		return true;
	}
	
	public String compress(String str){
		//edit is str without punctuation or capitals.
		String edit =removePunctuation(str);
		//code in starting characters, otherwise it will skip the first one and have problems.
		int sameChar=1;
		char lastChar=edit.charAt(0);
		String end ="";
		//loop to add up all the reacurring letters.
		for(int i =1; i<edit.length(); i++){
			if(edit.charAt(i)==lastChar){
				sameChar++;
			}
			else{
				//once it hits a different character, add the number and character that was recorded earlier.
				if(sameChar>1){
					end+=""+sameChar+lastChar;
					sameChar=1;
				}
				else{
					//if it's just one lone character, add it as a "1a" wastes space.
					end+=lastChar;
				}
				// recording the last character to compare later with a future character.
				lastChar = edit.charAt(i);
			}
		}
		//recording the final one, as usually it's recorded at the beginning, but it can't be looped again.
		end+=""+sameChar+lastChar;
		return end;
	}
	public String getName(){
		//Name for the GUI
		return "Run Length Encoding";
	}
	public String getSuffix(){
		//Extension for compression
		return ".rle";
	}
	public int getSize(String in){
		//size for the compression calculations
		return in.length();
	}
}
