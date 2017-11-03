package util;

public class Syntax {
	
	public static final String
			statementBlockEnd = "¤",
			
			ifBegin = "?",
			
			loopBegin = "~",
			loopEnd = "$",
			
			add = "+",
			subtract = "-",
			multiply = "*",
			divide = "/",
			
			lessThan = "<",
			greaterThan = ">",
			equals = "=",
			
			not = "'",
			and = "&",
			or = "|",
			
			constTrue = "\"",
			constFalse = "!",
			
			methodLB = "(",
			methodRB = ")",
			expressionLB = "(",
			expressionRB = ")",
			indexLB = "[",
			indexRB = "]",
			arrayLB = "{",
			arrayRB = "}",
			conditionLB = "{",
			conditionRB = "}",
			
			methodSeparator = ".",
			arrayElementSeparator = ",",
			charIdentifier = "#",
			
			print = "£",
			assign = "@",
			elseBegin = new String(new char[] {(char)226, (char)130})    // â‚¬
			;
	
	public static final String
			bool = "bool",
			integer = "int",
			doubl = "float",
			chr = "char"
			;
	
	public static final String[] type = new String[] {
		bool, integer, doubl, chr
	};
	
	public static boolean primName(String s) {
		for (String t : type) {
			if (s.equals(t)) {
				return true;
			}
		}
		return false;
	}
	
	public static char[] getIdentifiers() {
		return new char[] {
				'_'
		};
	}
	
	public static String[] getTokens() {
		return new String[] {
				elseBegin
		};
	}
}