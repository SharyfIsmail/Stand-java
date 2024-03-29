package stand.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import javafx.scene.control.CheckBox;


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

	private void addSheet(HSSFSheet sheet, Deque<? extends Number> data, Deque<? extends Number> Time) {

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
			Iterator< ? extends Number> itertator = Time.iterator();
			while (itertator.hasNext() && !data.isEmpty()) {
				rownum++;
				row = sheet.createRow(rownum);
				cell = row.createCell(1, CellType.NUMERIC);
				style.setDataFormat(workbook.createDataFormat().getFormat("hh:mm:ss"));
				
				cellTime.setCellStyle(style);
				cellTime = row.createCell(0, CellType.NUMERIC);
				
				
				Long timeTest =  (Long) itertator.next();
				cellTime.setCellValue(LocalTime.ofSecondOfDay(timeTest.longValue()).toString());
				Number dataTest =  data.poll();
				cell.setCellValue(dataTest.doubleValue());
			}
			sheet.autoSizeColumn(rownum);
		}
	}

	@Override
	public void write(File file, Deque<? extends Number> turnoverValue, Deque<? extends Number> torqueValue,
			Deque<? extends Number> tempValue,Deque<? extends Number> experimentDuration) throws IOException {
		workbook = new HSSFWorkbook();
		style = createStyleForTitle(workbook);
		// Создаем новый лист
		HSSFSheet turnover = workbook.createSheet("Частота вращения");
		// Наполняем лист данными
		addSheet(turnover, turnoverValue, experimentDuration);

		HSSFSheet torque = workbook.createSheet("Крутящий момент");
		addSheet(torque, torqueValue, experimentDuration);

		HSSFSheet temp = workbook.createSheet("Температура");
		addSheet(temp, tempValue, experimentDuration);

		try (FileOutputStream outFile = new FileOutputStream(file)) {
			workbook.write(outFile);
		}
	}
	public HSSFWorkbook writeSemicronData(File file,List<CheckBox> checkBoxSaveList, List<String> name, List<Deque<? extends Number>> list, List<Deque<? extends Number>> list2)throws IOException
	{
		workbook = new HSSFWorkbook();
		style = createStyleForTitle(workbook);
		for(int i = 0; i < checkBoxSaveList.size();i++)
		{				
			if(checkBoxSaveList.get(i).isSelected())
			{
				HSSFSheet sheet = workbook.createSheet(name.get(i));
				addSheet(sheet, list.get(i), list2.get(i));
			
			}
		}
		if(workbook.getNumberOfSheets() == 0)
			return null;
		return workbook;
	}
	
}