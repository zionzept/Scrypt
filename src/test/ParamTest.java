package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public abstract class ParamTest {

	@Parameters
	public static Iterable<? extends Object> data() {
		System.err.println("GET ITER");
	    return fileList;
	}
	
	public static List<File> fileList;
	// hello there
	public ParamTest(String path, boolean searchSubFolders) throws FileNotFoundException {
		File rootFolder = new File(path);
		if (!rootFolder.exists()) {
			throw new FileNotFoundException("The specified path doesn't exist: " + path + ".");
		}
		if (!rootFolder.isDirectory()) {
			throw new FileNotFoundException("The specified path is not a folder: " + path + ".");
		}
		List<File> list = new LinkedList<>();
		collectFiles(list, rootFolder, searchSubFolders);
		fileList = list;
		printFiles(list);
		System.err.println("SETUP FILES");
	}

	private void collectFiles(List<File> list, File folder, boolean searchSubFolders) {
		List<File> files = Arrays.asList(folder.listFiles());
		for (File file : files) {
			if (file.isFile()) {
				list.add(file);
			} else if (file.isDirectory() && searchSubFolders) {
				collectFiles(list, file, searchSubFolders);
			}
		}
	}
	
	private void printFiles(List<File> files) {
		for (File file : files) {
			System.out.println(file.getPath());
		}
	}

	 @Test
	 public void test() {
	 }
}
