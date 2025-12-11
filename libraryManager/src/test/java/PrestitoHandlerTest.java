import it.unisa.diem.softeng.librarymanager.controllers.PrestitoHandler;
import it.unisa.diem.softeng.librarymanager.managers.*;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.StatoPrestitoEnum;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PrestitoHandlerTest {
    private PrestitoHandler handler;
    private GestorePrestito gestorePrestito;
    private GestoreLibro gestoreLibro;
    private GestoreUtente gestoreUtente;

    @BeforeEach
    void setUp() {
        gestorePrestito = new GestorePrestito();
        gestoreLibro = new GestoreLibro();
        gestoreUtente = new GestoreUtente();

        handler = new PrestitoHandler(gestorePrestito, gestoreLibro, gestoreUtente);
    }

    @Test
    void testGetCriteriOrdinamento() {
        //controllare che la lista criteri non sia vuota.
        List<String> criteri = handler.getCriteriOrdinamento();

        assertNotNull(criteri, "La lista dei criteri non deve essere nulla");
        assertFalse(criteri.isEmpty(), "La lista dei criteri non deve essere vuota");

        //controllo chiavi della mappa.
        assertTrue(criteri.contains("Utente (A-Z)"));
        assertTrue(criteri.contains("Libro (A-Z)"));
        assertTrue(criteri.contains("Data Inizio (Recenti)"));
        assertTrue(criteri.contains("Stato"));

        assertEquals(4, criteri.size(), "Dovrebbero esserci esattamente 4 criteri di ordinamento");
    }

    @Test
    void testOnRemoveChiudePrestito() {
        //setup dati test
        Utente u = new Utente("Mario", "Rossi", "MRARSS", "m@test.it");
        Libro l = new Libro("Java", "Autore", 2020, "ISBN1", 5);
        Prestito p = new Prestito(u, l, LocalDate.now(), null);

        //verifica stato iniziale
        assertEquals(StatoPrestitoEnum.ATTIVO, p.getStato());

        /*esecuzione: si chiama onRemove dell'handler
        in PrestitoHandler, onRemove è implementato per settare lo stato a CHIUSO*/
        handler.onRemove(p);

        //Il prestito deve risultare CHIUSO
        assertEquals(StatoPrestitoEnum.CHIUSO, p.getStato(), "Il metodo onRemove dovrebbe impostare lo stato del prestito a CHIUSO");
    }

    @Test
    void testOnRemoveGestisceNull() {

        //controllo lancio eccezione quando onRemove riceve un valore null
        assertThrows(NullPointerException.class, () -> handler.onRemove(null),
                "Se viene passato null al metodo, lancia una NullPointerException");
    }
}
