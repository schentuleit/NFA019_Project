package src.view;

import src.model.Auteur;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SauvegardeDialog extends JDialog {
    private JTextField nomField;
    private JComboBox<Auteur> auteurCombo;
    private boolean validated = false;

    public SauvegardeDialog(JFrame parent, List<Auteur> auteurs) {
        super(parent, "Sauvegarder le dessin", true);
        setLayout(new GridLayout(3, 2, 10, 10));
        setSize(400, 200);
        setLocationRelativeTo(parent);

        add(new JLabel("Nom du dessin :"));
        nomField = new JTextField();
        add(nomField);

        add(new JLabel("Auteur :"));
        auteurCombo = new JComboBox<>(auteurs.toArray(new Auteur[0]));
        add(auteurCombo);

        JButton btnSave = new JButton("Sauvegarder");
        JButton btnCancel = new JButton("Annuler");

        btnSave.addActionListener(e -> {
            validated = true;
            setVisible(false);
        });

        btnCancel.addActionListener(e -> {
            validated = false;
            setVisible(false);
        });

        add(btnSave);
        add(btnCancel);
    }

    public boolean isValidated() {
        return validated;
    }

    public String getNomDessin() {
        return nomField.getText();
    }

    public Auteur getAuteurSelectionne() {
        return (Auteur) auteurCombo.getSelectedItem();
    }
}
