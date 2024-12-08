package ws.rest.tp8;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Classe représentant une personne.
 * Annotée pour être utilisée dans des services RESTful avec JAXB pour la sérialisation/désérialisation en XML.
 */
@XmlRootElement(name = "person")
public class Personne implements Serializable {

    // Champ pour la compatibilité de sérialisation
    private static final long serialVersionUID = 1L;

    private int id;
    private String nom;
    private int age;

    public Personne(int id, String nom, int age) {
        this.id = id;
        this.nom = nom;
        this.age = age;
    }

    public Personne(String nom, int age) {
        this();
        this.nom = nom;
        this.age = age;
    }

    public Personne() {
        this.id = 0;
        this.nom = "";
        this.age = 0;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        this.nom = nom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("L'âge ne peut pas être négatif.");
        }
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("L'identifiant ne peut pas être négatif.");
        }
        this.id = id;
    }

    @Override
    public String toString() {
        return "[" + id + "::" + nom + "::" + age + "]";
    }
}
