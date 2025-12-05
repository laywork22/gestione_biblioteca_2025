package it.unisa.diem.softeng.librarymanager.comparators;

import it.unisa.diem.softeng.librarymanager.model.Libro;

import java.util.Comparator;

public class AutoreLibroComparator implements Comparator<Libro> {

    @Override
    public int compare(Libro o1, Libro o2) {
        return o1.getAutore().compareTo(o2.getAutore());
    }
}
