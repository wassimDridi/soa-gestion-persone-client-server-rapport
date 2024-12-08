package ws.rest.tp8;

import org.junit.jupiter.api.*;
import org.mockito.*;
import java.sql.*;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class PersonneServiceImplTest {
    private static PersonneServiceImpl metier;
    private static Connection mockConnection;
    private static PreparedStatement mockPreparedStatement;
    private static ResultSet mockResultSet;
    @BeforeEach
    void setUp() throws Exception {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        DBConnexion.setConnection(mockConnection); // Simule la connexion
        metier = new PersonneServiceImpl();
    }

    @Test
    void testAjouterPersonne() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Personne personne = new Personne(1, "Alice", 25);
        Reponse reponse = metier.ajouterPersonne(personne);

        assertTrue(reponse.isStatus());
        assertEquals("Personne créée avec succès...", reponse.getMessage());

        //Reponse reponsefalse = metier.ajouterPersonne(personne);
        //assertFalse(reponsefalse.isStatus());
        //assertEquals("Personne avec cet ID existe déjà.", reponsefalse.getMessage());
    }
    @Test
    void testGetAllPersonnes() throws Exception {
        // Création d'un mock Statement et mock ResultSet
        Statement mockStatement = mock(Statement.class);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        // Configuration du mock ResultSet pour renvoyer deux personnes
        when(mockResultSet.next()).thenReturn(true, true, false); // 2 résultats, puis fin
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("nom")).thenReturn("Alice", "Bob");
        when(mockResultSet.getInt("age")).thenReturn(25, 30);

        // Appel de la méthode
        Personne[] personnes = metier.getAllPersonnes();

        // Vérification des résultats
        assertNotNull(personnes, "La liste des personnes ne devrait pas être nulle");
        assertEquals(2, personnes.length, "Le nombre de personnes retournées doit être 2");

        // Vérification des attributs des personnes
        assertEquals(1, personnes[0].getId(), "Le premier ID doit être 1");
        assertEquals("Alice", personnes[0].getNom(), "Le premier nom doit être 'Alice'");
        assertEquals(25, personnes[0].getAge(), "Le premier âge doit être 25");

        assertEquals(2, personnes[1].getId(), "Le deuxième ID doit être 2");
        assertEquals("Bob", personnes[1].getNom(), "Le deuxième nom doit être 'Bob'");
        assertEquals(30, personnes[1].getAge(), "Le deuxième âge doit être 30");
    }

    @Test
    void testSupprimerPersonne() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Reponse reponse = metier.supprimerPersonne(1);

        assertTrue(reponse.isStatus());
        assertEquals("Personne supprimée avec succès...", reponse.getMessage());
    }

    @Test
    void testGetPersonne() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("nom")).thenReturn("Alice");
        when(mockResultSet.getInt("age")).thenReturn(25);

        Personne personne = metier.getPersonne(1);

        assertNotNull(personne);
        assertEquals(1, personne.getId());
        assertEquals("Alice", personne.getNom());
        assertEquals(25, personne.getAge());
    }
    @Test
    void testModifierPersonne() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Personne personne = new Personne(1, "Alice", 30);
        Reponse reponse = metier.modifierPersonne(personne);

        assertTrue(reponse.isStatus());
        assertEquals("Personne mise à jour avec succès...", reponse.getMessage());
    }
}
