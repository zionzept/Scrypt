package test;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;

public class ParseTest extends ParamTest {
	private static final String sep = File.separator;
	private static final String expression = "expression" + sep;
	private static final String statement = "statement" + sep;
	private static final String declare = statement + "declare" + sep;
	private static final String assign = statement + "assign" + sep;
	
	public ParseTest() throws FileNotFoundException {
		super("parser", true);
	}
}