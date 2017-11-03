package conditions;

import types.BooleanValue;
import types.Condition;
import util.Syntax;

public class ConditionClause implements Condition {
	private Condition c;
	
	public ConditionClause(Condition c) {
		this.c = c;
	}
	
	@Override
	public BooleanValue value() {
		return c.value();
	}

	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		sb.append(Syntax.conditionLB);
		c.addToStringBuilder(sb, nestledness);
		sb.append(Syntax.conditionRB);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}
}