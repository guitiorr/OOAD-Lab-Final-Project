package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(this.url, this.uname, this.password);
            st = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Connect getInstance() {
        if (connect == null) {
            connect = new Connect();
        }
        return connect;
    }
    
    public ResultSet execQuery(String query) {
        try {
            rs = st.executeQuery(query);
            rsm = rs.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public void execUpdate(String query) {
        try {
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public PreparedStatement preparedStatement(String query) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ps;
    }

    // New methods for transaction management
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        con.setAutoCommit(autoCommit);
    }

    public void commit() throws SQLException {
        con.commit();
    }

    public void rollback() throws SQLException {
        con.rollback();
    }

    // Optionally, close the connection after operations
    public void close() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
