package parsing;

import reader.Reader;

public class ParsingException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public ParsingException(Reader read, String msg) {
		super(read.toString() + ": " + msg);
	}
	
	public ParsingException (String msg) {
		super(msg);
	}
}
