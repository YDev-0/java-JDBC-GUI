package ma.ydev0.javajdbcgui.service;

import ma.ydev0.javajdbcgui.dao.UserDao;
import ma.ydev0.javajdbcgui.dao.imp.UserDaoImp;

public class ServiceConnect {

  private UserDao userDao = new UserDaoImp();

  public boolean connect(String username, String password) {
    return userDao.login(username, password);
  }
}
