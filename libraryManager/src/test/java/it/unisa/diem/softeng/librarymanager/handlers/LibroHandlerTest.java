package it.unisa.diem.softeng.librarymanager.handlers;

import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @brief NB. Il test non può verificare alcune delle specifiche del contratto, come ad esempio la gestione delle
 * eccezioni con gli alert.
 */
public class LibroHandlerTest {
    private LibroHandler handler;
    private GestoreLibro gestoreLibro;

    @BeforeEach
    public void setUp() {
        gestoreLibro = new GestoreLibro();
        handler = new LibroHandler(gestoreLibro);
    }

    @Test
    void testGetCriteriOrdinamento() {
        List<String> criteri = handler.getCriteriOrdinamento();

        assertNotNull(criteri, "La lista dei criteri non deve essere nulla");
        assertFalse(criteri.isEmpty(), "La lista dei criteri non deve essere vuota");

        assertTrue(criteri.contains("Titolo (A-Z)"), "Deve contenere il criterio di ordinamento per Titolo");
        assertTrue(criteri.contains("Autore (A-Z)"), "Deve contenere il criterio di ordinamento per Autore");
        assertTrue(criteri.contains("Anno (Recenti)"), "Deve contenere il criterio di ordinamento per Anno");

        assertEquals(3, criteri.size(), "Dovrebbero esserci esattamente 3 criteri di ordinamento");
    }

    @Test
    void testOnRemoveDisattivaLibro() {
        Libro l = new Libro("Il Signore degli Anelli", "J.R.R. Tolkien", 1954, "88-123-456", 5);

        try {
            gestoreLibro.add(l);
        } catch (Exception e) {
            fail("Setup del test fallito: impossibile aggiungere il libro al gestore");
        }

        assertTrue(l.isAttivo(), "Il libro dovrebbe essere attivo inizialmente");


        handler.onRemove(l);

        assertFalse(l.isAttivo(), "Il metodo onRemove dovrebbe impostare il libro come non attivo");
    }

    @Test
    void testOnRemoveGestisceNull() {
        /*
         Nel codice di LibroHandler c'è un check "if (l != null)".
         Risultato atteso: non viene lanciata alcuna eccezione.
        */
        assertDoesNotThrow(() -> handler.onRemove(null),
                "Se viene passato null al metodo, non deve lanciare eccezioni ma ignorare l'operazione");
    }
}