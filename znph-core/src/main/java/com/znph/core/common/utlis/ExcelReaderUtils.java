package com.znph.core.common.utlis;

import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
* @author Minco
* @date 2017年7月27日 上午11:58:28
* 
*/
public class ExcelReaderUtils {
	
	private Workbook workbook;

	public ExcelReaderUtils(File file) throws Exception {
		this.workbook = WorkbookFactory.create(file);
	}

	public ExcelReaderUtils(InputStream inputStream) throws Exception {
		this.workbook = WorkbookFactory.create(inputStream);
	}

	public int getSheetCount() {
		int num = this.workbook.getNumberOfSheets();
		return num;
	}

	public String getSheetName(int sheetIndex) {
		Sheet sheet = this.workbook.getSheetAt(sheetIndex);
		return sheet.getSheetName();
	}

	public String[][] readExcel(int sheetIndex, int offsetX, int offsetY, Integer countX) throws Exception {
		String[][] result = null;

		Sheet sheet = this.workbook.getSheetAt(sheetIndex);

		if (countX == null) {
			countX = Integer.valueOf(sheet.getRow(0).getLastCellNum() - offsetX);
		}
		int countY = sheet.getLastRowNum() + 1 - offsetY;
		result = new String[countY][countX.intValue()];
		for (int y = 0; y < countY; y++) {
			Row row = sheet.getRow(y + offsetY);
			for (int x = 0; x < countX.intValue(); x++) {
				String content = null;
				if (row != null) {
					Cell cell = row.getCell(x + offsetX, Row.CREATE_NULL_AS_BLANK);

					switch (cell.getCellType()) {
					case 1:
						content = cell.getRichStringCellValue().getString();
						break;
					case 0:
						DecimalFormat df = new DecimalFormat("#0.###");
						content = df.format(cell.getNumericCellValue());
						break;
					}

				}

				if (content != null) {
					content = content.trim();
				}

				result[y][x] = content;
			}
		}

		return result;
	}

}
