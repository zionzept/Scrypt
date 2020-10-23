package ss;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import test.TestParser;
import test_framework.STest;
import test_framework.STesting;

public class Test {

	public static void main(String[] args) throws FileNotFoundException {
		List<STest<?>> test_list = new LinkedList<>();
		TestParser.make_tests(test_list);
		new STesting(test_list);
		a();
	}
	
	public static void a() throws FileNotFoundException {
		//Scrypt.PRINT_PARSED_CODE = false;
				LinkedList<Exception> errors = new LinkedList<>();
				
				TestParser.run_test(errors);
				
				
				
				if (errors.isEmpty()) {
					System.out.println("All set!");
				} else {
					System.out.println(errors.size() + " test" + (errors.size() == 1 ? "" : "s") + " failed.\n");
					for (Exception e : errors) {
						System.out.println(e.getCause().getMessage());
						System.out.println(e);
						for (int i = 0; i < e.getStackTrace().length; i++) {
							System.out.println(e.getStackTrace()[i]);					
						}
						System.out.println();
					}
				}
	}
	
}