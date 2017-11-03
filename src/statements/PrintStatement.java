package statements;

import types.TypeExpression;
import util.Syntax;
import util.Util;

public class PrintStatement implements Statement{
	private TypeExpression type;
	
	public PrintStatement(TypeExpression type) {
		this.type = type;
	}
	
	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		Util.addTabs(nestledness, sb);
		sb.append(Syntax.print).append(' ');
		type.addToStringBuilder(sb, nestledness);
		sb.append(Util.LF);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public boolean interpret() {
		System.out.println(type.value());
		return false;
	}
	
}