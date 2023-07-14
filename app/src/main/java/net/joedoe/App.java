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
        List<Entry> entries = setupLib();
        Library.printToc(entries);
        Scanner in = new Scanner(System.in);
        while (true) {
            output("\nWhat would you like to read? ");
            String input = in.next();
            if (input.startsWith("s:")) {
                System.out.println();
                String search = input.substring(2).trim();
                Library.printToc(entries.stream()
                        .filter(entry -> entry.tags.toLowerCase().contains(search))
                        .collect(Collectors.toList()));
                continue;
            }
            int num;
            try {
                num = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                output("Not a valid input.\n");
                continue;
            }
            if (num == TOC) {
                System.out.println();
                Library.printToc(entries);
                continue;
            } else if (num == EXIT) {
                output("Devil's neighbour wishes you a good day.\n");
                break;
            } else if (num < 0 || num > entries.size()) {
                output("Not a valid number.");
                continue;
            }
            entries.get(num - 1).printEntry();
        }
        in.close();
    }

    static List<Entry> setupLib() {
        List<Path> paths = new ArrayList<>();
        try {
            paths = Files.walk(Paths.get(DIR)).filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            output("Opening Directory " + DIR + " failed.");
        }
        List<Entry> entries = new ArrayList<>();
        for (Path path : paths) {
            try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
                Entry entry = new Entry();
                entry.path = path.toString();
                entry.title = br.readLine();
                br.readLine();
                entry.tags = br.readLine();
                entries.add(entry);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(entries);
        int index = 1;
        for (Entry e : entries) e.index = index++;
        return entries;
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
