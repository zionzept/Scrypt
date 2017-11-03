package parsing;

import java.util.HashMap;

import conditions.AndCondition;
import conditions.BooleanVariable;
import conditions.ConditionClause;
import conditions.EqualsCondition;
import conditions.GreaterThanCondition;
import conditions.LessThanCondition;
import conditions.NotCondition;
import conditions.OrCondition;
import reader.Reader;
import types.BooleanValue;
import types.Condition;
import types.Expression;
import types.Type;
import types.TypeExpression;
import util.Syntax;

public class ConditionParser {
	private Reader read;
	private ExpressionParser expressionParser;
	HashMap<String, Type> varmap;
	
	public ConditionParser(Reader read, ExpressionParser expressionParser, HashMap<String, Type> varmap) {
		this.read = read;
		this.expressionParser = expressionParser;
		this.varmap = varmap;
	}
	
	public Condition parseCondition() {
		return parseCondition(false);
	}
	
	private Condition parseCondition(boolean clause) {
		Condition c = parseConditionTerm();
		while (read.found().equals(Syntax.or)) {
			read.next();
			c = new OrCondition(c, parseConditionTerm());
		}
		if (clause) {
			read.check(Syntax.conditionRB);
			c = new ConditionClause(c);
		}
		return c;
	}
	
	private Condition parseConditionTerm() {
		Condition c = parseConditionPiece();
		while (read.found().equals(Syntax.and)) {
			read.next();
			c = new AndCondition(c, parseConditionPiece());
		}
		return c;
	}
	
	private Condition parseConditionPiece() {
		Condition c = null;
		if (read.found().equals(Syntax.conditionLB)) {
			read.next();
			c = parseCondition(true);
		} else {
			c = parseConditionalExpression();
		}
		while (read.found().equals(Syntax.not)) {
			read.next();
			c = new NotCondition(c);
		}
		return c;
	}
	
	private Condition parseConditionalExpression() {
		Condition c = null;
		if (read.found().equals(Syntax.constTrue)) {
			read.next();
			c = BooleanValue.TRUE;
		} else if (read.found().equals(Syntax.constFalse)) {
			read.next();
			c = BooleanValue.FALSE;
		} else {
			if (read.foundText()) {
				TypeExpression type = varmap.get(read.txtVal);
				if (type instanceof BooleanValue) {
					c = new BooleanVariable(read.txtVal, varmap);
					read.next();
					return c;
				} 
			}
			Expression exp0 = expressionParser.parseExpression();
			final int t = read.check(true, Syntax.lessThan, Syntax.greaterThan, Syntax.equals);
			Expression exp1 = expressionParser.parseExpression();
			switch (t) {
			case 0:
				c = new LessThanCondition(exp0, exp1);
				break;
			case 1:
				c = new GreaterThanCondition(exp0, exp1);
				break;
			case 2:
				c = new EqualsCondition(exp0, exp1);
				break;
			default:
				System.out.println(read + "ERROR IN CONDITIONAL EXPRESSION");
				System.exit(0);
				break;
			}
		}
		return c;
	}
	
	
	
	
	
	//with start exp
	
	public Condition parseCondition(Expression exp) {
		return parseCondition(false, exp);
	}
	
	private Condition parseCondition(boolean clause, Expression exp) {
		Condition c = parseConditionInner(exp);
		while (read.found().equals(Syntax.or)) {
			read.next();
			c = new OrCondition(c, parseConditionTerm());
		}
		if (clause) {
			read.check(Syntax.conditionRB);
			c = new ConditionClause(c);
		}
		return c;
	}
	
	private Condition parseConditionInner(Expression exp) {
		Condition c = parseConditionPiece(exp);
		while (read.found().equals(Syntax.and)) {
			read.next();
			c = new AndCondition(c, parseConditionPiece());
		}
		return c;
	}
	
	private Condition parseConditionPiece(Expression exp) {
		Condition c = null;
		c = parseConditionalExpression(exp);
		while (read.found().equals(Syntax.not)) {
			read.next();
			c = new NotCondition(c);
		}
		return c;
	}
	
	private Condition parseConditionalExpression(Expression exp0) {
		Condition c = null;
		final int t = read.check(true, Syntax.lessThan, Syntax.greaterThan, Syntax.equals);
		Expression exp1 = expressionParser.parseExpression();
		switch (t) {
		case 0:
			c = new LessThanCondition(exp0, exp1);
			break;
		case 1:
			c = new GreaterThanCondition(exp0, exp1);
			break;
		case 2:
			c = new EqualsCondition(exp0, exp1);
			break;
		default:
			System.out.println(read + "ERROR IN CONDITIONAL EXPRESSION with start exp");
			System.exit(0);
			break;
		}
		return c;
	}
	
	
	// Continued condition after type parser found condition in clause
	
	public Condition parseCondition(Condition c) {
		c = parseConditionInner(c);
		while (read.found().equals(Syntax.or)) {
			read.next();
			c = new OrCondition(c, parseConditionTerm());
		}
		return c;
	}
	
	//TODO: where to catch bool var / const then? ;<
	
	private Condition parseConditionInner(Condition c) {
		while (read.found().equals(Syntax.and)) {
			read.next();
			c = new AndCondition(c, parseConditionPiece());
		}
		return c;
	}
}
