package conditions;

import types.BooleanValue;
import types.Condition;
import types.Expression;
import util.Syntax;

public class LessThanCondition implements Condition {
	private Expression exp0;
	private Expression exp1;
	
	public LessThanCondition(Expression exp0, Expression exp1) {
		this.exp0 = exp0;
		this.exp1 = exp1;
	}
	
	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		exp0.addToStringBuilder(sb, nestledness);
		sb.append(' ');
		sb.append(Syntax.lessThan);
		sb.append(' ');
		exp1.addToStringBuilder(sb, nestledness);
	}
	
	@Override
	public void addToStringBuilderDetailed(StringBuilder sb, int nesting) {
		sb.append("LT(");
		exp0.addToStringBuilderDetailed(sb, nesting);
		sb.append(", ");
		exp1.addToStringBuilderDetailed(sb, nesting);
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
		return exp0.value().lessThan(exp1.value());
	}
}