package it.unisa.diem.softeng.librarymanager.model;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Proprietario
 */
public class LibroTest {
    private Libro libro;
    private final String titolo = "I Promessi Sposi";
    private final String autore = "Alessandro Manzoni";
    private final int anno = 1840;
    private final String isbn = "978-8804668283";
    private final int copieTotali = 10;
    
    @BeforeEach
    public void setUp() {
        // Inizializza il libro "I Promessi Sposi"
        libro = new Libro(titolo, autore, anno, isbn, copieTotali);
    }
    
//Test sul costruttore
@Test 
public void testCostruttore() {
    assertEquals(titolo, libro.getTitolo(), "Il titolo non corrisponde");
    assertEquals(isbn, libro.getIsbn(), "L'ISBN non corrisponde");
    assertEquals(copieTotali, libro.getCopieTotali(), "Le copie totali non corrispondono");
    assertEquals(copieTotali, libro.getCopieDisponibili(), "Le copie disponibili devono essere uguali alle copie totali all'inizializzazione");     
    assertTrue(libro.isAttivo(), "Il libro deve essere disponibile di default dopo l'inserimento");
    }
    
//Test su decrementaCopie()
@Test
    public void testDecrementaCopie() {
        //decremento di 1 del numero di copie dopo un prestito (il prestito viene effettuato)
        int disponibili = copieTotali - 1; 
        
        libro.decrementaCopie(); 
     
        assertEquals(disponibili, libro.getCopieDisponibili(), "Dopo un prestito, le copie disponibili devono diminuire di 1");
    
        //il numero di copie non deve scendere al di sotto di 0
        libro.setCopieDisponibili(0);
        
        libro.decrementaCopie(); 
        
        assertEquals(0, libro.getCopieDisponibili(), "La funzione deve bloccare il conteggio a zero");
    
    }
     
    
//Test su incrementaCopie()   
@Test
    public void testIncrementaCopie(){
    //aggiornamento copie totali (aggiunta copie)
    int i = 1;   //i=numero di copie restituite
    
    int totaliAttese = copieTotali + i;
    
    libro.incrementaCopie(i);
    
    assertEquals(totaliAttese, libro.getCopieTotali(), "Le copie totali non sono state incrementate correttamente");
    
    assertEquals(copieTotali, libro.getCopieDisponibili(), "Le copie disponibili non devono cambiare");
    }

//Test su equals()
@Test
    public void testEquals() {
        //se l'ISBN è lo stesso, allora i due libri sono uguali
        Libro libroTestUno = new Libro("Narciso e Boccadoro", "Hermann Hesse", 1930, isbn, 1);
        
        assertTrue(libro.equals(libroTestUno), "Due oggetti Libro con lo stesso ISBN devono essere considerati uguali");
        
        //se l'ISBN è diverso, allora i due libri sono diversi
        Libro libroTestDue = new Libro(titolo, autore, anno, "929-9998919979", copieTotali);
        
        assertFalse(libro.equals(libroTestDue), "Due oggetti Libro con ISBN differenti devono essere considerati diversi");
    }
    
    
//test su compareTo()
@Test
    public void testCompareTo() {
        //libro con ISBN minore viene prima
        Libro libroPrecedente = new Libro(titolo, autore, anno, "978-8804668282", copieTotali); 
 
        int ris_1 = libroPrecedente.compareTo(libro);
    
        assertTrue(ris_1 < 0, "Un libro con ISBN minore deve risultare precedente nell'ordinamento");
        
        //i libri con lo stesso ISBN devono risultare uguali nell'ordinamento
        Libro libroCopia = new Libro("XXX", autore, anno, isbn, copieTotali);

        int ris_2 = libro.compareTo(libroCopia);

        assertEquals(0, ris_2, "Libri con lo stesso ISBN devono risultare uguali nell'ordinamento");
    }
    
 }
    
    
 
    
    

