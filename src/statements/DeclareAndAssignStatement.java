package statements;

import util.Syntax;
import util.Util;

public class DeclareAndAssignStatement implements Statement{
	DeclareStatement declare;
	AssignStatement assign;
	
	public DeclareAndAssignStatement(DeclareStatement declare, AssignStatement assign) {
		this.declare = declare;
		this.assign = assign;
	}

	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		Util.addTabs(nestledness, sb);
		sb.append(declare.getType()).append(' ');
		sb.append(declare.getName()).append(' ');
		sb.append(Syntax.assign).append(' ');
		assign.getType().addToStringBuilder(sb, nestledness);
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
		declare.interpret();
		assign.interpret();
		return false;
	}
}
