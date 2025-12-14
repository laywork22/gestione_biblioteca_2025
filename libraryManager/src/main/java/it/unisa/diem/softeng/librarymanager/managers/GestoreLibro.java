package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.exceptions.LibroException;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.function.Predicate;
/**
 * @brief Classe specifica per la gestione delle entità Libro.
 *
 *Questa classe implementa i metodi relativi a la logica di formazione
 *della ObservableList di Libro.
 * @author Gruppo 12
 */
public class GestoreLibro implements Gestore<Libro> {
    private ObservableList<Libro> listaLibri;

    public GestoreLibro() {
        this.listaLibri = FXCollections.observableArrayList();
    }


    @Override
    public void setLista(List<Libro> l) {
        listaLibri = FXCollections.observableArrayList(l);
    }

    /**
     * @brief Aggiunge un libro alla lista.
     * Implementazione specifica per i Libri: controlla se il Libro è già presente
     * (tramite ISBN). Se esiste, non aggiunge una nuova istanza, ma incrementa il contatore delle copie disponibili.
     *
     * @param l il Libro da aggiungere o aggiornare
     * @see Gestore#add(Object)
     */
    @Override
    public void add(Libro l) throws LibroException {
        if (l == null) return;

        int index = listaLibri.indexOf(l);

        if (index != -1) {
            Libro libroEsistente = listaLibri.get(index);
            if(!libroEsistente.isAttivo()){
                throw new LibroException("Il libro risulta non attivo");
            }
            libroEsistente.setCopieTotali(libroEsistente.getCopieTotali()+l.getCopieTotali());
            libroEsistente.setCopieDisponibili(libroEsistente.getCopieDisponibili()+l.getCopieTotali());

            listaLibri.set(index, libroEsistente);

            return;
        }

        listaLibri.add(l);
    }

    @Override
    public void remove(Libro l) throws LibroException {
        if(l == null) return;

        if(!l.isAttivo())
            throw new LibroException("Il libro risulta non attivo");

        if(l.getCopieDisponibili()!=l.getCopieTotali())
            throw new LibroException("Il libro ha copie ancora in prestito");

       l.setAttivo(false);
    }

    @Override
    public ObservableList<Libro> getLista() {
        return listaLibri;
    }

    @Override
    public void modifica(Libro vecchio, Libro nuovo) throws IllegalArgumentException,LibroException {
        int index = listaLibri.indexOf(vecchio);
        if(!vecchio.isAttivo()){
            throw new LibroException("Il libro non è attivo");
        }
        if (index != -1) {
            int copieNonDisponibili = vecchio.getCopieTotali() - vecchio.getCopieDisponibili();

            if (copieNonDisponibili > nuovo.getCopieTotali()) {
                throw new IllegalArgumentException("Impossibile ridurre le copie totali a " + nuovo.getCopieTotali() +
                        " perché ci sono " + copieNonDisponibili + " copie ancora in prestito.");
            }

            int nuoveDisponibili = nuovo.getCopieTotali() - copieNonDisponibili;

            vecchio.setTitolo(nuovo.getTitolo());
            vecchio.setAutore(nuovo.getAutore());
            vecchio.setAnno(nuovo.getAnno());
            vecchio.setIsbn(nuovo.getIsbn());
            vecchio.setCopieTotali(nuovo.getCopieTotali());


            vecchio.setCopieDisponibili(nuoveDisponibili);

            listaLibri.set(index, vecchio);
        }
    }


    /** @brief Ottiene il Predicate corrispondente per la ricerca filtrata di un libro
     *
     * @param str La stringa da filtrare nella tabella per la ricerca
     *
     * @return Predicate associato alla ricerca attuale
     *
     * @see Gestore#getPredicato(String)
     */
    @Override
    public Predicate<Libro> getPredicato(String str) {
        return libro -> {
            if (str == null || str.isEmpty()) return true; // Se vuoto mostra tutto
            String filtro = str.toLowerCase();

            // Cerca in Titolo, Autore o ISBN
            return libro.getTitolo().toLowerCase().contains(filtro) ||
                    libro.getAutore().toLowerCase().contains(filtro) ||
                    libro.getIsbn().toLowerCase().contains(filtro);
        };
    }


    /** @brief Inizializza il gestore con la lista di osservabili di libri caricata dal file
     *
     * @pre Il file deve esistere
     *
     * @post La lista è caricata in memoria nel GestoreLibro
     *
     * @return GestoreLibro con listaLibri non vuota
     *
     * @param nomeFile Il nome del file da cui caricare la lista
      */
    public static GestoreLibro caricaListaLibri(String nomeFile) {
        GestoreLibro gl = new GestoreLibro();

        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(Paths.get(nomeFile))))) {
            List<Libro> listaCaricata = (List<Libro>) ois.readObject();

            gl.setLista(listaCaricata);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return gl;
    }

}
