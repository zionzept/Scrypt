package statements;

import java.util.HashMap;

import parsing.ParsingException;
import types.DoubleValue;
import types.IntegerValue;
import types.Type;
import types.TypeExpression;
import util.Syntax;
import util.Util;

public class AssignStatement implements Statement{
	protected String name;
	protected TypeExpression type;
	protected HashMap<String, Type> varmap;
	
	public AssignStatement(String name, TypeExpression type, HashMap<String, Type> varmap) {
		this.name = name;
		this.type = type;
		this.varmap = varmap;
	}
	
	protected TypeExpression getType() {
		return type;
	}
	
	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		Util.addTabs(nestledness, sb);
		sb.append(name).append(' ');
		sb.append(Syntax.assign).append(' ');
		type.addToStringBuilder(sb, nestledness);
		sb.append(Util.LF);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public boolean interpret() {
		if (type.value().getClass().equals(varmap.get(name).getClass())) {
			varmap.put(name, type.value());
			return false;
		} else if (type.value().getClass().equals(IntegerValue.class) && varmap.get(name).getClass().equals(DoubleValue.class)) {
			varmap.put(name, new DoubleValue(type.toString()));
			return false;
		} else {
			throw new ParsingException("assign type missmatch");
		}
	}
}