package types;

public interface NumericValue extends Type, Expression {
	public NumericValue add(NumericValue o);
	public NumericValue sub(NumericValue o);
	public NumericValue mul(NumericValue o);
	public NumericValue div(NumericValue o);
	
	public NumericValue negate();
	
	public BooleanValue lessThan(NumericValue o);
	public BooleanValue greaterThan(NumericValue o);
	public BooleanValue equivalent(NumericValue o);
}