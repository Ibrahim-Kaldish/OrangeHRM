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

    private final List<String> columns = new ArrayList<>();
    private final List<String[]> rows = new ArrayList<>();

    public OpenCSVFileManager(String csvFilePath) {
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
        List<String[]> rows = getRows();

        int st = 0, end = 1;

        rows = rows.subList(st, end);

        Object[][] array = new Object[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            array[i] = rows.get(i);
        }
        return array;
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

}