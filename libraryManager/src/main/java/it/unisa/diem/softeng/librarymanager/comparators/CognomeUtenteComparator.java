package it.unisa.diem.softeng.librarymanager.comparators;

import it.unisa.diem.softeng.librarymanager.model.Utente;

import java.util.Comparator;

public class CognomeUtenteComparator implements Comparator<Utente> {

    @Override
    public int compare(Utente utente, Utente t1) {
        if(utente.getCognome().equals(t1.getCognome())){
            return utente.getNome().compareTo(t1.getNome());
        }

        return utente.getCognome().compareTo(t1.getCognome());
    }
}
