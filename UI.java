package esercizi.Progetto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe UI che implementa un'interfaccia grafica per il sistema di gestione utenti
 */
public class UI extends JFrame {
    private GestoreUtenti gestoreUtenti;
    private JPanel mainPanel;
    private JPanel loginPanel;
    private JPanel userPanel;
    private JPanel adminPanel;
    
    // Componenti per il login
    private JTextField emailField;
    private JPasswordField passwordField;
    
    // Componenti per la registrazione
    private JTextField regEmailField;
    private JTextField regNomeField;
    private JTextField regCognomeField;
    private JPasswordField regPasswordField;
    private JTextField regIndirizzoField;
    
    /**
     * Costruttore della classe UI
     */
    public UI() {
        gestoreUtenti = new GestoreUtenti();
        
        // Configurazione della finestra principale
        setTitle("Sistema di Gestione Utenti");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Inizializzazione dei pannelli
        initPanels();
        
        // Mostra il pannello di login all'avvio
        showLoginPanel();
        
        setVisible(true);
    }
    
    /**
     * Inizializza tutti i pannelli dell'interfaccia
     */
    private void initPanels() {
        // Pannello principale con CardLayout per gestire le diverse schermate
        mainPanel = new JPanel(new CardLayout());
        add(mainPanel);
        
        // Inizializza i vari pannelli
        initLoginPanel();
        initUserPanel();
        initAdminPanel();
        
        // Aggiungi i pannelli al pannello principale
        mainPanel.add(loginPanel, "login");
        mainPanel.add(userPanel, "user");
        mainPanel.add(adminPanel, "admin");
    }
    
