package scriptforge.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	public static List<String> readLines(File file) throws IOException {
		List<String> lines = new ArrayList<String>();
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		br.close();
		
		return lines;
	}
}
