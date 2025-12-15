package it.unisa.diem.softeng.librarymanager.managers;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import it.unisa.diem.softeng.librarymanager.model.Libro;
import it.unisa.diem.softeng.librarymanager.exceptions.LibroException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.function.Predicate;

public class GestoreLibroTest {

    private GestoreLibro gestore;
    private Libro libro1;
    private Libro libro2;

    @BeforeEach
    public void setUp() {
        gestore = new GestoreLibro();
        // Libri di base per i test
        libro1 = new Libro("Il Signore degli Anelli", "J.R.R. Tolkien", 1954, "9788845242796", 5);
        libro2 = new Libro("1984", "George Orwell", 1949, "9788804677701", 3);
    }
    
    
    @Test
    //Aggiunge nuovo Libro
    public void addNuovoLibro() throws LibroException {
     
        gestore.add(libro1);

        assertTrue(gestore.getLista().contains(libro1));
        assertEquals(1, gestore.getLista().size());
        assertEquals(5, libro1.getCopieTotali());
        assertEquals(5, libro1.getCopieDisponibili());
    }

    
//Test per il metodo add(Libro l)
    @Test
    //Incrementa copie ce Libro esiste
    public void addIncrementa() throws LibroException {
      
        gestore.add(libro1);
      
        Libro libro1Duplicato = new Libro("Il Signore degli Anelli", "J.R.R. Tolkien", 1954, "9788845242796", 2);

        gestore.add(libro1Duplicato);

        assertEquals(1, gestore.getLista().size(), "La lista deve contenere un solo elemento");
        Libro libroAggiornato = gestore.getLista().get(0);
        assertEquals(7, libroAggiornato.getCopieTotali(), "Le copie totali devono essere 5 + 2 = 7");
        assertEquals(7, libroAggiornato.getCopieDisponibili(), "Le copie disponibili devono essere 5 + 2 = 7");
    }

    @Test
    //null
    public void addIgnoraLibroNull() throws LibroException {
       
        gestore.add(null);

        assertTrue(gestore.getLista().isEmpty());
    }

    @Test
    public void addRiattivaLibroNonAttivo() throws LibroException {
        libro1.setAttivo(false);
        gestore.getLista().add(libro1);

        // Creiamo un duplicato con nuovi dati (es. 2 copie invece di 5)
        Libro libro1Duplicato = new Libro("Il Signore degli Anelli", "J.R.R. Tolkien", 1954, "9788845242796", 2);

        //Proviamo ad aggiungere il libro duplicato.
        //NON deve lanciare eccezione, ma riattivare quello esistente.
        assertDoesNotThrow(() -> gestore.add(libro1Duplicato));

        //Verifiche
        assertTrue(libro1.isAttivo(), "Il libro doveva essere riattivato");
        assertEquals(2, libro1.getCopieTotali(), "Le copie dovevano essere resettate al valore del nuovo inserimento");
        assertEquals(2, libro1.getCopieDisponibili(), "Le copie disponibili dovevano essere aggiornate");
    }


    @Test
    //Rende Libro non attivo se non ci sono prestiti attivi inerenti a esso
    public void removeLibro() throws LibroException {
    
        gestore.add(libro1);

        gestore.remove(libro1);

        assertFalse(libro1.isAttivo(), "Il libro dovrebbe essere marcato come non attivo");
    }

    @Test
    //Verifica che la rimozione di un riferimento nullo a un libro sia ignorata
    public void removeIgnoraLibroNull() throws LibroException {
        
        assertDoesNotThrow(() -> gestore.remove(null));
        assertTrue(gestore.getLista().isEmpty());
    }

    @Test
    //Lancia eccezione se Libro non è attivo
    public void removeLanciaEccezioneUno() throws LibroException {
     
        libro1.setAttivo(false);
        gestore.add(libro1);

        assertThrows(LibroException.class, () -> gestore.remove(libro1), "Deve lanciare LibroException se il libro è già non attivo");
    }

    @Test
    //Lancia eccezione se esistono copie in prestito
    public void removeLanciaEccezioneDue() throws LibroException {
    
        gestore.add(libro1);
       
        libro1.setCopieDisponibili(4);

        assertThrows(LibroException.class, () -> gestore.remove(libro1), "Deve lanciare LibroException se ci sono copie in prestito");
        assertTrue(libro1.isAttivo(), "Il libro deve rimanere attivo");
        assertEquals(4, libro1.getCopieDisponibili());
    }


//Test per il metodo modifica(Libro vecchio, Libro nuovo)

