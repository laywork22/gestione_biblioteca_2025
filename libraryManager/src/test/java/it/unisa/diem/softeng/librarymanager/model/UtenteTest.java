/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package it.unisa.diem.softeng.librarymanager.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author letiz
 */
public class UtenteTest {
    private Utente instance;
 
    
    @BeforeEach
    public void setUp() {
        instance = new Utente("Michele", "Giamberta", "01325", "michele.giamberta@email.it");
    }

    /**
     * Test of getMatricola method, of class Utente.
     */
    @Test
    public void testGetMatricola() {
        assertEquals("01325", instance.getMatricola());

    }

    /**
     * Test of getCognome method, of class Utente.
     */
    @Test
    public void testGetCognome() {
        assertEquals("Giamberta", instance.getCognome());
    }

    /**
     * Test of getEmail method, of class Utente.
     */
    @Test
    public void testGetEmail() {
        assertEquals("michele.giamberta@email.it", instance.getEmail());
    }

    /**
     * Test of getNome method, of class Utente.
     */
    @Test
    public void testGetNome() {
        assertEquals("Michele", instance.getNome());       
    }

    /**
     * Test of setNome method, of class Utente.
     */
    @Test
    public void testSetNome() {
        instance.setNome("Luigi");
        assertEquals("Luigi", instance.getNome());
    }

    /**
     * Test of setCognome method, of class Utente.
     */
    @Test
    public void testSetCognome() {
        instance.setCognome("Rossi");
        assertEquals("Rossi", instance.getCognome());
    }

    /**
     * Test of setMatricola method, of class Utente.
     */
    @Test
    public void testSetMatricola() {
        instance.setMatricola("99999");
        assertEquals("99999", instance.getMatricola());
    }

    /**
     * Test of setEmail method, of class Utente.
     */
    @Test
    public void testSetEmail() {
        instance.setEmail("test@email.it");
        assertEquals("test@email.it", instance.getEmail());
    }

    /**
     * Test of getCountPrestiti method, of class Utente.
     */
    @Test
    public void testGetCountPrestiti() {
        assertEquals(0, instance.getCountPrestiti());

    }

    /**
     * Test of setCountPrestiti method, of class Utente.
     */
    @Test
    public void testSetCountPrestiti() {
        instance.setCountPrestiti(3);
        assertEquals(3, instance.getCountPrestiti());
    }

    /**
     * Test of isAttivo method, of class Utente.
     */
    @Test
    public void testIsAttivo() {
        assertTrue(instance.isAttivo());
    }

    /**
     * Test of setAttivo method, of class Utente.
     */
    @Test
    public void testSetAttivo() {
        instance.setAttivo(false);
        assertFalse(instance.isAttivo());
    }

    /**
     * Test of toString method, of class Utente.
     */
    @Test
    public void testToString() {
        String result = instance.toString();
        assertNotNull(result);
        assertTrue(result.contains("Michele"));
    }

    /**
     * Test of equals method, of class Utente.
     */
    @Test
    public void testEquals() {
        Utente u2 = new Utente("Michele", "Giamberta", "01325", "michele.giamberta@email.it");
        assertEquals(instance, u2);
    }

    /**
     * Test of compareTo method, of class Utente.
     */
    @Test
    public void testCompareTo() {
        Utente u2 = new Utente("Anna", "Bianchi", "00001", "anna@email.it");
        assertNotEquals(0, instance.compareTo(u2));
    }
    
}
