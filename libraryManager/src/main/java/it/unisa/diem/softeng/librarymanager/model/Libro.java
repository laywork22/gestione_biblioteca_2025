package it.unisa.diem.softeng.librarymanager.model;

/**
 * @brief Rappresenta un libro all'interno del catalogo della biblioteca.
 *
 * Questa classe definisce il modello dati per un libro, includendo le informazioni
 * descrittive e lo stato delle copie.
 * Implementa le specifiche definite nel requisito **DF-1.1** (Dati Libro).
 *
 * @author Gruppo 12
 * @version 1.0
 */
public class Libro implements Comparable<Libro> {

    /**
     * @brief Il titolo del libro.
     * Utile per l'ordinamento (IF-1.4) e la ricerca (IF-1.5).
     */
    String titolo;

    /**
     * @brief L'autore o gli autori del libro.
     */
    String autore;

    /**
     * @brief L'anno di pubblicazione del libro.
     */
    int anno;

    /**
     * @brief Codice ISBN univoco del libro.
     * Identifica univocamente il libro nel sistema ed è usato per il controllo duplicati (IF-1.1).
     */
    String isbn;

    /**
     * @brief Numero totale di copie possedute dalla biblioteca.
     */
    int copieTotali;

    /**
     * @brief Numero di copie attualmente disponibili per il prestito.
     * Viene decrementato alla creazione di un prestito e incrementato alla restituzione.
     */
    int copieDisponibili;

    /**
     * @brief Costruttore della classe Libro.
     *
     * Inizializza un nuovo libro con i dati forniti.
     * Nota: Alla creazione, le copie disponibili sono impostate uguali alle copie totali.
     *
     * @param titolo Il titolo del libro.
     * @param autore L'autore o gli autori del libro.
     * @param anno L'anno di pubblicazione.
     * @param isbn Il codice ISBN univoco.
     * @param copieTotali Il numero totale di copie fisiche da inserire a catalogo.
     * @param copieDisponibili Il numero di copie fisiche prestabili
     */
    public Libro(String titolo, String autore, int anno, String isbn, int copieTotali, int copieDisponibili) {
        this.titolo = titolo;
        this.autore = autore;
        this.anno = anno;
        this.isbn = isbn;
        this.copieTotali = copieTotali;
        // All'inizio tutte le copie sono disponibili
        this.copieDisponibili = copieDisponibili;
    }

    /**
     * @brief Restituisce il titolo del libro.
     * @return Stringa contenente il titolo.
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * @brief Restituisce l'anno di pubblicazione.
     * @return Intero rappresentante l'anno.
     */
    public int getAnno() {
        return anno;
    }

    /**
     * @brief Restituisce l'autore del libro.
     * @return Stringa contenente l'autore o gli autori.
     */
    public String getAutore() {
        return autore;
    }

    /**
     * @brief Restituisce il codice ISBN.
     * @return Stringa contenente l'ISBN.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @brief Restituisce il numero totale di copie.
     * @return Intero rappresentante le copie totali.
     */
    public int getCopieTotali() {
        return copieTotali;
    }

    /**
     * @brief Restituisce il numero di copie attualmente disponibili.
     * @return Intero rappresentante le copie disponibili.
     */
    public int getCopieDisponibili() {
        return copieDisponibili;
    }

    /**
     * @brief Imposta o aggiorna il titolo del libro.
     * @param titolo Il nuovo titolo da impostare.
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * @brief Imposta o aggiorna l'autore del libro.
     * @param autore Il nuovo autore da impostare.
     */
    public void setAutore(String autore) {
        this.autore = autore;
    }

    /**
     * @brief Imposta o aggiorna il codice ISBN.
     * Attenzione: modificare l'ISBN potrebbe influenzare l'univocità del libro nel catalogo.
     * @param isbn Il nuovo ISBN.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @brief Imposta il numero totale di copie.
     * @param copieTotali Il nuovo numero totale di copie.
     */
    public void setCopieTotali(int copieTotali) {
        this.copieTotali = copieTotali;
    }

    /**
     * @brief Imposta manualmente il numero di copie disponibili.
     * Generalmente questo valore dovrebbe essere gestito dalla logica dei prestiti.
     * @param copieDisponibili Il nuovo numero di copie disponibili.
     */
    public void setCopieDisponibili(int copieDisponibili) {
        this.copieDisponibili = copieDisponibili;
    }

    /**
     * @brief Incrementa le copie totali di un libro
     * @return Incrementa le copie totali di un libro di uno.
     */
    public void incrementaCopie() {
        this.copieTotali ++;
    }

    /**
     * @brief Decrementa le copie disponibili di un libro
     * @return Decrementa le copie disponibili di un libro di uno
     * */
    public void decrementaCopie() {
        if(copieDisponibili > 0) {
            this.copieDisponibili--;
        }
    }
    /**
     * @brief Restituisce una rappresentazione in formato stringa dell'oggetto Libro.
     * @return Stringa formattata con i dettagli del libro.
     */
    @Override
    public String toString() {
        return "Titolo: " + titolo + "\nAutore: " + autore + "\nISBN: " + isbn +
                "\nCopie Totali: " + copieTotali + "\nCopie Disponibili: " + copieDisponibili + "\n";
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) return false;

        if (this == o) return true;

        if (this.getClass() != o.getClass()) return false;

        Libro l = (Libro) o;

        return this.isbn.equals(l.isbn);
    }

    @Override
    public int compareTo(Libro o) {
        return this.isbn.compareToIgnoreCase(o.isbn);
    }
}