package ma.ydev0.javajdbcgui.dao.imp;

import ma.ydev0.javajdbcgui.dao.UserDao;
import ma.ydev0.javajdbcgui.entities.Gclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImp implements UserDao {
  private static Connection conn = Dao.getConnection();

  @Override
  public boolean login(String username, String password) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    boolean connected = false;

    try {
      ps = conn.prepareStatement("SELECT * FROM user where username = ? and password = ?");
      ps.setString(1, username);
      ps.setString(2, password);
      rs = ps.executeQuery();

      connected = rs.next();
    } catch (SQLException e) {
      System.err.println("problème de requête pour sélectionner un utilisateur");
    } finally {
      Dao.closeResultSet(rs);
      Dao.closeStatement(ps);
    }
    return connected;
  }

}
