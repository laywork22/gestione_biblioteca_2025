package it.unisa.diem.softeng.librarymanager.model;

public class Utente {
    private String nome;
    private String cognome;
    private String matricola;
    private String email;

    public Utente(String nome, String cognome, String matricola, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.email = email;
    }
}
