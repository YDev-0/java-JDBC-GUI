package ma.ydev0.javajdbcgui.service;

import ma.ydev0.javajdbcgui.dao.GclassDao;
import ma.ydev0.javajdbcgui.dao.imp.GclassDaoImp;
import ma.ydev0.javajdbcgui.entities.Gclass;
import ma.ydev0.javajdbcgui.fio.GclassFio;
import ma.ydev0.javajdbcgui.fio.imp.GclassFioImp;

import java.util.List;

public class ServiceGclass {
  private GclassDao gclassDao = new GclassDaoImp();
  private GclassFio gclassFio = new GclassFioImp();

  public List<Gclass> findAll() {
    return gclassDao.findAll();
  }

  public void save(Gclass gclass) {
    gclassDao.insert(gclass);
  }
  public void update(Gclass gclass) {
    gclassDao.update(gclass);
  }
  public void remove(Gclass gclass) {
    gclassDao.deleteById(gclass.getId());
  }

  public Gclass findById(int id) {
    return gclassDao.findById(id);
  }

  public boolean exportAsExcel(List<Gclass> gclasses, String fileName, boolean replace) {
    return gclassFio.exportAsExcel(gclasses, fileName, replace);
  }
}
