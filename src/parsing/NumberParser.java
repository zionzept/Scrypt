package parsing;
import types.DoubleValue;
import types.IntegerValue;
import types.NumericValue;

public class NumberParser {
	public static NumericValue parseNumber(StringBuilder sb, boolean decimal) {
		if (decimal) {
			return parseDouble(sb, true);
		}
		return parseInteger(sb, false);
	}
	
	public static NumericValue parseInteger(StringBuilder sb, boolean decimal) {
		if (decimal) {
			System.out.println("INTEGER CANNOT CONTAIN DECIMAL");
			return null;
		}
		return new IntegerValue(sb.toString());
	}
	
	public static NumericValue parseDouble(StringBuilder sb, boolean decimal) {
		if (decimal) {
			return new DoubleValue(sb.toString());
		}
		System.out.println("LOLERROR in numberparser");
		return null;
	}
	
	// WTF is this class?
}