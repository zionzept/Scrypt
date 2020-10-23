package parsing;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import javax.lang.model.element.TypeParameterElement;

import expressions.ArrayAccess;
import reader.Reader;
import ss.Scrypt;
import statements.ArrayAssignStatement;
import statements.AssignStatement;
import statements.DeclareAndAssignStatement;
import statements.DeclareStatement;
import statements.EndStatement;
import statements.IfStatement;
import statements.LoopStatement;
import statements.PrintStatement;
import statements.Statement;
import statements.StatementBlock;
import types.Array;
import types.BooleanValue;
import types.CharValue;
import types.Condition;
import types.DoubleValue;
import types.IntegerValue;
import types.Type;
import types.TypeExpression;
import util.Syntax;
import util.TypeCreator;
import util.Util;

public class StatementParser {
	private Reader read;
	private HashMap<String, Type> varmap;
	private ExpressionParser expressionParser;
	private ConditionParser conditionParser;
	private ArrayParser arrayParser;
	private TypeExpressionParser valueParser;
	private Scrypt scrypt;
	
	
	public StatementParser(InputStream in, Scrypt scrypt) {
		this.scrypt = scrypt;
		this.read = new Reader(in, Syntax.getIdentifiers(), Syntax.getTokens());
		this.varmap = new HashMap<String, Type>();
		this.expressionParser = new ExpressionParser(read, varmap);
		this.conditionParser = new ConditionParser(read, expressionParser, varmap);
		this.arrayParser = new ArrayParser(read, varmap, valueParser, expressionParser);
		expressionParser.linkArrayParser(arrayParser);
		this.valueParser = new TypeExpressionParser(read, expressionParser, conditionParser, arrayParser, varmap);
		TypeCreator.register(read);
		
	}
	
	public StatementBlock parse() {
		return parseStatementBlock(0);
	}
	
	private Statement parseStatement(int nestledness) {
		if (read.foundChar()) {
			String[] s = new String[] {Syntax.ifBegin, Syntax.loopBegin, Syntax.loopEnd, Syntax.print};
			switch (read.check(true, s)) {
			case 0:
				return parseIfStatement(nestledness);
			case 1:
				return parseLoopStatement(nestledness);
			case 2:
				return parseEndStatement();
			case 3:
				return parsePrintStatement();
			default:
				throw new ParsingException(read + "ParseStatement: Expects " + Util.stringArrayToString(s) + ".");
			}
		} else if (read.foundText()) {
			// none of the set statement starts--
			if (Syntax.primName(read.found())) {
				return parseDeclareStatement();
			} else {
				// str should be identifier
				// of either method
				if (read.peek(Syntax.methodLB)) {
					read.next();
					throw new ParsingException(read, "ParseStatement: Methods unimplemented.");
				// or variable
				} else {
					return parseAssignStatement();
				} //else {
//								throw new ParsingException(read + "StartWithString: Expects \"" + Syntax.assign + "\" : found \"" + read.found() + "\".");
//							}		// assuming it's variable now, not casting exception from here anymore
			}
		}
		throw new ParsingException(read + "ParseStatement: Unimplemented.");
		
//		switch (read.type) {
//		case Reader.CHAR:
//			String[] s = new String[] {Syntax.ifBegin, Syntax.loopBegin, Syntax.loopEnd, Syntax.print};
//			int q = read.check(true, s);
//			if (q == 0) {
//				return parseIfStatement(nestledness);
//			} else if (q == 1) {
//				return parseLoopStatement(nestledness);
//			} else if (q == 2) {
//				return parseEndStatement();
//			} else if (q == 3) {
//				return parsePrintStatement();
//			} else {
//				
//			}
//			
//		case Reader.TEXT:
//			String string = read.txtVal;
//			if (Syntax.primName(string)) {
//				return parseDeclareStatement();
//			} else {
//				read.next();
//				if (read.type == Reader.CHAR && read.check(Syntax.methodLB)) {
//					throw new ParsingException(read + "StartWithString: Methods unimplemented.");
//				} else if (read.type == Reader.TEXT && read.check(Syntax.assign)) {
//					return parseAssignStatement(string);
//				} else {
//					throw new ParsingException(read + "StartWithString: Expects \"" + Syntax.assign + "\" : found \"" + read.found() + "\".");
//				}
//			}
//			
//		case Reader.NUMERIC:
//			
//			break;
//		}
//		throw new ParsingException(read + "ParseStatement: Unimplemented.");
	}
	
