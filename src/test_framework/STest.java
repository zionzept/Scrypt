package test_framework;

public class STest<E> {

	private String id;
	private E expected;
	private E actual;

	private boolean pass;
	
	public STest(String id, E expected, E actual) {
		this.id = id;
		this.expected = expected;
		this.actual = actual;
		this.pass = expected.equals(actual);
	}
	
	public E expected() {
		return expected;
	}
	
	public E actual() {
		return actual;
	}
	
	public boolean pass() {
		return pass;
	}
	
	@Override
	public String toString() {
		return id;
	}
}