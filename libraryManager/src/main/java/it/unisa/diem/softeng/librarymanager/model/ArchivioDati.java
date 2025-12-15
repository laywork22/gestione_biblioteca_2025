package it.unisa.diem.softeng.librarymanager.model;

import java.io.Serializable;
import java.util.List;

/**
 * @brief Classe contenitore di tutti i prestiti della sessione attiva o caricata
 * da file. Il suo unico scopo è fornire un'unica classe serializzabile e di conseguenza
 * poter scrivere e leggere comodamente da un unico file.
 */
public class ArchivioDati implements Serializable {
    
    /**
     * @brief Lista di prestiti attualmente presenti nell'archivio
     */
    private List<Prestito> listaPrestiti;
    
    /**
     * @brief Lista di utenti attualmente presenti nell'archivio.
     */
    private List<Utente> listaUtenti;
    
    /**
     * brier Lista di libri attualmente presenti nell'archivio.
     */
    private List<Libro> listaLibri;

    public ArchivioDati(List<Prestito> listaPrestiti, List<Utente> listaUtenti, List<Libro> listaLibri) {
        this.listaPrestiti = listaPrestiti;
        this.listaUtenti = listaUtenti;
        this.listaLibri = listaLibri;
    }

    /**
     * @brief Metodo getter di listaPrestiti.
     * 
     * @return La lista prestiti attualmente presente nell'archivio.
     */
    public List<Prestito> getListaPrestiti() {
        return listaPrestiti;
    }

        /**
     * @brief Metodo getter di listaLibri.
     * 
     * @return La lista di libi attualmente presente nell'archivio.
     */
    public List<Libro> getListaLibri() {
        return listaLibri;
    }

    /**
     * @brief Metodo getter di listaUtenti.
     * 
     * @return La lista prestiti attualmente presente nell'archivio.
     */
    public List<Utente> getListaUtenti() {
        return listaUtenti;
    }
}
