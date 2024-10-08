package controller;

import dao.*;
import model.Articles;
import view.*;

import java.util.ArrayList;
import java.util.Map;

public class GUIController {

	public static void main(String[] args) {


		//initialise la BDD
		InitialisationDAO DAO = new InitialisationDAO();
		Boolean check1 = false;
		//Connexion utilisateur:
		UtilisateurDAO user = new UtilisateurDAO(DAO.getConnection(), DAO.getDatabase());

		//boucle de jeu
		do {
			MainFrame login = new MainFrame();
			//connexion
			if(login.GUI_Connexion()==1){
				if(user.se_connecter(login.get_email(), login.get_mdp())){
					if(user.getUtilisateur().getType().equals("EMPLOYE")){
						page_employe(user);
					}
					else {//client
						page_client(user);
					}
				}
			}
			//nouvelle inscription
			else {
				if(user.inscription(login.get_nom(),login.get_email(),login.get_mdp(),login.get_role())){
					if(user.se_connecter(login.get_email(), login.get_mdp())){
						if(user.getUtilisateur().getType().equals("EMPLOYE")){
							page_employe(user);
						}
						else {//client
							page_client(user);
						}
					}
				}
			}
		}while(true);
	}

	public static void  page_client(UtilisateurDAO user){

		boolean check=true;
		//Création du client:
		ClientDAO client = new ClientDAO(
				user.getConnection(),
				user.getDatabase(),
				user.getUtilisateur().getId(),
				user.getUtilisateur().getNom(),
				user.getUtilisateur().getMail(),
				user.getUtilisateur().getMdp(),
				user.getUtilisateur().getType()
		);

		//chercher Arrayliste Article
		ArrayList<Articles> articles = new ArrayList<>();
		articles = client.getArticlesDAO();

		//boucle de jeu
		do{
			Page_Client page_user = new Page_Client();
			page_user.actualise(articles.get(0),articles.get(1),articles.get(2),articles.get(3),client.getClient().getPanier().calculTotalPrize());
			int action=page_user.GUI_Client();

			if(action==2){//recherche
				String nom_A = page_user.get_recherche();
				for(int i=0;i<articles.size();i++){
					if(articles.get(i).getNom().equals(nom_A)){
						Articles x=articles.get(i);
						articles.remove(i);
						articles.add(0,x);
					}
				}
			}

			else if (action==1) {//ajouter un produit
				client.recherche_article(page_user.get_nom(),page_user.get_nb());
			}

			else if (action==0) {//passé à la page panier
				boolean x=true;
				do{
					//page panier
					Panier_GUI page_panier = new Panier_GUI();
					Articles supprime=page_panier.GUI_Panier(client.getClient().getPanier());
					if(supprime==null){//passe au règlement
						Valider_Achat cb=new Valider_Achat();
						cb.GUI_valider_panier();
						String choix = "terminer";
						// 'annuler' et 'attendre' aussi

						//	Créer notre commande:
						client.creer_commande();
						//	Procéder à la commande:
						client.proceder_commande(choix);
						x=false;
					}
					else{//supprimer un produit
						client.getClient().getPanier().deleteArticle(supprime);
					}
				}while (x);
			}
		}while (check);
	}

	public static void  page_employe(UtilisateurDAO user){
		Boolean quitter = false;
		//    Création de l'employé:
		EmployeDAO employe = new EmployeDAO(
				user.getConnection(),
				user.getDatabase(),
				user.getUtilisateur().getId(),
				user.getUtilisateur().getNom(),
				user.getUtilisateur().getMail(),
				user.getUtilisateur().getMdp(),
				user.getUtilisateur().getType()
		);
		//boucle de jeu
		do{
			ArrayList<Articles> articles = new ArrayList<>();
			articles = employe.getArticlesDAO();

			ArrayList<UtilisateurDAO> users = employe.getUsersDAO();
			Map<String, Double> caArticles = employe.ca_articles();
			Map<String, Integer> quantiteArticles = employe.quantite_articles();
			double caT=employe.rapport_ventes_CA();
			int qT=employe.rapport_ventes_stocks();

			Admin employeGUI = new Admin();
			int action=employeGUI.GUI_Admin(articles,users,quantiteArticles,caArticles,caT,qT);

			//ajouter du stock
			if(action==3){
				employe.incrementer(employeGUI.get_index());
			}

			//supprimer un article
			else if(action==4){
				employe.supprimer_article(articles.get(employeGUI.get_index()).getNom());
			}

			//ajoute un nouveau produit
			else if (action==0){
				New_Article page_new_a=new New_Article();
				page_new_a.GUI_nouveau_Article();
				employe.ajouter_article(new ArticleDAO(0,page_new_a.GUI_Article_getNom(),page_new_a.GUI_Article_getdescription(),page_new_a.GUI_Article_getprix_uni(),page_new_a.GUI_Article_getprix_V(),page_new_a.GUI_Article_getquantite_v(),page_new_a.GUI_Article_getquantite_s()));
			}

			//ajoute un nouveau compte
			else if (action==1) {
				New_Compte page_new_c=new New_Compte();
				page_new_c.GUI_new_Compte();
				user.inscription(page_new_c.GUI_Compte_getNom(),page_new_c.GUI_Compte_getemail(),page_new_c.GUI_Compte_getmdp(),page_new_c.GUI_Compte_gettype());
			}

			//supprime un compte
			else if (action==6) {
				employe.supprimer_client(users.get(employeGUI.get_index()).getUtilisateur().getNom());
			}

		}while(!quitter);

	}
}
