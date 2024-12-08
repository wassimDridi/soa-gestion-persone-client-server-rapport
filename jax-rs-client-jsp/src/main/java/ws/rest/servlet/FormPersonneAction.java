package ws.rest.servlet;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import ws.rest.tp8.Personne;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/FormPersonneAction")
public class FormPersonneAction extends HttpServlet {

    private WebResource getService() {
        // Objet de configuration
        ClientConfig config = new DefaultClientConfig();
        // Objet client
        Client client = Client.create(config);
        // Créer l'uri
        URI uri = UriBuilder.fromUri("http://localhost:9999/rest_arc/tp8/person").build();
        // Obtenir une ressource correspondante à l'uri du service web
        WebResource service = client.resource(uri);
        return service ;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebResource service = getService();
        WebResource resourceGetAll = service.path("getAll");
        String responseGetAll = resourceGetAll.accept(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println(responseGetAll);
        Gson gson = new GsonBuilder().create();
        ArrayList<Personne> personnesList = new ArrayList<>();
        if (!responseGetAll.equals("null")) {
            // s'il existe au moins un objet "Personne"
            JsonObject jo = new JsonParser().parse(responseGetAll).getAsJsonObject();
            // Remplacement de JsonParser obsolète
            if (jo.get("person").isJsonArray()) {
                // en cas de plusieurs personnes
                JsonArray jsonArray = jo.getAsJsonArray("person");
                Personne[] listePers = gson.fromJson(jsonArray, Personne[].class);
                personnesList = new ArrayList<>(Arrays.asList(listePers));

            } else { // en cas d'un seul objet "Personne"
                // Une seule personne
                JsonObject jsonObject = jo.getAsJsonObject("person");
                Personne personne = gson.fromJson(jsonObject, Personne.class);
                personnesList.add(personne);
            }
        } else {
            System.out.println("Aucune personne n'est trouvée..");
        }
        HttpSession session = request.getSession();
        session.setAttribute("personnes", personnesList);
        //request.setAttribute("personnes", responseGetAll);
        request.getRequestDispatcher("listPersonnes.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> erreurs = new ArrayList<>();
        WebResource service = getService();
        try{
            String mode = request.getParameter("mode");
            String id = request.getParameter("id");
            String nom = request.getParameter("nom");
            String age = request.getParameter("age");
            if (id == null || id.trim().isEmpty()) {
                erreurs.add("L'ID est obligatoire.");
            }
            if (nom == null || nom.trim().isEmpty()) {
                erreurs.add("Le nom est obligatoire.");
            }
            if (age == null || age.trim().isEmpty() || Integer.parseInt(age) < 0) {
                erreurs.add("L'âge est obligatoire et doit être un entier positif.");
            }
            if (!erreurs.isEmpty()) {
                request.setAttribute("tab_err", erreurs);
                request.getRequestDispatcher("formPersonne.jsp").forward(request, response);
                return;
            }
            Personne p = new Personne(Integer.parseInt(id), nom, Integer.parseInt(age));
            int status;
            String message;
            if ("edit".equals(mode)) {
                WebResource resourceEdit = service.path("update");
                ClientResponse clientResponse = resourceEdit.put(ClientResponse.class, p);
                status = clientResponse.getStatus();

                System.out.println("Statut de la réponse : edit " + status);

                if (status == 200) {

                    HttpSession session = request.getSession();
                    session.setAttribute("personne", null);
                    message = "Modification effectuée avec succès.";
                    request.setAttribute("successMessage", message);
                } else {
                    String errorResponse = clientResponse.getEntity(String.class);
                    message = "Erreur lors de la modification : " + errorResponse;
                    request.setAttribute("errorMessage", message);
                }

            } else {
                WebResource resourceAdd = service.path("add");
                ClientResponse clientResponse = resourceAdd.post(ClientResponse.class, p);
                status = clientResponse.getStatus();

                System.out.println("Statut de la réponse.............. : " + status);

                if (status == 200) {
                    message = "Ajout effectué avec succès.";
                    request.setAttribute("successMessage", message);
                } else {
                    String errorResponse = clientResponse.getEntity(String.class);
                    message = "Erreur lors de l'ajout : " + errorResponse;
                    request.setAttribute("errorMessage", message);
                }

            }
            request.getRequestDispatcher("formPersonne.jsp").forward(request, response);
            return;
        }catch (NumberFormatException e) {
            System.out.println("catch in number **************");
            erreurs.add("L'ID et l'âge doivent être des nombres.");
            request.setAttribute("tab_err", erreurs);
            request.getRequestDispatcher("formPersonne.jsp").forward(request, response);
        } catch (Exception e) {
            erreurs.add("Erreur inattendue : " + e.getMessage());
            request.setAttribute("tab_err", erreurs);
            request.getRequestDispatcher("formPersonne.jsp").forward(request, response);
        }


        //request.getRequestDispatcher("listPersonnes.jsp").forward(request, response);
    }

}
