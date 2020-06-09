package stand.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

	private void addSheet(HSSFSheet sheet, Deque<?> data) {

		int rownum = 0;
		row = sheet.createRow(rownum);

		cell = row.createCell(0, CellType.STRING);
		cell.setCellValue(sheet.getSheetName());
		cell.setCellStyle(style);
//		sheet.autoSizeColumn(0);

		// Data save
		if (data != null) {
			while (!data.isEmpty()) {
				rownum++;
				row = sheet.createRow(rownum);
				cell = row.createCell(0, CellType.NUMERIC);
				cell.setCellValue(data.poll().toString());
			}
			sheet.autoSizeColumn(rownum);
		}
	}

	@Override
	public void write(File file, Deque<? extends Number> turnovers,
			Deque<? extends Number> torques) throws IOException {
		workbook = new HSSFWorkbook();
		style = createStyleForTitle(workbook);
		// Создаем новый лист
		HSSFSheet turnover = workbook.createSheet("Частота вращения");
		// Наполняем лист данными
		addSheet(turnover, turnovers);

		HSSFSheet torque = workbook.createSheet("Крутящий момент");
		addSheet(torque, torques);

		try (FileOutputStream outFile = new FileOutputStream(file)) {
			workbook.write(outFile);
		}
	}

	@Override
	public void write(File file, Deque<? extends Number> value, String name) throws IOException {
		workbook = new HSSFWorkbook();
		style = createStyleForTitle(workbook);
		HSSFSheet sheet = workbook.createSheet(name);
		System.out.println(name);
		addSheet(sheet, value);
		try (FileOutputStream outFile = new FileOutputStream(file)) {
			workbook.write(outFile);
		}
	}
}
