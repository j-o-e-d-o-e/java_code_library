package net.joedoe;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static net.joedoe.Format.format;
import static net.joedoe.Format.outputFormat;

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
        LineReader reader = LineReaderBuilder.builder().build();
        while (true) {
            String input = reader.readLine(format("\nWhat would you like to read? "));
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
                outputFormat("Not a valid input.\n");
                continue;
            }
            if (num == TOC) {
                System.out.println();
                Library.printToc(entries);
                continue;
            } else if (num == EXIT) {
                outputFormat("Devil's neighbour wishes you a good day.\n");
                break;
            } else if (num < 0 || num > entries.size()) {
                outputFormat("Not a valid number.");
                continue;
            }
            entries.get(num - 1).printEntry();
        }
    }

    static List<Entry> setupLib() {
        List<Path> paths = new ArrayList<>();
        try {
            paths = Files.walk(Paths.get(DIR)).filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            outputFormat("Opening Directory " + DIR + " failed.");
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
            outputFormat(String.format("%s %s %s%nCommands:\t- %d: Table of Content (or any char)%n\t- %d: Exit%n",
                    Library.DELIMITER_TOC, "JAVA CODE LIBRARY", Library.DELIMITER_TOC, TOC, EXIT));
            outputFormat("Literature:");
            for (String s : LITERATURE) outputFormat(String.format("\t- %s\n", s));
        }
    }
}
