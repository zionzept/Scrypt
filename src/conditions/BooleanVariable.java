package conditions;

import java.util.HashMap;

import parsing.ParsingException;
import types.BooleanValue;
import types.Condition;
import types.Type;

public class BooleanVariable implements Condition {
	private String name;
	private HashMap<String, Type> varmap;
	
	public BooleanVariable(String name, HashMap<String, Type> varmap) {
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
	public BooleanValue value() {
		Type type = varmap.get(name);
		if (type instanceof BooleanValue) {
			return (BooleanValue)type;
		}
		throw new ParsingException("Non numeric variable");
	}
}