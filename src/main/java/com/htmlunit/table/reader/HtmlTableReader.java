package com.htmlunit.table.reader;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class HtmlTableReader {

    private String pageUrl;

    private String tableXPath;

    public HtmlTableReader(String pageUrl, String tableXPath) {
        this.pageUrl = pageUrl;
        this.tableXPath = tableXPath;
    }

    public Map<String, List<String>> readTableContent() {
        Map<String, List<String>> tableContent = new LinkedHashMap<>();

        try (final WebClient webClient = new WebClient()) {
            LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
            Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
            Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

            final HtmlPage page = webClient.getPage(pageUrl);
            final HtmlTable table = (HtmlTable) page.getByXPath(tableXPath).get(0);

            List<String> headers = table.getRows().get(0).getCells()
                    .stream()
                    .map(HtmlTableCell::asText)
                    .collect(Collectors.toList());

            for (int column = 0; column < headers.size(); ++column) {
                List<String> values = new LinkedList<>();
                String header = headers.get(column);
                for (int row = 1; row < table.getRows().size(); ++row) {
                    values.add(table.getRows().get(row).getCells().get(column).asText());
                }
                tableContent.put(header, values);
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return tableContent;
    }
}
