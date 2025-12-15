package it.unisa.diem.softeng.librarymanager.handlers;


import it.unisa.diem.softeng.librarymanager.exceptions.UtenteException;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @brief NB. Il test non può verificare alcune delle specifiche del contratto, come ad esempio la gestione delle
 * eccezioni con gli alert.
 */
public class UtenteHandlerTest {
    private UtenteHandler handler;
    private GestoreUtente gu;

    @BeforeEach
    public void setup() {
        gu = new GestoreUtente();

        handler = new UtenteHandler(gu);
    }

    @Test
    void testCriteriOrdinamento() {
        List<String> criteri = handler.getCriteriOrdinamento();
        assertTrue(criteri.contains("Cognome (A-Z)"));
        assertEquals(2, criteri.size());
    }


    @Test
    void testOnRemoveDisattivaUtente() {
        // Setup dati test
        Utente u = new Utente("Mario", "Rossi", "MRARSS", "m@test.it");


        //si assume che l'utente sia nuovo e che add non lanci eccezioni.
        try {
            gu.add(u);
        } catch (UtenteException ignored) {
            fail("Setup del test fallito: impossibile aggiungere utente al gestore");
        }

        assertTrue(u.isAttivo(), "L'utente dovrebbe essere attivo inizialmente");

        handler.onRemove(u);

        assertFalse(u.isAttivo(), "Il metodo onRemove dovrebbe impostare l'utente come non attivo");
    }


    @Test
    void testOnRemoveGestisceNull() {
        /*È presente un controllo "if (u != null)".
          Risultato atteso: nessuna eccezione lanciata*/
        assertDoesNotThrow(() -> handler.onRemove(null),
                "Se viene passato null al metodo, non deve lanciare eccezioni ma ignorare l'operazione");
    }

}
