import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A Class to encrypt and decrypt some text using a Vigenère Cipher. Contains methods to 
 * obtain a key through the console, encrypt and decrypt using a key, calculate the index of coincidence
 * of a piece of text
 * 
 * @author Joseph Whitten	
 * @date 23/10/16
 */
public class VigenèreCipher {
	
	private final static char START_OF_ALPHABET = 'a';
	private final static int ALPHABET_LENGTH = 26;
	private final static int SHIFT_MODIFIER = 2;
	
	
	/**
	 * Obtain a key from the scanner. Excepts letters only and makes them lower case.
	 * 
	 * @return The entered key.
	 */
	public static String obtainKey() {
		//Start the scanner
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter password, using letters only:");
		
		//Make sure input is letters only
		while (!scanner.hasNext("[A-Za-z]+")) {
			System.out.println("Please enter letters only!");
			scanner.next();
		}
		//Convert key to lower case
		String key = scanner.next().toLowerCase();
		System.out.println(key + " Entered\n");
		scanner.close();
		
		//Return key
		return key;
	}
	
	/** 
	 * Encrypt some text using Vigenère Cipher and a given key.
	 * 
	 * @param text : Text to be encrypted.
	 * @param key : Key used for encryption.
	 * @return The encrypted text.
	 */
	public static String encrypt(String text, String key) {
		
        String encryptedString = "";
        
        //Make sure text and key are lower case.
        key = key.toLowerCase();
        text = text.toLowerCase();
        
        //Loop over each character in the text.
        for (int textIndex = 0, keyIndex = 0; textIndex < text.length(); textIndex++) {
            char characterAtTextIndex = text.charAt(textIndex);
            
            //Check if character is a letter.
            if (Character.isLetter(characterAtTextIndex)) {
            	//If so then encrypt the character and increment the key index (loop back to start of key if we have gone past the end).
            	encryptedString += (char)((characterAtTextIndex + key.charAt(keyIndex) - (SHIFT_MODIFIER * START_OF_ALPHABET)) % ALPHABET_LENGTH + START_OF_ALPHABET);
            	keyIndex = ++keyIndex % key.length();
            } else {
            	//If the character isn't a letter than just add it to the string.
            	encryptedString += characterAtTextIndex;
            }
            
        }
        return encryptedString;
    }

	/**
	 * Decrypt some text using Vigenère Cipher and a given key.
	 * 
	 * @param text : Cipher text to decrypt.
	 * @param key : Key used to decrypt the text.
	 * @return The decrypted text.
	 */
    public static String decrypt(String text, String key) {
    	
        String decrytedString = "";
        
        //Make sure text and key are lower case.
        key = key.toLowerCase();
        text = text.toLowerCase();
        
        //Loop over each character in the text.
        for (int textIndex = 0, keyIndex = 0; textIndex < text.length(); textIndex++) {
            char characterAtTextIndex = text.charAt(textIndex);
            
          //Check if character is a letter.
            if (Character.isLetter(characterAtTextIndex)) {
            	//If so then decrypt the character and increment the key index (loop back to start of key if we have gone past the end).
            	decrytedString += (char)((characterAtTextIndex - key.charAt(keyIndex) + ALPHABET_LENGTH) % ALPHABET_LENGTH + START_OF_ALPHABET);
            	keyIndex = ++keyIndex % key.length();
            } else {
            	//If the character isn't a letter than just add it to the string.
            	decrytedString += characterAtTextIndex;
            }
            
        }
        return decrytedString;
    }
    
    /**
     * Calculate the index of coincidence.
     * 
     * @param map : HashMap of characters in the string and the number of their. 
     * @param textLength : Length of text the map was generated from.
     * @return Index of coincidence.
     */
    public static double indexOfCoincidence(HashMap<Character, Integer> map, double textLength) {
		
		double indexOfCoincidence = 0;
    	
		//Iterate over each character from the sequence.
		for(Map.Entry<Character, Integer> entry : map.entrySet()) {
			
			indexOfCoincidence += ((double) entry.getValue() * ((double)entry.getValue() - 1)) / (textLength * (textLength - 1)); 
			
		}
		return indexOfCoincidence;
		
    }
    
