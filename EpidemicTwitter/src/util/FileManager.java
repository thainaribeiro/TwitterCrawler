package util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {

	public String[] getKeywords(String path) {
		BufferedReader br = null;
		try {
			String linha = null;
			br = new BufferedReader(new FileReader(path));
			while (br.ready()) {
				linha = br.readLine();
			}
			return linha.split(",");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
