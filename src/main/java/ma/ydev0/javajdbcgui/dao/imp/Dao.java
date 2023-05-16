package ma.ydev0.javajdbcgui.dao.imp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.*;

public class Dao {
  private static Connection conn = null;

  public static Connection getConnection() {
    if (conn == null) {
      try {
        Properties props = loadProperties();
        Class.forName(props.getProperty("driver"));
        String url = props.getProperty("dburl");
        conn = DriverManager.getConnection(url, props);
      } catch (ClassNotFoundException e) {
        System.err.println("Problème de chargement de Driver");
      } catch (SQLException e) {
        System.err.println("Problème de chargement de Driver Manager");
      }
    }

    return conn;
  }

  public static void closeConnection() {
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        System.err.println("Erreur de fermeture de connexion");
      }
    }
  }

  private static Properties loadProperties() {
    try (FileInputStream fs = new FileInputStream("src/main/resources/db.properties")) {
      Properties props = new Properties();

      props.load(fs);

      return props;
    } catch (IOException e) {
      System.err.println("Erreur de chargement de proriétés");
    }
    return null;
  }

  public static void closeStatement(Statement st) {
    if (st != null) {
      try {
        st.close();
      } catch (SQLException e) {
        System.err.println("Erreur de fermeture de Statement");
      }
    }
  }

  public static void closeResultSet(ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        System.err.println("Erreur de fermeture de ResultSet");
      }
    }
  }


//  @Override
//  public Connection getMysqlConnection(String host, String database, String username, String password) {
//    String url = "jdbc:mysql://" + host + '/' + database;
//    Connection connection = null;
//    try {
//      Class.forName("com.mysql.cj.jdbc.Driver");
//      connection = DriverManager.getConnection(url, username, password);
//    } catch (ClassNotFoundException e) {
//      System.err.println(e.getMessage());
//    } catch (SQLException e) {
//      System.err.println(e.getMessage());
//    }
//    return connection;
//  }
}
