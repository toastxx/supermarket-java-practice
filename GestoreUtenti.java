package esercizi.Progetto;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GestoreUtenti {
    private Map<String, Utenti> utenti;
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
            reader.lines()
                  .filter(line -> !line.trim().isEmpty())
                  .forEach(line -> {
                      Utenti utente = Utenti.fromFileString(line);
                      utenti.put(utente.getEmail(), utente);
                  });
        } catch (IOException e) {
        }
    }
    
    // Salva gli utenti nel file
    public void salvaUtenti() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_DATI))) {
            utenti.values().forEach(utente -> {
                try {
                    writer.write(utente.toFileString());
                    writer.newLine();
                } catch (IOException e) {
                }
            });
        } catch (IOException e) {
        }
    }
    
    // Registrazione nuovo utente
    public boolean registraUtente(String email, String nome, String cognome, String password, String indirizzo) {
        if (utenti.containsKey(email)) {
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
            return false;
        }
        
        Utenti utente = utenti.get(email);
        if (utente == null || !utente.getPassword().equals(password)) {
            return false;
        }
        
        utenteCorrente = utente;
        return true;
    }
    
    // Logout utente
    public boolean logout() {
        if (utenteCorrente == null) {
            return false;
        }
        
        utenteCorrente = null;
        return true;
    }
    
    // Aggiungi un ordine per l'utente corrente
    public boolean aggiungiOrdine(String prodotto, int quantita) {
        if (utenteCorrente == null) {
            return false;
        }
        
        utenteCorrente.aggiungiOrdine(prodotto, quantita);
        salvaUtenti();
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