package parsing;

import java.util.HashMap;
import java.util.LinkedList;

import reader.Reader;
import types.Array;
import types.Expression;
import types.Type;
import util.Syntax;

public class ArrayParser {
	
	private Reader read;
	private HashMap<String, Type> varmap;
	private TypeExpressionParser valueParser;
	private ExpressionParser expressionParser;
	
	
	public ArrayParser(Reader read, HashMap<String, Type> varmap, TypeExpressionParser valueParser, ExpressionParser expressionParser) {
		this.read = read;
		this.varmap = varmap;
		this.valueParser = valueParser;
		this.expressionParser = expressionParser;
	}
	
	public LinkedList<Expression> parseIndexList() {
		LinkedList<Expression> indexList = new LinkedList<>();
		do {
			indexList.add(expressionParser.parseExpression());
			if (!read.check(Syntax.indexRB)) {
				throw new ParsingException(read, Syntax.indexRB + " expected to close array index caluse");
			}
		} while (read.check(Syntax.indexLB));
		return indexList;
	}
	
	public Array parseArray() {
		if (read.check(Syntax.arrayLB)) {
			return parseConstArray();
		}
		//more
		throw new ParsingException(read, "cannot parse array");
	}
	
	private Array parseConstArray() {
		Array a = null;
		
		LinkedList<Type> elements = new LinkedList<>();
		
		while (!read.check(Syntax.arrayRB)) {
			elements.add(parseElement());
			if (read.found().equals(Syntax.arrayElementSeparator)) {
				read.next();
			}
		}
		
		a = new Array(elements.get(0));
		a.create(elements.size());
		for (int i = 0; i < elements.size(); i++) {
			a.set(i, elements.get(i));
		}
		
		return a;
	}
	
	private Type parseElement() {
		if (read.found().equals(Syntax.arrayLB)) {
			read.next();
			return parseConstArray();
		} else {
			return valueParser.parseTypeExpression().value();
		}
	}
	
	
}
