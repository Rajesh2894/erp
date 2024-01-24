package com.abm.mainet.workManagement.dto;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.Utility;

public class WriteExcelData<T> {

	private String fileName;
	private HttpServletRequest request;
	private HttpServletResponse response;
	static final String MIME_TYPE="application/octet-stream;charset=UTF-8";
	static final String HEADER_KEY="Content-Disposition";
	private  String keyHeaderForExcel;

	public WriteExcelData(String fileName, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		this.fileName = fileName;
		this.request = request;
		this.response = response;
	}
	
	public void setAlternetKeyForHeader(String keyHeaderForExcel) {
		this.keyHeaderForExcel=keyHeaderForExcel;
	}

	@SuppressWarnings("hiding")
	public void getExpotedExcelSheet(List<T> dtoList, Class<T> cls) throws Exception {

		String classNamestr = null;
		int headerRow = 0;
		int rowCount = 1;

		classNamestr = null;
		headerRow = 0;
		rowCount = 1;

		if(keyHeaderForExcel!=null) {
			classNamestr=keyHeaderForExcel;
		}
		if (classNamestr==null && dtoList != null && !dtoList.isEmpty())
			classNamestr = dtoList.get(0).getClass().getSimpleName();
		else if(classNamestr==null){
			classNamestr = cls.getSimpleName().toString();
		}

		String lableName = ApplicationSession.getInstance().getMessage(classNamestr);

		Map<String, String> map = new LinkedHashMap<String, String>();

		for (String keyValue : lableName.split(",")) {

			String[] pairs = keyValue.split(":", 2);
			map.put(pairs[0].trim(), pairs.length == 1 ? "" : pairs[1].trim());
		}

		XSSFWorkbook workbookWrite = new XSSFWorkbook();
        Font headerFont = workbookWrite.createFont();
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        headerFont.setFontHeightInPoints((short) 11);
        headerFont.setFontName(HSSFFont.FONT_ARIAL);
        headerFont.setBold(true);
        CellStyle headerCellStyle = workbookWrite.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setWrapText(true);
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFSheet sheet = (XSSFSheet) workbookWrite.createSheet(classNamestr);
        Row r = sheet.createRow(0);
        int count = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sheet.setColumnWidth(count, 5000);
            Cell cell = r.createCell(headerRow++);
            cell.setCellValue(entry.getValue());
            cell.setCellStyle(headerCellStyle);
            count++;

        }
		for (Object data : dtoList) {

			Row row = sheet.createRow(rowCount);
			int rowDataIndex = 0;

			for (Map.Entry<String, String> entry : map.entrySet()) {

				PropertyDescriptor objPropertyDescriptor = new PropertyDescriptor(entry.getKey(), data.getClass());

				Object ob = objPropertyDescriptor.getReadMethod().invoke(data);
				if (ob != null)
					row.createCell(rowDataIndex).setCellValue(ob.toString());
				rowDataIndex++;
			}
			rowCount++;
		}

		String realPath =  MainetConstants.operator.FORWARD_SLACE+Filepaths.getfilepath()+Utility.getTimestamp();
		File file = new File(realPath);
		file.mkdirs();
		String realPathFile =  realPath+"\\"+fileName;
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
