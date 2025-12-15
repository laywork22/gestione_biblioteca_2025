package it.unisa.diem.softeng.librarymanager.model;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */


import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.managers.GestorePrestito;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.ArchivioDati;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Flytr
 */
public class ArchivioDatiTest {
    private ArchivioDati instance;
    
    @BeforeEach
    public void setUp() {
        GestorePrestito gp = new GestorePrestito();
        GestoreLibro gl = new GestoreLibro();
        GestoreUtente gu = new GestoreUtente();
        
        instance = new ArchivioDati(gp.getLista(), gu.getLista(), gl.getLista() );
    }


    @Test
    public void testGetListaPrestiti() {
        List<Prestito> l = instance.getListaPrestiti();
        
        assertNotNull(l);
    }


    @Test
    public void testGetListaLibri() {
        List<Libro> l = instance.getListaLibri();
        
        assertNotNull(l);
    }


    @Test
    public void testGetListaUtenti() {
        List<Utente> l = instance.getListaUtenti();
        
        assertNotNull(l);
    }
    
}
