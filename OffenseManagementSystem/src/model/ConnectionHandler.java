package model;

import java.sql.*;


public class ConnectionHandler {
    public Connection conn;
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String myUrl = "jdbc:mysql://"+Config.server+":"+Config.port+"/"+Config.database+"?autoReconnect=true&useSSL=false";
        String myDriver = "com.mysql.jdbc.Driver";
        Class.forName(myDriver);
        conn = DriverManager.getConnection(myUrl,Config.username,Config.password);
        return conn;
    }
    public void close(Connection connection, PreparedStatement prs, ResultSet rs) throws SQLException {
        if(connection != null){
            connection.close();
        }if(prs != null){
            prs.close();
        }if(rs != null){
            rs.close();
        }
    }
}
