package src.view;

import src.model.Dessin;
import src.model.Pixel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PixelCanvas extends JPanel {
    private int rows;
    private int cols;
    private int pixelSize;
    private Color[][] pixels;
    private Supplier<Color> colorSupplier;

    public PixelCanvas(int rows, int cols, Supplier<Color> colorSupplier, int canvasSize) {
        this.rows = rows;
        this.cols = cols;
        this.colorSupplier = colorSupplier;

        this.pixelSize = canvasSize / Math.max(rows, cols); // Taille de pixel dynamique
        this.pixels = new Color[rows][cols];

        setPreferredSize(new Dimension(cols * pixelSize, rows * pixelSize));
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / pixelSize;
                int y = e.getY() / pixelSize;
                if (x >= 0 && x < cols && y >= 0 && y < rows) {
                    pixels[y][x] = colorSupplier.get();
                    repaint();
                }
            }
        });
    }
    public static BufferedImage generatePreview(Dessin dessin, int size) {
        int rows = dessin.getHauteur();
        int cols = dessin.getLargeur();
        BufferedImage image = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);

        for (var pixel : dessin.getPixels()) {
            int x = pixel.getX();
            int y = pixel.getY();
            try {
                Color color = Color.decode(pixel.getCouleurHex());
                image.setRGB(x, y, color.getRGB());
            } catch (NumberFormatException e) {
                image.setRGB(x, y, Color.WHITE.getRGB()); // Fallback couleur si hex invalide
            }
        }

        // Agrandissement en miniature lisible
        Image scaled = image.getScaledInstance(size, size, Image.SCALE_FAST);
        BufferedImage output = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = output.createGraphics();
        g2d.drawImage(scaled, 0, 0, null);
        g2d.dispose();

        return output;
    }

    public BufferedImage toImage() {
        BufferedImage image = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Color color = pixels[y][x] != null ? pixels[y][x] : Color.WHITE;
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Color color = pixels[y][x];
                if (color != null) {
                    g.setColor(color);
                    g.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
                }
                g.setColor(Color.GRAY);
                g.drawRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
            }
        }
    }

    public void clearCanvas() {
        for (int y = 0; y < pixels.length; y++) {
            for (int x = 0; x < pixels[y].length; x++) {
                pixels[y][x] = null;
            }
        }
        repaint();
    }

    public void setPixel(int x, int y, Color color) {
        if (x >= 0 && x < cols && y >= 0 && y < rows) {
            pixels[y][x] = color;
        }
    }

    public Color[][] getPixels() {
        return pixels;
    }

    public List<Pixel> getPixelData() {
        List<Pixel> pixelList = new ArrayList<>();
        for (int y = 0; y < pixels.length; y++) {
            for (int x = 0; x < pixels[y].length; x++) {
                Color color = pixels[y][x];
                if (color != null) {
                    String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
                    pixelList.add(new Pixel(x, y, hex));
                }
            }
        }
        return pixelList;
    }
}
