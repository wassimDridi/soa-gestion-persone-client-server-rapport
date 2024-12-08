package ws.rest.tp8;

public interface PersonneService {
    public Reponse ajouterPersonne(Personne p);
    public Reponse supprimerPersonne(int id);
    public Personne getPersonne(int id);
    public Personne[] getAllPersonnes();
}