package com.znph.core.common.utlis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelExportUtils<T> {

	private HttpServletResponse httpServletResponse;

	public ExcelExportUtils(HttpServletResponse httpServletResponse) {
		this.httpServletResponse = httpServletResponse;
	}

	/**
	 * Excel导出
	 *
	 * @param headers
	 *            Excel头
	 * @param dataList
	 *            导出的数据
	 * @param fileName
	 *            导出的文件名
	 * @return
	 * @throws Exception
	 */
	public String exportExcel(String[] headers, List<T> dataList, String fileName) throws Exception {

		// 声明工作簿
		WritableWorkbook workbook = Workbook.createWorkbook(httpServletResponse.getOutputStream());
		WritableSheet ws = workbook.createSheet("sheet", 0);

		SheetSettings ss = ws.getSettings();
		ss.setVerticalFreeze(1);// 冻结表头

		// 设置字体
		WritableFont normalFont = new WritableFont(WritableFont.ARIAL, 12);
		WritableFont boldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);

		// 标题居中
		WritableCellFormat titleFormat = new WritableCellFormat(boldFont);
		titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
		titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
		titleFormat.setAlignment(Alignment.CENTRE); // 文字水平对齐
		titleFormat.setWrap(false);

		// 正文居中
		WritableCellFormat contentCenterFormat = new WritableCellFormat(normalFont);
		contentCenterFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		contentCenterFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		contentCenterFormat.setAlignment(Alignment.CENTRE);
		contentCenterFormat.setWrap(true);// 自动换行

		// 设置表头
		for (int i=0; i<headers.length; i++) {
			ws.addCell(new Label(i, 0, headers[i], titleFormat));
			ws.setColumnView(i, 20);// 设置列宽
		}

		// 数据写入Excel
		for (int i = 0; i < dataList.size(); i++) {
			T t = dataList.get(i);
			Field[] fields = t.getClass().getDeclaredFields();
			
			for(int j=0; j<headers.length; j++) {
				
				Field field = fields[j + 1];// 过滤id列
				String fieldName = field.getName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

				Method getMethod = t.getClass().getMethod(getMethodName, new Class[] {});
				Object val = getMethod.invoke(t, new Object[] {});
				String textVal = val == null ? "" : val.toString();
				ws.addCell(new Label(j, i + 1, textVal, contentCenterFormat));// 写入列 列/行/数据/格式
			}
		}

		workbook.write();
		httpServletResponse.setHeader("Content-Disposition",
				"attachment;filename=" + java.net.URLEncoder.encode(fileName + ".xls", "UTF-8"));
		httpServletResponse.setContentType("application/vnd..ms-excel");
		httpServletResponse.getOutputStream().flush();
		workbook.close();
		return "OK";
	}

}
