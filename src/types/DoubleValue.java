package types;

import util.Syntax;

public class DoubleValue implements NumericValue {
	public static final DoubleValue NULL = new DoubleValue(0);
	@Override
	public Type getNull() {
		return NULL;
	}
	
	double value;
	
	public DoubleValue(double d) {
		value = d;
	}
	
	public DoubleValue(String s) {
		value = Double.parseDouble(s);
	}

	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		sb.append(Double.toString(value));
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public NumericValue add(NumericValue o) {
		if (o instanceof IntegerValue) {
			return new DoubleValue(value + ((IntegerValue)o).value);
		} else if (o instanceof CharValue) {
			return new DoubleValue(value + ((CharValue)o).value);
		} else if (o instanceof DoubleValue) {
			return new DoubleValue(value + ((DoubleValue)o).value);
		}
		throw new RuntimeException("add end reach");
	}

	@Override
	public NumericValue sub(NumericValue o) {
		if (o instanceof IntegerValue) {
			return new DoubleValue(value - ((IntegerValue)o).value);
		} else if (o instanceof CharValue) {
			return new DoubleValue(value - ((CharValue)o).value);
		} else if (o instanceof DoubleValue) {
			return new DoubleValue(value - ((DoubleValue)o).value);
		}
		throw new RuntimeException("sub end reach");
	}

	@Override
	public NumericValue mul(NumericValue o) {
		if (o instanceof IntegerValue) {
			return new DoubleValue(value * ((IntegerValue)o).value);
		} else if (o instanceof CharValue) {
			return new DoubleValue(value * ((CharValue)o).value);
		} else if (o instanceof DoubleValue) {
			return new DoubleValue(value * ((DoubleValue)o).value);
		}
		throw new RuntimeException("mul end reach");
	}

	@Override
	public NumericValue div(NumericValue o) {
		if (o instanceof IntegerValue) {
			return new DoubleValue(value / ((IntegerValue)o).value);
		} else if (o instanceof CharValue) {
			return new DoubleValue(value / ((CharValue)o).value);
		} else if (o instanceof DoubleValue) {
			return new DoubleValue(value / ((DoubleValue)o).value);
		}
		throw new RuntimeException("div end reach");
	}

	@Override
	public NumericValue value() {
		return this;
	}

	@Override
	public NumericValue negate() {
		return new DoubleValue(-value);
	}

	@Override
	public BooleanValue lessThan(NumericValue o) {
		if (o instanceof IntegerValue) {
			return new BooleanValue(value < ((IntegerValue)o).value);
		} else if (o instanceof CharValue) {
			return new BooleanValue(value < ((CharValue)o).value);
		} else if (o instanceof DoubleValue) {
			return new BooleanValue(value < ((DoubleValue)o).value);
		}
		throw new RuntimeException("lessThan end reach");
	}

	@Override
	public BooleanValue greaterThan(NumericValue o) {
		if (o instanceof IntegerValue) {
			return new BooleanValue(value > ((IntegerValue)o).value);
		} else if (o instanceof CharValue) {
			return new BooleanValue(value > ((CharValue)o).value);
		} else if (o instanceof DoubleValue) {
			return new BooleanValue(value > ((DoubleValue)o).value);
		}
		throw new RuntimeException("greaterThan end reach");
	}

	@Override
	public BooleanValue equivalent(NumericValue o) {
		if (o instanceof IntegerValue) {
			return new BooleanValue(value == ((IntegerValue)o).value);
		} else if (o instanceof CharValue) {
			return new BooleanValue(value == ((CharValue)o).value);
		} else if (o instanceof DoubleValue) {
			return new BooleanValue(value == ((DoubleValue)o).value);
		}
		throw new RuntimeException("equivalent end reach");
	}

	@Override
	public BooleanValue equals(TypeExpression x) {
		return new BooleanValue(x instanceof DoubleValue && value == ((DoubleValue)x).value);
	}

	@Override
	public String getTypeName() {
		return Syntax.doubl;
	}
}