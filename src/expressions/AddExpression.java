package expressions;

import types.Expression;
import types.NumericValue;
import util.Syntax;

public class AddExpression implements Expression {
	private Expression e0;
	private Expression e1;
	
	public AddExpression(Expression e0, Expression e1) {
		this.e0 = e0;
		this.e1 = e1;
	}
	
	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		e0.addToStringBuilder(sb, nestledness);
		sb.append(' ').append(Syntax.add).append(' ');
		e1.addToStringBuilder(sb, nestledness);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public NumericValue value() {
		return e0.value().add(e1.value());
	}
}