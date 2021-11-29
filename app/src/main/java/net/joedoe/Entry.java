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

    @Override
    public int compareTo(Entry other) {
        return title.toLowerCase().compareTo(other.title.toLowerCase());
    }

    public void printEntry() {
        System.out.printf("\n%s\n", DELIMITER_ENTRY);
        System.out.printf("%d - %s\n\n", index, title);
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
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

    @Override
    public String toString() {
        return "Entry{" +
                "index=" + index +
//                ", path='" + path + '\'' +
                ", title='" + title + '\'' +
                ", src='" + src + '\'' +
                '}';
    }
}
