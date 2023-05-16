package ma.ydev0.javajdbcgui.fio.imp;

import ma.ydev0.javajdbcgui.entities.Gcharacter;
import ma.ydev0.javajdbcgui.fio.GcharacterFio;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class GcharacterFioImp implements GcharacterFio {
    @Override
    public void exportAsExcel(List<Gcharacter> gcharacters, String fileName) {
        // Création d'un objet de type fichier Excel
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Création d'un objet de type feuille Excel
        XSSFSheet spreadsheet = workbook.createSheet("Game Characters Info");

        //Les données à inserer;
        Map< String, Object[] > characterInfo = new TreeMap< String, Object[] >();
        characterInfo.put( "1", new Object[] { "ID", "NAME", "HEALTH", "DAMAGE", "CLASS ID" });
        for (int i = 0; i < gcharacters.size(); i++) {
            characterInfo.put( "" + (i + 2), new Object[] {
                gcharacters.get(i).getId(),
                gcharacters.get(i).getName(),
                gcharacters.get(i).getHealth(),
                gcharacters.get(i).getDamage() ,
                gcharacters.get(i).getgClass().getId()
            });
        }

        //parcourir les données pour les écrire dans le fichier Excel
        Set<String> keyid = characterInfo.keySet();
        int rowid = 0;

        //Création d'un objet row (ligne)
        XSSFRow row;

        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = characterInfo.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
            }
        }

        //Excrire les données dans un FileOutputStream
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("src/main/resources/" + fileName + ".xlsx");
            workbook.write(out);
            out.close();
            System.out.println("Work done.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void importAsExcel(String path) {

    }
}
