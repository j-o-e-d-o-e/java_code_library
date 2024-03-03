package net.joedoe;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.joedoe.Format.format;
import static net.joedoe.Format.outputFormat;

public class App {
    static final String DIR = "/home/joe/prog/java/code_library/library";
    static final int TOC = 0;
    static final int EXIT = 667;
    static final String[] LITERATURE = {
            "Joshua Bloch (2018): Effective Java, 3rd edition, Addison-Wesley. [effective]",
            "<author> (<YYYY>): <title>, <edition>, <publisher>. [abbreviation]"
    };

    public static void main(String[] args) {
        if (args.length == 1) {
            flags(args[0]);
            return;
        }
        List<Entry> entries = setupLib();
        Library.printToc(entries);
        Hist hist = new Hist();
        LineReader reader = LineReaderBuilder.builder().history(hist).build();
        while (true) {
            String input = reader.readLine(format("\nWhat would you like to read? "));
            if (input.startsWith("s:")) {
                String search = input.substring(2).trim();
                List<Entry> tmp = entries.stream()
                        .filter(entry -> entry.tags.toLowerCase().contains(search))
                        .collect(Collectors.toList());
                if (!tmp.isEmpty()) {
                    System.out.println();
                    Library.printToc(tmp);
                } else {
                    hist.removeLast();
                    outputFormat("No match found.\n");
                }
                continue;
            }
            int num;
            try {
                input = input.replaceFirst(" \\(.+\\)$", "");
                num = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                hist.removeLast();
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
                hist.removeLast();
                outputFormat("Not a valid number.\n");
                continue;
            }
            Entry e = entries.get(num - 1);
            hist.addTitle(input, e.title);
            e.printEntry();
        }
    }

    static List<Entry> setupLib() {
        List<Path> paths = new ArrayList<>();
        try (Stream<Path> p = Files.walk(Paths.get(DIR))) {
            paths = p.filter(Files::isRegularFile).collect(Collectors.toList());
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
                outputFormat("Opening File " + path + " failed.");
            }
        }
        Collections.sort(entries);
        int index = 1;
        for (Entry e : entries) e.index = index++;
        return entries;
    }

    static void flags(String arg) {
        if (arg.equals("-h") || arg.equals("--h") || arg.equals("-help") || arg.equals("--help")) {
            outputFormat(String.format("%s %s %s%n", Library.DELIMITER_TOC, "JAVA CODE LIBRARY", Library.DELIMITER_TOC));
            outputFormat(String.format("Commands:\n\t- %d  : Table of Content (or any char)%n\t- %d: Exit\n\t -s: : Search%n", TOC, EXIT));
            outputFormat("Literature:\n");
            for (String s : LITERATURE) outputFormat(String.format("\t- %s\n", s));
        }
    }
}
