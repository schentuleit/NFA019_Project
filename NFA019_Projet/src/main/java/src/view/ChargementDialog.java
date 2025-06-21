package src.view;

import src.dao.DessinDAO;
import src.model.Dessin;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ChargementDialog extends JDialog {

    private JList<Dessin> dessinList;
    private DefaultListModel<Dessin> listModel;
    private boolean validated = false;
    private Dessin selected;

    public ChargementDialog(JFrame parent, List<Dessin> dessins) {
        super(parent, "Chargement de dessins", true);
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        listModel = new DefaultListModel<>();
        for (Dessin d : dessins) listModel.addElement(d);

        dessinList = new JList<>(listModel);
        dessinList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(dessinList);
        add(scrollPane, BorderLayout.CENTER);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton btnLoad = new JButton("📂 Charger");
        JButton btnDelete = new JButton("🗑️ Supprimer");
        JButton btnCancel = new JButton("Annuler");

        btnLoad.addActionListener(e -> {
            selected = dessinList.getSelectedValue();
            if (selected != null) {
                validated = true;
                setVisible(false);
            }
        });

        JLabel previewLabel = new JLabel();
        previewLabel.setPreferredSize(new Dimension(128, 128));
        add(previewLabel, BorderLayout.EAST);

        btnDelete.addActionListener(e -> {
            Dessin toDelete = dessinList.getSelectedValue();
            if (toDelete != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Supprimer le dessin \"" + toDelete.getNom() + "\" ?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new DessinDAO().deleteById(toDelete.getId());
                    listModel.removeElement(toDelete);
                    JOptionPane.showMessageDialog(this, "Dessin supprimé.");
                }
            }
        });

        btnCancel.addActionListener(e -> {
            validated = false;
            setVisible(false);
        });

        buttonPanel.add(btnLoad);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);

        // Écouteur pour mettre à jour la prévisualisation
        dessinList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Dessin d = dessinList.getSelectedValue();
                if (d != null) {
                    BufferedImage preview = PixelCanvas.generatePreview(d, 128);
                    previewLabel.setIcon(new ImageIcon(preview));
                } else {
                    previewLabel.setIcon(null);
                }
            }
        });

    }



    public boolean isValidated() {
        return validated;
    }

    public Dessin getSelectedDessin() {
        return selected;
    }
}
