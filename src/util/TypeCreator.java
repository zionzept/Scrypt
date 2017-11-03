package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import parsing.ParsingException;
import reader.Reader;
import types.BooleanValue;
import types.CharValue;
import types.DoubleValue;
import types.IntegerValue;
import types.Type;
import types.TypeExpression;

public class TypeCreator {
	private static Reader read;
	
	public static void register(Reader read) {
		TypeCreator.read = read;
	}
	
	public static Type getNull(String type) {
		switch (type) {
		case Syntax.bool:
			return BooleanValue.NULL;
		case Syntax.integer:
			return IntegerValue.NULL;
		case Syntax.doubl:
			return DoubleValue.NULL;
		case Syntax.chr:
			return CharValue.NULL;
		}
		throw new ParsingException(read + "Type convertion error (should never occur). in: " + type);
	}
	
	public static CharValue getCharType() {
		if (read.check(Syntax.charIdentifier)) {
			if (read.found().length() == 1) {
				return new CharValue((read.found().charAt(0)));
			} else {
				throw new ParsingException(read + "Only one character may be used after identifier");
			}
		} else {
			throw new ParsingException(read + "No character identifier found");
		}
	}
	
	public static Class<? extends TypeExpression> getPrimitiveClass(String name) {
		Class<? extends TypeExpression> a = BooleanValue.class;
			Method m;
			try {
				m = a.getMethod("getValue", new Class<?>[] {});
				m.invoke(null, new Object[] {});
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		switch (name) {
		case Syntax.bool:
			return BooleanValue.class;
		case Syntax.integer:
			return IntegerValue.class;
		case Syntax.doubl:
			return DoubleValue.class;
		case Syntax.chr:
			return CharValue.class;
		}
		throw new ParsingException(read + "TypeCreator: no class found for provided name " + name);
	}
}