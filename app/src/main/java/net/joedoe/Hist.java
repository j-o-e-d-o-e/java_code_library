package net.joedoe;

import org.jline.reader.impl.history.DefaultHistory;

import java.time.Instant;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;


class Hist extends DefaultHistory {
    @Override
    protected void internalAdd(Instant time, String line, boolean checkDuplicates) {
        if (!line.equals("0")) super.internalAdd(time, line, true);
    }

    public void addTitle(String line, String title) {
        for (ListIterator<Entry> lit = iterator(); lit.hasNext(); ) {
            Entry e = lit.next();
            if (Objects.equals(e.line(), line)) {
                EntryImpl entry = createEntry(e.index(), e.time(), String.format("%s (%s)", e.line(), title));
                lit.set(entry);
                break;
            }
        }
    }

    void removeLast() {
        Iterator<Entry> lit = reverseIterator();
        lit.next();
        lit.remove();
        resetIndex();
    }
}
