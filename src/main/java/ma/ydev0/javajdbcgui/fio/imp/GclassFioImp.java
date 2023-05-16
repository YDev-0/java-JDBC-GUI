package ma.ydev0.javajdbcgui.fio.imp;

import ma.ydev0.javajdbcgui.entities.Gclass;
import ma.ydev0.javajdbcgui.fio.GclassFio;
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

public class GclassFioImp implements GclassFio {
    @Override
    public void exportAsExcel(List<Gclass> gclasses, String fileName) {
        //Création d'un objet de type fichier Excel
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Création d'un objet de type feuille Excel
        XSSFSheet spreadsheet = workbook.createSheet("Game Classes Info");

        //Création d'un objet row (ligne)
        XSSFRow row;

        //Les données à inserer;
        Map< String, Object[] > classInfo = new TreeMap< String, Object[] >();
        classInfo.put( "1", new Object[] { "ID", "LABEL", "DESCRIPTION" });
        for (int i = 0; i < gclasses.size(); i++) {
            classInfo.put( "" + (i + 2), new Object[] {
                gclasses.get(i).getId(),
                gclasses.get(i).getLabel(),
                gclasses.get(i).getDescription()
            });
        }

        //parcourir les données pour les écrire dans le fichier Excel
        Set<String> keyid = classInfo.keySet();
        int rowid = 0;

        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object [] objectArr = classInfo.get(key);
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
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void importAsExcel(String path) {

    }
}
