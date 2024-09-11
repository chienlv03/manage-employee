package com.globits.da.utils;

import com.globits.da.dto.request.EmployeeImportExcelRequest;
import com.globits.da.dto.response.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ImportExportExcelUtil {
	private Workbook workbook;
	private Sheet sheet;

	public Workbook exportEmployeesExcelFile(List<EmployeeResponse> employees) {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Employees");
		writeColumnHeader();
		int rowNum = 1;
		writeColumn(employees, rowNum);
		return workbook;
	}

	private void writeColumn(List<EmployeeResponse> employees, int rowNum) {
		for (EmployeeResponse employee : employees) {
			List<String> employeeData = new ArrayList<>();
			employeeData.add(String.valueOf(rowNum));
			employeeData.add(employee.getName());
			employeeData.add(employee.getCode());
			employeeData.add(employee.getEmail());
			employeeData.add(employee.getPhone());
			employeeData.add(employee.getAge().toString());
			employeeData.add(employee.getProvince().getName());
			employeeData.add(employee.getDistrict().getName());
			employeeData.add(employee.getCommune().getName());
			writeLine(employeeData, rowNum++, createCellStyle());
		}
	}

	private void writeColumnHeader() {
		List<String> columnHeaders = new ArrayList<>();
		columnHeaders.add("STT");
		columnHeaders.add("Tên ");
		columnHeaders.add("Mã");
		columnHeaders.add("Email");
		columnHeaders.add("Phone");
		columnHeaders.add("Age");
		columnHeaders.add("Province name");
		columnHeaders.add("District name");
		columnHeaders.add("Commune name");
		writeLine(columnHeaders, 0, createHeaderStyle());
	}

	private void writeLine(List<String> data, int rowNum, CellStyle style) {
		Row row = sheet.createRow(rowNum);
		for (int i = 0; i < data.size(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(data.get(i));
			cell.setCellStyle(style);
		}
	}

	private CellStyle createCellStyle() {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		return cellStyle;
	}

	private CellStyle createHeaderStyle() {
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN1.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		// Customize the header style further if needed
		return headerCellStyle;
	}

	public List<EmployeeImportExcelRequest> readEmployeeFromExcel(MultipartFile file) {
		List<EmployeeImportExcelRequest> employeeRequests = new ArrayList<>();

		try {
			Workbook workbook;
			String extension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));

			// Check file extension and choose appropriate workbook class
			if (extension.equalsIgnoreCase(".xls")) {
				workbook = new HSSFWorkbook(file.getInputStream()); // For .xls files
			} else if (extension.equalsIgnoreCase(".xlsx")) {
				workbook = new XSSFWorkbook(file.getInputStream()); // For .xlsx files
			} else {
				throw new IllegalArgumentException("Invalid file type. Only .xls and .xlsx are supported.");
			}

			Sheet sheet = workbook.getSheetAt(0);

			if (sheet.getPhysicalNumberOfRows() <= 1) {
				throw new IllegalArgumentException("Excel file is empty or only contains the header.");
			}

			for (Row row : sheet) {
				if (row.getRowNum() == 0) continue; // Skip header row
				EmployeeImportExcelRequest employeeRequest = new EmployeeImportExcelRequest();

				employeeRequest.setCode(getCellValueAsString(row.getCell(0)));
				employeeRequest.setName(getCellValueAsString(row.getCell(1)));
				employeeRequest.setEmail(getCellValueAsString(row.getCell(2)));
				employeeRequest.setPhone(getCellValueAsString(row.getCell(3)));
				employeeRequest.setAge((int) row.getCell(4).getNumericCellValue());
				employeeRequest.setProvinceName(getCellValueAsString(row.getCell(5)));
				employeeRequest.setDistrictName(getCellValueAsString(row.getCell(6)));
				employeeRequest.setCommuneName(getCellValueAsString(row.getCell(7)));

				employeeRequests.add(employeeRequest);
			}
		} catch (IOException e) {
			throw new RuntimeException("Error reading Excel file", e);
		}

		return employeeRequests;
	}

	// Utility method to handle different cell types (text, number, etc.)
	private String getCellValueAsString(Cell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellTypeEnum()) {
			case STRING:
				return cell.getStringCellValue();
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					return cell.getDateCellValue().toString();
				} else {
					return String.valueOf((int) cell.getNumericCellValue());
				}
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case FORMULA:
				return cell.getCellFormula();
			default:
				return "";
		}
	}

}
