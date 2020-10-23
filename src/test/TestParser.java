package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import parsing.ParsingException;
import ss.Scrypt;
import statements.StatementBlock;
import test_framework.STest;
import util.Util;

public class TestParser {
	
	public static void make_tests(List<STest<?>> test_list) throws FileNotFoundException {
		List<File> file_list = new LinkedList<>();
		File root_folder = new File("scrypts/test/parse_test/formatted");
		dfs(root_folder, file_list);
		run_tests(test_list, file_list);
	}
	
	private static void dfs(File root_folder, List<File> file_list) throws FileNotFoundException {
		File[] files = root_folder.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				dfs(f, file_list);
			} else {
				file_list.add(f);
			}
		}
	}
	
	private static void run_tests(List<STest<?>> test_list, List<File> file_list) {
		for (File f : file_list) {
			try {
				String expected = read_file(f);
				String actual = parse_file(f);
				test_list.add(new STest<>(f.getName(), expected, actual));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static String read_file(File file) throws FileNotFoundException {
		StringBuilder sb = new StringBuilder();
		Scanner sc = new Scanner(file, "UTF-8");
		if (sc.hasNextLine()) {
			sb.append(sc.nextLine());
			while (sc.hasNextLine()) {
				sb.append(Util.LF).append(sc.nextLine());
			}
		}
		sc.close();
		return sb.toString();
	}
	
	private static String parse_file(File file) throws FileNotFoundException {
		Scrypt scrypt = new Scrypt(file);
		try {
			StatementBlock s = scrypt.parse();
			return Scrypt.format(s);
		} catch (ParsingException e) {
			return e.getMessage();
		}
	}

	public static void run_test(LinkedList<Exception> errors) throws FileNotFoundException {
		File root_folder = new File("scrypts/test/parse_test/formatted");
		dfs(root_folder, errors);
		System.out.println();
	}

	private static void dfs(File dir, LinkedList<Exception> errors) throws FileNotFoundException {
		File[] files = dir.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				dfs(f, errors);
			} else {
				parse_test(f, errors);
			}
		}
	}

	private static void parse_test(File file, LinkedList<Exception> errors) throws FileNotFoundException {
		StringBuilder sb = new StringBuilder();
		sb.append(file.getName());
		while (sb.length() < 30) {
			sb.append(" ");
		}
		try {
			Scrypt scrypt;
			scrypt = new Scrypt(file);
			StatementBlock s = scrypt.parse();
			if (match_syntax(s, file)) {
				sb.append("ok!");
			} else {
				sb.append("not ok!");
			}
		} catch (ParsingException e) {
			e.initCause(new Throwable(file.getName()));
			errors.add(e);
			sb.append("fail :(");
		}
		System.out.println(sb);
	}

	private static boolean match_syntax(StatementBlock s, File file) throws FileNotFoundException {
		String a = Scrypt.format(s);

		StringBuilder sb = new StringBuilder();
		Scanner sc = new Scanner(file, "UTF-8");
		// InputStreamReader isr = new InputStreamReader(new FileInputStream(file),
		// "UTF-8");
		if (sc.hasNextLine()) {
			sb.append(sc.nextLine());
			while (sc.hasNextLine()) {
				sb.append(Util.LF).append(sc.nextLine());
			}
		}

		sc.close();

		for (int i = 0; i < sb.length(); i++) {
			// if ()
		}
		String b = sb.toString();

	

		if (!b.equals(a)) {
			System.out.println("A: " + a.length());
			System.out.println(a);
			System.out.println();
			System.out.println("B: " + b.length());
			System.out.println(b);
			for (int i = 0; i < Math.max(a.length(), b.length()); i++) {
				if (i < a.length()) {
					System.out.print((int) a.charAt(i));
				}
				System.out.print('\t');
				if (i < b.length()) {
					System.out.print((int) b.charAt(i));
				}
				System.out.println();
			}
		}

		return b.equals(a);
	}
}
