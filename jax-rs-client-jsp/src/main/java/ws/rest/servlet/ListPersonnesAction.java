package ws.rest.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
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
import java.net.URI;

@WebServlet("/ListPersonnesAction")
public class ListPersonnesAction extends HttpServlet {

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
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        WebResource service = getService();
        Gson gson = new GsonBuilder().create();
        if ("supprimer".equals(action)) {
            service.path(id).path("delete").get(String.class);
        } else if ("editer".equals(action)) {
            String jsonResponse = service.path(id).path("get").accept(MediaType.APPLICATION_JSON).get(String.class);
            System.out.println(jsonResponse);
            JsonObject jo = new JsonParser().parse(jsonResponse).getAsJsonObject();
            Personne personne = gson.fromJson(jo, Personne.class);
            HttpSession session = request.getSession();
            session.setAttribute("personne", personne);
            //request.setAttribute("personne", personne);
            request.getRequestDispatcher("formPersonne.jsp").forward(request, response);
            return;
        }
        response.sendRedirect("FormPersonneAction");
    }
}
