package statements;

import types.Condition;
import util.Syntax;
import util.Util;

public class EndStatement implements Statement{
	private Condition c;
	
	public EndStatement(Condition c) {
		this.c = c;
	}
	
	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		Util.addTabs(nestledness, sb);
		sb.append(Syntax.loopEnd).append(' ');
		c.addToStringBuilder(sb, nestledness);
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
		return c.value().evaluate();
	}
}