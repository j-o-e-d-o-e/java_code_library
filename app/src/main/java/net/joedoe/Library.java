package net.joedoe;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Library {
    static String DELIMITER_TOC = "=======================================";
    List<Entry> entries = new ArrayList<>();

    void printToc() {
        System.out.printf("%s %s %s\n", DELIMITER_TOC, "JAVA CODE LIBRARY", DELIMITER_TOC);
        for (Entry e : entries) {
            System.out.printf("%d - %s", e.index, e.title);
            if (!e.src.isEmpty()) {
                int n = 52 - e.title.length();
                if (e.index < 10) n++;
                String whitespace = String.format("%1$" + n + "s", " ");
                System.out.printf("%s-> (%d) %s", whitespace, e.index, e.src);
            }
            System.out.println();
        }
    }
}
