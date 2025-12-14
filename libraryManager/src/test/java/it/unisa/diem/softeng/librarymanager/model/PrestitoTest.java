package it.unisa.diem.softeng.librarymanager.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PrestitoTest {
    @BeforeEach
    public void setUp() throws Exception {
        Field campoId = Prestito.class.getDeclaredField("nextId");

        campoId.setAccessible(true);

        campoId.setInt(null, 1);
    }


    @Test
    public void testCostruttore() {
        Libro l1 = new Libro("I Promessi Sposi", "Alessandro Manzoni", 1827, "978-8804668283", 1);
        Utente u1 = new Utente("Mario", "Rossi", "RSSMRA80A01H501U", "mario.rossi@email.it");

        Prestito p = new Prestito(u1, l1, LocalDate.now(), LocalDate.now().plusDays(20));

        assertNotNull(p);

        assertNotNull(p.getUtente());
        assertNotNull(p.getLibro());
        assertNotNull(p.getDataInizio());
        assertNotNull(p.getDataFine());
        assertNotNull(p.getStato());

        assertTrue(p.getDataFine().isAfter(p.getDataInizio()));


        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,() ->  new Prestito(u1, l1, LocalDate.now(), LocalDate.of(2021, 4, 5)));
        assertEquals("La data di fine non può essere antecedente alla data di inizio!", e.getMessage());

        Prestito p2 = new Prestito(u1, l1, null, null);

        assertTrue(p2.getDataInizio().plusDays(30).isEqual(p2.getDataFine()));
    }

    @Test
    public void testEquals() {
        Libro l1 = new Libro("I Promessi Sposi", "Alessandro Manzoni", 1827, "978-8804668283", 1);
        Utente u1 = new Utente("Mario", "Rossi", "RSSMRA80A01H501U", "mario.rossi@email.it");

        Prestito p = new Prestito(u1, l1, LocalDate.now(), LocalDate.now().plusDays(20));
        Prestito p2 = new Prestito(u1, l1, null, null);

        assertNotEquals(p, p2);

        assertEquals(p, p);
    }


    @Test
    public void testCompareTo() {
        Libro l1 = new Libro("I Promessi Sposi", "Alessandro Manzoni", 1827, "978-8804668283", 1);
        Utente u1 = new Utente("Mario", "Rossi", "RSSMRA80A01H501U", "mario.rossi@email.it");

        LocalDate oggi = LocalDate.now();
        LocalDate ieri = oggi.minusDays(1);

        //prestito vecchio (ieri)
        Prestito pVecchio = new Prestito(u1, l1, ieri, oggi);

        //prestito nuovo (oggi)
        Prestito pNuovo = new Prestito(u1, l1, oggi, oggi.plusDays(30));

        assertTrue(pVecchio.compareTo(pNuovo) < 0, "Il prestito più vecchio deve precedere quello nuovo");

        assertTrue(pNuovo.compareTo(pVecchio) > 0);

        assertEquals(0, pVecchio.compareTo(pVecchio));
    }

}
