import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A class containing methods to read in a file to a string
 * 
 * @author Joseph Whitten	
 * @date 23/10/16
 */
public class FileReader {

	public FileReader() {
		
	}
	
	/**
	 * Read a file from 'path'
	 * 
	 * @param path : path to file.
	 * @return File as a string.
	 */
	public String readFile(String path) {
		URL url = getClass().getResource(path);
		byte[] fileBytes = null;
		try {
			fileBytes = Files.readAllBytes(Paths.get(url.toURI()));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return new String(fileBytes);
	}
	
}
