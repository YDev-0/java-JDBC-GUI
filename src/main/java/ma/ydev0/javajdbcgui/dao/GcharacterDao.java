package ma.ydev0.javajdbcgui.dao;

import ma.ydev0.javajdbcgui.entities.Gcharacter;

import java.util.List;

public interface GcharacterDao {
  void insert(Gcharacter gcharacter);
  void update(Gcharacter gcharacter);
  void deleteById(int id);
  Gcharacter findById(int id);
  List<Gcharacter> findAll();
}
