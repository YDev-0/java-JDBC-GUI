package ma.ydev0.javajdbcgui.dao;

import ma.ydev0.javajdbcgui.entities.Gclass;

import java.util.List;

public interface GclassDao {
  void insert(Gclass gclass);
  void update(Gclass gclass);
  void deleteById(int id);
  Gclass findById(int id);
  List<Gclass> findAll();
}
