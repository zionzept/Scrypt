package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import ss.Scrypt;

public abstract class AbstractScryptTest {
	private static final String PRE_PATH = "scrypts" + File.separator + "test" + File.separator;
	private static final String EXTENSION = ".cry";
	private String path;
	
	public AbstractScryptTest(String path) {
		this.path = path + File.separator;
	}
	
	public void assertParsable(String file_name) {
		File file = new File(PRE_PATH + path + file_name + EXTENSION);
		Scrypt scrypt;
		try {
			scrypt = new Scrypt(file);
			scrypt.parse();
		} catch (FileNotFoundException e) {
			fail("File not found.");
		}
	}
}
