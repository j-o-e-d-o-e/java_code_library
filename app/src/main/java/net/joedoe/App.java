package net.joedoe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static net.joedoe.Format.output;

public class App {
    static final String DIR = "./library";
    static final int TOC = 0;
    static final int EXIT = 667;
    static final String[] LITERATURE = {
            "Joshua Bloch (2018): Effective Java, 3rd edition, Addison-Wesley. [e]",
            "<author> (<YYYY>): <title>, <edition>, <publisher>. [<char>]"
    };

    public static void main(String[] args) {
        if (args.length == 1) {
            flags(args[0]);
            return;
        }
        Library lib = setupLib();
        lib.printToc();
        Scanner in = new Scanner(System.in);
        while (true) {
            output("\nWhat would you like to read? ");
            int input = in.nextInt();
            if (input == TOC) {
                System.out.println();
                lib.printToc();
                continue;
            } else if (input == EXIT) {
                output("Devil's neighbour wishes you a good day.\n");
                break;
            } else if (input < 0 || input > lib.entries.size()) {
                output("Not a valid number.");
                continue;
            }
            lib.entries.get(input - 1).printEntry();
        }
        in.close();
    }

    static Library setupLib() {
        List<Path> paths = new ArrayList<>();
        try {
            paths = Files.walk(Paths.get(DIR)).filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            output("Opening Directory " + DIR + " failed.");
        }
        Library lib = new Library();
        for (Path path : paths) {
            try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
                Entry entry = new Entry();
                entry.path = path.toString();
                entry.title = br.readLine();
                br.readLine();
                entry.src = br.readLine();
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
        if (arg.equals("-h") || arg.equals("--h") || arg.equals("-help") || arg.equals("--help")) {
            output(String.format("%s %s %s%nCommands:\t- %d: Table of Content (or any char)%n\t- %d: Exit%n",
                    Library.DELIMITER_TOC, "JAVA CODE LIBRARY", Library.DELIMITER_TOC, TOC, EXIT));
            output("Literature:");
            for (String s : LITERATURE) output(String.format("\t- %s\n", s));
        }
    }
}
