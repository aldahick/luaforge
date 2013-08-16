package scriptforge.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class InfoParser {
	public static HashMap<String, String> parseFile(File file) throws IOException {
		HashMap<String, String> props = new HashMap<String, String>();
		
		List<String> rawlines = FileUtils.readLines(file);
		if (rawlines==null || rawlines.isEmpty()) {
			die(file);
		}
		for (String i : rawlines) {
			String[] split = i.split(":");
			if (split.length < 2) {
				die(file);
			}
			String val = split[1];
			if (split.length > 2) {
				for (int k=2; k<split.length; k++) {
					val += split[k];
				}
			}
			
			props.put(split[0].replaceAll(" ", ""), val);
		}
		for (String i : props.keySet()) {
			System.out.println(i+":"+props.get(i));
		}
		return props;
	}
	private static void die(File file) {
		throw new RuntimeException("File "+file.getAbsolutePath()+" could not be parsed as info by Luaforge.");
	}
}
