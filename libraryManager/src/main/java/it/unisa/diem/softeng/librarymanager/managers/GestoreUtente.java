package it.unisa.diem.softeng.librarymanager.managers;


import it.unisa.diem.softeng.librarymanager.exceptions.PrestitoException;
import it.unisa.diem.softeng.librarymanager.exceptions.UtenteException;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.function.Predicate;
/**
 * @brief Classe specifica per la gestione delle entità Utente.
 *
 *Questa classe implementa i metodi relativi a la logica
 *della ObservableList di Utente.
 * @author Gruppo 12
 */
public class GestoreUtente implements Gestore<Utente> {
    private ObservableList<Utente> utentiList;

    public GestoreUtente() {
        utentiList = FXCollections.observableArrayList();
    }

    /**
     * @brief Aggiunge un utente alla lista.
     * Implementazione specifica per gli utent: controlla se la matricola è già presente nella lista,
     * se esiste, non aggiunge una nuova istanza e non inserisce l'Utente, altrimenti lo aggiunge..
     *
     * @param l il Libro da aggiungere o aggiornare
     *
     * @see Gestore#add(Object)
     */
    @Override
    public void add(Utente l) throws UtenteException {
        if (l == null) return;
        if (this.utentiList.contains(l)) {
            throw new UtenteException("Utente già presente nel sistema!");
        }

        this.utentiList.add(l);
    }

    @Override
    public void remove(Utente l) throws PrestitoException {
        if(!l.isAttivo()){
            throw new PrestitoException("L'utente risulta non attivo");
        }

        if(l.getCountPrestiti()!=0)
            throw new PrestitoException("L'utente ha almeno un prestito attivo");
        else
            l.setAttivo(false);
    }

    @Override
    public ObservableList<Utente> getLista() {
        return this.utentiList;
    }

    @Override
    public void modifica(Utente vecchio, Utente nuovo) throws UtenteException {

        if(!vecchio.isAttivo()){
            throw new UtenteException("L'utente risulta non attivo");
        }
        if (this.utentiList.contains(vecchio)) {
            vecchio.setNome(nuovo.getNome());
            vecchio.setCognome(nuovo.getCognome());
            vecchio.setEmail(nuovo.getEmail());
            vecchio.setCountPrestiti(vecchio.getCountPrestiti());
            vecchio.setMatricola(nuovo.getMatricola());
        }


    }



    /** @brief Ottiene il Predicate corrispondente per la ricerca filtrata di un Utente
     *
     * @param filtro La stringa da filtrare nella tabella per la ricerca
     *
     * @return Predicate associato alla ricerca attuale
     *
     * @see Gestore#getPredicato(String)
     */
    @Override
    public Predicate<Utente> getPredicato(String filtro) {
        return utente -> {
            if (filtro == null || filtro.isEmpty()) {
                return true;
            }
            if (utente == null) {
                return false;
            }
            String filtroLower = filtro.toLowerCase();

            return utente.getCognome().toLowerCase().contains(filtroLower) ||
                    utente.getNome().toLowerCase().contains(filtroLower) ||
                    utente.getEmail().toLowerCase().contains(filtroLower);
        };
    }

    @Override
    public void setLista(List<Utente> listaCaricata) {
        utentiList = FXCollections.observableArrayList(listaCaricata);
    }

}
