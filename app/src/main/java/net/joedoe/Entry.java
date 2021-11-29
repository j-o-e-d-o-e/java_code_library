package net.joedoe;

import lombok.Data;

@Data
public class Entry implements Comparable<Entry> {
    int index;
    String path;
    String title;
    String src;

    String getTitle(){
        return this.title;
    }

    @Override
    public int compareTo(Entry other) {
        return title.toLowerCase().compareTo(other.title.toLowerCase());
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
