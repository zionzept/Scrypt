package expressions;

import types.Expression;
import types.NumericValue;
import util.Syntax;

public class DivideExpression implements Expression {
	private Expression e0;
	private Expression e1;
	
	public DivideExpression(Expression e0, Expression e1) {
		this.e0 = e0;
		this.e1 = e1;
	}
	
	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		e0.addToStringBuilder(sb, nestledness);
		sb.append(' ').append(Syntax.divide).append(' ');
		e1.addToStringBuilder(sb, nestledness);
	}
	
	@Override
	public void addToStringBuilderDetailed(StringBuilder sb, int nesting) {
		sb.append("DIV(");
		e0.addToStringBuilderDetailed(sb, nesting);
		sb.append(", ");
		e1.addToStringBuilderDetailed(sb, nesting);
		sb.append(")");
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public NumericValue value() {
		return e0.value().div(e1.value());
	}
}