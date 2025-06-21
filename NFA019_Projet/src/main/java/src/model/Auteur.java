package src.model;

public class Auteur {
    private int id;
    private String nom;
    private String email;

    public Auteur(int id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }
    public Auteur(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }
    public Auteur(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    public Auteur(String nom) {
        this.nom = nom;
    }


    public Auteur() {
    }


    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return nom;
    }
}
