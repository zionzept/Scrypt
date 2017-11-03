package statements;

import types.Condition;
import util.Syntax;
import util.Util;

public class IfStatement implements Statement{
	private Condition cIf;
	private StatementBlock sThen;
	private StatementBlock sElse;
	
	public IfStatement(Condition c, StatementBlock s) {
		this.cIf = c;
		this.sThen = s;
	}
	
	public IfStatement(Condition cIf, StatementBlock sThen, StatementBlock sElse) {
		this.cIf = cIf;
		this.sThen = sThen;
		this.sElse = sElse;
	}
	
	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		Util.addTabs(nestledness, sb);
		sb.append(Syntax.ifBegin).append(' ');
		cIf.addToStringBuilder(sb, nestledness);
		sb.append(Util.LF);
		sThen.addToStringBuilder(sb, nestledness);
		if (sElse != null) {
			Util.addTabs(nestledness, sb);
			sb.append(Syntax.elseBegin);
			sb.append(Util.LF);
			sElse.addToStringBuilder(sb, nestledness);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public boolean interpret() {
		if (cIf.value().evaluate()) {
			return sThen.interpret();
		} else if (sElse != null) {
			return sElse.interpret();
		}
		return false;
	}
}