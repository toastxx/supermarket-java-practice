package esercizi.Progetto;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Prodotti {
    private static List<Prodotto> listaProdotti = new ArrayList<>(Arrays.asList(
        new Prodotto("Pane", 1.99),
        new Prodotto("Acqua", 0.99),
        new Prodotto("Latte", 1.49),
        new Prodotto("Pomodori", 2.49),
        new Prodotto("Pasta", 0.89),
        new Prodotto("Formaggio", 3.99),
        new Prodotto("Mele", 1.79),
        new Prodotto("Biscotti", 2.29),
        new Prodotto("Caffè", 4.99),
        new Prodotto("Zucchero", 1.19)
    ));
    
    public static List<Prodotto> getListaProdotti() { return listaProdotti; }
    public static Prodotto getProdotto(int indice) {
        return (indice >= 0 && indice < listaProdotti.size()) ? listaProdotti.get(indice) : null;
    }
}

class Prodotto {
    private String nome;
    private double prezzo;
    
    public Prodotto(String nome, double prezzo) { this.nome = nome; this.prezzo = prezzo; }
    public String getNome() { return nome; }
    public double getPrezzo() { return prezzo; }
    
    @Override
    public String toString() { return nome + " - " + String.format("%.2f euro", prezzo); }
}