package esercizi.Progetto;

import java.util.Scanner;

public class Main {
    private static GestoreUtenti gestoreUtenti;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        gestoreUtenti = new GestoreUtenti();
        scanner = new Scanner(System.in);
        
        boolean esci = false;
        
        while (!esci) {
            mostraMenu();
            int scelta = leggiIntero("Seleziona un'opzione: ");
            
            switch (scelta) {
                case 1:
                    registraUtente();
                    break;
                case 2:
                    effettuaLogin();
                    break;
                case 3:
                    effettuaLogout();
                    break;
                case 4:
                    aggiungiOrdine();
                    break;
                case 5:
                    visualizzaProfiloUtente();
                    break;
                case 6:
                    gestioneAdmin();
                    break;
                case 7:
                    esci = true;
                    System.out.println("Arrivederci!");
                    break;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        }
        scanner.close();
    }
    
    private static void mostraMenu() {
        System.out.println("\n===== SISTEMA DI GESTIONE UTENTI =====");
        
        // Verifica se c'è un utente loggato
        if (gestoreUtenti.getUtenteCorrente() == null) {
            // Menù per utenti non loggati
            System.out.println("1. Registra nuovo utente");
            System.out.println("2. Login");
            System.out.println("7. Esci");
        } else if (gestoreUtenti.getUtenteCorrente().getEmail().equals("admin@gmail.com")) {
            // Menù per admin
            System.out.println("5. Visualizza profilo utente");
            System.out.println("6. Gestione Admin");
            System.out.println("7. Esci");
        } else {
            // Menù per utenti normali loggati
            System.out.println("3. Logout");
            System.out.println("4. Aggiungi ordine");
            System.out.println("5. Visualizza profilo utente");
            System.out.println("7. Esci");
        }
        
        System.out.println("=====================================");
    }
    
    private static void registraUtente() {
        System.out.println("\n----- REGISTRAZIONE UTENTE -----");
        String email = "";
        boolean emailValida = false;
        
        while (!emailValida) {
            email = leggiStringa("Email: ");
            if (email.contains("@")) {
                emailValida = true;
            } else {
                System.out.println("Errore: Inserire un'email valida.");
            }
        }
        
        String nome = leggiStringa("Nome: ");
        String cognome = leggiStringa("Cognome: ");
        String password = leggiStringa("Password: ");
        String indirizzo = leggiStringa("Indirizzo: ");
        
        if (gestoreUtenti.registraUtente(email, nome, cognome, password, indirizzo)) {
            System.out.println("Utente registrato con successo!");
        }
    }
    
    private static void effettuaLogin() {
        System.out.println("\n----- LOGIN -----");
        String email = leggiStringa("Email: ");
        String password = leggiStringa("Password: ");
        
        gestoreUtenti.login(email, password);
    }
    
    private static void effettuaLogout() {
        gestoreUtenti.logout();
    }
    
    private static void aggiungiOrdine() {
        if (gestoreUtenti.getUtenteCorrente() == null) {
            System.out.println("Devi effettuare il login prima di aggiungere un ordine.");
            return;
        }
        
        System.out.println("\n----- AGGIUNGI ORDINE -----");
        
        // Visualizza la lista dei prodotti disponibili
        Prodotti.visualizzaProdotti();
        
        // Chiedi all'utente di selezionare un prodotto
        int sceltaProdotto = leggiIntero("Seleziona un prodotto (1-" + Prodotti.getListaProdotti().size() + "): ");
        
        // Verifica che la scelta sia valida
        if (sceltaProdotto < 1 || sceltaProdotto > Prodotti.getListaProdotti().size()) {
            System.out.println("Selezione non valida.");
            return;
        }
        
        // Ottieni il prodotto selezionato
        Prodotto prodottoSelezionato = Prodotti.getProdotto(sceltaProdotto - 1);
        
        // Chiedi la quantità
        int quantita = leggiIntero("Quantità: ");
        
        if (quantita <= 0) {
            System.out.println("La quantità deve essere maggiore di zero.");
            return;
        }
        
        // Aggiungi l'ordine
        gestoreUtenti.aggiungiOrdine(prodottoSelezionato.getNome(), quantita);
        System.out.println("Hai ordinato: " + quantita + " x " + prodottoSelezionato.getNome() + 
                           " (Prezzo unitario: " + String.format("%.2f euro", prodottoSelezionato.getPrezzo()) + ")");
    }
    
    private static void visualizzaProfiloUtente() {
        if (gestoreUtenti.getUtenteCorrente() == null) {
            System.out.println("Devi effettuare il login per visualizzare il profilo.");
            return;
        }
        
        System.out.println("\n----- PROFILO UTENTE -----");
        System.out.println(gestoreUtenti.getUtenteCorrente());
    }
    
    private static void gestioneAdmin() {
        if (gestoreUtenti.getUtenteCorrente() == null || 
            !gestoreUtenti.getUtenteCorrente().getEmail().equals("admin@gmail.com")) {
            System.out.println("Accesso negato. Solo l'amministratore può accedere a questa sezione.");
            return;
        }
        
        boolean esciAdmin = false;
        
        while (!esciAdmin) {
            System.out.println("\n===== PANNELLO AMMINISTRATORE =====");
            System.out.println("1. Visualizza tutti gli utenti");
            System.out.println("2. Rimuovi utente");
            System.out.println("3. Torna al menu principale");
            System.out.println("===================================");
            
            int scelta = leggiIntero("Seleziona un'opzione: ");
            
            switch (scelta) {
                case 1:
                    visualizzaTuttiUtenti();
                    break;
                case 2:
                    rimuoviUtente();
                    break;
                case 3:
                    esciAdmin = true;
                    break;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        }
    }
    
    private static void visualizzaTuttiUtenti() {
        System.out.println("\n----- ELENCO UTENTI -----");
        for (Utenti utente : gestoreUtenti.getUtenti().values()) {
            if (!utente.getEmail().equals("admin@gmail.com")) {
                System.out.println("Email: " + utente.getEmail());
                System.out.println("Nome: " + utente.getNome() + " " + utente.getCognome());
                System.out.println("------------------------");
            }
        }
    }
    
    private static void rimuoviUtente() {
        System.out.println("\n----- RIMOZIONE UTENTE -----");
        String email = leggiStringa("Inserisci l'email dell'utente da rimuovere: ");
        
        if (email.equals("admin@gmail.com")) {
            System.out.println("Non è possibile rimuovere l'account amministratore.");
            return;
        }
        
        if (gestoreUtenti.rimuoviUtente(email)) {
            System.out.println("Utente rimosso con successo.");
        } else {
            System.out.println("Utente non trovato.");
        }
    }
    
    private static String leggiStringa(String messaggio) {
        System.out.print(messaggio);
        return scanner.nextLine();
    }
    
    private static int leggiIntero(String messaggio) {
        while (true) {
            try {
                System.out.print(messaggio);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
            }
        }
    }
}