package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.exceptions.PrestitoException;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.StatoPrestitoEnum;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.function.Predicate;
import static org.junit.jupiter.api.Assertions.*;

public class GestorePrestitoTest {

    private GestorePrestito gestore;
    private Utente utente;
    private Libro libro;
    private Prestito prestito;

    @BeforeEach
    public void setUp() {
        gestore = new GestorePrestito();
        utente = new Utente("Mario", "Rossi", "MRARSS", "mario@test.it");
        libro = new Libro("Java Programming", "Autore X", 2024, "9788800000000", 5);

        // Assicuriamo stato valido per il prestito
        utente.setAttivo(true);
        utente.setCountPrestiti(0);
        libro.setAttivo(true);
        libro.setCopieDisponibili(5);

        prestito = new Prestito(utente, libro, LocalDate.now(), LocalDate.now().plusDays(30));
    }

    @Test
    public void testAdd() throws PrestitoException {
        gestore.add(prestito);
        assertEquals(1, gestore.getLista().size());
        assertEquals(1, utente.getCountPrestiti());
        assertEquals(4, libro.getCopieDisponibili());
    }

    @Test
    public void testAddIgnoraNull() throws PrestitoException {
        gestore.add(null);
        assertTrue(gestore.getLista().isEmpty());
    }

    @Test
    public void testAddLimitePrestitiRaggiunto() {
        utente.setCountPrestiti(3);
        assertThrows(PrestitoException.class, () -> gestore.add(prestito));
    }

    @Test
    public void testAddCopieNonDisponibili() {
        libro.setCopieDisponibili(0);
        assertThrows(PrestitoException.class, () -> gestore.add(prestito));
    }

    @Test
    public void testAddLibroNonAttivo() {
        libro.setAttivo(false);
        assertThrows(PrestitoException.class, () -> gestore.add(prestito));
    }

    @Test
    public void testAddUtenteNonAttivo() {
        utente.setAttivo(false);
        assertThrows(PrestitoException.class, () -> gestore.add(prestito));
    }

    @Test
    public void testRemove() throws PrestitoException {
        gestore.add(prestito);
        gestore.remove(prestito);

        assertEquals(StatoPrestitoEnum.CHIUSO, prestito.getStato());
        assertEquals(0, utente.getCountPrestiti());
        assertEquals(5, libro.getCopieDisponibili());
    }

    @Test
    public void testRemoveIgnoraNull() throws PrestitoException {
        assertDoesNotThrow(() -> gestore.remove(null));
    }

    @Test
    public void testRemoveGiaChiuso() throws PrestitoException {
        gestore.add(prestito);
        gestore.remove(prestito);
        assertThrows(PrestitoException.class, () -> gestore.remove(prestito));
    }

    @Test
    public void testModifica() throws PrestitoException {
        gestore.add(prestito);
        Prestito nuovo = new Prestito(utente, libro, LocalDate.now().minusDays(5), LocalDate.now().plusDays(5));
        nuovo.setStato(StatoPrestitoEnum.SCADUTO);

        gestore.modifica(prestito, nuovo);
        assertEquals(StatoPrestitoEnum.SCADUTO, prestito.getStato());
        assertEquals(nuovo.getDataInizio(), prestito.getDataInizio());
    }

    @Test
    public void testModificaPrestitoChiuso() throws PrestitoException {
        gestore.add(prestito);
        gestore.remove(prestito);
        Prestito nuovo = new Prestito(utente, libro, LocalDate.now(), LocalDate.now());

        assertThrows(PrestitoException.class, () -> gestore.modifica(prestito, nuovo));
    }

    @Test
    public void testGetPredicato() throws PrestitoException {
        gestore.add(prestito);

        Predicate<Prestito> filtroUtente = gestore.getPredicato("Rossi");
        assertTrue(filtroUtente.test(prestito));

        Predicate<Prestito> filtroLibro = gestore.getPredicato("Java");
        assertTrue(filtroLibro.test(prestito));

        Predicate<Prestito> filtroErrato = gestore.getPredicato("Python");
        assertFalse(filtroErrato.test(prestito));
    }

    @Test
    public void testAggiornaStati() throws PrestitoException {
        Prestito prestitoScaduto = new Prestito(utente, libro, LocalDate.now().minusDays(40), LocalDate.now().minusDays(10));
        gestore.add(prestitoScaduto);

        gestore.aggiornaStati();
        assertEquals(StatoPrestitoEnum.SCADUTO, prestitoScaduto.getStato());
    }
}