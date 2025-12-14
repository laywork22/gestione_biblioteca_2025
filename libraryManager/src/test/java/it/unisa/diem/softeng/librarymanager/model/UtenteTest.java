package it.unisa.diem.softeng.librarymanager.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtenteTest {

    private Utente utenteA;
    private Utente utenteB;

    @BeforeEach
    void setUp() {

        utenteA = new Utente("Mario", "Rossi", "MRARSS00A01H501U", "mario.rossi@email.it");

        utenteB = new Utente("Luigi", "Verdi", "LGIVRD99B02F205Z", "luigi.verdi@test.com");
    }


    @Test
    void testCostruttore() {
        assertEquals("Mario", utenteA.getNome());
        assertEquals("Rossi", utenteA.getCognome());
        assertEquals("MRARSS00A01H501U", utenteA.getMatricola());
        assertEquals("mario.rossi@email.it", utenteA.getEmail());
        assertEquals(0, utenteA.getCountPrestiti(), "Il contatore prestiti deve partire da 0");
        assertTrue(utenteA.isAttivo(), "Lo stato iniziale deve essere attivo (true)");
        assertEquals(3, Utente.MAX_PRESTITI, "La costante MAX_PRESTITI deve essere 3");
    }



    @Test
    @DisplayName("EQUALS: Uguali se stessa matricola, ignorando altri campi")
    void testEqualsSameMatricola() {

        Utente utenteC = new Utente("Marco", "Bianchi", "MRARSS00A01H501U", "marco.b@test.it");

        assertTrue(utenteA.equals(utenteC), "Due utenti con la stessa matricola devono essere uguali");

        assertFalse(utenteA.equals(utenteB), "Due utenti con matricole diverse devono essere diversi");

        assertTrue(utenteA.equals(utenteA), "L'oggetto è uguale a sé stesso");

        assertFalse(utenteA.equals(null), "L'oggetto non è uguale a null");

    }



    @Test
    void testCompareTo() {

        assertTrue(utenteA.compareTo(utenteB) > 0, "UtenteA deve essere maggiore di UtenteB se la sua matricola viene dopo");
        assertTrue(utenteB.compareTo(utenteA) < 0, "UtenteB deve essere minore di UtenteA se la sua matricola viene prima");

        Utente utenteC = new Utente("Marco", "Bianchi", "MRARSS00A01H501U", "marco.b@test.it");
        assertEquals(0, utenteA.compareTo(utenteC), "Due utenti con la stessa matricola devono restituire 0");
    }

}