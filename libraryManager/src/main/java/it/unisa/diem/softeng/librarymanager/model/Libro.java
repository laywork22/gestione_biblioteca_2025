package it.unisa.diem.softeng.librarymanager.model;

import java.util.List;

public class Libro {
    private String titolo;
    private List<String> autori;
    private int anno;
    private String isbn;
    private int copieTotali;
    private int copieDisponibili;

    public Libro(String titolo, List<String> autori, String isbn, int copieDisponibili, int copieTotali, int anno) {
        this.titolo = titolo;
        this.autori = autori;
        this.isbn = isbn;
        this.copieDisponibili = 1;
        this.copieTotali = 1;
        this.anno = anno;
    }


}
