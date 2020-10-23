package statements;

import java.util.HashMap;

import types.Type;
import util.Util;

public class DeclareStatement implements Statement{

	private String typeName;
	private String name;
	
	public DeclareStatement(Type type, String name, HashMap<String, Type> varmap) {
		this.typeName = type.getTypeName();
		this.name = name;
		varmap.put(name, /*type.getNull()*/ type);
	}
	
	protected String getName() {
		return name;
	}
	
	protected String getType() {
		return typeName;
	}

	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		Util.addTabs(nestledness, sb);
		sb.append(typeName).append(' ');
		sb.append(name).append(Util.LF);
	}
	
	@Override
	public void addToStringBuilderDetailed(StringBuilder sb, int nesting) {
		sb.append("DECLARE(").append(name).append(", ").append(typeName).append(")");
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public boolean interpret() {
		return false;
	}
}