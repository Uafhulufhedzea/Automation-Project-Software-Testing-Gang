package Utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {
    private static Workbook workbook;
    private static Sheet sheet;

    public static void setExcelFile(String path, String sheetName) throws IOException {
        FileInputStream excelFile = new FileInputStream(path);
        workbook = new XSSFWorkbook(excelFile);
        sheet = workbook.getSheet(sheetName);
    }

    public static String getCellData(int rowNum, int colNum) {
        try {
            Cell cell = sheet.getRow(rowNum).getCell(colNum);
            if (cell == null) {
                return "";
            }
            return cell.getStringCellValue();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