	private StatementBlock parseStatementBlock(int nestledness) {
		StatementBlock sb = new StatementBlock();
		while (!read.found().equals(Syntax.statementBlockEnd) && read.charVal != 65535) {
			sb.addStatement(parseStatement(nestledness));
		}
		if (read.found().equals(Syntax.statementBlockEnd)) {
			read.next();
		}
		else {
			throw new ParsingException(read, "end of parse statement block");
		}
		return sb;
	}
	
	private Statement parseIfStatement(int nestledness) {
		Condition c = conditionParser.parseCondition();
		StatementBlock s = parseStatementBlock(nestledness);
		if (read.check(Syntax.elseBegin)) {
			read.next();
			return new IfStatement(c, s, parseStatementBlock(nestledness));
		} else {
			return new IfStatement(c, s);
		}
	
	}
	
	private Statement parseLoopStatement(int nestledness) {
		return new LoopStatement(parseStatementBlock(nestledness));
	}
	
	private Statement parseEndStatement() {
		return new EndStatement(conditionParser.parseCondition());
	}
	
	private Statement parsePrintStatement() {
		return new PrintStatement(valueParser.parseTypeExpression(), scrypt.out);
	}
	
	private Statement parseDeclareStatement() {
		String primName = read.txtVal;
		if (Syntax.primName(primName)) {
			read.next();
		} else {
			//error?
			throw new ParsingException(read, "Declare Statement: Invalid type name");
		}
		
		Type prim = TypeCreator.getNull(primName);
		
		while (read.check(Syntax.indexLB)) {
			if (!read.check(Syntax.indexRB)) {
				throw new ParsingException(read, "Declare Statement: Expected right array clause");
			}
			prim = new Array(prim);
		}
		
		// check variable name
		if (!read.foundText()) {
			throw new ParsingException(read, "Declare statement: Illegal type or variable name.");
		}
		//String name = read.txtVal;
		DeclareStatement statement = new DeclareStatement(prim, read.found(), varmap);
		if (read.peek(Syntax.assign)) {
			return new DeclareAndAssignStatement(statement, parseAssignStatement());
		} else {
			read.next();
		}
		
		return statement;
	}
	
	private AssignStatement parseAssignStatement() {
		String name = read.found();
		TypeExpression t = varmap.get(name);
		read.next(); // name is stored, moving on to see if identifier is followed by assign symbol or indexLB
		if (read.found(Syntax.assign)) {
			read.next();
			if (t instanceof BooleanValue) {
				return new AssignStatement(name, conditionParser.parseCondition(), varmap);
			} else if (t instanceof IntegerValue || t instanceof DoubleValue || t instanceof CharValue) {
				return new AssignStatement(name, expressionParser.parseExpression(), varmap);
			} else if (t instanceof Array) {
				return new AssignStatement(name, arrayParser.parseArray(), varmap);
			} else {
				throw new ParsingException(read, "Parse assign statement: undeclared variable '" + name + "'.");
			}
		} else if (read.check(Syntax.indexLB)) {
			if (!(t instanceof Array)) {
				throw new ParsingException(read, name + " is no array, cannot access specified index");
			}
			ArrayAccess arrayAccess = new ArrayAccess(name, varmap, arrayParser.parseIndexList());
			if (!(read.found(Syntax.assign))) {
				throw new ParsingException(read, " Tried to parse assign statement but failed");
			}
			read.next();
			TypeExpression val = valueParser.parseTypeExpression();
			return new ArrayAssignStatement(name, arrayAccess, val, varmap);
		}
		throw new ParsingException(read, " Tried to parse assign statement but failed");
	}
}