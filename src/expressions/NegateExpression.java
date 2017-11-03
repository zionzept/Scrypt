package expressions;

import types.Expression;
import types.NumericValue;
import util.Syntax;

public class NegateExpression implements Expression {
	private Expression e;

	public NegateExpression(Expression e) {
		this.e = e;
	}
	
	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		sb.append(Syntax.subtract);
		e.addToStringBuilder(sb, nestledness);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public NumericValue value() {
		return e.value().negate();
	}
}