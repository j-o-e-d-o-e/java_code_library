package net.joedoe;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    static final String DIR = "/media/joe/E/programming/java/code-library/library";
    static final String[] literature = {
            "<author> (<YYYY>): <title>, <edition>, <publisher>. [<char>]",
            "<author> (<YYYY>): <title>, <edition>, <publisher>. [<char>]"
    };
    static final Scanner in = new Scanner(System.in);
    static final int TOC = 0;
    static final int EXIT = 667;

    public static void main(String[] args) {
        if (args.length == 1) {
            flags(args[0]);
            return;
        }
        Library lib = setupLib();
        lib.printToc();
        while (true) {
            System.out.print("\nWhat would you like to read? ");
            int input = in.nextInt();
            if (input == TOC) {
                System.out.println();
                lib.printToc();
                continue;
            } else if (input == EXIT) {
                System.out.println("Devil's neighbour wishes you a good day.");
                break;
            } else if (input < 0 || input > lib.entries.size()) {
                System.out.print("Not a valid number.");
                continue;
            }
            lib.entries.get(input - 1).printEntry();
        }
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

    static void flags(String arg) {
        if (arg.equals("-h") || arg.equals("-help")) {
            System.out.printf("%s %s %s\n", Library.DELIMITER_TOC, "JAVA CODE LIBRARY", Library.DELIMITER_TOC);
            System.out.println("Commands:");
            System.out.printf("\t- %d: Table of Content (or any char)\n\t- %d: Exit\n", TOC, EXIT);
            System.out.println("Literature:");
            for (String s : literature) System.out.printf("\t- %s\n", s);
        }
    }
}
