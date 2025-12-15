package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.exceptions.PrestitoException;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.StatoPrestitoEnum;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class GestorePrestitoTest {

    private GestorePrestito gestore;
    private Utente utenteStandard;
    private Libro libroStandard;
    private Prestito prestitoStandard;

    @BeforeEach
    void setUp() {
        gestore = new GestorePrestito();

        utenteStandard = new Utente("Mario", "Rossi", "MRARSS", "mario@test.it");
        utenteStandard.setAttivo(true);
        utenteStandard.setCountPrestiti(0);


        libroStandard = new Libro("Java Programming", "Autore X", 2024, "123456", 5);
        libroStandard.setAttivo(true);

        libroStandard.setCopieDisponibili(5);


        prestitoStandard = new Prestito(utenteStandard, libroStandard, LocalDate.now(), LocalDate.now().plusDays(30));
    }



    @Test
    void testAddSuccess() throws PrestitoException {
        gestore.add(prestitoStandard);

        assertEquals(1, gestore.getLista().size(), "La lista dovrebbe contenere 1 prestito");
        assertEquals(1, utenteStandard.getCountPrestiti(), "I prestiti dell'utente dovrebbero salire a 1");
        assertEquals(4, libroStandard.getCopieDisponibili(), "Le copie disponibili dovrebbero scendere a 4");
    }

    @Test
    void testAddFailLimiteUtente() {
        utenteStandard.setCountPrestiti(3);

        PrestitoException e = assertThrows(PrestitoException.class, () -> gestore.add(prestitoStandard));
        assertEquals("L'utente ha raggiunto il limite dei 3 prestiti", e.getMessage());
    }

    @Test
    void testRemoveSuccess() throws PrestitoException {
        gestore.add(prestitoStandard);

        assertEquals(1, utenteStandard.getCountPrestiti());
        assertEquals(4, libroStandard.getCopieDisponibili());


        gestore.remove(prestitoStandard);

        assertEquals(StatoPrestitoEnum.CHIUSO, prestitoStandard.getStato(), "Lo stato deve diventare CHIUSO");
        assertEquals(0, utenteStandard.getCountPrestiti(), "Il contatore utente deve scendere");
        assertEquals(5, libroStandard.getCopieDisponibili(), "Le copie libro devono aumentare");
    }



    @Test
    void testModificaSuccess() throws PrestitoException {
        gestore.add(prestitoStandard);

        Prestito nuovoDati = new Prestito(utenteStandard, libroStandard, LocalDate.now().minusDays(1), LocalDate.now().plusDays(10));
        nuovoDati.setStato(StatoPrestitoEnum.SCADUTO);

        gestore.modifica(prestitoStandard, nuovoDati);

        Prestito pModificato = gestore.getLista().get(0);
        assertEquals(StatoPrestitoEnum.SCADUTO, pModificato.getStato());
        assertEquals(nuovoDati.getDataFine(), pModificato.getDataFine());
    }

    @Test
    void testModificaFailSeChiuso() throws PrestitoException {
        gestore.add(prestitoStandard);
        gestore.remove(prestitoStandard);

        Prestito nuovoDati = new Prestito(utenteStandard, libroStandard, LocalDate.now(), LocalDate.now());

        PrestitoException e = assertThrows(PrestitoException.class, () -> gestore.modifica(prestitoStandard, nuovoDati));
        assertEquals("Lo stato del prestito è chiuso", e.getMessage());
    }


    @Test
    void testGetPredicato() throws PrestitoException {
        gestore.add(prestitoStandard);

        Predicate<Prestito> filtroNome = gestore.getPredicato("Mario");
        Predicate<Prestito> filtroCognome = gestore.getPredicato("Rossi");
        Predicate<Prestito> filtroTitolo = gestore.getPredicato("Java");
        Predicate<Prestito> filtroErrato = gestore.getPredicato("Python");

        assertTrue(filtroNome.test(prestitoStandard), "Dovrebbe trovare per Nome");
        assertTrue(filtroCognome.test(prestitoStandard), "Dovrebbe trovare per Cognome");
        assertTrue(filtroTitolo.test(prestitoStandard), "Dovrebbe trovare per Titolo");
        assertFalse(filtroErrato.test(prestitoStandard), "Non dovrebbe trovare stringhe diverse");
    }

}