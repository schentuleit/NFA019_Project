package src.view;

import src.dao.AuteurDAO;
import src.dao.DessinDAO;
import src.model.Auteur;
import src.model.Dessin;
import src.model.Pixel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PixelArtFrame extends JFrame {

    private Color selectedColor = Color.BLACK;
    private final PixelCanvas canvas;
    private Dessin dessinEnCours = null;

    public Color getCurrentColor() {
        return selectedColor;
    }

    private void exporterEnPNG() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Exporter le dessin en PNG");
        chooser.setSelectedFile(new File("dessin.png"));
        int userSelection = chooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = chooser.getSelectedFile();
            BufferedImage image = canvas.toImage();
            try {
                ImageIO.write(image, "png", fileToSave);
                JOptionPane.showMessageDialog(this, "Export réussi !");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de l'export PNG", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public PixelArtFrame() {
        setTitle("🎨 Mini éditeur de Pixel Art");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        String[] options = {"16", "32", "64"};
        String choix = (String) JOptionPane.showInputDialog(
                null,
                "Choisissez la taille de la grille (carrée)",
                "Taille grille",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1] // valeur par défaut = 32
        );

        int taille = Integer.parseInt(choix);


        //  Initialisation du canevas avec la couleur actuelle
        canvas = new PixelCanvas(taille, taille, this::getCurrentColor, 640);
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerWrapper.add(canvas);

        add(centerWrapper, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);


        //  Boutons (en bas)
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton btnColorPicker = new JButton("🎨 Choisir une couleur");
        JButton btnSave = new JButton("💾 Sauvegarder");
        JButton btnLoad = new JButton("📂 Charger");
        JButton btnClear = new JButton("🧹 Effacer");

        //  Action Color Picker
        btnColorPicker.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choisir une couleur", selectedColor);
            if (newColor != null) {
                selectedColor = newColor;
                canvas.repaint(); // Optionnel pour rafraîchir si tu veux une prévisualisation
            }
        });
        JButton exportBtn = new JButton("Exporter PNG");
        exportBtn.addActionListener(e -> exporterEnPNG());
        buttonPanel.add(exportBtn);


        // 💾 Sauvegarde
        btnSave.addActionListener(e -> {
            // Nom du dessin
            String nomDessin = JOptionPane.showInputDialog(this, "Nom du dessin :");
            if (nomDessin == null || nomDessin.trim().isEmpty()) return;

            // Nom de l’auteur
            String nomAuteur = JOptionPane.showInputDialog(this, "Nom de l’auteur :");
            if (nomAuteur == null || nomAuteur.trim().isEmpty()) return;

            // Récupérer ou créer l’auteur
            AuteurDAO auteurDAO = new AuteurDAO();
            Auteur auteur = auteurDAO.findOrCreate(nomAuteur);

            // Créer le dessin
            Dessin dessin = new Dessin(nomDessin, canvas.getPixels()[0].length, canvas.getPixels().length);
            dessin.setAuteur(auteur);
            dessin.setPixels(canvas.getPixelData());

            // Enregistrer ou mettre à jour
            DessinDAO dao = new DessinDAO();
            if (dessin.getId() == 0) {
                dao.save(dessin);
                dessinEnCours = dessin;
                JOptionPane.showMessageDialog(this, "Dessin créé !");
            } else {
                dao.update(dessin);
                JOptionPane.showMessageDialog(this, "Dessin mis à jour !");
            }
        });



        //  Chargement
        btnLoad.addActionListener(e -> {
            List<Dessin> dessins = new DessinDAO().findAll();

            if (dessins.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun dessin trouvé en base.");
                return;
            }

            Dessin selected = (Dessin) JOptionPane.showInputDialog(
                    this,
                    "Choisissez un dessin à charger :",
                    "Charger un dessin",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    dessins.toArray(),
                    null
            );

            if (selected != null) {
                dessinEnCours = selected; // 🔁 on le garde en mémoire
                canvas.clearCanvas();

                for (Pixel p : selected.getPixels()) {
                    Color color = Color.decode(p.getCouleurHex());
                    canvas.setPixel(p.getX(), p.getY(), color);
                }

                canvas.repaint();
                JOptionPane.showMessageDialog(this, "Dessin chargé !");
            }
        });


        //  Clear
        btnClear.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Effacer le dessin actuel ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                canvas.clearCanvas();
            }
        });

        ColorPalette palette = new ColorPalette();
        palette.setOnColorChange(color -> {
            selectedColor = color;
            canvas.repaint(); // Facultatif
        });
        buttonPanel.add(btnColorPicker);
        buttonPanel.add(btnLoad);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnSave);

        // ️ Ajout du panneau de boutons
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
