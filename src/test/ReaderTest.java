package test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import reader.Reader;

public class ReaderTest {

	private Reader read;
	private String str = "hej 23 + 1 ++ a +++- ++++ 2+++++b# ++++++ /##";
	
	@Before
	public void setup() {
		InputStream in = new ByteArrayInputStream(str.getBytes());
		this.read = new Reader(in, new char[] {'#'}, new String[] {"+++", "+++++"});
	}
	
	@Test
	public void testTokens() {
		assertEquals("hej", read.found());
		read.next();
		assertEquals("23", read.found());
		read.next();
		assertEquals("+", read.found()); 
		read.next();
		assertEquals("1", read.found());
		read.next();
		assertEquals("+", read.found());
		read.next();
		assertEquals("+", read.found());
		read.next();
		assertEquals("a", read.found());
		read.next();
		assertEquals("+++", read.found());
		read.next();
		assertEquals("-", read.found());
		read.next();
		assertEquals("+++", read.found());
		read.next();
		assertEquals("+", read.found());
		read.next();
		assertEquals("2", read.found());
		read.next();
		assertEquals("+++++", read.found());
		read.next();
		assertEquals("b#", read.found());
		read.next();
		assertEquals("+++++", read.found());
		read.next();
		assertEquals("+", read.found());
		read.next();
		assertEquals("/", read.found());
		read.next();
		assertEquals("#", read.found());
		read.next();
		assertEquals("#", read.found());
	}

}
