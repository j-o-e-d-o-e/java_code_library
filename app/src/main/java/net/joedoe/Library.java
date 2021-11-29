package net.joedoe;

import lombok.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class Library {
    static String DELIMITER_TOC = "=======================================";
    static String DELIMITER_ENTRY = "-------------------------------------";

    List<Entry> entries = new ArrayList<>();

    void printToc() {
        System.out.printf("%s %s %s\n", DELIMITER_TOC, "JAVA CODE LIBRARY", DELIMITER_TOC);
        int len = entries.size();
        for (Entry e : entries) {
            System.out.printf("%d - %s", e.index, e.title);
            if (!e.src.isEmpty()) {
                int n = 52 - e.title.length();
                if (e.index < 10) n++;
                String whitespace = String.format("%1$" + n + "s", " ");
                System.out.printf("%s-> (%d) %s", whitespace, e.index, e.src);
            }
            System.out.println("");
        }
    }

    public void printEntry(Entry entry) {
        System.out.printf("\n%s\n", DELIMITER_ENTRY);
        System.out.printf("%d - %s\n\n", entry.index, entry.title);
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(entry.path))) {
            String line;
            while ((line = br.readLine()) != null) {
                count++;
                if (count <= 4) continue;
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("%s\n", DELIMITER_ENTRY);
    }
}
