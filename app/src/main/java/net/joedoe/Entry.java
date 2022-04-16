package net.joedoe;

import lombok.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Data
public class Entry implements Comparable<Entry> {
    static String DELIMITER_ENTRY = "-------------------------------------";
    int index;
    String path;
    String title;
    String src;

    public void printEntry() {
        System.out.printf("\n%s\n", DELIMITER_ENTRY);
        System.out.printf("%d - %s\n\n", index, title);
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int count = 0;
            String line;
            while ((line = br.readLine()) != null) {
                if (count <= 3) {
                    count++;
                    continue;
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("%s\n", DELIMITER_ENTRY);
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
                ", src='" + src + '\'' +
                '}';
    }
}
