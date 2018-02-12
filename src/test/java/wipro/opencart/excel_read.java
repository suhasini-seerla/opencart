package wipro.opencart;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excel_read {

	//Creating objects for POI class
    static XSSFSheet sh;
    static XSSFWorkbook wb;
  
    //Passing the filepath and sheetname
    //Creating an array with values of all rows and columns
    public Object[][] ExcelData(String filepath,String sheet) throws IOException
    {
          // TODO Auto-generated method stub
          File fil = new File(filepath);
          FileInputStream fis = new FileInputStream(fil);
          wb = new XSSFWorkbook(fis);
          sh = wb.getSheet(sheet);
          //getting row count
          int rowcount = sh.getLastRowNum();
          Row fistrow = sh.getRow(0);
          int colcount = fistrow.getLastCellNum();
          //creating an array
          Object[][] obj = new Object[rowcount][colcount];
          for(int i=1; i<=rowcount; i++)
          {
                Row row = sh.getRow(i);
                //navigating through rows
                for(int j=0; j<row.getLastCellNum();j++)
                {
                	  //Navigating through columns
                      Cell cell = row.getCell(j);
                      //System.out.print(cell+" ");
                      obj[i-1][j]=cell.getStringCellValue();
                }
               // System.out.print("\n");
          }
          //Returning the array with values
          return obj;
    }
}