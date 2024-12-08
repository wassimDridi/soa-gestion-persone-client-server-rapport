package soa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import soa.entities.Categorie;
import soa.entities.Produit;
import soa.entities.Responsable;
import soa.entities.Stock;
import soa.metier.ProduitMetierInterface;
import soa.repository.CategorieRepository;
import soa.repository.ProduitRepository;
import soa.repository.ResponsableRepository;
import soa.repository.StockRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class SpringJpaApplication2 {
    //déclaration des objets de type Repository
    //Déclaration d'un objet métier pour gérer les produits
    static ProduitMetierInterface produitMetier;

    public static void main(String[] args) {
        System.out.println("---------Injection de dépendances----------");
        //Commencer par réaliser les injections de dépendances pour les objets de type Repository
        // référencer le contexte
        ApplicationContext contexte = SpringApplication.run(SpringJpaApplication2.class, args);
        // Récupérer une implémentation de l'interface "ProduitRepository" par injection de dépendance
        produitMetier = contexte.getBean(ProduitMetierInterface.class);


        //objet pour formater les dates selon le pattern "yyyy-MM-dd"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date1 = null;
        java.util.Date date2 = null;
        java.util.Date date3 = null;
        java.util.Date date4 = null;
        java.util.Date dateFinPromotion = null;
        //trois objets de type date
        try
            {
            date1 = sdf.parse("2021-04-15");
            date2 = sdf.parse("2022-02-20");
            date3 = sdf.parse("2023-01-10");
            date4 = sdf.parse("2023-05-15");
            dateFinPromotion = sdf.parse("2023-01-01");
             }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Insérer un produit sans catégorie et sans stock
        System.out.println("-2-Insérer un produit 'Ordinateur' sans catégorie et sans stock ----------");
        Produit pOrdinateur =new Produit("Ordinateur", 2400, 1, date1 );
        produitMetier.ajouterProduit(pOrdinateur);
        afficherTousLesProduits();


        // Insérer un produit avec catégorie et sans stock
        System.out.println("-2-Insérer un produit 'Imprimate' avec la catégorie 'Informatique' et sans stock ----------");
        Produit pImprimante =new Produit("Imprimante", 500, 10, date2 );
        Categorie catInformatique = new Categorie("INF", "Informatique");
        produitMetier.ajouterProduit(pImprimante,catInformatique);
        afficherTousLesProduits();

        // Insérer un produit avec catégorie et sans stock
        System.out.println("-3-Insérer un produit 'Tablette' avec la catégorie 'Jouet' et sans stock ----------");
        Produit pTablette =new Produit("Tablette", 300, 3, date3 );
        Categorie catJouet = new Categorie("JOUET", "Jouets");
        produitMetier.ajouterProduit(pTablette,catJouet);
        afficherTousLesProduits();


        // Insérer un produit avec catégorie et un stock
        System.out.println("-4-Insérer un produit 'SmartPhone' avec la catégorie 'Informatique' et le Stock du Tunis");
        Produit pSmartPhone =new Produit("SmartPhone", 1000, 2, date4 );
        Stock stockTunis= new Stock("1","Tunis");
        produitMetier.ajouterProduit(pSmartPhone,catInformatique, stockTunis);
        afficherTousLesProduits();


        // Changer la catégorie du produit 'Tablette' à 'Informatique'
        System.out.println("-5-Changer la catégorie du produit 'Tablette' à 'Informatique'");
        boolean resultat =produitMetier.changerCategorieProduit(3L,1L);
        if (resultat)
            System.out.println("----Succès du changement de catégorie----'");
        else
            System.out.println("----Echec du changement de catégorie----'");
        afficherTousLesProduits();

        //Rendre tous les produits achetés avant '2023' en promotion
        System.out.println("-6-Rendre tous les produits achetés avant '2023' en promotion");
        produitMetier.rendreProduitsEnPromotionAvant(dateFinPromotion);
        afficherTousLesProduits();

        //Calculer le coût de vente de tous les produits en stock en appliquant la remise sur les produits en promotion
        System.out.println("-7-Calculer le coût de vente de tous les produits en stock en appliquant la remise sur les produits en promotion");
        System.out.println("Cout de vente du stock:"+produitMetier.calculerCoutVenteStock(50));

    }


    static void afficherTousLesProduits()
    {
        System.out.println("********************Début**********************");
        System.out.println("Afficher tous les produits...");
        System.out.println("***********************************************");

        List<Produit> lp = produitMetier.listeProduits();
        for (Produit p : lp)
        {
            System.out.println(p);
        }
        System.out.println("********************Fin************************");
    }
}
