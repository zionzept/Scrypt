package ss;

import util.Util;

public class Shit {
	public static void main(String[] args) {
		String lf = Util.LF;
		System.out.println(lf.length());
		for(char c : lf.toCharArray()) {
			System.out.println((int)c);
		}
	}
}
