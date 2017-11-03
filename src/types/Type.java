package types;

public interface Type extends TypeExpression {
	public String getTypeName();
	public BooleanValue equals(TypeExpression x);
	public Type getNull();
}