package ma.ydev0.javajdbcgui.dao.imp;


import ma.ydev0.javajdbcgui.dao.GclassDao;
import ma.ydev0.javajdbcgui.entities.Gclass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GclassDaoImp implements GclassDao {

  private static Connection conn = Dao.getConnection();

  @Override
  public void insert(Gclass gclass) {
    PreparedStatement ps = null;

    try {
      ps = conn.prepareStatement("INSERT INTO gclass (label, description) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, gclass.getLabel());
      ps.setString(2, gclass.getDescription());
      int rowsAffected = ps.executeUpdate();

      if (rowsAffected > 0) {
        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
          int id = rs.getInt(1);

          gclass.setId(id);
        }

        Dao.closeResultSet(rs);
      } else {
        System.out.println("Aucune ligne renvoyé");;
      }

    } catch (SQLException e) {
      System.err.println("problème de requête pour l'ajout de la class : " + gclass);
      System.err.println(e.getMessage());
    } finally {
      Dao.closeStatement(ps);
    }
  }

  @Override
  public void update(Gclass gclass) {
    PreparedStatement ps = null;

    try {
      ps = conn.prepareStatement("UPDATE gclass SET label = ?, description = ? WHERE id = ?");
      ps.setString(1, gclass.getLabel());
      ps.setString(2, gclass.getDescription());
      ps.setInt(3, gclass.getId());
      ps.executeUpdate();

    } catch (SQLException e) {
      System.err.println("problème de requête pour mettre a jour de la class : " + gclass);
    } finally {
      Dao.closeStatement(ps);
    }
  }

  @Override
  public void deleteById(int id) {
    PreparedStatement ps = null;

    try {
      ps = conn.prepareStatement("DELETE FROM gclass WHERE id = ?");
      ps.setInt(1, id);
      ps.executeUpdate();
    } catch (SQLException e) {
      System.err.println("problème de requête pour supprimer la class avec id : " + id);
    } finally {
      Dao.closeStatement(ps);
    }
  }

  @Override
  public Gclass findById(int id) {
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      ps = conn.prepareStatement("SELECT * FROM gclass where id = ?");
      ps.setInt(1, id);
      rs = ps.executeQuery();

      Gclass gclass = new Gclass();

      if (rs.next()) {
        gclass.setId(rs.getInt("id"));
        gclass.setLabel(rs.getString("label"));
        gclass.setDescription(rs.getString("description"));
      }

      return gclass;
    } catch (SQLException e) {
      System.err.println("problème de requête pour sélectionner un département");;
      return null;
    } finally {
      Dao.closeResultSet(rs);
      Dao.closeStatement(ps);
    }
  }

  @Override
  public List<Gclass> findAll() {
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      ps = conn.prepareStatement("SELECT * FROM gclass");
      rs = ps.executeQuery();

      List<Gclass> gclasses = new ArrayList<>();

      while (rs.next()) {
        Gclass department = new Gclass();

        department.setId(rs.getInt("id"));
        department.setLabel(rs.getString("label"));
        department.setDescription(rs.getString("description"));

        gclasses.add(department);
      }

      return gclasses;
    } catch (SQLException e) {
      System.err.println("problème de requête pour sélectionner un département");;
      return null;
    } finally {
      Dao.closeResultSet(rs);
      Dao.closeStatement(ps);
    }
  }
}
