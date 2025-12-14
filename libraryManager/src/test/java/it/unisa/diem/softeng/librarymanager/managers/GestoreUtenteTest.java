package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.exceptions.PrestitoException;
import it.unisa.diem.softeng.librarymanager.exceptions.UtenteException;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class GestoreUtenteTest {

    private GestoreUtente gestore;
    private Utente utenteStandard;

    @BeforeEach
    void setUp() {
        gestore = new GestoreUtente();

        utenteStandard = new Utente("Mario", "Rossi", "MRARSS00A01H501U", "mario.rossi@email.it");

        utenteStandard.setAttivo(true);
        utenteStandard.setCountPrestiti(0);
    }

    @Test
    void testAddSuccess() throws UtenteException {
        gestore.add(utenteStandard);

        assertEquals(1, gestore.getLista().size(), "La lista dovrebbe contenere 1 utente");
        assertEquals(utenteStandard, gestore.getLista().get(0), "L'utente inserito deve corrispondere");
    }

    @Test
    void testAddDuplicate() throws UtenteException {
        gestore.add(utenteStandard);

        Utente utenteDuplicato = new Utente("Mario", "Rossi", "MRARSS00A01H501U", "mario.rossi@email.it");

        UtenteException exception = assertThrows(UtenteException.class, () -> {
            gestore.add(utenteDuplicato);
        });

        assertEquals("Utente già presente nel sistema!", exception.getMessage());
    }

    @Test
    @DisplayName("ADD: Inserimento null ignorato")
    void testAddNull() throws UtenteException {
        gestore.add(null);
        assertEquals(0, gestore.getLista().size(), "Non dovrebbe aggiungere elementi nulli");
    }

    @Test
    void testRemoveFailPrestitiAttivi() throws UtenteException {
        utenteStandard.setCountPrestiti(1); // Simuliamo un prestito
        gestore.add(utenteStandard);

        PrestitoException exception = assertThrows(PrestitoException.class, () -> {
            gestore.remove(utenteStandard);
        });

        assertEquals("L'utente ha almeno un prestito attivo", exception.getMessage());
        assertTrue(utenteStandard.isAttivo(), "L'utente dovrebbe rimanere attivo se la rimozione fallisce");
    }


    @Test
    @DisplayName("MODIFICA: Aggiornamento dati corretto")
    void testModificaSuccess() throws UtenteException {
        gestore.add(utenteStandard);

        Utente nuoviDati = new Utente("Luigi", "Verdi", "LGIVRD00A01H501U", "luigi@email.it");

        gestore.modifica(utenteStandard, nuoviDati);

        // Verifichiamo che l'oggetto originale sia stato aggiornato
        assertEquals("Luigi", utenteStandard.getNome());
        assertEquals("Verdi", utenteStandard.getCognome());
        assertEquals("LGIVRD00A01H501U", utenteStandard.getMatricola());
        assertEquals("luigi@email.it", utenteStandard.getEmail());
    }

    @Test
    @DisplayName("MODIFICA: Errore su utente non attivo")
    void testModificaFailNonAttivo() throws UtenteException {
        utenteStandard.setAttivo(false);
        gestore.add(utenteStandard);

        Utente nuoviDati = new Utente("Luigi", "Verdi", "LGIVRD", "luigi@email.it");

        UtenteException exception = assertThrows(UtenteException.class, () -> {
            gestore.modifica(utenteStandard, nuoviDati);
        });

        assertEquals("L'utente risulta non attivo", exception.getMessage());
    }



    @Test
    @DisplayName("PREDICATO: Filtro ricerca")
    void testGetPredicato() throws UtenteException {
        gestore.add(utenteStandard);

        Predicate<Utente> filtroNome = gestore.getPredicato("Mario");
        Predicate<Utente> filtroCognome = gestore.getPredicato("Rossi");
        Predicate<Utente> filtroEmail = gestore.getPredicato("email.it");
        Predicate<Utente> filtroErrato = gestore.getPredicato("Giovanni");

        assertTrue(filtroNome.test(utenteStandard), "Dovrebbe trovare per Nome");
        assertTrue(filtroCognome.test(utenteStandard), "Dovrebbe trovare per Cognome");
        assertTrue(filtroEmail.test(utenteStandard), "Dovrebbe trovare per Email");
        assertFalse(filtroErrato.test(utenteStandard), "Non dovrebbe trovare match inesistenti");

    }



}