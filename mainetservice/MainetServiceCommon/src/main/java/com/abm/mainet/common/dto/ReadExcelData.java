package com.abm.mainet.common.dto;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.formbuilder.domain.FormBuilderValueEntity;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author satish.rathore
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
    private List<FormBuilderValueEntity> rowValue = new ArrayList<>();
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
                if(cell.getCellTypeEnum()==CellType.NUMERIC) {
                    BigDecimal bd = new BigDecimal(cell.toString());
                    objPropertyDescriptor.getWriteMethod().invoke(obj, bd.round(new MathContext(15)).toPlainString());
                }else {
                objPropertyDescriptor.getWriteMethod().invoke(obj, cell.toString());
                }
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
                //for solid waste date format changed as dd-MM-yyyy defect no- 34150
                if(Utility.isValidFormat(MainetConstants.DATE_FORMAT_UPLOAD, cell.toString())) {
                	objPropertyDescriptor.getWriteMethod().invoke(obj, Utility.stringToDateFormatUpload(cell.toString()));
                }else {
                	objPropertyDescriptor.getWriteMethod().invoke(obj, Utility.stringToDate(cell.toString()));
                }
            } else if (objPropertyDescriptor.getPropertyType() == BigDecimal.class) {
                checkEmptyRow = 1;
                objPropertyDescriptor.getWriteMethod().invoke(obj, new BigDecimal(cell.toString()));
            }
        }
        return obj;

    }
    
    private String getCellDataInObject1( String colName, int rowNum)
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
        	cell.toString();
        }
        return "";

    }


    @SuppressWarnings({ "unchecked" })
    public void parseExcelList() throws Exception {

        String sheetName = workbook.getSheetName(sheetNo);
        sheet = workbook.getSheet(sheetName);
        String lableName = ApplicationSession.getInstance().getMessage(type.getSimpleName());

        Map<String, String> map = new HashMap<String, String>();

        for (String keyValue : lableName.split(Pattern.quote("|"))) {

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
    
    @SuppressWarnings("deprecation")
	public void parseExcelList1(String serviceName, List<String> labels, List<Long> labelsIds) throws Exception {

        String sheetName = workbook.getSheetName(sheetNo);
        sheet = workbook.getSheet(sheetName);
        String lableName = ApplicationSession.getInstance().getMessage(type.getSimpleName());
        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        Map<String, String> map = new HashMap<String, String>();

        for (String keyValue : lableName.split(Pattern.quote("|"))) {

            String[] pairs = keyValue.split(":", 2);
            map.put(pairs[0], pairs.length == 1 ? "" : pairs[1]);
        }

        for (int i = 0; i < (sheet.getPhysicalNumberOfRows()); i++) {
            checkEmptyRow = 0;
            try {
                Object obj = type.newInstance();
                if (i>0) {
                row = sheet.getRow(i);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
                String formatedDate = LocalDate.now().format(formatter).toString();
                
                SeqGenFunctionUtility  seqGenFunctionUtility = 
                		ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class);

                Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
				final Long sequence = seqGenFunctionUtility.generateSequenceNo(
                        MainetConstants.SolidWasteManagement.SHORT_CODE,
                        "TB_FORMBUILDER_VALUES", "FORM_VID", orgid,
                        MainetConstants.FlagD, null);

                final String paddingAppNo = String.format(MainetConstants.LOI.LOI_NO_FORMAT,
                        Integer.parseInt(sequence.toString()));
                final String orgId = orgid.toString();
                final String appNumber = orgId.concat(formatedDate).concat(paddingAppNo);
               Long surveyNumber = Long.valueOf(appNumber);
                
                FormBuilderValueEntity entity = null;
                int index =0;
                for (Cell cell : row) {
                	
                	entity = new FormBuilderValueEntity();
                	
                		entity.setSlLabelId(labelsIds.get(index));                            
                		 if (cell != null && !cell.toString().isEmpty()) {
                			 String str ="";
                			 if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                				 if (DateUtil.isCellDateFormatted(cell)) {
              		                Date date = cell.getDateCellValue();
              		                str=format.format(date);
              		            }else {
              		            	str = NumberToTextConverter.toText(cell.getNumericCellValue());
              		            }
                				}
                			 if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
             				    str = cell.getRichStringCellValue().toString();
             				}
                		entity.setSvValue(str);
                		 }
                		entity.setSaApplicationId(surveyNumber);
                		entity.setLevels(1L);
                		entity.setOrgId(new Organisation(orgid));
                		entity.setLmodDate(new Date());
                		entity.setUserId(UserSession.getCurrent().getEmployee());
                		rowValue.add(entity);
                /*	}*/
                	index++;	
                }
            }  
                if (checkEmptyRow == 1)
                    parseData.add((T) obj);
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

    /**
     * @return this will return the list of error
     */
    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }


	public List<FormBuilderValueEntity> getRowValue() {
		return rowValue;
	}


	public void setRowValue(List<FormBuilderValueEntity> rowValue) {
		this.rowValue = rowValue;
	}

}