    @Test
    //Aggiorna dati e copie disponibili
    public void modificaDatiCopie() throws Exception {
      
        gestore.add(libro1); 

        libro1.setCopieDisponibili(3);
        int copieNonDisponibili = libro1.getCopieTotali() - libro1.getCopieDisponibili(); 

        Libro nuovoLibro1 = new Libro("Il Silmarillion", "J.R.R. Tolkien", 2000, "9788845242796", 10);

        gestore.modifica(libro1, nuovoLibro1);

        assertEquals(1, gestore.getLista().size());
        assertEquals("Il Silmarillion", libro1.getTitolo(), "Il titolo deve essere aggiornato");
        assertEquals(10, libro1.getCopieTotali(), "Le copie totali devono essere aggiornate");
        assertEquals(8, libro1.getCopieDisponibili(), "Le copie disponibili devono essere ricalcolate");
    }

    @Test
    //Lancia eccezione se Libro non è attivo
    public void modificaLanciaEccezione() {
  
        libro1.setAttivo(false);
        Libro nuovoLibro1 = new Libro("Nuovo Titolo", "Autore", 2000, "9788845242796", 10);

        assertThrows(LibroException.class, () -> gestore.modifica(libro1, nuovoLibro1),
                     "Deve lanciare LibroException se il libro da modificare non è attivo");
    }

    @Test
    //Lancia una IllegalArgumentException quando l'utente tenta di ridurre il numero totale di copie di un libro ad un valore minore del numero di copie che sono già state date in prestito
    public void modificaLanciaIllegalArgumentException() throws LibroException {

        gestore.add(libro1); 

        libro1.setCopieDisponibili(2); 

        Libro nuovoLibro1 = new Libro("Titolo", "Autore", 2000, "9788845242796", 2); 

        assertThrows(IllegalArgumentException.class, () -> gestore.modifica(libro1, nuovoLibro1),
                     "Deve lanciare IllegalArgumentException se le nuove copie totali sono minori di quelle in prestito");

        assertEquals(5, libro1.getCopieTotali());
        assertEquals(2, libro1.getCopieDisponibili());
    }

    @Test
    //Non fa nulla se Libro non viene trovato
    public void modificaLibroNonTrovato() throws LibroException {

        gestore.add(libro1);
        Libro libroNonInLista = new Libro("Outsider", "Autore", 2000, "9990001112223", 1);
        Libro nuovoLibro = new Libro("Nuovo Titolo", "Autore", 2000, "9990001112223", 10);
        

        assertDoesNotThrow(() -> gestore.modifica(libroNonInLista, nuovoLibro));


        assertEquals(1, gestore.getLista().size());
        assertEquals(libro1, gestore.getLista().get(0));
    }
    
//Test per il metodo getPredicato(String str)

    @Test
    //Filtra per Titolo
    public void getPredicatoTitolo() throws LibroException {
       
        gestore.add(libro1); 
        gestore.add(libro2); // 1984
        
        Predicate<Libro> predicato = gestore.getPredicato("Signore");

        assertTrue(predicato.test(libro1), "Dovrebbe trovare 'Il Signore degli Anelli'");
        assertFalse(predicato.test(libro2), "Non dovrebbe trovare '1984'");
    }

    @Test
    //Filtra per Autore
    public void getPredicatoAutore() throws LibroException {
        // Arrange
        gestore.add(libro1); 
        gestore.add(libro2); 
        
        Predicate<Libro> predicato = gestore.getPredicato("Orwell"); 

        assertFalse(predicato.test(libro1), "Non dovrebbe trovare 'Tolkien'");
        assertTrue(predicato.test(libro2), "Dovrebbe trovare 'Orwell'");
    }

    @Test
    //Filtra per ISBN
    public void getPredicatoISBN() throws LibroException {
     
        gestore.add(libro1); 
        gestore.add(libro2); 
        
        Predicate<Libro> predicato = gestore.getPredicato("4677701");
        
        assertFalse(predicato.test(libro1), "Non dovrebbe trovare ISBN di libro1");
        assertTrue(predicato.test(libro2), "Dovrebbe trovare ISBN di libro2");
    }

    @Test
    //Restituisce sempre vero se la stringa è vuota o null
    public void getPredicatoNull() throws LibroException {
       
        gestore.add(libro1);
        gestore.add(libro2);

        Predicate<Libro> predicatoVuoto = gestore.getPredicato("");
        Predicate<Libro> predicatoNull = gestore.getPredicato(null);

        assertTrue(predicatoVuoto.test(libro1));
        assertTrue(predicatoVuoto.test(libro2));
        assertTrue(predicatoNull.test(libro1));
        assertTrue(predicatoNull.test(libro2));
    }
}