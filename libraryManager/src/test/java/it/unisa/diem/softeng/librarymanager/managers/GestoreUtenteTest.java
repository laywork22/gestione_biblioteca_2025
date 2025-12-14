/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.model.Utente;
import it.unisa.diem.softeng.librarymanager.exceptions.PrestitoException;
import it.unisa.diem.softeng.librarymanager.exceptions.UtenteException;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author letiz
 */
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
    
    /**
     * Test of add method, of class GestoreUtente.
     */
    @Test
    public void testAdd() throws UtenteException {
        // Aggiunta di un utente
        gestore.add(utente1);
        ObservableList<Utente> lista = gestore.getLista();
        assertTrue(lista.contains(utente1));

        // Controlla se ci sono duplicati dell'utente
        Utente duplicato = new Utente("Federica", "Rossi", "75018", "federica.rossi@email.it");
        UtenteException exception = assertThrows(UtenteException.class, () -> gestore.add(duplicato));
        assertEquals("Utente già presente nel sistema!", exception.getMessage());
    }

    /**
     * Test of remove method, of class GestoreUtente.
     */
    @Test
    public void testRemove() throws PrestitoException {
        utente1.setCountPrestiti(0);
        gestore.getLista().add(utente1);

        // Rimozione logica
        gestore.remove(utente1);
        assertFalse(utente1.isAttivo());

        // Eccezione: Rimozione di un utente che ha prestiti attivi
        Utente utenteConPrestiti = new Utente("Mario", "Verdi", "12345", "mario.verdi@email.it");
        utenteConPrestiti.setCountPrestiti(1);
        gestore.getLista().add(utenteConPrestiti);
        PrestitoException exception = assertThrows(PrestitoException.class, () -> gestore.remove(utenteConPrestiti));
        assertEquals("L'utente ha almeno un prestito attivo", exception.getMessage());
    }

    /**
     * Test of getLista method, of class GestoreUtente.
     */
    @Test
    public void testGetLista() throws UtenteException {
        assertEquals(0, gestore.getLista().size());
        gestore.add(utente1);
        gestore.add(utente2);
        ObservableList<Utente> lista = gestore.getLista();
        assertEquals(2, lista.size());
        assertTrue(lista.contains(utente1));
        assertTrue(lista.contains(utente2));
    }

    /**
     * Test of modifica method, of class GestoreUtente.
     */
    @Test
    public void testModifica() throws UtenteException {
        gestore.add(utente1);
        Utente nuovoUtente = new Utente("Federica", "Neri", "75018", "federica.neri@email.it");
        gestore.modifica(utente1, nuovoUtente);

        assertEquals("Neri", utente1.getCognome());
        assertEquals("Federica", utente1.getNome());
    }

    /**
     * Test of getPredicato method, of class GestoreUtente.
     */
    @Test
    public void testGetPredicato() throws UtenteException {
        gestore.add(utente1);
        gestore.add(utente2);

        Predicate<Utente> filtroNome = gestore.getPredicato("Lucio");
        assertTrue(filtroNome.test(utente2));
        assertFalse(filtroNome.test(utente1));

        Predicate<Utente> filtroVuoto = gestore.getPredicato("");
        assertTrue(filtroVuoto.test(utente1));
        assertTrue(filtroVuoto.test(utente2));
    }
    
}
