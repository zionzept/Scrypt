package expressions;

import java.util.HashMap;
import java.util.LinkedList;

import parsing.ParsingException;
import types.Array;
import types.Expression;
import types.NumericValue;
import types.Type;
import util.Syntax;

public class ArrayAccess implements Expression {
	private String name;
	private HashMap<String, Type> varmap;
	private LinkedList<Expression> indexList;

	public ArrayAccess(String name, HashMap<String, Type> varmap, LinkedList<Expression> indexList) {
		this.name = name;
		this.varmap = varmap;
		this.indexList = indexList;
		if (indexList.size() < 1) {
			throw new ParsingException("array path must include at least one index");
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		sb.append(name).append(indexList());
	}
	
	private String indexList() {
		StringBuilder sb = new StringBuilder();
		for (Expression e : indexList) {
			sb.append(Syntax.indexLB).append(e).append(Syntax.indexRB);
		}
		return sb.toString();
	}
	
	private Array array() {
		Type t = varmap.get(name);
		if (!(t instanceof Array)) {
			throw new ParsingException(name + " is no array. Cannot access array index, true");
		}
		Array a = (Array) t;
		for (int i = 0; i < indexList.size() - 1; i++) {
			if (!(a.getArrayType() instanceof Array)) {
				throw new ParsingException("Unvalid index path: " + indexList + " when accessing " + name);
			}
			a = (Array)a.get(indexList.get(i));
		}
		return a;
	}

	@Override
	public NumericValue value() {
		Array a = array();
		Type t = a.get(indexList.getLast());
		// bad solution for array access, shouldn't be used only for numerics
		if (!(t instanceof NumericValue)) {
			throw new ParsingException("wrong type in array access");
		}
		return (NumericValue)t;
	}
	
	public void write(Type t) {
		Array a = array();
		a.set(indexList.getLast(), t);
	}

}