    /**
     * Finds the most probable key length for a piece of cipher text encrypted with Vigenere Cipher,
     * looks for values of key length between 'lowestKeyvalue' and 'greatestKeyValue'.
     * 
     * @param cipherText : The cipher text.
     * @param lowestKeyValue : The lowest key value the program should try.
     * @param greatestKeyValue : The highest key value the program should try.
     * @return The key length that was most probably used to encrypt the text.
     */
    public static int findMostProbableKeyLength(String cipherText, int lowestKeyValue, int greatestKeyValue) {
    	
    	//Remove all non letter characters from the text.
    	String text = cipherText.toLowerCase().replaceAll("[^A-Za-z]+", "");
    	
    	//Initialise variables
    	int mostProbableLength = 0;
    	double mostProbableIndexOfCoincidence = Double.MAX_VALUE;
    	 
    	//Iterate through key lengths.
    	for(int i = lowestKeyValue; i <= greatestKeyValue; i++) {
    		
    		System.out.println("\nKey Length: " + i);
    		
    		//List of maps of Characters to number of occurrences. (One for each index of the key).
    		ArrayList<HashMap<Character, Integer>> listOfMaps = splitTextByIndex(text, i);
    		
    		//Average Index of coincidence 
    		double indexOfCoincidence = 0;
    		System.out.print("IOC: ");
    		
    		//Loop through each of the key index groups
    		for(HashMap<Character, Integer> map : listOfMaps) {
    			double textLength = 0;
        		
    			//Figure out the text length
        		for(Map.Entry<Character, Integer> entry : map.entrySet()) {
        			
        			textLength += (double) entry.getValue(); 
        			
        		}
        		//Calculate the index of coincidence for this index and print result
        		double indexOfCoinceidenceForIndex = indexOfCoincidence(map, textLength);
        		indexOfCoincidence += indexOfCoinceidenceForIndex;
    			System.out.format("%1.7f, ", indexOfCoinceidenceForIndex);
    		}
    		System.out.println();
    		
    		//Average 
    		indexOfCoincidence /= i;
    		
    		//Print average
    		System.out.format("Average IOC : %1.6f\n", indexOfCoincidence);
    		
    		//Finds the index of coincidence most similar to normal English.
    		boolean shouldUpdate = Math.abs(0.065 - indexOfCoincidence) < Math.abs(0.065 - mostProbableIndexOfCoincidence) ;
    		if(shouldUpdate) {
    			mostProbableLength = i;
    			mostProbableIndexOfCoincidence = indexOfCoincidence;
    		}
    	
    	}
    	System.out.println("\nMost Probable Key Length: " + mostProbableLength + "\n");
    	return mostProbableLength;
    }
    
    /**
     * Sort text into groups of 'keySize' then analyse each group to figure out the 
     * monoalphabetic cipher applied to each group, and as such find the key.
     * 
     * @param text : Cipher text to analyse.
     * @param keyLength : Length of the key.
     * @return The key used the encrypt the message.
     */
    public static String analyseEachKeyIndex(String text, int keyLength) {
    	
    	String key = "";    	
    	text = text.toLowerCase();
    	
    	//List of maps of Characters to number of occurrences. (One for each index of the key).
		ArrayList<HashMap<Character, Integer>> listOfMaps = splitTextByIndex(text, keyLength);
		
		//For each of the character groups print the letter frequencies and figure out the shift cipher.
		int index = 0;
		for(HashMap<Character, Integer> map : listOfMaps) {
			
			int totalLength = 0;
			
			//Find the most common value and its 'shift distance' from 'e'.
			char eShift = '-';
			int eValue = 0;
    		for(Map.Entry<Character, Integer> entry : map.entrySet()) {
    			
    			totalLength += entry.getValue();
    			
    			if(entry.getValue() > eValue) {
    				eShift = entry.getKey();
    				eValue = entry.getValue();
    			}
    			
    		}
    		System.out.println("Key Index: " + index);
    		
			FrequencyAnalyser.printFrequency(map, totalLength);
    		
    		System.out.println("Total Length: " + totalLength);
    		
    		//Find key using the shift value to find the letter when shifting from 'a'
    		int offset = (eShift - 'e');
    		System.out.println("Character Offset: " + offset);
    		key += ShiftCipher.monoalphabeticCipher("a", offset);
    		
    		System.out.println("Most Frequent Character: " + eShift + "\n");
    		index++;
		}
		
		System.out.println("Key: " + key);
		return key;
    	
    }
    
    /**
     * Split some text into groups corresponding to the indexes of a key used to encrypt it.
     * 
     * @param text : The cipher text.
     * @param keyLength : The length of the key.
     * @return A list of HashMaps (letters -> number of occurrences).
     */
    private static ArrayList<HashMap<Character, Integer>> splitTextByIndex(String text, int keyLength) {
    	
    	//List of maps of Characters to number of occurrences. (One for each index of the key).
    	ArrayList<HashMap<Character, Integer>> listOfMaps = new ArrayList<HashMap<Character, Integer>>();
    	for(int i = 0; i < keyLength; i++) {
    			listOfMaps.add(new HashMap<Character, Integer>());
    	}
    			
    	//Split characters into groups of letters to be analysed (ordered by index).
    	for (int i = 0, j = 0; i < text.length(); i++) {
    	char c = text.charAt(i);
    	if(Character.isLetter(c)) {
    		//Increment value in map for this index or create a new entry.
    		Integer val = listOfMaps.get(j).get(new Character(c));
    	    if (val != null) {
    	      	listOfMaps.get(j).put(c, new Integer(val + 1));
    	    } else {
    	       	listOfMaps.get(j).put(c, 1);
    	    }
    	    	//Move on to next index
    	    	j = ++j % keyLength;
    	    }
    	}	
    			
    	return listOfMaps;
    	
    }
    
    /**
     * Attempt to decrypt a piece of text encrypted with Vigenere
     * 
     * @param cipherText : Text to be decrypted.
     * @param minKeyLength : Minimum key length to try and decrypt with.
     * @param maxKeyLength : Maximum key to try and decrypt with.
     */
    public static void attemptVigenereDecryption(String cipherText, int minKeyLength, int maxKeyLength) {
    	
    	System.out.println("Attempting Decryption...");
    	System.out.println("Finding Most Probable Key Length...");
    	int keyLength = findMostProbableKeyLength(cipherText, minKeyLength, maxKeyLength);
    	System.out.println("Attempting To Find Key");
    	String key = analyseEachKeyIndex(cipherText, keyLength);
    	System.out.println("Decrypting...");
    	String text = decrypt(cipherText, key);
    	System.out.println("Decrypted Text: " + text);
    	System.out.println("done");
    }
    

}
