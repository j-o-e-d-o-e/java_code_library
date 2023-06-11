package net.joedoe;


import java.util.ArrayList;
import java.util.List;

import static net.joedoe.Format.*;

public class Library {
    static String DELIMITER_TOC = "=================================";
    List<Entry> entries = new ArrayList<>();

    void printToc() {
        output(String.format("%s %s %s%n", DELIMITER_TOC, "JAVA CODE LIBRARY", DELIMITER_TOC), RED, false, true, false);
        for (Entry e : entries) {
            String str = String.format("%s%d - %s",
                    " ".repeat(e.index < 10 ? 1 : 0),
                    e.index,
                    e.title);
            if (e.src.length() > 0) str += " ".repeat(47 - e.title.length()) + e.src;
            if (e.index % 2 == 0) output(str, GREEN);
            else output(str, CYAN, true, false, false);
            System.out.println();
        }
    }
}
