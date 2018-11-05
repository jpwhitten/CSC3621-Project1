/**
 * A Class to encrypt and decrypt some text using monoalphabetic (shift) cipher
 * 
 * @author Joseph Whitten	
 * @date 23/10/16
 */
public class ShiftCipher {
	
	/**
	 * Perform a monoalphabetic cipher on a piece of text.
	 * 
	 * @param text : Text to be encrypted.
	 * @param shiftNumber : Number to shift each character by.
	 * @return The encrypted cipher text.
	 */
	public static String monoalphabeticCipher(String text, int shiftNumber) {
		
		String shiftedText = "";
		int offset = shiftNumber;
		
		//Adjust for negative shift numbers.
		if(shiftNumber < 0) {
			offset = Math.abs((26 + shiftNumber) % 26);
		}
		
		//Loop over the characters in the text. 
		for(char c : text.toCharArray()) {
			//Is the character a letter.
			if(Character.isLetter(c)) {
				//Is a letter: then shift it.
				shiftedText += (char) ((((c - 'a') + offset) % 26) + 'a');	
			} else {
				//Isn't a letter: just add it to the encrypted text (keeps punctuation and formatting).
				shiftedText += c;
			}
		}
		
		//return the cipher text.
		return shiftedText;
		
	}

}
