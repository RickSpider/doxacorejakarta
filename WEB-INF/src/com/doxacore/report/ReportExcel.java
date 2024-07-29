package com.doxacore.report;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.zul.Filedownload;

public class ReportExcel {

	private XSSFWorkbook workbook;
	private Sheet sheet;
	private String fileName;

	public ReportExcel(String fileName) {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Datos");
		this.fileName = fileName;

	}

	private void agregarLogoTitulo(String titulo, String pathImg) {

		
		
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setWrapText(true);

		Row headerRow = sheet.createRow(0);
		Cell cell = headerRow.createCell(0);
		cell.setCellValue(titulo);
		cell.setCellStyle(cellStyle);
		
		sheet.addMergedRegion(new CellRangeAddress(0,3,0,5));

		byte[] bytes = null;
		try {

			FileInputStream logo = new FileInputStream(pathImg);
			bytes = IOUtils.toByteArray(logo);
			logo.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (bytes == null) {

			return;

		}

		int logoID = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);

		XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();

		XSSFClientAnchor logoSize = new XSSFClientAnchor();

		logoSize.setCol1(0);
	//	logoSize.setCol2(1);
		logoSize.setRow1(0);
	//	logoSize.setRow2(2);

		 Picture pict = drawing.createPicture(logoSize, logoID);
		 pict.resize();
		 
		 int pictOriginalWidthInPixels = pict.getImageDimension().width;
		  int pictOriginalHeightInPixels = pict.getImageDimension().height;
		  
		  float rowHeightInPixels = 0f;
		  for (int r = 0; r < 4; r++) {
			  headerRow = sheet.getRow(r); if (headerRow == null) headerRow = sheet.createRow(r);
		   float rowHeightInPoints = headerRow.getHeightInPoints(); 
		   rowHeightInPixels += rowHeightInPoints * Units.PIXEL_DPI / Units.POINT_DPI;
		  }
		  
		  float scale = rowHeightInPixels / pictOriginalHeightInPixels;
		  pict.resize(scale, scale); //now picture is resized to fit into the first 4 rows
		
	}

	private void agregarTitulos(List<String[]> titulos) {

		int c = sheet.getLastRowNum() + 1;

		for (String[] x : titulos) {

			Row headerRow = sheet.createRow(c);

			for (int i = 0; i < x.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(x[i]);
			}

			c++;

		}

	}

	private void agregarDatosCabecera(List<String[]> headersDatos) {

		CellStyle headerCellStyle = workbook.createCellStyle();

		// headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		// headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);

		XSSFFont font = this.workbook.createFont();
		font.setBold(true);
		headerCellStyle.setFont(font);

		int c = sheet.getLastRowNum() + 1;

		for (String[] x : headersDatos) {

			Row headerRow = sheet.createRow(c);

			for (int i = 0; i < x.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(x[i]);
				cell.setCellStyle(headerCellStyle);
				sheet.autoSizeColumn(i);
			}

			c++;

		}

	}

	private void agregarDatos(List<Object[]> datos) {

		CellStyle cellStyle = this.workbook.createCellStyle();
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setTopBorderColor(IndexedColors.BLACK.index);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setLeftBorderColor(IndexedColors.BLACK.index);

		int c = sheet.getLastRowNum() + 1;
		for (Object[] x : datos) {

			Row row = sheet.createRow(c);

			for (int i = 0; i < x.length; i++) {

				Cell cell = row.createCell(i);
				cell.setCellValue(x[i].toString());
				cell.setCellStyle(cellStyle);

				sheet.autoSizeColumn(i);

			}

			c++;

		}

	}

	public void descargar(List<String[]> titulos, List<String[]> headersDatos, List<Object[]> datos) {

		this.descargar(titulos, headersDatos, datos, null, null);

	}

	public void descargar(List<String[]> titulos, List<String[]> headersDatos, List<Object[]> datos, String titulo,
			String logo) {

		if (logo != null) {

			this.agregarLogoTitulo(titulo, logo);

		}

		agregarTitulos(titulos);
		agregarDatosCabecera(headersDatos);
		agregarDatos(datos);

		// FileOutputStream out = new FileOutputStream(new File(fileName));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			this.workbook.write(baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Filedownload.save(baos.toByteArray(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				this.fileName + ".xlsx");

	}
}
