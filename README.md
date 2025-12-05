Library Manager - Documentazione Tecnica

Introduzione

Documentazione tecnica del progetto **Library Manager**.

Questo software è un'applicazione desktop sviluppata in **Java** con interfaccia grafica **JavaFX**. Il sistema è progettato per automatizzare e semplificare le operazioni quotidiane di una biblioteca, permettendo la gestione efficiente del catalogo libri, dell’anagrafica utenti e del monitoraggio dei prestiti.

features_sec Funzionalità Principali

Il sistema è suddiviso in tre macro-aree funzionali:

### Gestione Libri
- Inserimento di nuovi volumi con dettagli (Titolo, Autore, Anno, ISBN)
- Monitoraggio delle copie totali e disponibili
- Ricerca e filtraggio del catalogo

### Gestione Utenti
- Registrazione di nuovi utenti (studenti/docenti)
- Gestione dei dati anagrafici (Matricola, Email)

### Gestione Prestiti
- Creazione di nuovi prestiti associando un Utente a un Libro
- Controllo delle scadenze e dello stato del prestito

Architettura del Sistema

Il progetto segue il pattern architetturale **MVC (Model-View-Controller)**:

- **Controllers** (`it.unisa.diem.softeng.librarymanager.controllers`)
  Gestiscono l'interazione tra le view FXML e i dati.
  Il `PrincipaleController` funge da orchestratore, delegando ai vari sotto-controller (`LibroController`, `PrestitoController`, ecc.).

- **Forms** (`it.unisa.diem.softeng.librarymanager.controllers.forms`)
  Controller dedicati alle finestre di dialogo (popup).

- **Managers** (`it.unisa.diem.softeng.librarymanager.managers`)
  Contengono la logica di business e lo stato dell'applicazione.

- **Model** (`it.unisa.diem.softeng.librarymanager.model`)
  Classi POJO che rappresentano le entità del dominio (`Libro`, `Utente`, `Prestito`).

Requisiti e Installazione

- **Java:** JDK 17 o superiore
- **JavaFX:** SDK configurato o via Maven
- **Build Tool:** Maven

Per compilare ed eseguire il progetto:

<pre> mvn clean javafx:run </pre>



authors_sec Autori

- Emiddio Ferrentino
- Rosa Genovese
- Letizia Argenio
- Antonio D'Urso