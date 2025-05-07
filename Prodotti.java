package esercizi.Progetto;

import java.util.ArrayList;
import java.util.List;

public class Prodotti {
    private static List<Prodotto> listaProdotti = new ArrayList<>();
    
    // Inizializzazione statica della lista prodotti
    static {
        listaProdotti.add(new Prodotto("Pane", 1.99));
        listaProdotti.add(new Prodotto("Acqua", 0.99));
        listaProdotti.add(new Prodotto("Latte", 1.49));
        listaProdotti.add(new Prodotto("Pomodori", 2.49));
        listaProdotti.add(new Prodotto("Pasta", 0.89));
        listaProdotti.add(new Prodotto("Formaggio", 3.99));
        listaProdotti.add(new Prodotto("Mele", 1.79));
        listaProdotti.add(new Prodotto("Biscotti", 2.29));
        listaProdotti.add(new Prodotto("Caffè", 4.99));
        listaProdotti.add(new Prodotto("Zucchero", 1.19));
    }
    
    // Metodo per ottenere la lista dei prodotti
    public static List<Prodotto> getListaProdotti() {
        return listaProdotti;
    }
    
    // Metodo per visualizzare tutti i prodotti disponibili
    public static void visualizzaProdotti() {
        System.out.println("\n----- PRODOTTI DISPONIBILI -----");
        for (int i = 0; i < listaProdotti.size(); i++) {
            Prodotto prodotto = listaProdotti.get(i);
            System.out.printf("%d. %s - %.2f euro\n", (i + 1), prodotto.getNome(), prodotto.getPrezzo());
        }
        System.out.println("-------------------------------");
    }
    
    // Metodo per ottenere un prodotto tramite indice
    public static Prodotto getProdotto(int indice) {
        if (indice >= 0 && indice < listaProdotti.size()) {
            return listaProdotti.get(indice);
        }
        return null;
    }
}

// Classe per rappresentare un singolo prodotto
class Prodotto {
    private String nome;
    private double prezzo;
    
    public Prodotto(String nome, double prezzo) {
        this.nome = nome;
        this.prezzo = prezzo;
    }
    
    public String getNome() {
        return nome;
    }
    
    public double getPrezzo() {
        return prezzo;
    }
    
    @Override
    public String toString() {
        return nome + " - " + String.format("%.2f euro", prezzo);
    }
}