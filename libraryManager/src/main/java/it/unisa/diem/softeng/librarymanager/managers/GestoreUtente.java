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
     * Implementazione specifica per gli utentI: controlla se la matricola è già presente nella lista,
     * Se è presente nell'archivio e ha stato attivo: lancia un'eccezzione.
     * Se è presente nell'archivio ma è stato eliminato logicamente in precedenza, lo riattiva e
     * imposta i dati forniti nel form.
     * Se non è presente nell'archivio lo aggiunge.
     *
     * @pre u != null
     *
     * @post Se nuovo: utentiList.size() = utentiList.size() + 1
     * @post Se riattivato: u.isAttivo() = true e campi aggiornati
     *
     * @param l il Libro da aggiungere o aggiornare
     *
     * @throws UtenteException
     *
     * @see Gestore#add(Object)
     */
    @Override
    public void add(Utente u) throws UtenteException {
        if (u == null) return;

        int index = utentiList.indexOf(u);

        if (index == -1) {
            utentiList.add(u);
        } else {
            Utente utenteEsistente = utentiList.get(index);

            if (!utenteEsistente.isAttivo()) {
                utenteEsistente.setAttivo(true);
                utenteEsistente.setNome(u.getNome());
                utenteEsistente.setCognome(u.getCognome());
                utenteEsistente.setEmail(u.getEmail());


                utentiList.set(index, utenteEsistente);
            } else {
                throw new UtenteException("Esiste già un utente attivo con questa matricola.");
            }
        }
    }


    /**
     * @param u l'utente da rimuovere
     * @brief Rimuove logicamente un utente (lo marca come non attivo).
     *
     * @pre u != null
     * @pre u.isAttivo() == true
     * @pre u.getCountPrestiti() == 0 (Nessun prestito attivo)
     *
     * @post u.isAttivo() = false
     * @post utentiList.size() invariata
     *
     * @throws PrestitoException
     */
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


    /**
     * @param vecchio l'utente da modificare
     * @param nuovo   l'utente con i nuovi dati
     * @brief Permette di modificare i dati anagrafici di un utente esistente.
     *
     * @pre utentiList.contains(vecchio) == true
     * @pre vecchio.isAttivo() == true
     * @pre nuovo != null
     *
     * @post vecchio aggiornato con i valori di nuovo (Nome, Cognome, Email)
     * @post utentiList.size() invariata
     *
     * @throws UtenteException
     */
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
