package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.model.ArchivioDati;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class GestoreArchivioTest {

    private GestoreArchivio gestore;
    private File tempFile;

    @BeforeEach
    public void setUp() {
        gestore = new GestoreArchivio();
    }

    @AfterEach
    public void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    public void testSalvaECaricaArchivio() throws IOException, ClassNotFoundException {
        //creazione file temporaneo per testare salvataggio
        tempFile = File.createTempFile("testArchivio", ".bin");


        //creazione dati dummy (campioni) per l'archivio
        List<Libro> libri = new ArrayList<>();
        Libro l1 = new Libro("Java Guide", "Author A", 2020, "ISBN1", 5);
        libri.add(l1);

        List<Utente> utenti = new ArrayList<>();
        Utente u1 = new Utente("Mario", "Rossi", "MAT123", "email@test.it");
        utenti.add(u1);

        List<Prestito> prestiti = new ArrayList<>();
        Prestito p1 = new Prestito(u1, l1, LocalDate.now(), LocalDate.now().plusDays(30));
        prestiti.add(p1);

        //creazione archivio
        ArchivioDati archivioOriginale = new ArchivioDati(prestiti, utenti, libri);

        //TEST: salvaArchivio (salvataggio su file)
        gestore.salvaArchivio(archivioOriginale, tempFile);

        //controlliamo che il file esista e abbia una dimensione > 0 cioè
        //che sia stato scritto qualcosa
        assertTrue(tempFile.exists(), "Il file dovrebbe esistere dopo il salvataggio");
        assertTrue(tempFile.length() > 0, "Il file non dovrebbe essere vuoto");

        //TEST: caricaArchivio
        ArchivioDati archivioCaricato = gestore.caricaArchivio(tempFile);

        //controlliamo che l'archivio caricato da file sia stato instanziato
        assertNotNull(archivioCaricato, "L'oggetto caricato non deve essere null");

        //verifichiamo le dimensioni delle liste (devono essere uguali alle liste che abbiamo creato prima
        assertEquals(archivioOriginale.getListaLibri().size(), archivioCaricato.getListaLibri().size());
        assertEquals(archivioOriginale.getListaUtenti().size(), archivioCaricato.getListaUtenti().size());
        assertEquals(archivioOriginale.getListaPrestiti().size(), archivioCaricato.getListaPrestiti().size());

        //verifichiamo il contenuto specifico (es. il titolo del primo libro)
        assertEquals(l1.getTitolo(), archivioCaricato.getListaLibri().get(0).getTitolo());
        assertEquals(u1.getCognome(), archivioCaricato.getListaUtenti().get(0).getCognome());

        //verifica integrità referenziale (Opzionale ma utile)
        //controlliamo che l'utente dentro il prestito abbia lo stesso nome dell'utente salvato
        assertEquals("Mario", archivioCaricato.getListaPrestiti().get(0).getUtente().getNome());
    }

    @Test
    public void testCaricaFileInesistente() {
        //TEST: file inesistente
        File fileNonEsistente = new File("nonesistente.bin");

        //dovrebbe lanciare un'eccezione (IOException o FileNotFoundException)
        assertThrows(IOException.class, () -> {
            gestore.caricaArchivio(fileNonEsistente);
        });
    }
}
