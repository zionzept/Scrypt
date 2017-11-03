package types;

public class ArrayTester {
	public static void main(String[] args) {
		Array a = new Array(IntegerValue.NULL);
		a.create(2);
		a.set(0, new IntegerValue(2));
		a.set(1, new IntegerValue(5));
		
		System.out.println(a);
		
		Type t = a.get(new IntegerValue(0));
		System.out.println(t);
		
		t = a.get(new IntegerValue(1));
		System.out.println(t);
		
		a.set(0, new IntegerValue(3).mul(new IntegerValue(5)));
		
		System.out.println(a);
		
		t = a.get(new IntegerValue(0));
		System.out.println(t);
		
		t = a.get(new IntegerValue(1));
		System.out.println(t);
	}
}
