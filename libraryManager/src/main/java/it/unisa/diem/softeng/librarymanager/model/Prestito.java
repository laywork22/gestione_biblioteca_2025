package it.unisa.diem.softeng.librarymanager.model;

import java.time.LocalDate;

/**
 * @brief Rappresenta un'operazione di prestito nel sistema Library Manager.
 *
 * Questa classe associa un {@link Utente} a un {@link Libro}, definendo un arco temporale
 * per il prestito.
 * Implementa le specifiche relative ai dati definite nel requisito **DF-1.3**.
 *
 * @author Gruppo 12
 * @version 1.0
 */
public class Prestito implements Comparable<Prestito> {

    /**
     * @brief L'utente che ha richiesto il prestito.
     * Riferimento all'anagrafica utenti (DF-1.2).
     */
    private Utente utente;

    /**
     * @brief Il libro oggetto del prestito.
     * Riferimento al catalogo libri (DF-1.1).
     */
    private Libro libro;

    /**
     * @brief La data di inizio del prestito.
     * Solitamente corrisponde alla data di creazione del record.
     */
    private LocalDate dataInizio;

    /**
     * @brief La data prevista per la restituzione.
     * Come specificato in **IF-2.6**, questa data è arbitraria e viene inserita dall'operatore.
     */
    private LocalDate dataFine;
    /**
     * @brief Lo stato attuale del prestito
     * Valori possibili ATTIVO, NON_ATTIVO, IN_SCADENZA, SCADUTO
     */
    private StatoPrestitoEnum stato;
    /**
     * @brief Costruttore della classe Prestito.
     *
     * Crea una nuova istanza di prestito. Se la data di inizio non viene fornita (null),
     * viene impostata automaticamente alla data odierna.
     *
     * @param utente L'utente che richiede il prestito.
     * @param libro Il libro da prestare.
     * @param dataInizio La data di inizio prestito (se null, usa LocalDate.now()).
     * @param dataFine La data di scadenza/restituzione prevista.
     */

    public Prestito(Utente utente, Libro libro, LocalDate dataInizio, LocalDate dataFine) {

    }

    /**
     * @brief Restituisce l'utente associato al prestito.
     * @return Oggetto Utente.
     */
    public Utente getUtente() {
        return utente;
    }

    /**
     * @brief Restituisce il libro associato al prestito.
     * @return Oggetto Libro.
     */
    public Libro getLibro() {
        return libro;
    }

    /**
     * @brief Restituisce la data di inizio del prestito.
     * @return LocalDate rappresentante l'inizio del prestito.
     */
    public LocalDate getDataInizio() {
        return dataInizio;
    }

    /**
     * @brief Restituisce la data di scadenza del prestito.
     * @return LocalDate rappresentante la fine prevista del prestito.
     */
    public LocalDate getDataFine() {
        return dataFine;
    }
    /**
     * @brief Restituisce lo stato del prestito.
     * @return StatoPrestitoEnum rappresentante lo stato del prestito.
     */
    public StatoPrestitoEnum getStato() {
        return stato;
    }
    /**
     * @brief Imposta l'utente del prestito.
     * @param utente Il nuovo utente da associare.
     */
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    /**
     * @brief Imposta il libro del prestito.
     * @param libro Il nuovo libro da associare.
     */
    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    /**
     * @brief Imposta la data di inizio prestito.
     * @param dataInizio La nuova data di inizio.
     */
    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    /**
     * @brief Imposta la data di scadenza del prestito.
     * Utile in fase di modifica o proroga.
     * @param dataFine La nuova data di scadenza.
     */
    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }
    /**
     * @brief Restituisce una rappresentazione testuale del prestito.
     * @return Stringa descrittiva con nome utente, titolo libro e date.
     */
    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int compareTo(Prestito o) {
        return 0;
    }
}