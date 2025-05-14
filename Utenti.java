package esercizi.Progetto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Utenti implements Serializable {
    private String email;
    private String nome;
    private String cognome;
    private String password;
    private Map<String, Integer> ordini;
    private String indirizzo;
    
    public Utenti(String email, String nome, String cognome, String password, String indirizzo) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.indirizzo = indirizzo;
        this.ordini = new HashMap<>();
    }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Map<String, Integer> getOrdini() { return ordini; }
    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }
    
    public void aggiungiOrdine(String prodotto, int quantita) {
        ordini.put(prodotto, ordini.getOrDefault(prodotto, 0) + quantita);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Email: ").append(email).append("\n")
          .append("Nome: ").append(nome).append("\n")
          .append("Cognome: ").append(cognome).append("\n")
          .append("Indirizzo: ").append(indirizzo).append("\n")
          .append("Ordini: \n");
        
        ordini.forEach((prodotto, quantita) -> 
            sb.append("  - ").append(prodotto).append(": ").append(quantita).append("\n"));
        
        return sb.toString();
    }
    
    public String toFileString() {
        StringBuilder sb = new StringBuilder(email).append(";")
            .append(nome).append(";")
            .append(cognome).append(";")
            .append(password).append(";")
            .append(indirizzo).append(";");
        
        if (!ordini.isEmpty()) {
            String ordiniStr = ordini.entrySet().stream()
                .map(e -> e.getKey() + ":" + e.getValue())
                .collect(Collectors.joining(","));
            sb.append(ordiniStr);
        }
        
        return sb.toString();
    }
    
    public static Utenti fromFileString(String line) {
        String[] parts = line.split(";");
        if (parts.length < 5) throw new IllegalArgumentException("Formato dati utente non valido");
        
        Utenti utente = new Utenti(parts[0], parts[1], parts[2], parts[3], parts[4]);
        
        if (parts.length > 5 && !parts[5].isEmpty()) {
            for (String ordine : parts[5].split(",")) {
                String[] ordineInfo = ordine.split(":");
                if (ordineInfo.length == 2)
                    utente.aggiungiOrdine(ordineInfo[0], Integer.parseInt(ordineInfo[1]));
            }
        }
        return utente;
    }
}