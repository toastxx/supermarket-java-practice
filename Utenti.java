package esercizi.Progetto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Utenti implements Serializable {
    private String email; // ID univoco
    private String nome;
    private String cognome;
    private String password;
    private Map<String, Integer> ordini; // Prodotto -> Numero di ordini
    private String indirizzo;
    
    // Costruttore
    public Utenti(String email, String nome, String cognome, String password, String indirizzo) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.indirizzo = indirizzo;
        this.ordini = new HashMap<>();
    }
    
    // Getter e setter
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCognome() {
        return cognome;
    }
    
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Map<String, Integer> getOrdini() {
        return ordini;
    }
    
    public void aggiungiOrdine(String prodotto, int quantita) {
        if (ordini.containsKey(prodotto)) {
            ordini.put(prodotto, ordini.get(prodotto) + quantita);
        } else {
            ordini.put(prodotto, quantita);
        }
    }
    
    public String getIndirizzo() {
        return indirizzo;
    }
    
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Email: ").append(email).append("\n");
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Cognome: ").append(cognome).append("\n");
        sb.append("Indirizzo: ").append(indirizzo).append("\n");
        sb.append("Ordini: \n");
        
        for (Map.Entry<String, Integer> entry : ordini.entrySet()) {
            sb.append("  - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        
        return sb.toString();
    }
    
    // Metodo per convertire l'utente in una stringa per il salvataggio su file
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(email).append(";");
        sb.append(nome).append(";");
        sb.append(cognome).append(";");
        sb.append(password).append(";");
        sb.append(indirizzo).append(";");
        
        // Salva gli ordini nel formato: prodotto1:quantità1,prodotto2:quantità2,...
        if (!ordini.isEmpty()) {
            boolean first = true;
            for (Map.Entry<String, Integer> entry : ordini.entrySet()) {
                if (!first) {
                    sb.append(",");
                }
                sb.append(entry.getKey()).append(":").append(entry.getValue());
                first = false;
            }
        }
        
        return sb.toString();
    }
    
    // Metodo statico per creare un utente da una stringa letta dal file
    public static Utenti fromFileString(String line) {
        String[] parts = line.split(";");
        if (parts.length < 5) {
            throw new IllegalArgumentException("Formato dati utente non valido");
        }
        
        String email = parts[0];
        String nome = parts[1];
        String cognome = parts[2];
        String password = parts[3];
        String indirizzo = parts[4];
        
        Utenti utente = new Utenti(email, nome, cognome, password, indirizzo);
        
        // Carica gli ordini se presenti
        if (parts.length > 5 && !parts[5].isEmpty()) {
            String[] ordiniArray = parts[5].split(",");
            for (String ordine : ordiniArray) {
                String[] ordineInfo = ordine.split(":");
                if (ordineInfo.length == 2) {
                    String prodotto = ordineInfo[0];
                    int quantita = Integer.parseInt(ordineInfo[1]);
                    utente.aggiungiOrdine(prodotto, quantita);
                }
            }
        }
        return utente;
    }
}