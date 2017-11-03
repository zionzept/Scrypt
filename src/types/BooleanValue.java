package types;

import util.Syntax;

public class BooleanValue implements Type, Condition {
	public static final BooleanValue FALSE = new BooleanValue(false);
	public static final BooleanValue TRUE = new BooleanValue(true);
	public static final BooleanValue NULL = new BooleanValue(false);
	@Override
	public Type getNull() {
		return NULL;
	}
	
	private boolean value;
	
	public BooleanValue(boolean value) {
		this.value = value;
	}
	
	public BooleanValue and(BooleanValue other) {
		return new BooleanValue(value && other.value);
	}
	
	public BooleanValue or(BooleanValue other) {
		return new BooleanValue(value || other.value);
	}
	
	public BooleanValue equivalent(BooleanValue other) {
		return new BooleanValue(value == other.value);
	}
	
	public BooleanValue not() {
		return new BooleanValue(!value);
	}

	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		sb.append(value ? Syntax.constTrue : Syntax.constFalse);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public BooleanValue value() {
		return this;
	}
	
	public boolean evaluate() {
		return value;
	}

	@Override
	public BooleanValue equals(TypeExpression x) {
		return new BooleanValue(x instanceof BooleanValue && value == ((BooleanValue)x).value);
	}

	@Override
	public String getTypeName() {
		return Syntax.bool;
	}
}