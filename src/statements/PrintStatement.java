package statements;

import java.io.OutputStream;
import java.io.PrintStream;

import types.TypeExpression;
import util.Syntax;
import util.Util;

public class PrintStatement implements Statement{
	private TypeExpression type;
	private PrintStream out;
	
	public PrintStatement(TypeExpression type, PrintStream out) {
		this.type = type;
		this.out = out;
	}
	
	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		Util.addTabs(nestledness, sb);
		sb.append(Syntax.print).append(' ');
		type.addToStringBuilder(sb, nestledness);
		sb.append(Util.LF);
	}
	
	@Override
	public void addToStringBuilderDetailed(StringBuilder sb, int nesting) {
		Util.addTabs(nesting,  sb);
		sb.append("PRINT(");
		type.addToStringBuilderDetailed(sb, nesting);
		sb.append(")");
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public boolean interpret() {
		out.println(type.value());
		return false;
	}
	
}