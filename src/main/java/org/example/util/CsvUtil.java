package org.example.util;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class CsvUtil {
    private CsvUtil() {
    }

    public static byte[] convertCsvToBytes(List<String[]> csvData) {
        StringBuilder csvContent = new StringBuilder();
        for (String[] row : csvData) {
            csvContent.append(String.join(",", row)).append("\n");
        }
        return csvContent.toString().getBytes(StandardCharsets.UTF_8);
    }
}
