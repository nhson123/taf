package com.anecon.taf.client.data.read;

import com.anecon.taf.core.AutomationFrameworkException;
import com.google.common.io.Files;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelParser implements TableParser {
    private static final DataFormatter DATA_FORMATTER = new DataFormatter();

    private final Sheet sheet;

    private ExcelParser(Sheet sheet) throws IOException {
        this.sheet = sheet;
    }

    @Deprecated
    public static ExcelParser fromXls(InputStream input, String sheetName) throws IOException {
        return new ExcelParser(new HSSFWorkbook(input).getSheet(sheetName));
    }

    @Deprecated
    public static ExcelParser fromXls(InputStream input, int sheetIndex) throws IOException {
        return new ExcelParser(new HSSFWorkbook(input).getSheetAt(sheetIndex));
    }

    @Deprecated
    public static ExcelParser fromXlsx(InputStream input, String sheetName) throws IOException {
        return new ExcelParser(new XSSFWorkbook(input).getSheet(sheetName));
    }

    @Deprecated
    public static ExcelParser fromXlsx(InputStream input, int sheetIndex) throws IOException {
        return new ExcelParser(new XSSFWorkbook(input).getSheetAt(sheetIndex));
    }

    @Override
    public Table readFile() {
        final int lastRowNum = sheet.getLastRowNum();
        final List<List<String>> content = new ArrayList<>(lastRowNum);

        final List<String> header = new ArrayList<>();
        final Row firstRow = sheet.getRow(0);

        // lastCellNum is 0-based!
        final short lastCellNum = firstRow.getLastCellNum();
        for (int columnIndex = 0; columnIndex < lastCellNum; columnIndex++) {
            header.add(firstRow.getCell(columnIndex).getStringCellValue());
        }

        // lastRowNum is 1-based!
        for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) {
            final Row row = sheet.getRow(rowIndex);

            final List<String> rowContent = new ArrayList<>(lastCellNum);
            content.add(rowContent);

            for (int columnIndex = 0; columnIndex < lastCellNum; columnIndex++) {
                // There could be empty rows before the last row, or a wrong value from getLastRowNum()
                if (row != null) {
                    rowContent.add(getCellValue(row.getCell(columnIndex)));
                }
            }
        }

        return new Table(header, content);
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellTypeEnum()) {
            case BLANK:
                return "";
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    final Date cellDate = cell.getDateCellValue();
                    return DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.systemDefault()).format(cellDate.toInstant());
                } else {
                    return DATA_FORMATTER.formatCellValue(cell);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                cell.setCellType(CellType.STRING);
                return cell.getStringCellValue();
        }
    }

    public static class Builder {
        public enum Extension {
            XLS, XLSX
        }

        private final Workbook workbook;

        public Builder(File file) {
            final String extension = Files.getFileExtension(file.getName()).toLowerCase();
            try (InputStream inputStream = new FileInputStream(file)) {
                switch (extension) {
                    case "xls":
                        this.workbook = new HSSFWorkbook(inputStream);
                        break;
                    case "xlsx":
                        this.workbook = new XSSFWorkbook(inputStream);
                        break;
                    default:
                        throw new AutomationFrameworkException("Can't process file due to its extension: " + file);
                }
            } catch (IOException e) {
                throw new AutomationFrameworkException("Couldn't process file for Excel import: " + file, e);
            }
        }

        public Builder(InputStream inputStream, Extension fileExtension) {
            try {
                switch (fileExtension) {
                    case XLS:
                        this.workbook = new HSSFWorkbook(inputStream);
                        break;
                    case XLSX:
                        this.workbook = new XSSFWorkbook(inputStream);
                        break;
                    default:
                        throw new AutomationFrameworkException("Unknown enum value: " + fileExtension);
                }
            } catch (IOException e) {
                throw new AutomationFrameworkException("Couldn't read from input stream", e);
            }

        }

        public ExcelParser sheetName(String sheetName) {
            try {
                return new ExcelParser(workbook.getSheet(sheetName));
            } catch (IOException e) {
                throw new AutomationFrameworkException("Couldn't read from sheet " + sheetName, e);
            }
        }

        public ExcelParser sheetIndex(int sheetIndex) {
            try {
                return new ExcelParser(workbook.getSheetAt(sheetIndex));
            } catch (IOException e) {
                throw new AutomationFrameworkException("Couldn't read from sheet " + sheetIndex, e);
            }
        }
    }
}
