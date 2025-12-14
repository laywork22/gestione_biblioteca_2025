package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.model.ArchivioDati;

import java.io.*;



/**
 * @brief Questa classe ha il solo e unico scopo di gestire l'I/O dell'archivio della
 * biblioteca. Utilizza la serializzazione binaria e garantisce la scrittura su unico file 
 * dell'intero archivio.
 */
public class GestoreArchivio {

    /**
     * @brief Si occupa di scrivere su un file (default biblioteca.bib) l'intero archivio.
     * 
     * @param archivio L'archivio di prestiti, libri e utenti a runtime.
     * @param file Il file su cui sarà serializzato l'archivio.
     * @throws IOException 
     */
    public void salvaArchivio(ArchivioDati archivio, File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            oos.writeObject(archivio);
        }
    }
    
    
    /**
     * @brief Si occupa di estrarre l'archivio dal file. 
     * 
     * @param file File da cui caricare l'archivio.
     * @return ArchivioDati caricato da file
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public ArchivioDati caricaArchivio(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            return (ArchivioDati) ois.readObject();
        }
    }
}
