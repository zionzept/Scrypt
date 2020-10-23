package statements;

import java.util.LinkedList;

import util.Syntax;
import util.Util;

public class StatementBlock implements Statement{
	private LinkedList<Statement> statements;
	
	public StatementBlock() {
		statements = new LinkedList<Statement>();
	}
	
	public void addStatement(Statement s) {
		statements.add(s);
	}

	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		for (Statement s : statements) {
			s.addToStringBuilder(sb, nestledness + 1);
		}
		Util.addTabs(nestledness + 1, sb);
		sb.append(Syntax.statementBlockEnd);
		if (nestledness >= 0) {	//don't want extra blank row at the end
			sb.append(Util.LF);
		}
	}
	
	@Override
	public void addToStringBuilderDetailed(StringBuilder sb, int nesting) {
		for (Statement s : statements) {
			s.addToStringBuilderDetailed(sb, nesting + 1);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, -1);
		return sb.toString();
	}

	@Override
	public boolean interpret() {
		for (Statement s : statements) {
			if (s.interpret()) {
				return true;
			}
			if (Thread.currentThread().isInterrupted()) {
				break;
			}
		}
		return false;
	}
}