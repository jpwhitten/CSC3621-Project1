import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * A Class to analyse the frequency of letters within some text and perform 
 * monoalphabetic ciphers on the text.
 * 
 * @author Joseph Whitten
 * @version 2
 * @date 23/10/16
 */
public class FrequencyAnalyser {
	
	/*
	 * Analyse the frequency of all letters that appear within the input String.
	 * 
	 * @param s : Text to analyse.
	 * @return A map of letters to number of occurrences.
	 */
	public static HashMap<Character, Integer> analyseFrequency(String s) {
		
		//Create a new map for this text.
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		
		//Loop through all characters within the text.
		for (int i = 0; i < s.length(); i++) {
			
			//Check to see if the character is a letter. (Only interested in letters).
			char c = s.charAt(i);
			if(Character.isLetter(c)) {
				
				//If the character is a letter we check to see if it already in the map by getting its value.
				Integer val = map.get(new Character(c));
				if (val != null) {
					//It is in the map: increment its value by one.
					map.put(c, new Integer(val + 1));
				} else {
					//It isn't in the map: make new entry for character.
					map.put(c, 1);
				}
			}
		}
		//return the finished map.
		return map;
	}
	
	/**
	 * Print the frequency analysis of the text including the percentage of occurrences for each letter 
	 * 
	 * @param map : HashMap of characters to the number of their occurrences.
	 * @param stringLength : Length of the String that was analysed, used for percentages.
	 */
	public static void printFrequency(HashMap<Character, Integer> map, int stringLength) {
		
		//Format and print the header for the table.
		String header = String.format("%6s  %10s  %11s", "Letter", "Occurences", "Percentages");
		System.out.println("-------------------------------");
		System.out.println(header);
		System.out.println("-------------------------------");
		
		//Convert HashMap to a TreeMap so that I can sort by the Key.
		Map<Character, Integer> treeMap = new TreeMap<Character, Integer>(map);
		
		//Loop through each entry in the map.
		for(Map.Entry<Character, Integer> entry : treeMap.entrySet()) {
			
			//Calculate occurrence percentage for the character.
			double percentage = ((double) entry.getValue() / (double) stringLength) * 100;
			
			//Print letter along with its number of occurrences and its occurrence percentage.
			String row = String.format("%-6s  %10d  %11.3f", entry.getKey(), entry.getValue(), percentage);
			System.out.println(row);
			
		}
		
		System.out.println("\nText Length: " + stringLength);
		
		
	}
	
	public static void performFrequencyAnalysisOnString(String text) {
		//Read file and remove all non letter characters as they're irrelevant.
		String string = text.replaceAll("[^A-Za-z]+", "").toLowerCase();
		//Length of string being analysed.
		int stringLength = string.length();
		//Analyse and print analysis.
		HashMap<Character, Integer> letterOccurences = analyseFrequency(string);
		printFrequency(letterOccurences, stringLength);
	}

}
