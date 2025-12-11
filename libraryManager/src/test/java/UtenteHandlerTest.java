import it.unisa.diem.softeng.librarymanager.comparators.CognomeUtenteComparator;
import it.unisa.diem.softeng.librarymanager.controllers.UtenteHandler;
import it.unisa.diem.softeng.librarymanager.managers.Gestore;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtenteHandlerTest {

    @Test
    void testCriteriOrdinamento() {
        GestoreUtente gestoreMock = new GestoreUtente();
        UtenteHandler handler = new UtenteHandler(gestoreMock);

        List<String> criteri = handler.getCriteriOrdinamento();
        assertTrue(criteri.contains("Cognome (A-Z)"));
        assertEquals(1, criteri.size());
    }

    @Test
    void testCompare() {
        CognomeUtenteComparator comparator = new CognomeUtenteComparator();

        Utente u1 = new Utente("Mario", "Rossi", "M1", "m@test.it");
        Utente u2 = new Utente("Luigi", "Bianchi", "M2", "l@test.it");
        Utente u3 = new Utente("Anna", "Rossi", "M3", "a@test.it"); // Stesso cognome

        //Bianchi viene prima di Rossi -> deve restituire un numero negativo
        assertTrue(comparator.compare(u2, u1) < 0);

        //Rossi viene dopo Bianchi -> numero positivo
        assertTrue(comparator.compare(u1, u2) > 0);

        assertEquals(0, comparator.compare(u1, u3));
    }

}