    /**
     * Inizializza il pannello di login e registrazione
     */
    private void initLoginPanel() {
        loginPanel = new JPanel(new BorderLayout());
        
        // Pannello per il login
        JPanel loginFormPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        loginFormPanel.setBorder(BorderFactory.createTitledBorder("Login"));
        
        loginFormPanel.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        loginFormPanel.add(emailField);
        
        loginFormPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        loginFormPanel.add(passwordField);
        
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                effettuaLogin();
            }
        });
        loginFormPanel.add(loginButton);
        
        // Pannello per la registrazione
        JPanel registrationPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        registrationPanel.setBorder(BorderFactory.createTitledBorder("Registrazione"));
        
        registrationPanel.add(new JLabel("Email:"));
        regEmailField = new JTextField(20);
        registrationPanel.add(regEmailField);
        
        registrationPanel.add(new JLabel("Nome:"));
        regNomeField = new JTextField(20);
        registrationPanel.add(regNomeField);
        
        registrationPanel.add(new JLabel("Cognome:"));
        regCognomeField = new JTextField(20);
        registrationPanel.add(regCognomeField);
        
        registrationPanel.add(new JLabel("Password:"));
        regPasswordField = new JPasswordField(20);
        registrationPanel.add(regPasswordField);
        
        registrationPanel.add(new JLabel("Indirizzo:"));
        regIndirizzoField = new JTextField(20);
        registrationPanel.add(regIndirizzoField);
        
        JButton registerButton = new JButton("Registrati");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registraUtente();
            }
        });
        registrationPanel.add(registerButton);
        
        // Aggiungi i pannelli al pannello di login
        JPanel formsContainer = new JPanel(new GridLayout(2, 1, 10, 10));
        formsContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formsContainer.add(loginFormPanel);
        formsContainer.add(registrationPanel);
        
        loginPanel.add(formsContainer, BorderLayout.CENTER);
    }
    
    /**
     * Inizializza il pannello utente
     */
    private void initUserPanel() {
        userPanel = new JPanel(new BorderLayout());
        
        JPanel userInfoPanel = new JPanel(new BorderLayout());
        userInfoPanel.setBorder(BorderFactory.createTitledBorder("Informazioni Utente"));
        
        JTextArea userInfoArea = new JTextArea(10, 30);
        userInfoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(userInfoArea);
        userInfoPanel.add(scrollPane, BorderLayout.CENTER);
        
        JButton refreshButton = new JButton("Aggiorna Profilo");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userInfoArea.setText(gestoreUtenti.getUtenteCorrente().toString());
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gestoreUtenti.logout();
                showLoginPanel();
            }
        });
        
        JButton addOrderButton = new JButton("Aggiungi Ordine");
        addOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostraDialogOrdine();
            }
        });
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(addOrderButton);
        buttonPanel.add(logoutButton);
        
        userPanel.add(userInfoPanel, BorderLayout.CENTER);
        userPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Inizializza il pannello admin
     */
    private void initAdminPanel() {
        adminPanel = new JPanel(new BorderLayout());
        
        JPanel userListPanel = new JPanel(new BorderLayout());
        userListPanel.setBorder(BorderFactory.createTitledBorder("Lista Utenti"));
        
        JTextArea userListArea = new JTextArea(15, 40);
        userListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(userListArea);
        userListPanel.add(scrollPane, BorderLayout.CENTER);
        
        JButton refreshButton = new JButton("Aggiorna Lista");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aggiornaListaUtenti(userListArea);
            }
        });
        
        JPanel removeUserPanel = new JPanel(new FlowLayout());
        removeUserPanel.add(new JLabel("Email utente da rimuovere:"));
        JTextField removeEmailField = new JTextField(20);
        removeUserPanel.add(removeEmailField);
        
        JButton removeButton = new JButton("Rimuovi Utente");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = removeEmailField.getText();
                if (email.equals("admin@gmail.com")) {
                    JOptionPane.showMessageDialog(adminPanel, 
                        "Non è possibile rimuovere l'account amministratore.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (gestoreUtenti.rimuoviUtente(email)) {
                    JOptionPane.showMessageDialog(adminPanel, 
                        "Utente rimosso con successo.", 
                        "Successo", JOptionPane.INFORMATION_MESSAGE);
                    aggiornaListaUtenti(userListArea);
                } else {
                    JOptionPane.showMessageDialog(adminPanel, 
                        "Utente non trovato.", 
                        "Errore", JOptionPane.ERROR_MESSAGE);
                }
                removeEmailField.setText("");
            }
        });
        removeUserPanel.add(removeButton);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gestoreUtenti.logout();
                showLoginPanel();
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(refreshButton);
        buttonPanel.add(logoutButton);
        
        adminPanel.add(userListPanel, BorderLayout.CENTER);
        adminPanel.add(removeUserPanel, BorderLayout.NORTH);
        adminPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Aggiorna la lista degli utenti nel pannello admin
     */
    private void aggiornaListaUtenti(JTextArea userListArea) {
        StringBuilder sb = new StringBuilder();
        for (Utenti utente : gestoreUtenti.getUtenti().values()) {
            if (!utente.getEmail().equals("admin@gmail.com")) {
                sb.append("Email: ").append(utente.getEmail()).append("\n");
                sb.append("Nome: ").append(utente.getNome()).append(" ").append(utente.getCognome()).append("\n");
                sb.append("------------------------\n");
            }
        }
        userListArea.setText(sb.toString());
    }
    
    /**
     * Mostra il pannello di login
     */
    private void showLoginPanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "login");
    }
    
    /**
     * Mostra il pannello utente
     */
    private void showUserPanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "user");
        
        // Aggiorna le informazioni dell'utente
        JTextArea userInfoArea = (JTextArea) ((JScrollPane) ((JPanel) userPanel.getComponent(0)).getComponent(0)).getViewport().getView();
        userInfoArea.setText(gestoreUtenti.getUtenteCorrente().toString());
    }
    
    /**
     * Mostra il pannello admin
     */
    private void showAdminPanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "admin");
        
        // Aggiorna la lista degli utenti
        JTextArea userListArea = (JTextArea) ((JScrollPane) ((JPanel) adminPanel.getComponent(0)).getComponent(0)).getViewport().getView();
        aggiornaListaUtenti(userListArea);
    }
    
    /**
     * Effettua il login
     */
    private void effettuaLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Inserisci email e password", 
                "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (gestoreUtenti.login(email, password)) {
            if (email.equals("admin@gmail.com")) {
                showAdminPanel();
            } else {
                showUserPanel();
            }
            // Pulisci i campi
            emailField.setText("");
            passwordField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, 
                "Email o password non validi", 
                "Errore di login", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Registra un nuovo utente
     */
    private void registraUtente() {
        String email = regEmailField.getText();
        String nome = regNomeField.getText();
        String cognome = regCognomeField.getText();
        String password = new String(regPasswordField.getPassword());
        String indirizzo = regIndirizzoField.getText();
        
        if (!validateRequiredFields(email, nome, cognome, password, indirizzo)) {
            return;
        }
        
        if (!isValidEmail(email)) {
            return;
        }
        
        if (gestoreUtenti.registraUtente(email, nome, cognome, password, indirizzo)) {
            showMessage("Utente registrato con successo!", "Registrazione completata", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear fields
            regEmailField.setText("");
            regNomeField.setText("");
            regCognomeField.setText("");
            regPasswordField.setText("");
            regIndirizzoField.setText("");
        } else {
            showMessage("Errore durante la registrazione. Email già in uso.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Helper method for showing messages
    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
    
    // Helper method for validating email
    private boolean isValidEmail(String email) {
        if (!email.contains("@")) {
            showMessage("Inserisci un'email valida", "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    // Helper method for validating required fields
    private boolean validateRequiredFields(String... fields) {
        for (String field : fields) {
            if (field.isEmpty()) {
                showMessage("Tutti i campi sono obbligatori", "Errore", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }
    
    /**
     * Mostra il dialog per aggiungere un ordine
     */
    private void mostraDialogOrdine() {
        JDialog orderDialog = new JDialog(this, "Aggiungi Ordine", true);
        orderDialog.setLayout(new BorderLayout());
        orderDialog.setSize(400, 300);
        orderDialog.setLocationRelativeTo(this);
        
        // Pannello con la lista dei prodotti
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setBorder(BorderFactory.createTitledBorder("Prodotti Disponibili"));
        
        DefaultListModel<String> productListModel = new DefaultListModel<>();
        for (int i = 0; i < Prodotti.getListaProdotti().size(); i++) {
            Prodotto p = Prodotti.getProdotto(i);
            productListModel.addElement((i+1) + ". " + p.getNome() + " - " + String.format("%.2f euro", p.getPrezzo()));
        }
        
        JList<String> productList = new JList<>(productListModel);
        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(productList);
        productPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Pannello per la quantità
        JPanel quantityPanel = new JPanel(new FlowLayout());
        quantityPanel.add(new JLabel("Quantità:"));
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        quantityPanel.add(quantitySpinner);
        
        // Pannello per i pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Aggiungi al Carrello");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = productList.getSelectedIndex();
                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(orderDialog, 
                        "Seleziona un prodotto", 
                        "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int quantita = (Integer) quantitySpinner.getValue();
                Prodotto prodottoSelezionato = Prodotti.getProdotto(selectedIndex);
                
                gestoreUtenti.aggiungiOrdine(prodottoSelezionato.getNome(), quantita);
                
                JOptionPane.showMessageDialog(orderDialog, 
                    "Hai ordinato: " + quantita + " x " + prodottoSelezionato.getNome() + 
                    " (Prezzo unitario: " + String.format("%.2f euro", prodottoSelezionato.getPrezzo()) + ")",
                    "Ordine Aggiunto", JOptionPane.INFORMATION_MESSAGE);
                
                orderDialog.dispose();
            }
        });
        
        JButton cancelButton = new JButton("Annulla");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                orderDialog.dispose();
            }
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        
        orderDialog.add(productPanel, BorderLayout.CENTER);
        orderDialog.add(quantityPanel, BorderLayout.NORTH);
        orderDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        orderDialog.setVisible(true);
    }
    
    /**
     * Metodo main per avviare l'applicazione
     */
    public static void main(String[] args) {
        // Imposta il look and feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Avvia l'interfaccia grafica
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UI();
            }
        });
    }
}