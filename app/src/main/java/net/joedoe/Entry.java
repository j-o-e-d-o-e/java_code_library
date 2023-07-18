package net.joedoe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static net.joedoe.Format.*;

public class Entry implements Comparable<Entry> {
    static final String DELIMITER_ENTRY = "-------------------------------------";
    int index;
    String path;
    String title;
    String tags;

    public void printEntry() {
        outputFormat(String.format("%n%s%n", DELIMITER_ENTRY));
        int color = index % 2 == 0 ? GREEN : CYAN;
        outputFormat(String.format("%d - %s%n%n", index, title), color, false, true, true);
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int count = 0;
            String line;
            while ((line = br.readLine()) != null) {
                if (count <= 3) {
                    count++;
                    continue;
                }
                outputFormat(line + "\n", color, false, line.equals("EXAMPLE"), false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputFormat(String.format("%s%n", DELIMITER_ENTRY));
    }

    @Override
    public int compareTo(Entry other) {
        return title.toLowerCase().compareTo(other.title.toLowerCase());
    }

    @Override
    public String toString() {
        return "Entry{" +
                "index=" + index +
                ", title='" + title + '\'' +
                ", src='" + tags + '\'' +
                '}';
    }
}
