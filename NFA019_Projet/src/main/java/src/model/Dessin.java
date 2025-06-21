package src.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Dessin {
    private int id;
    private String nom;
    private int largeur;
    private int hauteur;
    private LocalDateTime dateCreation;
    private Auteur auteur;
    private List<Pixel> pixels;

    public Dessin(String nom, int largeur, int hauteur, Auteur auteur, List<Pixel> pixels) {
        this.nom = nom;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.auteur = auteur;
        this.pixels = pixels;
        this.dateCreation = LocalDateTime.now();
    }
    public Dessin(String nom, int largeur, int hauteur) {
        this.nom = nom;
        this.largeur = largeur;
        this.hauteur = hauteur;
    }
    public Dessin(int id, String nom, int largeur, int hauteur, Auteur auteur) {
        this.id = id;
        this.nom = nom;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.auteur = auteur;
        this.pixels = new ArrayList<>(); // vide par défaut
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public int getLargeur() { return largeur; }
    public int getHauteur() { return hauteur; }
    public void setAuteur(Auteur a) { this.auteur = a; }
    public Auteur getAuteur() { return auteur; }
    public List<Pixel> getPixels() { return pixels; }
    public void setPixels(List<Pixel> pixels) {
        this.pixels = pixels;
    }
    @Override
    public String toString() {
        return nom + " (id: " + id + ")";
    }


}
