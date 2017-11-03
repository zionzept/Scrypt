package test;

import java.io.File;

import org.junit.Test;

public class ParserTests extends AbstractScryptTest {
	private static final String sep = File.separator;
	private static final String expression = "expression" + sep;
	private static final String statement = "statement" + sep;
	private static final String declare = statement + "declare" + sep;
	private static final String assign = statement + "assign" + sep;
	private static final String problem = "problem" + sep;
	
	public ParserTests() {
		super("parse_test");
	}
	
	@Test
	public void empty_program() {
		assertParsable("empty_program");
	}
	
	@Test
	public void int_declare() {
		assertParsable(declare + "int_declare");
	}
	
	@Test
	public void float_declare() {
		assertParsable(declare + "float_declare");
	}
	
	@Test
	public void int_assign() {
		assertParsable(assign + "int_assign");
	}
	
	@Test
	public void float_assign() {
		assertParsable(assign + "float_assign");
	}
	
	@Test
	public void print_statement() {
		assertParsable(statement + "print_statement");
	}
	
	@Test
	public void add_expression() {
		assertParsable(expression + "add_expression");
	}
	
	@Test
	public void subtract_expression() {
		assertParsable(expression + "subtract_expression");
	}
	
	@Test
	public void multiply_expression() {
		assertParsable(expression + "multiply_expression");
	}
	
	@Test
	public void divide_expression() {
		assertParsable(expression + "divide_expression");
	}
	
	@Test
	public void if_statement() {
		assertParsable(statement + "if_statement");
	}

	@Test
	public void loop_statement() {
		assertParsable(statement + "loop_statement");
	}
	
	@Test
	public void p01() {
		assertParsable(problem + "p01");
	}
}
