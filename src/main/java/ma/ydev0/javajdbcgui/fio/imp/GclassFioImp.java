package ma.ydev0.javajdbcgui.fio.imp;

import ma.ydev0.javajdbcgui.entities.Gcharacter;
import ma.ydev0.javajdbcgui.entities.Gclass;
import ma.ydev0.javajdbcgui.fio.GclassFio;
import ma.ydev0.javajdbcgui.service.ServiceGclass;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class GclassFioImp implements GclassFio {
    private static XSSFRow row;
    @Override
    public boolean exportAsExcel(List<Gclass> gclasses, String fileName, boolean replace) {
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

        if(checkIfExist(fileName) && !replace) return false;

        //Excrire les données dans un FileOutputStream
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("src/main/resources/" + fileName + ".xlsx");
            workbook.write(out);
            out.close();
            System.out.println("Work done.");
            return true;
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkIfExist(String fileName) {
        File f = new File("src/main/resources/" + fileName + ".xlsx");
        return f.isFile();
    }


    @Override
    public List<Gclass> importFromExcel(File file)  {
        try(FileInputStream fis = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = spreadsheet.iterator();

            List<Gclass> gclassesList = new ArrayList<>();

            while (rowIterator.hasNext()) {
                row = (XSSFRow) rowIterator.next();
                if(row.getPhysicalNumberOfCells() == 2) new IOException("Error while getting data from row");

                gclassesList.add(instantiateGclass(row));
            }

            return gclassesList;
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private Gclass instantiateGclass(Row row) {
        int id = Integer.parseInt(row.getCell(0).toString());
        String label = row.getCell(1).toString();
        String description = row.getCell(2).toString();

        return new Gclass(id,label, description);
    }
}
