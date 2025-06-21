package src.model;

public class Pixel {
    private int x;
    private int y;
    private String couleurHex;

    public Pixel(int x, int y, String couleurHex) {
        this.x = x;
        this.y = y;
        this.couleurHex = couleurHex;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public String getCouleurHex() { return couleurHex; }
}
