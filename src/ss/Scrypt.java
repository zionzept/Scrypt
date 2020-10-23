package ss;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

import parsing.StatementParser;
import statements.StatementBlock;

public class Scrypt {
	public boolean PRINT_PARSED_CODE = true;
	private static final String FILE_NAME = "scrypts\\example\\test.ss"; // move to arg
	
	public PrintStream out;
	
	public static void main(String[] args) throws FileNotFoundException {
		Scrypt scrypt = new Scrypt(new File(FILE_NAME));
		//Scrypt scrypt = new Scrypt("int x@2�2*2+2*x�");
		StatementBlock sb = scrypt.parse();
		
		if (scrypt.PRINT_PARSED_CODE) {
			System.out.println("---v-PARSED-CODE-v--------------------------------------------------------------------------");
			System.out.println(sb);
			System.out.println("\n---v---OUTPUT----v--------------------------------------------------------------------------");
		}
		sb.interpret();
	}
	
	private InputStream in;
	
	public Scrypt(InputStream in) {
		this.in = in;
		this.out = System.out;
	}
	
	public Scrypt(File file) throws FileNotFoundException {
		this(new FileInputStream(file));
	}
	
	public Scrypt(String script) {
		this(new ByteArrayInputStream(script.getBytes(Charset.forName("UTF-8"))));
	}

	public StatementBlock parse() {
		StatementParser parser = new StatementParser(in, this);
		StatementBlock sb = parser.parse();
		return sb;
	}
}