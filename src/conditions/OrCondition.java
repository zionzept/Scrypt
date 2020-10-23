package conditions;

import types.BooleanValue;
import types.Condition;
import util.Syntax;

public class OrCondition implements Condition {
	private Condition c0;
	private Condition c1;
	
	public OrCondition(Condition c0, Condition c1) {
		this.c0 = c0;
		this.c1 = c1;
	}
	
	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		c0.addToStringBuilder(sb, nestledness);
		sb.append(' ');
		sb.append(Syntax.or);
		sb.append(' ');
		c1.addToStringBuilder(sb, nestledness);
	}
	
	@Override
	public void addToStringBuilderDetailed(StringBuilder sb, int nesting) {
		sb.append("OR(");
		c0.addToStringBuilderDetailed(sb, nesting);
		sb.append(", ");
		c1.addToStringBuilderDetailed(sb, nesting);
		sb.append(")");
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public BooleanValue value() {
		return c0.value().or(c1.value());
	}
}