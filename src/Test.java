import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A Class to encrypt and decrypt some text using a Vigenère Cipher. Contains methods to 
 * obtain a key through the console, encrypt and decrypt using a key, calculate the index of coincidence
 * of a piece of text
 * 
 * @author Joseph Whitten	
 * @date 23/10/16
 */
public class Test {

	public static void main(String[] args) {
		
		FileReader fileReader = new FileReader();
		
		//Ex1
		String frequencyTestOne = fileReader.readFile("test.txt");
		FrequencyAnalyser.performFrequencyAnalysisOnString(frequencyTestOne);
		
		String frequencyTestTwo = fileReader.readFile("test2.txt");
		FrequencyAnalyser.performFrequencyAnalysisOnString(frequencyTestTwo);
		
		String frequencyTestThree = fileReader.readFile("test3.txt");
		FrequencyAnalyser.performFrequencyAnalysisOnString(frequencyTestThree);
		
		String cipherTextFrequencyTest = fileReader.readFile("Ex1Ciphertext.txt");
		FrequencyAnalyser.performFrequencyAnalysisOnString(cipherTextFrequencyTest);
		
		String cipherText = fileReader.readFile("Ex1Ciphertext.txt");
		cipherText = ShiftCipher.monoalphabeticCipher(cipherText, -4);
		FrequencyAnalyser.performFrequencyAnalysisOnString(cipherText);
		System.out.println(cipherText);
		
		//Ex2
		
		String testKey = "ncl";
		String testText = "newcastleuniversity";
		testText = VigenèreCipher.encrypt(testText, testKey);
		System.out.println(testText);
		testText = VigenèreCipher.decrypt(testText, testKey);
		System.out.println(testText);
		
		String exTwoCipherText = fileReader.readFile("Ex2Ciphertext.txt");
		System.out.println(exTwoCipherText.length());
		FrequencyAnalyser.performFrequencyAnalysisOnString(exTwoCipherText);
		VigenèreCipher.attemptVigenereDecryption(exTwoCipherText, 1, 5);
		
		String key = VigenèreCipher.obtainKey();
		String text = fileReader.readFile("test.txt").substring(0, 40000);
		text = VigenèreCipher.encrypt(text, key);
		
		System.out.println(text);
		VigenèreCipher.attemptVigenereDecryption(text, 1, 10);
		//bug may occur where output is invisible, right click console and select word wrap to fix this.
			

		
	}

	
}
