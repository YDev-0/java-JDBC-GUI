package ma.ydev0.javajdbcgui.fio;

import ma.ydev0.javajdbcgui.entities.Gcharacter;

import java.io.File;
import java.util.List;

public interface GcharacterFio {

    boolean exportAsExcel(List<Gcharacter> gcharacters, String fileName, boolean replace);
    List<Gcharacter> importFromExcel(File file);
}
