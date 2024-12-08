package ws.rest.tp8;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/person")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class PersonneServiceImpl implements PersonneService {
    private Connection conn = DBConnexion.getConnection();
    @Override
    @POST
    @Path("/add")
    public Reponse ajouterPersonne(Personne p) {
        Reponse reponse = new Reponse();
        String query = "INSERT INTO person (id, nom, age) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, p.getId());
            ps.setString(2, p.getNom());
            ps.setInt(3, p.getAge());
            ps.executeUpdate();

            reponse.setStatus(true);
            reponse.setMessage("Personne créée avec succès...");
        } catch (SQLIntegrityConstraintViolationException e) {
            reponse.setStatus(false);
            reponse.setMessage("Personne avec cet ID existe déjà.");
        } catch (SQLException e) {
            reponse.setStatus(false);
            reponse.setMessage("Erreur lors de l'ajout de la personne : " + e.getMessage());
        }
        return reponse;
    }

    @Override
    @GET
    @Path("/{id}/delete")
    public Reponse supprimerPersonne(@PathParam("id") int id) {
        Reponse reponse = new Reponse();
        String query = "DELETE FROM person WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                reponse.setStatus(true);
                reponse.setMessage("Personne supprimée avec succès...");
            } else {
                reponse.setStatus(false);
                reponse.setMessage("Personne non existante...");
            }
        } catch (SQLException e) {
            reponse.setStatus(false);
            reponse.setMessage("Erreur lors de la suppression de la personne : " + e.getMessage());
        }
        return reponse;
    }

    @Override
    @GET
    @Path("/{id}/get")
    public Personne getPersonne(@PathParam("id") int id) {
        String query = "SELECT * FROM person WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Personne(rs.getInt("id"), rs.getString("nom"), rs.getInt("age"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retourne null si la personne n'existe pas
    }

    @Override
    @GET
    @Path("/getAll")
    public Personne[] getAllPersonnes() {
        String query = "SELECT * FROM person";
        List<Personne> personnes = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Personne p = new Personne(rs.getInt("id"), rs.getString("nom"), rs.getInt("age"));
                personnes.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnes.toArray(new Personne[0]);
    }

    @PUT
    @Path("/update")
    public Reponse modifierPersonne(Personne p) {
        Reponse reponse = new Reponse();
        String query = "UPDATE person SET nom = ?, age = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, p.getNom());
            ps.setInt(2, p.getAge());
            ps.setInt(3, p.getId());
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                reponse.setStatus(true);
                reponse.setMessage("Personne mise à jour avec succès...");
            } else {
                reponse.setStatus(false);
                reponse.setMessage("Personne non existante...");
            }
        } catch (SQLException e) {
            reponse.setStatus(false);
            reponse.setMessage("Erreur lors de la mise à jour de la personne : " + e.getMessage());
        }
        return reponse;
    }

    @DELETE
    @Path("/delete")
    public Reponse supprimerPersonneViaObjet(Personne p) {
        return supprimerPersonne(p.getId());
    }
}
