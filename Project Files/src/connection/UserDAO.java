package connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.User;

public class UserDAO {
	private Connect connect = Connect.getInstance();
	
	public ArrayList<User> getAllUsers(){
		ArrayList<User> userList = new ArrayList<>();
		String query = "SELECT * FROM Users";
		ResultSet rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String ID = rs.getString("ID");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String phoneNumber = rs.getString("phoneNumber");
				String address = rs.getString("address");
				String role = rs.getString("role");

				userList.add(new User(ID, username, password, phoneNumber, address, role));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

}
