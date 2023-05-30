package ma.ydev0.javajdbcgui.fio;

import ma.ydev0.javajdbcgui.entities.Gclass;

import java.io.File;
import java.util.List;

public interface GclassFio {
    boolean exportAsExcel(List<Gclass> gclasses, String fileName, boolean replace);
    List<Gclass> importFromExcel(File file);
}
