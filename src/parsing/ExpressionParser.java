package parsing;

import java.util.HashMap;

import expressions.AddExpression;
import expressions.ArrayAccess;
import expressions.DivideExpression;
import expressions.ExpressionClause;
import expressions.MultiplyExpression;
import expressions.NegateExpression;
import expressions.NumericVariable;
import expressions.SubtractExpression;
import reader.Reader;
import types.CharValue;
import types.Expression;
import types.Type;
import util.Syntax;

public class ExpressionParser {
	private Reader read;
	private HashMap<String, Type> varmap;
	private ArrayParser arrayParser;
	
	public ExpressionParser(Reader read, HashMap<String, Type> varmap) {
		this.read = read;
		this.varmap = varmap;
	}
	
	public void linkArrayParser(ArrayParser arrayParser) {
		this.arrayParser = arrayParser; //this is shit code
	}
	
	public Expression parseExpression() {
		return parseExpression(false);
	}
	
	private Expression parseExpression(boolean clause) {
		Expression e = parseTerm();
		int t = read.check(true, Syntax.add, Syntax.subtract);
		while (t >= 0) {
			switch (t) {
			case 0:
				e = new AddExpression(e, parseTerm());
				break;
			case 1:
				e = new SubtractExpression(e, parseTerm());
				break;
			}
			t = read.check(true, Syntax.add, Syntax.subtract);
		}
		if (clause) {
			read.check(Syntax.expressionRB);
			e = new ExpressionClause(e);
		}
		return e;
	}
		
	private Expression parseTerm() {
		Expression e;
		if (read.found().equals(Syntax.expressionLB)) {
			e = parseFactor();
			if (read.foundNumeric()) {
				e = new MultiplyExpression(e, parseFactor());
			}
		} else {
			e = parseFactor();
		}
		int t = read.check(false, Syntax.expressionLB, Syntax.multiply, Syntax.divide);
		while (t >= 0) {
			if (t == 0) {
				e = new MultiplyExpression(e, parseFactor());
				if (read.foundNumeric()) {
					e = new MultiplyExpression(e, parseFactor());
				}
			} else if (t == 1) {
				read.next();
				e = new MultiplyExpression(e, parseFactor());
			} else if (t == 2) {
				read.next();
				e = new DivideExpression(e, parseFactor());
			}
			t = read.check(false, Syntax.expressionLB, Syntax.multiply, Syntax.divide);
		}
		return e;
	}
	
	private Expression parseFactor() {
		Expression e = null;
		if (read.check(Syntax.expressionLB)) {
			// inner expression
			e = parseExpression(true);
		} else if (read.check(Syntax.add)) {
			// + sign before factor, ignored
			e = parseFactor();
		} else if (read.check(Syntax.subtract)) {
			// - sign before factor, negated value
			e = new NegateExpression(parseFactor());
		} else if (read.foundNumeric()) {
			// constant numeric value
				e = read.numVal;
				read.next();
		} else if(read.foundText()) {
			// symbol... variable or function?
			System.out.println("num var" + read);
			String name = read.txtVal;
			read.next();
			// check if variable index is specified
			if (read.check(Syntax.indexLB)) {
				// parse index path
				e = new ArrayAccess(name, varmap, arrayParser.parseIndexList());
			} else {
				// normal variable
				e = new NumericVariable(name, varmap);
			}	
		} else if(read.check(Syntax.charIdentifier)) {
			e = new CharValue(read.found().charAt(0));
			read.next();
		}
		return e;
	}
	
	
	/**
	 * Used when unknown type was parsed inside parantheses. This methods ensure any parts of the
	 * expression trailing after the parantheses are included aswell.
	 * @param e		Expression inside parantheses.
	 * @return		Complete expression.
	 */

	public Expression parseExpression(Expression e) {
		e = parseTerm(e);
		int t = read.check(true, Syntax.add, Syntax.subtract);
		while (t >= 0) {
			switch (t) {
			case 0:
				e = new AddExpression(e, parseTerm());
				break;
			case 1:
				e = new SubtractExpression(e, parseTerm());
				break;
			}
			t = read.check(true, Syntax.add, Syntax.subtract);
		}
		return e;
	}
	
	//TODO: does this catch variable after clause aswell as numeric??
	
	public Expression parseTerm(Expression e) {
		if (read.foundNumeric()) {
			e = new MultiplyExpression(e, parseFactor());
		}
		int t = read.check(false, Syntax.expressionLB, Syntax.multiply, Syntax.divide);
		while (t >= 0) {
			if (t == 0) {
				e = new MultiplyExpression(e, parseFactor());
				if (read.foundNumeric()) {
					e = new MultiplyExpression(e, parseFactor());
				}
			} else if (t == 1) {
				read.next();
				e = new MultiplyExpression(e, parseFactor());
			} else if (t == 2) {
				read.next();
				e = new DivideExpression(e, parseFactor());
			}
			t = read.check(false, Syntax.expressionLB, Syntax.multiply, Syntax.divide);
		}
		return e;
	}
}