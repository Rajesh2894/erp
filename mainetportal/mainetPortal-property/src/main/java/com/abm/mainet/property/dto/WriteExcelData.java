package com.abm.mainet.property.dto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.util.Utility;

public class WriteExcelData<T> {

    private String fileName;
    private HttpServletResponse response;
    static final String MIME_TYPE = "application/octet-stream;charset=UTF-8";
    static final String HEADER_KEY = "Content-Disposition";;

    public WriteExcelData(String fileName, final HttpServletRequest request, final HttpServletResponse response) {
        this.fileName = fileName;
        this.response = response;
    }

    public void getExpotedExcelSheet(List<T> dtoList, Class<T> cls) throws Exception {
        String classNamestr = null;
        int rowCount = 1;
        classNamestr = null;
        rowCount = 1;
        if (dtoList != null && !dtoList.isEmpty())
            classNamestr = dtoList.get(0).getClass().getSimpleName();
        else {
            classNamestr = cls.getSimpleName();
        }
        XSSFWorkbook workbookWrite = new XSSFWorkbook();
        Font headerFont = workbookWrite.createFont();
        headerFont.setColor(IndexedColors.BLUE.getIndex());
        CellStyle headerCellStyle = workbookWrite.createCellStyle();
        headerCellStyle.setFont(headerFont);

        XSSFSheet sheet = workbookWrite.createSheet(classNamestr);
        int rowDataIndex = 0;
        for (Object data : dtoList) {

            Row row = sheet.createRow(rowCount);
            rowDataIndex = 0;
            ExcelSheetDto dto = (ExcelSheetDto) data;
            Cell cell;
            if (rowCount == 1) {
                cell = row.createCell(rowDataIndex);
                cell.setCellValue(dto.getSrNo());
                cell.setCellStyle(headerCellStyle);
                rowDataIndex++;

                cell = row.createCell(rowDataIndex);
                cell.setCellValue(dto.getTaxName());
                cell.setCellStyle(headerCellStyle);
                rowDataIndex++;

                for (String amt : dto.getTaxAmount()) {
                    cell = row.createCell(rowDataIndex);
                    cell.setCellValue(amt);
                    cell.setCellStyle(headerCellStyle);
                    rowDataIndex++;
                }
            } else {
                row.createCell(rowDataIndex).setCellValue(dto.getSrNo());
                rowDataIndex++;

                row.createCell(rowDataIndex).setCellValue(dto.getTaxName());
                rowDataIndex++;

                for (String amt : dto.getTaxAmount()) {
                    row.createCell(rowDataIndex).setCellValue(amt);
                    rowDataIndex++;
                }
            }
            rowCount++;
        }

        Row r = sheet.createRow(0);
        r.createCell(0).setCellValue("Schedule Wise Tax Detail");
        sheet.autoSizeColumn(1);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, rowDataIndex));

        String realPath = MainetConstants.operator.FORWARD_SLACE + Filepaths.getfilepath() + Utility.getTimestamp();
        File file = new File(realPath);
        file.mkdirs();
        String realPathFile = realPath + "\\" + fileName;
        File files = new File(realPathFile);
        FileOutputStream fileOut = new FileOutputStream(realPathFile);
        workbookWrite.write(fileOut);

        InputStream in = new FileInputStream(files);

        byte[] fileArray = IOUtils.toByteArray(in);
        String mimeType = MIME_TYPE;
        response.setContentType(mimeType);
        response.setContentLength(fileArray.length);

        String headerKey = HEADER_KEY;
        String headerValue = String.format("attachment; filename=\"%s\"", URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader(headerKey, headerValue);
        response.setCharacterEncoding("UTF-8");

        try (OutputStream outStream = response.getOutputStream()) {
            outStream.write(fileArray);
        } catch (Exception ex) {
            throw ex;
        } finally {
            workbookWrite.close();
            file.delete();
            files.delete();
            fileOut.close();
            fileOut.flush();
        }

    }

}
