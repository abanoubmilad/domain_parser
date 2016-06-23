
/*
 * Java code - domain name prefix parser
 * 
 * by:       Abanoub Milad Nassief
 * email:    abanoubcs@gmail.com
 * 
 * created:  21/6/2016
 * edited    23/6/2016
 * 
 * 
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PrefixParser {
	private static final String DB_NAME = "test_db", USER = "root", PASS = "", OUTPUT_FILE = "prefix_list.txt",
			DOMAINS_TB = "test_tb";

	private static final String SQL_SELECT_DOMAINS = "SELECT * FROM " + DOMAINS_TB + " limit 150000";

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		Connection conn = null;
		Statement stmt1 = null, stmt2 = null;
		try {

			FileWriter fw = new FileWriter(OUTPUT_FILE, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/" + DB_NAME, USER, PASS);
			stmt1 = conn.createStatement();
			stmt2 = conn.createStatement();

			int pointer, size;
			String domainStr;
			ResultSet dictResult=null, domainsResult = stmt1.executeQuery(SQL_SELECT_DOMAINS);
			while (domainsResult.next()) {
				domainStr = domainsResult.getString("word");
				size = domainStr.length();
				pointer = 2;
				do {
					dictResult = stmt2.executeQuery(
							"select * from dict where word like '" + domainStr.substring(0, pointer) + "%' limit 1");
					if (dictResult.next()) {
						pointer++;
					} else {
						if (pointer != 2) {
							while (!stmt2
									.executeQuery(
											"select * from dict where word='" + domainStr.substring(0, --pointer) + "'")
									.next() && pointer > 1);
							if (pointer > 1)
								out.println(domainStr.substring(0, pointer));
						}

						break;
					}
				} while (pointer < size);
			}
			domainsResult.close();
			if(dictResult!=null){
			dictResult.close();
			}
			stmt1.close();
			stmt2.close();
			conn.close();
			out.close();
		} catch (

		Exception e) {
			e.printStackTrace();
		}

		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("YES!!, We did it in " + estimatedTime / 1000.0 + " seconds!! :D!");
	}
}