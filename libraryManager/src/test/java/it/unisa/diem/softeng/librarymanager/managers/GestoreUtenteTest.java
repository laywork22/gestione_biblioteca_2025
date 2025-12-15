package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.model.Utente;
import it.unisa.diem.softeng.librarymanager.exceptions.PrestitoException;
import it.unisa.diem.softeng.librarymanager.exceptions.UtenteException;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GestoreUtenteTest {
    private Utente utente1;
    private Utente utente2;
    private GestoreUtente gestore;

    @BeforeEach
    public void setUp() {
        utente1 = new Utente("Federica", "Rossi", "75018", "federica.rossi@email.it");
        utente2 = new Utente("Lucio", "Bianchi", "94327", "lucio.bianchi@email.it");
        gestore = new GestoreUtente();
    }

    @Test
    public void testAdd() throws UtenteException {
        gestore.add(utente1);
        ObservableList<Utente> lista = gestore.getLista();
        assertTrue(lista.contains(utente1));
        assertEquals(1, lista.size());
    }

    @Test
    public void testAddDuplicatoAttivo() throws UtenteException {
        gestore.add(utente1);
        Utente duplicato = new Utente("Federica", "Rossi", "75018", "federica.rossi@email.it");
        assertThrows(UtenteException.class, () -> gestore.add(duplicato));
    }

    @Test
    public void testAddRiattivaUtente() throws UtenteException, PrestitoException {
        gestore.add(utente1);
        gestore.remove(utente1);

        Utente utenteModificato = new Utente("Maria", "Verdi", "75018", "nuova@email.it");
        gestore.add(utenteModificato);

        Utente risultato = gestore.getLista().get(0);
        assertTrue(risultato.isAttivo());
        assertEquals("Maria", risultato.getNome());
        assertEquals("Verdi", risultato.getCognome());
        assertEquals("nuova@email.it", risultato.getEmail());
    }

    @Test
    public void testAddIgnoraNull() throws UtenteException {
        gestore.add(null);
        assertTrue(gestore.getLista().isEmpty());
    }

    @Test
    public void testRemove() throws PrestitoException, UtenteException {
        gestore.add(utente1);
        gestore.remove(utente1);
        assertFalse(utente1.isAttivo());
    }

    @Test
    public void testRemoveConPrestitiAttivi() throws UtenteException {
        utente1.setCountPrestiti(1);
        gestore.add(utente1);
        assertThrows(PrestitoException.class, () -> gestore.remove(utente1));
        assertTrue(utente1.isAttivo());
    }

    @Test
    public void testRemoveGiaNonAttivo() throws UtenteException, PrestitoException {
        gestore.add(utente1);
        gestore.remove(utente1);
        assertThrows(PrestitoException.class, () -> gestore.remove(utente1));
    }

    @Test
    public void testModifica() throws UtenteException {
        gestore.add(utente1);
        Utente nuovo = new Utente("Anna", "Neri", "75018", "anna@email.it");
        gestore.modifica(utente1, nuovo);

        assertEquals("Anna", utente1.getNome());
        assertEquals("Neri", utente1.getCognome());
        assertEquals("anna@email.it", utente1.getEmail());
    }

    @Test
    public void testModificaNonAttivo() throws UtenteException, PrestitoException {
        gestore.add(utente1);
        gestore.remove(utente1);
        Utente nuovo = new Utente("Anna", "Neri", "75018", "anna@email.it");

        assertThrows(UtenteException.class, () -> gestore.modifica(utente1, nuovo));
    }

    @Test
    public void testGetPredicato() throws UtenteException {
        gestore.add(utente1);
        gestore.add(utente2);

        Predicate<Utente> filtroNome = gestore.getPredicato("Lucio");
        assertTrue(filtroNome.test(utente2));
        assertFalse(filtroNome.test(utente1));

        Predicate<Utente> filtroEmail = gestore.getPredicato("federica.rossi");
        assertTrue(filtroEmail.test(utente1));

        Predicate<Utente> filtroVuoto = gestore.getPredicato("");
        assertTrue(filtroVuoto.test(utente1));
        assertTrue(filtroVuoto.test(utente2));
    }
}