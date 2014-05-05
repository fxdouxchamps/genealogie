package fx.genealogie.internal.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Util {

	public static boolean isBlankOrNull(String string) {
		if (string == null)
			return true;
		if ("".equals(string.trim()))
			return true;
		return false;
	}

	public static void copy(File src, File dst) throws IOException {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	public static void addStart(BufferedWriter writer) throws IOException {
		File template = new File(
				"d:\\workspace\\fx.genealogie\\src\\main\\resources\\template\\start.txt");
		BufferedReader reader = new BufferedReader(new FileReader(template));
		String line = null;
		while ((line = reader.readLine()) != null) {
			writer.append(line);
			writer.append(System.getProperty("line.separator"));
		}
		reader.close();
	}

	public static void addEnd(BufferedWriter writer) throws IOException {
		File template = new File(
				"d:\\workspace\\fx.genealogie\\src\\main\\resources\\template\\end.txt");
		BufferedReader reader = new BufferedReader(new FileReader(template));
		String line = null;
		while ((line = reader.readLine()) != null) {
			writer.append(line);
			writer.append(System.getProperty("line.separator"));
		}
		reader.close();
	}
}
