package ma.ydev0.javajdbcgui.dao.imp;

import ma.ydev0.javajdbcgui.dao.GcharacterDao;
import ma.ydev0.javajdbcgui.entities.Gcharacter;
import ma.ydev0.javajdbcgui.entities.Gclass;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GcharacterDaoImp implements GcharacterDao {

  private static Connection conn = Dao.getConnection();

  @Override
  public void insert(Gcharacter gcharacter) {
    PreparedStatement ps = null;

    try {
      ps = conn.prepareStatement("INSERT INTO gcharacter (name, health, damage, id_class) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, gcharacter.getName());
      ps.setInt(2, gcharacter.getHealth());
      ps.setFloat(3, gcharacter.getDamage());
      ps.setInt(4, gcharacter.getGClass().getId());
      int rowsAffected = ps.executeUpdate();

      if (rowsAffected > 0) {
        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
          int id = rs.getInt(1);
          gcharacter.setId(id);
        }

        Dao.closeResultSet(rs);
      } else {
        System.out.println("Aucune ligne renvoyée");
      }

    } catch (SQLException e) {
      System.err.println("Problème de requête pour ajouter le personnage : " + gcharacter + "\nDétail : " + e.getMessage());
    } finally {
      Dao.closeStatement(ps);
    }
  }

  @Override
  public void update(Gcharacter gcharacter) {
    PreparedStatement ps = null;

    try {
      ps = conn.prepareStatement("UPDATE gcharacter SET name = ?, health = ?, damage = ?, id_class = ? WHERE id = ?");
      ps.setString(1, gcharacter.getName());
      ps.setInt(2, gcharacter.getHealth());
      ps.setFloat(3, gcharacter.getDamage());
      ps.setInt(4, gcharacter.getGClass().getId());
      ps.setInt(5, gcharacter.getId());
      ps.executeUpdate();
    } catch (SQLException e) {
      System.err.println("problème de requête pour mettre a jour le personnage : " + gcharacter);
    } finally {
      Dao.closeStatement(ps);
    }
  }

  @Override
  public void deleteById(int id) {
    PreparedStatement ps = null;

    try {
      ps = conn.prepareStatement("DELETE FROM gcharacter WHERE id = ?");
      ps.setInt(1, id);
      ps.executeUpdate();
    } catch (SQLException e) {
      System.err.println("problème de requête pour supprimer un personnage avec l'id : " + id);
    } finally {
      Dao.closeStatement(ps);
    }
  }

  @Override
  public Gcharacter findById(int id) {
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      ps = conn.prepareStatement("SELECT * FROM gcharacter inner join gclass on gcharacter.id_class = gclass.id where gcharacter.id = ?");
      ps.setInt(1, id);
      rs = ps.executeQuery();

      Gcharacter gcharacter = null;

      if (rs.next()) {
        Gclass gclass = instantiateGclass(rs);
        gcharacter = instantiateGcharacter(rs, gclass);
      }

      return gcharacter;
    } catch (SQLException e) {
      System.err.println("problème de requête pour sélectionner un personnage");
      return null;
    } finally {
      Dao.closeResultSet(rs);
      Dao.closeStatement(ps);
    }
  }

  @Override
  public List<Gcharacter> findAll() {
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      ps = conn.prepareStatement("SELECT * FROM gcharacter inner join gclass on gcharacter.id_class = gclass.id order by gcharacter.name");
      rs = ps.executeQuery();

      List<Gcharacter> gcharacters = new ArrayList<>();
      Map<Integer, Gclass> map = new HashMap<>();

      while (rs.next()) {
        int gclass_id = rs.getInt(6);

        Gclass gclass = map.get(gclass_id);
        if(gclass == null) {
          gclass = instantiateGclass(rs);

          map.put(gclass_id, gclass);
        }

        gcharacters.add(instantiateGcharacter(rs, gclass));
      }

      return gcharacters;
    } catch (SQLException e) {
      System.err.println("problème de requête pour sélectionner un département");;
      return null;
    } finally {
      Dao.closeResultSet(rs);
      Dao.closeStatement(ps);
    }
  }

  private Gcharacter instantiateGcharacter(ResultSet rs, Gclass gclass) throws SQLException {
    Gcharacter gcharacter = new Gcharacter();

    gcharacter.setId(rs.getInt(1));
    gcharacter.setName(rs.getString(2));
    gcharacter.setHealth(rs.getInt(3));
    gcharacter.setDamage(rs.getFloat(4));
    gcharacter.setGClass(gclass);

    return gcharacter;
  }

  private Gclass instantiateGclass(ResultSet rs) throws SQLException {
    Gclass gclass = new Gclass();

    gclass.setId(rs.getInt(6));
    gclass.setLabel(rs.getString(7));
    gclass.setDescription(rs.getString(8));

    return gclass;
  }
}
