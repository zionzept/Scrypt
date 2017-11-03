package statements;

import util.Syntax;
import util.Util;

public class LoopStatement implements Statement{
	StatementBlock s;
	
	public LoopStatement(StatementBlock s) {
		this.s = s;
	}
	
	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		Util.addTabs(nestledness, sb);
		sb.append(Syntax.loopBegin);
		sb.append(Util.LF);
		s.addToStringBuilder(sb, nestledness);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public boolean interpret() {
		while (!s.interpret());
		return false;
	}
}
