package conditions;

import types.BooleanValue;
import types.Condition;
import types.Expression;
import util.Syntax;

public class GreaterThanCondition implements Condition {
	private Expression exp0;
	private Expression exp1;
	
	public GreaterThanCondition(Expression exp0, Expression exp1) {
		this.exp0 = exp0;
		this.exp1 = exp1;
	}
	
	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		exp0.addToStringBuilder(sb, nestledness);
		sb.append(' ');
		sb.append(Syntax.greaterThan);
		sb.append(' ');
		exp1.addToStringBuilder(sb, nestledness);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public BooleanValue value() {
		return exp0.value().greaterThan(exp1.value());
	}
}