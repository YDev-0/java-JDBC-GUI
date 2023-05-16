package ma.ydev0.javajdbcgui.fio;

import ma.ydev0.javajdbcgui.entities.Gclass;

import java.util.List;

public interface GclassFio {
    void exportAsExcel(List<Gclass> gclasses, String fileName);
    void importAsExcel(String path);
}
