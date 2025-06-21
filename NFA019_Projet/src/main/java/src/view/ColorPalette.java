package src.view;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ColorPalette extends JPanel {

    private final Color[] paletteColors = {
            Color.BLACK, Color.WHITE, Color.RED, Color.GREEN, Color.BLUE,
            Color.YELLOW, Color.ORANGE, Color.PINK, Color.CYAN, Color.MAGENTA,
            new Color(128, 0, 0), new Color(0, 128, 0), new Color(0, 0, 128),
            new Color(128, 128, 0), new Color(0, 128, 128), new Color(128, 0, 128)
    };

    private Color selectedColor = Color.BLACK;
    private Consumer<Color> onColorChange; //  Callback dynamique

    public ColorPalette() {
        setLayout(new GridLayout(2, paletteColors.length / 2));
        setBorder(BorderFactory.createTitledBorder("Palette de couleurs"));

        for (Color color : paletteColors) {
            JButton swatch = new JButton();
            swatch.setBackground(color);
            swatch.setPreferredSize(new Dimension(30, 30));
            swatch.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

            swatch.addActionListener(e -> {
                selectedColor = color;
                if (onColorChange != null) {
                    onColorChange.accept(color); //  On notifie le parent
                }
            });

            add(swatch);
        }
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setOnColorChange(Consumer<Color> callback) {
        this.onColorChange = callback;
    }
}
