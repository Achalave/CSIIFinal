package csiifinal;

import java.util.ArrayList;
import java.util.Collections;

public class BurrowsWheelerTransform extends Compressor{

	@Override
	public String compress(String doc) {
		//the period is the special character.
		String str=removePunctuation(doc)+".";
		String str1 = "";
		ArrayList<String> list = new ArrayList<String>();
		list.add(str);
		for(int i = 1; i<str.length(); i++){
			//going through all of the rotations. ex: "String" next is "g" + "Strin" and so on.
			list.add(list.get(i-1).substring(str.length()-1,str.length())+ list.get(i-1).substring(0,str.length()-1));
		}
		//sorts it
		Collections.sort(list);
		//gets the last characters of each string for compression.
		for(int i = 0; i < list.size(); i++){
			str1+= list.get(i).substring(list.get(i).length()-1, list.get(i).length());
		}
		return str1;
	}
	
	@Override
	public String decompress(String doc) {
		String str = doc;
		ArrayList<String> list = new ArrayList<String>(str.length());
		//adds all of the letters separately. "compressed" to "c","o","m"...
		for(int i = 0; i<str.length(); i++){
			list.add(str.substring(i,i+1));
		}
		//sorts the list, then adds another round of characters of the compressed file to the beginning.
		for(int i = 1; i<str.length(); i++){
			Collections.sort(list);
			for(int k = 0; k<str.length(); k++){
				list.set(k,str.substring(k,k+1)+list.get(k));
			}
		}
		//find the line with the period at the end and return it.
		for(int i = 0; i<list.size(); i++){
			if(list.get(i).substring(list.get(i).length()-1,list.get(i).length()).equals("."))
				return list.get(i).substring(0,list.get(i).length()-1);
		}
		return "Didn't work";
	}

	@Override
	public String getName() {
		//name for GUI
		return "Burrows Wheeler Transform";
	}

	@Override
	public String getSuffix() {
		//compression extension
		return ".bwt";
	}

	@Override
	public int getSize(String comp) {
		//length for returning compresion computation.
		return comp.length();
	}

}
