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
	private long time ;

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
				time = timeTest.longValue();
				cellTime.setCellValue(LocalTime.ofSecondOfDay(time).toString());
				Integer dataTest = (Integer) data.poll();
				int datavalue = dataTest.intValue();
				cell.setCellValue(datavalue);
			}
			sheet.autoSizeColumn(rownum);
		}
	}

	@Override
	public void write(File file, Deque<? extends Number> turnovers,Deque<? extends Number> timeTurnovers,
			Deque<? extends Number> torques, Deque<? extends Number> timeTorque) throws IOException {
		workbook = new HSSFWorkbook();
		style = createStyleForTitle(workbook);
		// Создаем новый лист
		HSSFSheet turnover = workbook.createSheet("Частота вращения");
		// Наполняем лист данными
		addSheet(turnover, turnovers, timeTurnovers);

		HSSFSheet torque = workbook.createSheet("Крутящий момент");
		addSheet(torque, torques, timeTorque);

		try (FileOutputStream outFile = new FileOutputStream(file)) {
			workbook.write(outFile);
		}
	}
}