package ru.tochilinmi.poiexcel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@SpringBootApplication
public class PoiExcelApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoiExcelApplication.class, args);

        String FILE_NAME = "./tmp/MyFirstExcel.xlsx";

        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Employee Data");

        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[] {"ID", "NAME", "LASTNAME"});
        for (int i=1;i<11;i++){
            data.put(String.valueOf(i), new Object[] {i, "some_"+i+"_NAME", "some_"+i+"_LASTNAME"});
        }

        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset){
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr){
                Cell cell = row.createCell(cellnum++);
                if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(FILE_NAME));
            workbook.write(out);
            out.close();
            System.out.println("written successfully on disk.");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
