package statements;

import java.util.HashMap;

import expressions.ArrayAccess;
import parsing.ParsingException;
import types.DoubleValue;
import types.IntegerValue;
import types.Type;
import types.TypeExpression;
import util.Syntax;
import util.Util;

public class ArrayAssignStatement extends AssignStatement {
	private ArrayAccess arrayAccess;

	public ArrayAssignStatement(String name, ArrayAccess arrayAccess, TypeExpression val, HashMap<String, Type> varmap) {
		super(name, val, varmap);
		this.arrayAccess = arrayAccess;
	}
	
	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		Util.addTabs(nestledness, sb);
		sb.append(arrayAccess).append(' ');
		sb.append(Syntax.assign).append(' ');
		type.addToStringBuilder(sb, nestledness);
		sb.append(Util.LF);
	}

	@Override
	public boolean interpret() {
		if (type.value().getClass().equals(arrayAccess.value().getClass())) {
			arrayAccess.write(type.value());
			return false;
		} else if (type.value().getClass().equals(IntegerValue.class) && arrayAccess.value().getClass().equals(DoubleValue.class)) {
			arrayAccess.write(new DoubleValue(type.toString()));
			return false;
		} else {
			throw new ParsingException("assign type missmatch");
		}
	}
}
