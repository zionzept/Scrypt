package conditions;

import types.BooleanValue;
import types.Condition;
import util.Syntax;

public class AndCondition implements Condition {
	private Condition c0;
	private Condition c1;
	
	public AndCondition(Condition c0, Condition c1) {
		this.c0 = c0;
		this.c1 = c1;
	}

	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		c0.addToStringBuilder(sb, nestledness);
		sb.append(' ');
		sb.append(Syntax.and);
		sb.append(' ');
		c1.addToStringBuilder(sb, nestledness);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public BooleanValue value() {
		return c0.value().and(c1.value());
	}
}