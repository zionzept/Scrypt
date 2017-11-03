package conditions;

import types.BooleanValue;
import types.Condition;
import types.TypeExpression;
import util.Syntax;

public class EqualsCondition implements Condition {
	private TypeExpression type0;
	private TypeExpression type1;
	
	public EqualsCondition(TypeExpression type0, TypeExpression type1) {
		this.type0 = type0;
		this.type1 = type1;
	}

	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		type0.addToStringBuilder(sb, nestledness);
		sb.append(' ');
		sb.append(Syntax.equals);
		sb.append(' ');
		type1.addToStringBuilder(sb, nestledness);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public BooleanValue value() {
		return type0.value().equals(type1.value());
	}
}