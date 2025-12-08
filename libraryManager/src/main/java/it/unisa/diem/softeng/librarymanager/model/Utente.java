package it.unisa.diem.softeng.librarymanager.model;

import java.util.Comparator;

/**
 * @brief Rappresenta un utente (studente o personale) della biblioteca.
 *
 * Questa classe definisce il modello dati per gli utenti registrati al sistema Library Manager.
 * Implementa le specifiche definite nel requisito **DF-1.2**, che richiede la memorizzazione
 * di Nome, Cognome, Matricola ed Email.
 *
 * @author Gruppo 12
 * @version 1.0
 */
public class Utente implements Comparable<Utente> {

    /**
     * @brief Il nome di battesimo dell'utente.
     * Utilizzato insieme al cognome per l'ordinamento delle liste (IF-2.4).
     */
    String nome;

    /**
     * @brief Il cognome dell'utente.
     * Campo chiave per l'ordinamento (IF-2.4) e la ricerca (IF-2.5).
     */
    String cognome;

    /**
     * @brief Il numero di matricola univoco dell'utente.
     * Identifica univocamente l'utente nel sistema.
     * [cite_start]È soggetto al vincolo di unicità durante la registrazione (IF-2.1)[cite: 61].
     */
    String matricola;

    /**
     * @brief L'indirizzo email istituzionale dell'utente.
     * [cite_start]Secondo il requisito **DF-1.2**, deve rispettare il formato "*@studenti.unisa.it"[cite: 128].
     */
    String email;

    /**
     * @brief Costruttore della classe Utente.
     *
     * Inizializza un nuovo utente con i dati anagrafici forniti.
     *
     * @param nome Il nome dell'utente.
     * @param cognome Il cognome dell'utente.
     * @param matricola La matricola univoca.
     * @param email L'email istituzionale.
     */
    public Utente(String nome, String cognome, String matricola, String email) {

    }

    /**
     * @brief Restituisce la matricola dell'utente.
     * @return Stringa contenente la matricola.
     */
    public String getMatricola() {
        return matricola;
    }

    /**
     * @brief Restituisce il cognome dell'utente.
     * @return Stringa contenente il cognome.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @brief Restituisce l'email dell'utente.
     * @return Stringa contenente l'indirizzo email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @brief Restituisce il nome dell'utente.
     * @return Stringa contenente il nome.
     */
    public String getNome() {
        return nome;
    }

    /**
     * @brief Imposta o aggiorna il nome dell'utente.
     * @param nome Il nuovo nome da impostare.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @brief Imposta o aggiorna il cognome dell'utente.
     * @param cognome Il nuovo cognome da impostare.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * @brief Imposta o aggiorna la matricola dell'utente.
     * Attenzione: la modifica della matricola potrebbe violare l'integrità referenziale dei prestiti.
     * @param matricola La nuova matricola univoca.
     */
    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    /**
     * @brief Imposta o aggiorna l'email dell'utente.
     * @param email Il nuovo indirizzo email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @brief Restituisce una rappresentazione in formato stringa dell'utente.
     * @return Stringa formattata con i dati anagrafici.
     */
    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;

        if (this == o) return true;

        if (this.getClass() != o.getClass()) return false;

        Utente l = (Utente) o;

        return this.matricola.equals(l.matricola);

    }

    @Override
    public int compareTo(Utente o) {
        return 0;
    }
}