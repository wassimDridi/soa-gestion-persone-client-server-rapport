package ws.rest.tp8;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Path("/person")
//spécifier le type de représentation que la ressource peut accepter ou consommer de la part du client
@Consumes( MediaType.APPLICATION_XML)
//spécifier le type de représentation de la valeur de retour
@Produces( MediaType.APPLICATION_XML)
public class PersonneServiceImpl implements PersonneService {

    private static Map<Integer, Personne> pers = new HashMap<Integer, Personne>();

    @Override
    @POST
    @Path("/add")
    public Reponse ajouterPersonne(Personne p) {
        Reponse reponse = new Reponse();
        if (pers.get(p.getId()) != null) {
            reponse.setStatus(false);
            reponse.setMessage("Personne déjà existante...");
            return reponse;
        }
        pers.put(p.getId(), p);
        reponse.setStatus(true);
        reponse.setMessage("Personne créée avec succès...");
        return reponse;
    }

    


    @Override
    @GET
    @Path("/{id}/delete")
    public Reponse supprimerPersonne(@PathParam("id") int id) {
        Reponse reponse = new Reponse();
        if (pers.get(id) == null) {
            reponse.setStatus(false);
            reponse.setMessage("Personne non existante...");
            return reponse;
        }
        pers.remove(id);
        reponse.setStatus(true);
        reponse.setMessage("Personne supprimée avec succès...");
        return reponse;
    }

    @Override
    @GET
    @Path("/{id}/get")
    public Personne getPersonne(@PathParam("id") int id) {
      return  pers.get(id);

    }


    @GET
    @Path("/{id}/getTest")
    public Personne getTestPersonne(@PathParam("id") int id) {
        Personne p = new Personne();
        p.setId(id);
        p.setAge(0);
        p.setNom("Test");
        return p;
    }

    @Override
    @GET
    @Path("/getAll")
    public Personne[] getAllPersonnes() {
        Set<Integer> ids = pers.keySet();
        Personne[] p = new Personne[ids.size()];
        int i = 0;
        for (Integer id : ids)
        {
            p[i] = pers.get(id);
            i++;
        }
        return p;
    }

}