package stand.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;
import java.util.Deque;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;


public class ExcelPcmFileWriter implements PcmFileWriter {

	private Cell cell;
	private Cell cellTime;
	private Row row;
	private HSSFWorkbook workbook;
	private HSSFCellStyle style;

	private HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
		HSSFFont font = workbook.createFont();
//		font.setBold(true);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		return style;
	}

	private void addSheet(HSSFSheet sheet, Deque<?> data, Deque<?> Time) {

		int rownum = 0;
		row = sheet.createRow(rownum);

		cell = row.createCell(1, CellType.STRING);
		cellTime = row.createCell(0, CellType.STRING);
		cellTime.setCellValue("Время испыт.");
		cell.setCellValue(sheet.getSheetName());
		cell.setCellStyle(style);
		cellTime.setCellStyle(style);
//		sheet.autoSizeColumn(0);

		// Data save
		if (data != null) {
			while (!data.isEmpty()) {
				rownum++;
				row = sheet.createRow(rownum);
				cell = row.createCell(1, CellType.NUMERIC);
				style.setDataFormat(workbook.createDataFormat().getFormat("hh:mm:ss"));
				
				cellTime.setCellStyle(style);
				cellTime = row.createCell(0, CellType.NUMERIC);
				
				
				Long timeTest =  (Long) Time.poll();
			//	System.out.println(timeTest);
				cellTime.setCellValue(LocalTime.ofSecondOfDay(timeTest.longValue()).toString());
				Float dataTest = (Float) data.poll();
				cell.setCellValue(dataTest.floatValue());
			}
			sheet.autoSizeColumn(rownum);
		}
	}

	@Override
	public void write(File file, Deque<? extends Number> turnoverValue,Deque<? extends Number> turnOverTime, Deque<? extends Number> torqueValue,
			Deque<? extends Number> torqueTime,Deque<? extends Number> tempValue,Deque<? extends Number> tempTime) throws IOException {
		workbook = new HSSFWorkbook();
		style = createStyleForTitle(workbook);
		// Создаем новый лист
		HSSFSheet turnover = workbook.createSheet("Частота вращения");
		// Наполняем лист данными
		addSheet(turnover, turnoverValue, turnOverTime);

		HSSFSheet torque = workbook.createSheet("Крутящий момент");
		addSheet(torque, torqueValue, torqueTime);

		HSSFSheet temp = workbook.createSheet("Температура");
		addSheet(temp, tempValue, tempTime);

		try (FileOutputStream outFile = new FileOutputStream(file)) {
			workbook.write(outFile);
		}
	}
}