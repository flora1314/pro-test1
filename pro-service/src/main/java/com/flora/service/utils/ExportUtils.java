package com.flora.service.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExportUtils {

	private final static Logger logger = LoggerFactory.getLogger(ExportUtils.class);

	/**
	 * 表格背景色，具体的色值参考IndexedColors类
	 */
	public final static String CELL_BACKGROUND_COLOR = "cellBC";

	/**
	 * 导出excel
	 * @param response
	 */
	public static void exportExcel(HttpServletResponse response, String fileName, List<List<Object>> dataList,
			String[] headerNames) {
		OutputStream out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getOutputStream();
			response.setContentType("application/download; charset=UTF-8");
			if (StringUtils.isNotBlank(fileName)) {
				fileName = new String(fileName.getBytes(), "ISO8859-1");
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			Workbook myWorkBook = new XSSFWorkbook();
			Sheet mySheet = myWorkBook.createSheet("sheet");

			if (ArrayUtils.isEmpty(headerNames)) {
				logger.warn("标题为空，无法导出excel.");
				myWorkBook.write(out);
				return;
			}

			if (dataList == null) {
				dataList = new ArrayList<List<Object>>();
			}

			Row myrow = null;
			Cell cell = null;
			int rowNum = 0;

			// 创建表头
			myrow = mySheet.createRow(rowNum);
			for (int j = 0; j < headerNames.length; j++) {
				cell = myrow.createCell(j);
				CellStyle headerStyle = createHeaderCellStyle(myWorkBook);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(headerNames[j]);
			}

			// 添加数据
			for (int i = 0; i < dataList.size(); i++) {
				List<Object> values = dataList.get(i);
				if (CollectionUtils.isEmpty(values)) {
					continue;
				}
				myrow = mySheet.createRow(++ rowNum);
				for (int j = 0; j < values.size(); j++) {
					cell = myrow.createCell(j);
					Object val = values.get(j);
					if (val != null) {
						setCellValue(cell, val);
					}
				}
			}

			myWorkBook.write(out);

		} catch (IOException e) {
			logger.error("系统异常", e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}


	/**
	 * 导出excel，可以自定义style
	 * @param response
	 */
	public static void exportExcel(HttpServletResponse response, String fileName, List<Map<String, Object>> dataList,
			Map<String, String> headerNames) {
		OutputStream out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getOutputStream();
			response.setContentType("application/download; charset=UTF-8");
			if (StringUtils.isNotBlank(fileName)) {
				fileName = new String(fileName.getBytes(), "ISO8859-1");
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			Workbook myWorkBook = new XSSFWorkbook();
			Sheet mySheet = myWorkBook.createSheet("sheet");

			if (MapUtils.isEmpty(headerNames)) {
				logger.warn("标题为空，无法导出excel.");
				myWorkBook.write(out);
				return;
			}

			if (dataList == null) {
				dataList = new ArrayList<Map<String, Object>>();
			}

			Row myrow = null;
			Cell cell = null;
			int rowNum = 0;

			// 创建表头
			myrow = mySheet.createRow(rowNum);
			int columnIndex = 0;
			for (String key : headerNames.keySet()) {
				cell = myrow.createCell(columnIndex++);
				CellStyle headerStyle = createHeaderCellStyle(myWorkBook);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(headerNames.get(key));
			}

			// 添加数据
			CellStyle commonStyle = createCommonCellStyle(myWorkBook);
			CellStyle individualStyle = createCommonCellStyle(myWorkBook);
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> values = dataList.get(i);
				if (MapUtils.isEmpty(values)) {
					continue;
				}
				columnIndex = 0;
				myrow = mySheet.createRow(++ rowNum);
				// 设值表格风格
				Short backColor = (Short)values.get(CELL_BACKGROUND_COLOR); // 背景色
				if (backColor != null) {
					individualStyle.setFillForegroundColor(backColor);
					individualStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				}

				for (String key : headerNames.keySet()) { // 根据标题的key，取value
					cell = myrow.createCell(columnIndex++);
					Object val = values.get(key);
					if (val != null) {
						setCellValue(cell, val);
					}
					if (backColor != null) {
						cell.setCellStyle(individualStyle);
					} else {
						cell.setCellStyle(commonStyle);
					}
				}
			}

			myWorkBook.write(out);

		} catch (IOException e) {
			logger.error("系统异常", e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}


	/**
	 * 设置标题的风格
	 * @param wb
	 * @return
	 */
	protected static CellStyle createHeaderCellStyle(Workbook wb) {
		Font defaultFont = wb.createFont();
		defaultFont.setFontName("Arial");
		defaultFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(defaultFont);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框
		return cellStyle;
	}

	/**
	 * 设置普通行的风格
	 * @param wb
	 * @return
	 */
	protected static CellStyle createCommonCellStyle(Workbook wb) {
		Font defaultFont = wb.createFont();
		defaultFont.setFontName("Arial");
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(defaultFont);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框
		return cellStyle;
	}

	/**
	 * 为表格填值，根据不同的类型填值
	 * @param cell
	 * @param value
	 */
	protected static void setCellValue(Cell cell, Object value) {
		if (cell == null || value == null) {
			return;
		}
		if (value instanceof String) {
			cell.setCellValue((String)value);
		} else if (value instanceof Double) {
			cell.setCellValue((Double) value);
		} else if (value instanceof Date) {
			cell.setCellValue((Date)value);
		} else {
			cell.setCellValue(value.toString());
		}
	}

}
