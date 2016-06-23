import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class PreSufLeanParser {
	private static final String INPUT_FILE = "lean_domain_search_pre_suf.txt";
	private static final String OUTPUT_FILE = "lean_domain_search_pre_suf_parsed_list.txt";

	public static void main(String args[]) {

		try {

			FileWriter fw = new FileWriter(OUTPUT_FILE, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);

			BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
			String line;
			int size;
			while ((line = br.readLine()) != null) {
				String[] arr = line.replaceAll("\\s+", " ").trim().split(" ");
				size = arr.length;
				for (int i = 0; i < size; i++) {
					out.println(arr[i].replace("+", ""));
				}
			}
			br.close();
			out.close();
			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
