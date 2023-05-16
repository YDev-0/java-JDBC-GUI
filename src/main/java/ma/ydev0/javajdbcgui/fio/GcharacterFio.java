package ma.ydev0.javajdbcgui.fio;

import ma.ydev0.javajdbcgui.entities.Gcharacter;

import java.util.List;

public interface GcharacterFio {

    void exportAsExcel(List<Gcharacter> gcharacters, String fileName);
    void importAsExcel(String path);
}
