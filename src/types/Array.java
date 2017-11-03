package types;

import parsing.ParsingException;
import util.Syntax;

public class Array implements Type {
	public static boolean printArrayTypes;
	@Override
	public Type getNull() {
		return this; // this is strange, but used so far just to get a returned type. Probably want to get rid of getNull from stuff that's not a primitive type
	}
	
	private Type type;
	private Type[] array;
	
	public Array(Type type) {
		this.type = type;
		create(0);
	}
	
	public void create(int size) {
		array = new Type[size];
	}
	
	public int size() {
		return array.length;
	}
	
	public Type get(Expression index) {
		NumericValue val = index.value();
		if (!(val instanceof IntegerValue)) {
			throw new ParsingException("Unvalid expression for array access, must be of type integer");
		}
		return array[((IntegerValue)val).getJavaIntValue()];
	}
	
	public Type getArrayType() {
		return type;
	}
	
	public void set(int index, Type value) {
		array[index] = value;
	}
	
	public void set(Expression index, Type value) {
		NumericValue val = index.value();
		if (!(val instanceof IntegerValue)) {
			throw new ParsingException("Unvalid expression for array access, must be of type integer");
		}
		array[((IntegerValue)val).getJavaIntValue()] = value;
	}

	@Override
	public void addToStringBuilder(StringBuilder sb, int nestledness) {
		if (printArrayTypes) sb.append(type.getTypeName() + ":");  //Prints type contained in array. e.g. {{1, 2, 3}, {4, 5, 6}} -> int[]:{int:{1, 2, 3}, int:{4, 5, 6}} 
		
		sb.append("{");
		if (array.length > 0) {
			sb.append(array[0].toString());
			for (int i = 1; i < array.length; i++) {
				sb.append(Syntax.arrayElementSeparator).append(' ').append(array[i].toString());
			}
		}
		sb.append("}"); //TODO: move to syntax rules, same for above ^
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addToStringBuilder(sb, 0);
		return sb.toString();
	}

	@Override
	public Type value() {
		return this;
	}
	
	@Override
	public BooleanValue equals(TypeExpression x) {
		if (x == this) {
			return new BooleanValue(true);
		}
		return new BooleanValue(false);
	}

	@Override
	public String getTypeName() {
		return type.getTypeName() + Syntax.indexLB + Syntax.indexRB;
	}
}