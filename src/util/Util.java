package util;

import java.util.HashMap;
import java.util.Map.Entry;


public class Util {
	public static final String LF = new String(new char[] {(char)10, (char)13});//System.getProperty("line.separator");
	public static final int TAB_SIZE = 1;
	
	public static void printTabbed(int tabs, char c) {
		StringBuilder sb = new StringBuilder();
		addTabs(tabs, sb);
		sb.append(c);
		System.out.println(sb.toString());
	}
	
	public static void printTabbed(int tabs, String s) {
		StringBuilder sb = new StringBuilder();
		addTabs(tabs, sb);
		sb.append(s);
		System.out.println(sb.toString());
	}
	
	public static void addTabs(int tabs, StringBuilder sb) {
		for (int i = 0; i < tabs * TAB_SIZE; i++) {
			sb.append('\t');
		}
	}
	
	public static String charArrayToString(char[] c) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < c.length; i++) {
			sb.append("'").append(c[i]).append("', ");
		}
		return sb.toString();
	}
	
	public static String stringArrayToString(String[] s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length; i++) {
			sb.append('"').append(s[i]).append("\", ");
		}
		return sb.toString();
	}
	
	public static <K, V> String hashMapToString(HashMap<K, V> map) {
		StringBuilder sb = new StringBuilder();
		for (Entry<K, V> e : map.entrySet()) {
			sb.append(e.getKey().toString()).append(" -- ").append(e.getValue().toString()).append('\n');
		}
		return sb.toString();
	}
}