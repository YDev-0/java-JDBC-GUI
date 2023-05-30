package ma.ydev0.javajdbcgui.service;

import ma.ydev0.javajdbcgui.dao.GcharacterDao;
import ma.ydev0.javajdbcgui.dao.imp.GcharacterDaoImp;
import ma.ydev0.javajdbcgui.entities.Gcharacter;
import ma.ydev0.javajdbcgui.fio.GcharacterFio;
import ma.ydev0.javajdbcgui.fio.imp.GcharacterFioImp;

import java.io.File;
import java.util.List;

public class ServiceGcharacter {
  private GcharacterDao gcharacterDao = new GcharacterDaoImp();
  private GcharacterFio gcharacterFio = new GcharacterFioImp();

  public List<Gcharacter> findAll() {
    return gcharacterDao.findAll();
  }

  public void save(Gcharacter gcharacter) {
    gcharacterDao.insert(gcharacter);
  }
  public void update(Gcharacter gcharacter) {
    gcharacterDao.update(gcharacter);
  }
  public void remove(Gcharacter gcharacter) {
    gcharacterDao.deleteById(gcharacter.getId());
  }

  public boolean exportAsExcel(List<Gcharacter> gcharacters, String fileName, boolean replace) {
    return gcharacterFio.exportAsExcel(gcharacters, fileName, replace);
  }

  public List<Gcharacter> importFromExcel(File file) {
    return gcharacterFio.importFromExcel(file);
  }
}
