<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="ws.rest.tp8.Personne" %>
<%
    // Récupérer la liste des personnes depuis la session
    List<Personne> personnes = (List<Personne>) session.getAttribute("personnes");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Personnes</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <!-- Custom CSS for additional styling -->
            <link rel="stylesheet" href="style/list.css">
+
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center text-secondary mb-4">Liste des Personnes</h1>

        <!-- Links for navigation -->
        <div class="text-center mb-4">
            <a href="formPersonne.jsp" class="btn btn-secondary mb-3">Ajouter une Personne</a>
            <a href="index.jsp" class="btn btn-secondary mb-3">Accueil</a>
        </div>

        <!-- Table displaying the persons -->
        <table class="table table-bordered table-striped">
            <thead class="thead-dark">
                <tr>
                    <th>Id</th>
                    <th>Nom</th>
                    <th>Âge</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (personnes != null && !personnes.isEmpty()) {
                        for (Personne personne : personnes) {
                %>
                <tr>
                    <td><%= personne.getId() %></td>
                    <td><%= personne.getNom() %></td>
                    <td><%= personne.getAge() %></td>
                    <td>
                        <a href="ListPersonnesAction?action=editer&id=<%= personne.getId() %>" class="btn btn-warning btn-sm">
                            <i class="fas fa-edit"></i> Éditer
                        </a>
                        <a href="ListPersonnesAction?action=supprimer&id=<%= personne.getId() %>" class="btn btn-danger btn-sm">
                            <i class="fas fa-trash"></i> Supprimer
                        </a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="4" class="text-center">Aucune personne trouvée.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
