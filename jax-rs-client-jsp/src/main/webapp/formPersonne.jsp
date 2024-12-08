<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="ws.rest.tp8.Personne" %>


<%
    Personne personne = (Personne) session.getAttribute("personne");

    String id = "", nom = "", age = "", mode = "ajout";

    if (personne != null) {
        id = String.valueOf(personne.getId());
        nom = personne.getNom();
        age = String.valueOf(personne.getAge());
        mode = "edit";
    }
    List<String> erreurs = (List<String>) request.getAttribute("tab_err");
    String successMessage = (String) request.getAttribute("successMessage");
    String errorMessage = (String) request.getAttribute("errorMessage");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire Personne</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="style/form.css">

</head>
<body>
    <div class="container mt-5">
        <div class="text-center">
            <a href="ListPersonnesAction" class="btn btn-secondary mb-3">Liste des Personnes</a>
            <a href="index.jsp" class="btn btn-secondary mb-3">Accueil</a>
        </div>

        <h1 class="text-center mb-4">Ajouter / Éditer une Personne</h1>

        <!-- Affichage des messages d'erreur -->
        <%
            if (erreurs != null && !erreurs.isEmpty()) {
        %>
            <div class="alert alert-danger">
                <ul>
                    <% for (String erreur : erreurs) { %>
                        <li><%= erreur %></li>
                    <% } %>
                </ul>
            </div>
        <%
            }
        %>

        <!-- Affichage du message de succès -->
        <%
            if (successMessage != null) {
        %>
            <div class="alert alert-success">
                <%= successMessage %>
            </div>
        <%
            }
        %>

        <!-- Affichage du message d'erreur général -->
        <%
            if (errorMessage != null) {
        %>
            <div class="alert alert-danger">
                <%= errorMessage %>
            </div>
        <%
            }
        %>

        <form action="FormPersonneAction" method="POST" class="border p-4 bg-white shadow-lg rounded">
            <input type="hidden" name="mode" value="<%= mode %>" />

            <div class="mb-3">
                <label for="id" class="form-label text-black">ID :</label>
                <input type="text" id="id" name="id" value="<%= id %>" class="form-control"
                       <%= mode.equals("edit") ? "readonly" : "" %> />
            </div>

            <div class="mb-3">
                <label for="nom" class="form-label text-black">Nom :</label>
                <input type="text" id="nom" name="nom" value="<%= nom %>" class="form-control" />
            </div>

            <div class="mb-3">
                <label for="age" class="form-label text-black">Âge :</label>
                <input type="text" id="age" name="age" value="<%= age %>" class="form-control" />
            </div>

            <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-primary">Soumettre</button>
            </div>
        </form>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
