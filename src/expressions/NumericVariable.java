package expressions;

import java.util.HashMap;

import parsing.ParsingException;
import types.Expression;
import types.NumericValue;
import types.Type;

public class NumericVariable implements Expression {
	private String name;
	private HashMap<String, Type> varmap;
	
	public NumericVariable(String name, HashMap<String, Type> varmap) {
		this.name = name;
		this.varmap = varmap;
	}
	
	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		sb.append(name);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public NumericValue value() {
		Type type = varmap.get(name);
		if (type instanceof NumericValue) {
			return (NumericValue)type;
		}
		throw new ParsingException("Non numeric variable");
	}
}