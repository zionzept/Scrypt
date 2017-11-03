package statements;

import parsing.Parsable;

public interface Statement extends Parsable{
	public boolean interpret();
}