package Utils;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import static org.apache.poi.ss.usermodel.CellType.*;

public class Excel {
    // Dependencies: POI | HSSF Workbook/Sheet/Row/Cell
// This method will read and return Excel data into a double array
    public static String[][] get(String filename) {
        String[][] dataTable = null;
        //File file = new File(filename);
        File file = new File(filename);
        try {
            // Create a file input stream to read Excel workbook and worksheet
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook xlwb = new XSSFWorkbook (fis);
            XSSFSheet xlSheet = xlwb.getSheetAt(0);

            // Get the number of rows and columns
            int numRows = xlSheet.getLastRowNum() + 1;
            int numCols = xlSheet.getRow(0).getLastCellNum();

            // Create double array data table - rows x cols
            // We will return this data table
            dataTable = new String[numRows][numCols];

            // For each row, create a HSSFRow, then iterate through the "columns"
            // For each "column" create an HSSFCell to grab the value at the specified cell (i,j)
            for (int i = 0; i < numRows; i++) {
                XSSFRow xlRow = xlSheet.getRow(i);
                for (int j = 0; j < numCols; j++) {
                    XSSFCell xlCell = xlRow.getCell(j);
                    String myCell = xlCell.toString();

                    switch(xlCell.getCellType())
                    {
                        case STRING:
                            dataTable[i][j] = xlCell.getStringCellValue();
                            break;
                        case NUMERIC:
                            Double numericValue = xlCell.getNumericCellValue();
                            dataTable[i][j] = numericValue.toString();
                            break;
                        case BOOLEAN:
                            Boolean booleanCellValue = xlCell.getBooleanCellValue();
                            dataTable[i][j] = booleanCellValue.toString();
                            break;
                    }

                    dataTable[i][j] = xlCell.toString();
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR FILE HANDLING " + e.toString());
        }
        return dataTable;
    }

    public static String[][] getExcel(String filename) {
        String[][] dataTable = null;
        //File file = new File(filename);
        File file = new File(filename);
        try {
            // Create a file input stream to read Excel workbook and worksheet
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook xlwb = new XSSFWorkbook (fis);
            XSSFSheet xlSheet = xlwb.getSheetAt(0);

            Iterator<Row> rowIterator = xlSheet.iterator();
            int i=0,j =0;
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();

                    switch(cell.getCellType())
                    {
                        case STRING:
                            dataTable[i][j] = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            Double numericValue = cell.getNumericCellValue();
                            dataTable[i][j] = numericValue.toString();
                            break;
                        case BOOLEAN:
                            Boolean booleanCellValue = cell.getBooleanCellValue();
                            dataTable[i][j] = booleanCellValue.toString();
                            break;
                    }


                }
            }

            // Traversing over each row of XLSX file while (rowIterator.hasNext()) { Row row = rowIterator.next(); // For each row, iterate through each columns Iterator<Cell> cellIterator = row.cellIterator(); while (cellIterator.hasNext()) { Cell cell = cellIterator.next(); switch (cell.getCellType()) { case Cell.CELL_TYPE_STRING: System.out.print(cell.getStringCellValue() + "\t"); break; case Cell.CELL_TYPE_NUMERIC: System.out.print(cell.getNumericCellValue() + "\t"); break; case Cell.CELL_TYPE_BOOLEAN: System.out.print(cell.getBooleanCellValue() + "\t"); break; default : } } System.out.println(""); }

        } catch (IOException e) {
            System.out.println("ERROR FILE HANDLING " + e.toString());
        }
        return dataTable;
    }
}
