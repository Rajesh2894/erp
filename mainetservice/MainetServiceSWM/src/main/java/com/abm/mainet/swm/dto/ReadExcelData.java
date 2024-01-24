package com.abm.mainet.swm.dto;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Utility;

/**
 * 
 * @author Jeetendra.Pal
 * @param <T>
 *
 */

public final class ReadExcelData<T> {
    private FileInputStream fis = null;
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;
    private XSSFRow row = null;
    private XSSFCell cell = null;
    private Cell rowError = null;
    private int sheetNo = 0;
    private int checkEmptyRow = 0;
    private Class<T> type;
    private List<T> parseData = new ArrayList<>();
    private List<String> errorList = new ArrayList<>();
    private String errorColumn;

    public ReadExcelData(String xlFilePath, Class<T> cls) throws Exception {
        this.fis = new FileInputStream(xlFilePath);
        this.type = cls;
        workbook = new XSSFWorkbook(fis);
        fis.close();
    }

    public ReadExcelData(String xlFilePath, Class<T> cls, int sheetNo) throws Exception {
        this.fis = new FileInputStream(xlFilePath);
        this.workbook = new XSSFWorkbook(fis);
        this.type = cls;
        this.sheetNo = sheetNo;
        fis.close();
    }

    private Object getCellDataInObject(PropertyDescriptor objPropertyDescriptor, Object obj, String colName, int rowNum)
            throws Exception {
        int col_Num = -1;
        row = sheet.getRow(0);

        for (int i = 0; i < row.getLastCellNum(); i++) {
            if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
                col_Num = i;
                break;
            }
        }
        row = sheet.getRow(rowNum + 1);
        if (row != null)
            cell = row.getCell(col_Num);
        errorColumn += "| " + cell + " | ";
        if (cell != null && !cell.toString().isEmpty()) {
            if (objPropertyDescriptor.getPropertyType() == String.class) {
                checkEmptyRow = 1;
                objPropertyDescriptor.getWriteMethod().invoke(obj, cell.toString());
            } else if (objPropertyDescriptor.getPropertyType() == Long.class) {
                checkEmptyRow = 1;
                objPropertyDescriptor.getWriteMethod().invoke(obj, (long) Double.parseDouble(cell.toString()));
            } else if (objPropertyDescriptor.getPropertyType() == Double.class) {
                checkEmptyRow = 1;
                objPropertyDescriptor.getWriteMethod().invoke(obj, Double.valueOf(cell.toString()));
            } else if (objPropertyDescriptor.getPropertyType() == Float.class) {
                checkEmptyRow = 1;
                objPropertyDescriptor.getWriteMethod().invoke(obj, Float.valueOf(cell.toString()));
            } else if (objPropertyDescriptor.getPropertyType() == Date.class) {
                checkEmptyRow = 1;
                objPropertyDescriptor.getWriteMethod().invoke(obj, Utility.stringToDate(cell.toString()));
            } else if (objPropertyDescriptor.getPropertyType() == BigDecimal.class) {
                checkEmptyRow = 1;
                objPropertyDescriptor.getWriteMethod().invoke(obj, new BigDecimal(cell.toString()));
            }
        }
        return obj;

    }

    @SuppressWarnings({ "unchecked" })
    public void parseExcelList() throws Exception {

        String sheetName = workbook.getSheetName(sheetNo);
        sheet = workbook.getSheet(sheetName);
        String lableName = ApplicationSession.getInstance().getMessage(type.getSimpleName());

        Map<String, String> map = new HashMap<String, String>();

        for (String keyValue : lableName.split("|")) {

            String[] pairs = keyValue.split(":", 2);
            map.put(pairs[0], pairs.length == 1 ? "" : pairs[1]);
        }

        for (int i = 0; i < (sheet.getPhysicalNumberOfRows() - 1); i++) {
            checkEmptyRow = 0;
            try {
                Object obj = type.newInstance();
                row = sheet.getRow(0);
                errorColumn = "";
                for (Cell row : row) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        if (entry.getValue().equals(row.toString())) {
                            try {
                                rowError = row;
                                PropertyDescriptor objPropertyDescriptor = new PropertyDescriptor(entry.getKey().toString(),
                                        obj.getClass());
                                getCellDataInObject(objPropertyDescriptor, obj, entry.getValue().toString(), i);
                            } catch (NumberFormatException e) {
                                errorList.add("Error Formate: Column [" + rowError.toString() + "] and Row No [" + (i + 2)
                                        + " ] Data: " + errorColumn);
                            }

                        }
                    }
                }

                if (checkEmptyRow == 1)
                    parseData.add((T) obj);
            } catch (IntrospectionException ie) {
                errorList.add("Error : Please Verify Header Column of Sheet: " + sheetName);
            } catch (Exception e) {
                errorList.add("Error : Please Verify xlsx sheet Template");
            }
        }

        if (parseData.isEmpty()) {
            errorList.add("Template Can Not Be Empty");
        }

    }

    public List<T> getParseData() {
        return parseData;
    }

    public void setParseData(List<T> parseData) {
        this.parseData = parseData;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

}
