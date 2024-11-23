package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Connect {
	String url = "jdbc:mysql://localhost:3306/CaLouselF";
	String uname = "root";
	String password = "password";
	
	public ResultSet rs;
	public ResultSetMetaData rsm;
	
	private Connection con;
	private Statement st;
	
	private static Connect connect = null;
	
	private Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(this.url, this.uname, this.password);
			st = con.createStatement();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static Connect getInstance() {
		if(connect == null) {
			connect = new Connect();
		}
		return connect;
	}
	
}
