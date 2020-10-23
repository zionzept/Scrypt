package parsing;

import java.util.HashMap;

import conditions.ConditionClause;
import expressions.ExpressionClause;
import reader.Reader;
import types.Array;
import types.BooleanValue;
import types.Condition;
import types.Expression;
import types.NumericValue;
import types.Type;
import types.TypeExpression;
import util.Syntax;

public class TypeExpressionParser {
	private Reader read;
	private ExpressionParser expressionParser;
	private ConditionParser conditionParser;
	private ArrayParser arrayParser;
	private HashMap<String, Type> varmap;
	
	public TypeExpressionParser(Reader read, ExpressionParser expressionParser, ConditionParser conditionParser, 
			ArrayParser arrayParser, HashMap<String, Type> varmap) {
		this.read = read;
		this.expressionParser = expressionParser;
		this.conditionParser = conditionParser;
		this.arrayParser = arrayParser;
		this.varmap = varmap;
	}
	
	public TypeExpression parseSample() {
		// if 'leftClause': read, parseTypeExpression, expect rightClause, continue type. ie (1 + 2) 3, 
		// returns 1 + 2 from inner call, put in clause here, then call continues which should fix the * 3.
		if (read.check(Syntax.expressionLB)) {
			TypeExpression texp = parseTypeExpression();
			if (!read.check(Syntax.expressionRB)) {
				throw new ParsingException(read, "Clause not closed, expecting \"" + Syntax.expressionRB + "\"");
			}
			if (texp instanceof Expression) {
				texp = new ExpressionClause((Expression)texp);
			} else if (texp instanceof Condition) {
				texp = new ConditionClause((Condition)texp);
			}
			// clause for other types?
			return texp;
		}
		
		// a: array			variable, function, array constant	
		// b: object		variable, function, ((object constant?))	
		// c: boolExp		variable, function, boolean constant, not																		
		// d: numExp		variable, function, numeric constant, +-
		
		// 1: variable		a, b, c or d
		// 2: function		a, b, c or d
		// 3: numC || +-	d
		// 4: boolC || not  c
		// 5: arrayC		a
		//(6: objectC		b)
		
		if (read.foundText()) {
			if (read.peek(Syntax.methodLB)) {
				// all 4 kinds of methods caught here	[1]
				throw new ParsingException(read, "methods not implemented");
			}
			Type var = varmap.get(read.found());
			if (var != null) {
				// check for array? yup, do it!
				
				if (var instanceof Array && read.check(Syntax.indexLB)) {
					//return arrayParser.parseIndexList();
				} else {
					return expressionParser.parseExpression();
				}
				
				return var;// <- shouldn't exist, catch above	// all 4 kinds of variables caught here	[2]
			}
			throw new ParsingException(read, "variable not declared");
		}
		
		if (read.foundNumeric() || read.found(Syntax.add) || read.found(Syntax.subtract)) {
			return expressionParser.parseExpression();	// [3]
		}
		
		if (read.found(Syntax.constTrue) || read.found(Syntax.constFalse) || read.found(Syntax.not)) {
			return conditionParser.parseCondition();	// [4]
		}
		
		if (read.found(Syntax.arrayLB)) {
			return arrayParser.parseArray();	// [5]
		}
		
		// [6] ?
		throw new ParsingException(read, "constant method not implemented");
	}
	
	public TypeExpression parseContinue(TypeExpression texp) {
		if (texp instanceof Expression) {
			// (, +, -, /, *, num   can follow directly
			Expression exp = (Expression) texp;
			exp = expressionParser.parseExpression(exp);
			// exp is now the complete expression
			// now check if expression is part of condition
			if (read.check(false, new String[] {Syntax.lessThan, Syntax.greaterThan, Syntax.equals}) >= 0) {
				return conditionParser.parseCondition(exp);
			}
			return exp;
		}
		
		if (texp instanceof Condition) {
			// and, or can follow directly
			return conditionParser.parseCondition((Condition)texp);
		}
		
		if (texp instanceof Array) {
			// concat and stuff?
//			return arrayParser.parseArray((Array)texp);
		}
		
		// method?
		throw new ParsingException(read, "method not implemented");
	}
	
	public TypeExpression parseTypeExpression() {
		TypeExpression texp;
		texp = parseSample();
		texp = parseContinue(texp);
		return texp;
		
		
		
		
		
		
//		// First try parsing array?
//		
//		
//		
//		// First try parsing boolean expression.
//		if (read.foundText()) {
//			if (read.peek(Syntax.methodLB)) {
//				// Check if text is initiating a boolean method call, if so a boolean expression can be parsed.
//				return null;// Implement boolean methods parsing here
//			} else {
//				Type type = varmap.get(read.txtVal);
//				if (type instanceof BooleanValue) {
//					return conditionParser.parseCondition();
//				}
//			}
//		} 
//		if (read.check(false, new String[] {Syntax.constTrue, Syntax.constFalse, Syntax.conditionLB}) >= 0) {
//			return conditionParser.parseCondition();
//		}
//
//		// If no boolean expression could be parsed, try parsing expression
//		Expression exp = null;
//		if (read.foundText()) {
//			if (read.peek(Syntax.methodLB)) {
//				// Check if text is initiating a numeric method call, if so an expression can be parsed
//				return null;// Implement numeric methods parsing here
//			}else {
//				// Check if text is the name of a numeric variable, if so an expression can be parsed
//				Type type = varmap.get(read.txtVal);
//				//System.out.println(type.getTypeName());
//				if (type instanceof NumericValue) {
//					exp = expressionParser.parseExpression();
//				} else if (type instanceof Array) {                   // TODO: If no numeric value was found directly, it could be an array variable, for now assumes it's an int var i guess
//					exp = expressionParser.parseExpression();
//				}
//			}
//		} else if (read.check(false, new String[] {Syntax.add, Syntax.subtract, Syntax.expressionLB}) >= 0) {
//			exp = expressionParser.parseExpression();
//		} else {
//			exp = expressionParser.parseExpression();
//		}
//		// failed parsing type if no expression was parsed at this point, abort
//		if (exp == null) {
//			throw new ParsingException(read, "Cannot parse TypeExpression");
//		}
//		
//		// now check if normal exp is part of a boolean exp
//		if (read.check(false, new String[] {Syntax.lessThan, Syntax.greaterThan, Syntax.equals}) >= 0) {
//			return conditionParser.parseCondition(exp);
//		}
//		return exp;
	}	
}