package ma.ydev0.javajdbcgui.fio.imp;

import ma.ydev0.javajdbcgui.dao.GclassDao;
import ma.ydev0.javajdbcgui.entities.Gcharacter;
import ma.ydev0.javajdbcgui.entities.Gclass;
import ma.ydev0.javajdbcgui.fio.GcharacterFio;
import ma.ydev0.javajdbcgui.service.ServiceGclass;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class GcharacterFioImp implements GcharacterFio {
    private static XSSFRow row;
    @Override
    public boolean exportAsExcel(List<Gcharacter> gcharacters, String fileName, boolean replace) {
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
                gcharacters.get(i).getGClass().getId()
            });
        }

        // Parcourir les données pour les écrire dans le fichier Excel
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

        if(checkIfExist(fileName) && !replace) return false;

        //Excrire les données dans un FileOutputStream
        FileOutputStream out;
        try {
            out = new FileOutputStream("src/main/resources/" + fileName + ".xlsx");
            workbook.write(out);
            out.close();
            System.out.println("Work done.");
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkIfExist(String fileName) {
        File f = new File("src/main/resources/" + fileName + ".xlsx");
        return f.isFile();
    }

    @Override
    public List<Gcharacter> importFromExcel(File file) {
        try(FileInputStream fis = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = spreadsheet.iterator();

            List<Gcharacter> gcharactersList = new ArrayList<>();
            Map<Integer, Gclass> gclassesMap = new HashMap<>();
            ServiceGclass serviceGclass = new ServiceGclass();
            rowIterator.next();
            while (rowIterator.hasNext()) {
                row = (XSSFRow) rowIterator.next();
                if(row.getPhysicalNumberOfCells() == 5) new IOException("Error while getting data from row");

                System.out.println("Gclass id from row is : " + row.getCell(4).toString());
                int class_id = Integer.parseInt(row.getCell(4).toString());
                Gclass gclass = gclassesMap.get(class_id);

                if(gclass == null) {
                    gclass = serviceGclass.findById(class_id);
                    gclassesMap.put(class_id, gclass);
                }

                gcharactersList.add(instantiateGcharacter(row, gclass));
            }

            return gcharactersList;
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private Gcharacter instantiateGcharacter(Row row, Gclass gclass) {
        int id = Integer.parseInt(row.getCell(0).toString());
        String name = row.getCell(1).toString();
        int health = Integer.parseInt(row.getCell(2).toString());
        float damage = Float.parseFloat(row.getCell(3).toString());

        return new Gcharacter(id, name, health, damage, gclass);
    }
}
