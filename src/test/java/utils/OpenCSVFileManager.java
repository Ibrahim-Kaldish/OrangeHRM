package utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class OpenCSVFileManager {

    private static final Logger log = LogManager.getLogger(OpenCSVFileManager.class);

    private final String csvFilePath;
    private final List<String> columns = new ArrayList<>();
    private final List<String[]> rows = new ArrayList<>();

    public OpenCSVFileManager(String csvFilePath) {
        this.csvFilePath = csvFilePath;
        log.info("📂 Reading CSV file: {}", csvFilePath);

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            List<String[]> all = reader.readAll();
            if (all.isEmpty()) {
                log.error("❌ CSV file is empty: {}", csvFilePath);
                throw new IllegalStateException("CSV file is empty: " + csvFilePath);
            }
            columns.addAll(Arrays.asList(all.get(0)));
            rows.addAll(all.subList(1, all.size()));
            log.info("✅ CSV loaded: {} columns, {} rows from [{}]", columns.size(), rows.size(), csvFilePath);
        } catch (IOException | CsvException e) {
            log.error("❌ Could not read CSV file [{}]", csvFilePath, e);
            throw new IllegalStateException("Could not read CSV file: " + csvFilePath, e);
        }
    }

    /**
     * Returns a copy of all data rows, header excluded.
     */
    public List<String[]> getRows() {
        return new ArrayList<>(rows);
    }

    public Object[][] getRowsAsArray() {
        int st = 0, end = rows.size();
        List<String[]> rows = getRows().subList(st, end);

        Object[][] array = new Object[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            array[i] = rows.get(i);
        }
        return array;
    }

    /**
     * Returns the column names from the header row.
     */
    public List<String> getColumns() {
        return new ArrayList<>(columns);
    }

    /**
     * Maps each column name to its values, in file order.
     */
    public Map<String, List<String>> getColumnsWithData() {
        Map<String, List<String>> map = new LinkedHashMap<>();
        for (int colIdx = 0; colIdx < columns.size(); colIdx++) {
            map.put(columns.get(colIdx), getSpecificColumnData(colIdx));
        }
        return map;
    }

    /**
     * Returns the first column name, or null if the file has no columns.
     */
    public String getFirstColumn() {
        if (columns.isEmpty()) {
            log.warn("⚠️ No columns found in [{}]", csvFilePath);
            return null;
        }
        return columns.get(0);
    }

    /**
     * Returns the last column name, or null if the file has no columns.
     */
    public String getLastColumn() {
        if (columns.isEmpty()) {
            log.warn("⚠️ No columns found in [{}]", csvFilePath);
            return null;
        }
        return columns.get(columns.size() - 1);
    }

    /**
     * @param columnIndex the 0-based index of the column
     * @return the column name, or null if the index is out of range
     */
    public String getSpecificColumnName(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= columns.size()) {
            log.warn("⚠️ Column index {} is out of range (0-{})", columnIndex, columns.size() - 1);
            return null;
        }
        return columns.get(columnIndex);
    }

    /**
     * @param columnName the name of the column
     * @return the column values, or an empty list if the column does not exist
     */
    public List<String> getSpecificColumnData(String columnName) {
        int idx = columns.indexOf(columnName);
        if (idx == -1) {
            log.warn("⚠️ Column not found: {}", columnName);
            return Collections.emptyList();
        }
        return getSpecificColumnData(idx);
    }

    /**
     * @param columnIndex the 0-based index of the column
     * @return the column values, or an empty list if the index is out of range
     */
    public List<String> getSpecificColumnData(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= columns.size()) {
            log.warn("⚠️ Column index {} is out of range", columnIndex);
            return Collections.emptyList();
        }
        List<String> data = new ArrayList<>();
        for (String[] row : rows) {
            if (columnIndex < row.length) {
                data.add(row[columnIndex]);
            }
        }
        log.debug("📋 Column '{}' has {} values", columns.get(columnIndex), data.size());
        return data;
    }

    /**
     * @param rowNum the 0-based index of the data row (0 is the first row after the header)
     * @param columnIndex the 0-based index of the column
     * @return the cell value, or null if the row or column is out of range
     */
    public String getCellData(int rowNum, int columnIndex) {
        if (rowNum < 0 || rowNum >= rows.size()) {
            log.warn("⚠️ Row {} is out of range (0-{})", rowNum, rows.size() - 1);
            return null;
        }
        String[] row = rows.get(rowNum);
        if (columnIndex < 0 || columnIndex >= row.length) {
            log.warn("⚠️ Column index {} is out of range for row {}", columnIndex, rowNum);
            return null;
        }
        return row[columnIndex];
    }

    /**
     * @param rowNum the 0-based index of the data row
     * @param columnName the name of the column
     * @return the cell value, or null if the row or column does not exist
     */
    public String getCellData(int rowNum, String columnName) {
        int idx = columns.indexOf(columnName);
        if (idx == -1) {
            log.warn("⚠️ Column not found: {}", columnName);
            return null;
        }
        return getCellData(rowNum, idx);
    }

    public int getCellCount(String columnName) {
        return getSpecificColumnData(columnName).size();
    }

    public int getCellCount(int columnIndex) {
        return getSpecificColumnData(columnIndex).size();
    }

}