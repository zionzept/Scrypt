package expressions;

import types.Expression;
import types.NumericValue;
import util.Syntax;

public class ExpressionClause implements Expression {
	private Expression e;
	
	public ExpressionClause(Expression e) {
		this.e = e;
	}
	
	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		sb.append(Syntax.expressionLB);
		e.addToStringBuilder(sb, nestledness);
		sb.append(Syntax.expressionRB);
	}
	
	@Override
	public void addToStringBuilderDetailed(StringBuilder sb, int nesting) {
		sb.append("EXPCLAUSE(");
		e.addToStringBuilderDetailed(sb, nesting);
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
		return e.value();
	}
}