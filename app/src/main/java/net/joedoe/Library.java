package net.joedoe;

import java.util.List;

import static net.joedoe.Format.*;

public class Library {
    static String DELIMITER_TOC = "=================================";

    static void printToc(List<Entry> entries) {
        outputFormat(String.format("%s %s %s%n", DELIMITER_TOC, "JAVA CODE LIBRARY", DELIMITER_TOC), RED, false, true, false);
        for (int i = 0; i < entries.size(); i++) {
            Entry e = entries.get(i);
            String str = String.format("%s%d - %s",
                    " ".repeat(e.index < 10 ? 1 : 0),
                    e.index,
                    e.title);
            if (!e.tags.isEmpty()) str += " ".repeat(47 - e.title.length()) + e.tags;
            if (i % 2 == 0) outputFormat(str, GREEN);
            else outputFormat(str, CYAN, true, false, false);
            System.out.println();
        }
    }
}
