package net.joedoe;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    static String DIR = "/media/joe/E/programming/java/code-library/library";

    public static void main(String[] args) {
        Library lib = setupLib();
        lib.printToc();
        lib.printEntry(lib.entries.get(44));
    }

    static Library setupLib() {
        List<Path> paths = new ArrayList<>();
        try {
            paths = Files.walk(Paths.get(DIR)).filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Opening Directory " + DIR + " failed.");
        }
        Library lib = new Library();
        for (Path path : paths) {
            try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
                Entry entry = new Entry();
                entry.path = path.toString();
                int count = 0;
                while (count < 3) {
                    String line = br.readLine();
                    if (count == 0) entry.title = line;
                    if (count == 2) entry.src = line;
                    count++;
                }
                lib.entries.add(entry);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(lib.entries);
        int index = 1;
        for (Entry e : lib.entries) e.index = index++;
        return lib;
    }
}
