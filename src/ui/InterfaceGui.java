package ui;

import dao.impl.SaleDaoImpl;
import dao.impl.UserDaoImpl;
import entity.Sale;
import entity.User;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class InterfaceGui extends JFrame implements ActionListener, ListSelectionListener {
    private JTextField txtArticle, txtPrice, txtQuantity, txtEmail, txtPassword;

    private JButton btnAjouter, btnModifier, btnSupprimer, btnLogin;

    private JTable jTable;
    private JLabel totalLabel = new JLabel("0");
    private DefaultTableModel tableModel;

    private SaleDaoImpl saleDao;
    private UserDaoImpl userDao;

    private JPanel salePanelForm;
    private JPanel authPanelForm;
    User user;

    public InterfaceGui() {
        setTitle("Gestion des ventes");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        saleDao = new SaleDaoImpl();
        userDao = new UserDaoImpl();

        tableModel = new DefaultTableModel(new String[]{"Id", "Article", "Prix(FCFA)", "Quantite", "Total(FCFA)"}, 0);
        jTable = new JTable(tableModel);


        ListSelectionModel listSelectionModel = jTable.getSelectionModel();
        listSelectionModel.addListSelectionListener(this);


        JScrollPane scrollPane = new JScrollPane(jTable);
        add(scrollPane, BorderLayout.CENTER);
        initAuthView();

        authPanelForm.setVisible(true);
//        loadSales();
        setVisible(true);

    }

    private void initAuthView() {
        authPanelForm = new JPanel(new GridBagLayout());
        authPanelForm.setBorder(new TitledBorder("Informations des ventes"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Labels et champs
        gbc.gridx = 0;
        gbc.gridy = 0;
        authPanelForm.add(new JLabel("Email :"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        txtEmail = new JTextField(15);
        authPanelForm.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        authPanelForm.add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        txtPassword = new JTextField(15);
        authPanelForm.add(txtPassword, gbc);

        JPanel panelBoutons = new JPanel(new FlowLayout());
        btnLogin = new JButton("Login");

        panelBoutons.add(btnLogin);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        authPanelForm.add(panelBoutons, gbc);

        add(authPanelForm, BorderLayout.SOUTH);

        btnLogin.addActionListener(this);
    }

    private void initSaleView() {
        salePanelForm = new JPanel(new GridBagLayout());
        salePanelForm.setBorder(new TitledBorder("Informations des ventes"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        // If you want the value to be committed on each keystroke instead of focus lost
        formatter.setCommitsOnValidEdit(true);
        // Labels et champs
        gbc.gridx = 0;
        gbc.gridy = 0;
        salePanelForm.add(new JLabel("Article :"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        txtArticle = new JTextField(15);
        salePanelForm.add(txtArticle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        salePanelForm.add(new JLabel("Prix (FCFA) :"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        txtPrice = new JNumberTextField(15);
        salePanelForm.add(txtPrice, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        salePanelForm.add(new JLabel("Quantite :"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        txtQuantity = new JNumberTextField(15);
        salePanelForm.add(txtQuantity, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        salePanelForm.add(new JLabel("Total (FCFA) :"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        salePanelForm.add(totalLabel, gbc);

        // Boutons
        JPanel panelBoutons = new JPanel(new FlowLayout());
        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");

        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnModifier);
        panelBoutons.add(btnSupprimer);

        // Ajout des composants
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        salePanelForm.add(panelBoutons, gbc);

        add(salePanelForm, BorderLayout.SOUTH);

        btnAjouter.addActionListener(this);
        btnModifier.addActionListener(this);
        btnSupprimer.addActionListener(this);
        txtPrice.getDocument().addDocumentListener(
            new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    warn();
                }

                public void removeUpdate(DocumentEvent e) {
                    warn();
                }

                public void insertUpdate(DocumentEvent e) {
                    warn();
                }

                public void warn() {
                    String total = "0";
                    String price = txtPrice.getText();
                    String quantity = txtQuantity.getText();
                    if(!price.isEmpty() && !quantity.isEmpty()) {
                        Integer result = Integer.parseInt(price) * Integer.parseInt(quantity);
                        total = result.toString();
                    }
                    totalLabel.setText(total);
                }
            }
        );

        txtQuantity.getDocument().addDocumentListener(
            new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    warn();
                }

                public void removeUpdate(DocumentEvent e) {
                    warn();
                }

                public void insertUpdate(DocumentEvent e) {
                    warn();
                }

                public void warn() {
                    String total = "0";
                    String price = txtPrice.getText();
                    String quantity = txtQuantity.getText();
                    if(!price.isEmpty() && !quantity.isEmpty()) {
                        Integer result = Integer.parseInt(price) * Integer.parseInt(quantity);
                        total = result.toString();
                    }
                    totalLabel.setText(total);
                }
            }
        );
    }
    public void updateQuantity(){

    }
    private void viderChamps() {
        txtArticle.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
    }

    private void addSale() {
        String article = txtArticle.getText();
        String price = txtPrice.getText();
        String quantity = txtQuantity.getText();

        if (!article.isEmpty() && !price.isEmpty() && !quantity.isEmpty()) {
            saleDao.insert(new Sale(0, article, Integer.parseInt(price), Integer.parseInt(quantity), Integer.parseInt(price) * Integer.parseInt(quantity), user.getId()), user.getId());
            loadSales();
            viderChamps();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
        }
    }


    private void editSale() {
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            String article = txtArticle.getText();
            String price = txtPrice.getText();
            String quantity = txtQuantity.getText();


            if (!article.isEmpty() && !price.isEmpty() && !quantity.isEmpty()) {
                saleDao.update(new Sale(0, article, Integer.parseInt(price), Integer.parseInt(quantity), Integer.parseInt(price) * Integer.parseInt(quantity), user.getId()));
                loadSales();
                viderChamps();
                JOptionPane.showMessageDialog(this, "Vente modifié");
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une vente à modifier !");
        }
    }

    private void deleteSale() {
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            saleDao.deleteById(id);
            loadSales();
            viderChamps();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une vente à supprimer !");
        }
    }

    private void login() {
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        if (!email.isEmpty() && !password.isEmpty()) {
            user = userDao.login(email, password);
            if (user.getId() == 0) {
                JOptionPane.showMessageDialog(this, "Vos access sont invalide");
            } else {
                JOptionPane.showMessageDialog(this, "Authentification réussi");
                authPanelForm.setVisible(false);
                initSaleView();
                salePanelForm.setVisible(true);
                loadSales();

            }

        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
        }
    }

    private void loadSales() {
        tableModel.setRowCount(0);
        for (Sale s : saleDao.findAll()) {
            tableModel.addRow(new Object[]{s.getId(), s.getArticle(), s.getPrice(), s.getQuantity(), s.getTotal(), s.getIdUser()});
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            new InterfaceGui();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Ajouter") {
            addSale();
        } else if (e.getActionCommand() == "Modifier") {
            editSale();
        } else if (e.getActionCommand() == "Supprimer") {
            deleteSale();
        } else if (e.getActionCommand() == "Login") {
            login();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = jTable.getSelectedRow();
            if (selectedRow != -1) {
                txtArticle.setText(jTable.getValueAt(selectedRow, 1).toString());
                txtPrice.setText(jTable.getValueAt(selectedRow, 2).toString());
                txtQuantity.setText(jTable.getValueAt(selectedRow, 3).toString());


            }
        }
    }


}
