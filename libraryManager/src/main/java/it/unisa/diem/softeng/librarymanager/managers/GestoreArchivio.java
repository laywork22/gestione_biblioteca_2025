package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.model.ArchivioDati;

import java.io.*;

public class GestoreArchivio {

    public void salvaArchivio(ArchivioDati archivio, File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            oos.writeObject(archivio);
        }
    }

    public ArchivioDati caricaArchivio(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            return (ArchivioDati) ois.readObject();
        }
    }
}
