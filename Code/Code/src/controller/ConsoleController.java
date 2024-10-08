package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.ArrayList;

import dao.ArticleDAO;
import dao.ClientDAO;
import dao.EmployeDAO;
import dao.InitialisationDAO;
import dao.UtilisateurDAO;
import model.*;

public class ConsoleController {
	/// ============================================================== ///
	///	Fonction MAIN:
	/// ============================================================== ///
	public static void main(String[] args) {
		//	Connexion à MySQL + création des tables:
		InitialisationDAO DAO = new InitialisationDAO();
		Boolean check1 = false;
		//	Connexion utilisateur:
		UtilisateurDAO user = new UtilisateurDAO(DAO.getConnection(), DAO.getDatabase());


		do {
			check1=user.se_connecter("michou@gmail.com","root");
		}while(check1==false);

		//	Menu utilisateur:
		String type = user.getUtilisateur().getType();
		if(type.equals("EMPLOYE"))
			menu_employe(user);
		else if(type.equals("CLIENT"))
			menu_client(user);
		else
			System.out.println("Erreur: Vous n'êtes pas connecté.");

		//	Fin de la connexion:
		try {
			if (DAO.getDatabase() != null)
				DAO.getDatabase().close();
			if (DAO.getConnection() != null)
				DAO.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public static void menu_client(UtilisateurDAO user) {
		Boolean  check1 = false;
		//	Création du client:
		ClientDAO client = new ClientDAO(
				user.getConnection(),
				user.getDatabase(),
				user.getUtilisateur().getId(),
				user.getUtilisateur().getNom(),
				user.getUtilisateur().getMail(),
				user.getUtilisateur().getMdp(),
				user.getUtilisateur().getType()
				);
		int choice = -1;
		Scanner scanner = new Scanner(System.in);
		do {
			//	First data shielding : the number must be in a range from 0 to 7 :
			while(choice<0 || choice>4) {

				//	Display the menu:
				System.out.println("Menu CLIENT :");
				System.out.println("1. Rechercher un article puis l'ajouter au panier");
				System.out.println("2. Effectuer la commande");
				System.out.println("3. Voir l'historique des achats");
				System.out.println("4. Voir les articles");
				System.out.println("0. Quitter");

				//	Second data shielding : the number must be an integer:
				while(!scanner.hasNextInt()) {
					//	Error message:
					System.out.println("Erreur de saisie. Veuillez entrer un entier en 0 et 7");
					//	We "empty" scanner object to be able to enter a new value:
					scanner.next();
				}
				//	Input data:
				choice = scanner.nextInt();
			} 
			switch (choice) {
			case 1:
				//	Recherche de l'article à partir de son nom:
				do {
					check1 = client.recherche_article("cahier",2);
				}while(check1==false);
				break;
			case 2:
				String choix = "terminer";
				// 'annuler' et 'attendre' aussi
				
				//	Créer notre commande:
				client.creer_commande();
				//	Procéder à la commande:
				client.proceder_commande(choix);
				break;
			case 3:
				client.historique_commandes();
				break;
			case 4:
				ArrayList<Articles> articles = new ArrayList<>();
				articles = client.getArticlesDAO();
				System.out.println("Liste des articles :");
				for (Articles article : articles) {
					System.out.println("ID: " + article.getId());
					System.out.println("Nom: " + article.getNom());
					System.out.println("Description: " + article.getDescription());
					System.out.println("Prix unitaire: " + article.getPrixUnitaire());
					System.out.println("Prix vrac: " + article.getPrixVrac());
					System.out.println("Quantité vrac: " + article.getQuantiteVrac());
					System.out.println("Stock: " + article.getStock());
					System.out.println("------------");
				}
				break;
			case 0:
				System.out.println("Au revoir !");
				break;
			default:
				System.out.println("Choix invalide !");
				break;
			}
			if(choice!=0)
				choice = -1;
		} while (choice != 0);
		scanner.close();
	}
	public static void menu_employe(UtilisateurDAO user) {
		//	Création du client:
		EmployeDAO employe = new EmployeDAO(
				user.getConnection(),
				user.getDatabase(),
				user.getUtilisateur().getId(),
				user.getUtilisateur().getNom(),
				user.getUtilisateur().getMail(),
				user.getUtilisateur().getMdp(),
				user.getUtilisateur().getType()
				);
		int choice = -1;
		Scanner scanner = new Scanner(System.in);
		do {
			//	First data shielding : the number must be in a range from 0 to 7 :
			while(choice<0 || choice>9) {
				//	Display the menu:
				System.out.println("Menu EMPLOYE :");
				System.out.println("1. Ajouter un nouvel article");
				System.out.println("2. Supprimer article");
				System.out.println("3. Modifier le champ d'un article existant");
				System.out.println("4. Ajouter compte client");
				System.out.println("5. Supprimer compte client");
				System.out.println("6. Rapport de ventes: Visualiser le nombre total de ventes");
				System.out.println("7. Rapport de ventes: Visualiser le CA total: ");
				System.out.println("0. Quitter");

				//	Second data shielding : the number must be an integer:
				while(!scanner.hasNextInt()) {
					//	Error message:
					System.out.println("Erreur de saisie. Veuillez entrer un entier en 0 et 7");
					//	We "empty" scanner object to be able to enter a new value:
					scanner.next();
				}
				//	Input data:
				choice = scanner.nextInt();
			} 
			switch (choice) {
			case 1:
				//	L'employé créait un article:
				ArticleDAO article = null;
				article = employe.creer_article();
				employe.ajouter_article(article);
				break;
			case 2:
				String nom_article = "règle";
				//	Supprimer article
				employe.supprimer_article("cahier");
				break;
			case 3:
				String champ = "stock";
				employe.modifier_article(champ);
				break;
			case 4:
				ClientDAO new_client = null;
				new_client = employe.creer_client();
				employe.ajouter_client(new_client);
				break;
			case 5:
				champ = "Ginou";
				employe.supprimer_client(champ);
				break;
			case 6:
				//	Rapport de ventes: Visualiser les stocks total vendus:
				int stock_total_vendu = employe.rapport_ventes_stocks();
				System.out.println("Stocks vendus: " + stock_total_vendu);
				break;
			case 7:
				//	Rapport de ventes: CA et nombre de ventes
				System.out.println(employe.rapport_ventes_CA());
				
				break;
			case 8:
				//	Rapport de ventes: Nombre de ventes en fonction de chaque articles:

				Map<String, Double> caArticles = employe.ca_articles();

				// Afficher le contenu de ventesArticles
				System.out.println("Chiffre d'affaires par article :");
				for (Map.Entry<String, Double> entry : caArticles.entrySet()) {
					System.out.println("Article : " + entry.getKey() + " | CA : " + entry.getValue());
				}
			case 9:
				Map<String, Integer> quantiteArticles = employe.quantite_articles();
				// Afficher le contenu de ventesArticles
				System.out.println("Chiffre d'affaires par article :");
				for (Map.Entry<String, Integer> entry : quantiteArticles.entrySet()) {
					System.out.println("Article : " + entry.getKey() + " | Quantite : " + entry.getValue());
				}
				break;
			case 0:
				System.out.println("Au revoir !");
				break;
			default:
				System.out.println("Choix invalide !");
				break;
			}
			if(choice!=0)
				choice = -1;
		} while (choice != 0);
		scanner.close();
	}
}