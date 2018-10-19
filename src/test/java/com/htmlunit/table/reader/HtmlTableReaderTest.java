package com.htmlunit.table.reader;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class HtmlTableReaderTest {

    @Test
    public void readTableContent() {
        String pageUrl = "https://www.w3schools.com/html/html_tables.asp";
        String tableXPath = "//*[@id=\"customers\"]";
        HtmlTableReader reader = new HtmlTableReader(pageUrl, tableXPath);

        Map<String, List<String>> tableContent = reader.readTableContent();
        for (String key: tableContent.keySet()) {
            System.out.println(key + ": " + tableContent.get(key));
        }

        assertNotNull(tableContent);
    }
}