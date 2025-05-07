package esercizi.Progetto;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GestoreUtenti {
    private Map<String, Utenti> utenti; // Email -> Utente
    private Utenti utenteCorrente;
    private final String FILE_DATI = "src/esercizi/Progetto/dati.txt";
    
    public GestoreUtenti() {
        utenti = new HashMap<>();
        utenteCorrente = null;
        caricaUtenti();
    }
    
    // Carica gli utenti dal file
    private void caricaUtenti() {
        File file = new File(FILE_DATI);
        
        // Se il file non esiste, crea la directory se necessario
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Utenti utente = Utenti.fromFileString(line);
                    utenti.put(utente.getEmail(), utente);
                }
            }
            System.out.println("Dati utenti caricati con successo.");
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento dei dati: " + e.getMessage());
        }
    }
    
    // Salva gli utenti nel file
    public void salvaUtenti() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_DATI))) {
            for (Utenti utente : utenti.values()) {
                writer.write(utente.toFileString());
                writer.newLine();
            }
            System.out.println("Dati utenti salvati con successo.");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio dei dati: " + e.getMessage());
        }
    }
    
    // Registra un nuovo utente
    public boolean registraUtente(String email, String nome, String cognome, String password, String indirizzo) {
        if (utenti.containsKey(email)) {
            System.out.println("Utente con questa email già registrato.");
            return false;
        }
        
        Utenti nuovoUtente = new Utenti(email, nome, cognome, password, indirizzo);
        utenti.put(email, nuovoUtente);
        salvaUtenti();
        return true;
    }
    
    // Login utente
    public boolean login(String email, String password) {
        if (utenteCorrente != null) {
            System.out.println("C'è già un utente connesso. Effettua prima il logout.");
            return false;
        }
        
        Utenti utente = utenti.get(email);
        if (utente == null) {
            System.out.println("Utente non trovato.");
            return false;
        }
        
        if (!utente.getPassword().equals(password)) {
            System.out.println("Password non corretta.");
            return false;
        }
        
        utenteCorrente = utente;
        System.out.println("Login effettuato con successo. Benvenuto, " + utente.getNome() + "!");
        return true;
    }
    
    // Logout utente
    public boolean logout() {
        if (utenteCorrente == null) {
            System.out.println("Nessun utente connesso.");
            return false;
        }
        
        System.out.println("Logout effettuato con successo. Arrivederci, " + utenteCorrente.getNome() + "!");
        utenteCorrente = null;
        return true;
    }
    
    // Aggiungi un ordine per l'utente corrente
    public boolean aggiungiOrdine(String prodotto, int quantita) {
        if (utenteCorrente == null) {
            System.out.println("Nessun utente connesso. Effettua prima il login.");
            return false;
        }
        
        utenteCorrente.aggiungiOrdine(prodotto, quantita);
        salvaUtenti();
        System.out.println("Ordine aggiunto con successo.");
        return true;
    }
    
    // Ottieni l'utente corrente
    public Utenti getUtenteCorrente() {
        return utenteCorrente;
    }
    
    // Ottieni tutti gli utenti
    public Map<String, Utenti> getUtenti() {
        return utenti;
    }

    // Rimuovi un utente
    public boolean rimuoviUtente(String email) {
        if (!utenti.containsKey(email)) {
            return false;
        }
        
        utenti.remove(email);
        salvaUtenti();
        return true;
    }
}