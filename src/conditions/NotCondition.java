package conditions;

import types.BooleanValue;
import types.Condition;
import util.Syntax;

public class NotCondition implements Condition {
	private Condition c;
	
	public NotCondition(Condition c) {
		this.c = c;
	}

	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		c.addToStringBuilder(sb, nestledness);
		sb.append(Syntax.not);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public BooleanValue value() {
		return c.value().not();
	}
}