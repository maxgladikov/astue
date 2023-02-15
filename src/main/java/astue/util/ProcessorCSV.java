package astue.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessorCSV {

	public static List<List<String>> read(String name) throws IOException {
	List<List<String>> records = new ArrayList<>();
	try (BufferedReader br = new BufferedReader(new FileReader(name))) {
	    String line;
	    while ((line = br.readLine()) != null) {
	        String[] values = line.split(",");
	        records.add(Arrays.asList(values));
	    	}
		}
	return records;
	}

	public static List<List<String>> readUTF(String path) throws IOException {
		List<List<String>> records = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(path);
			       InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
			       BufferedReader reader = new BufferedReader(isr)
			  ) {
			      String str;
			      while ((str = reader.readLine()) != null) {
			         records.add(Arrays.asList(str.split(",")));
			      }
			  } catch (IOException e) {
			      e.printStackTrace();
			  }
		return records;
	}
}
