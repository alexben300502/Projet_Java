package dao;

import model.Articles;
import model.Client;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class ClientDAO extends UtilisateurDAO{
	/// ============================================================== ///
	///	Déclaration des attributs:
	/// ============================================================== ///
	private Client client = null;

	/// ============================================================== ///
	///	Constructeur par défaut:
	/// ============================================================== ///
	public ClientDAO(Connection c, Statement s) {
		super(c, s);
		this.client = new Client(0,null,null,null,"CLIENT");
	}

	/// ============================================================== ///
	///	Constructeur surchargé:
	/// ============================================================== ///
	public ClientDAO(Connection c, Statement s, int id, String n, String mail, String mdp, String type) {
		super(c, s);
		this.client = new Client(id, n, mail, mdp, type);
	}
	/// ============================================================== ///
	///	Getter:
	/// ============================================================== ///
	public Client getClient() {return this.client;}

	/// ============================================================== ///
	///	Méthodes:
	/// -------------------------------------------------------------- ///
	///	CASE 1:
	/// -------------------------------------------------------------- ///
	/** RECHERCHER UN ARTICLE A PARTIR DE SON NOM: */
	public Boolean recherche_article(String nom_article,int quantite){
		PreparedStatement prepare = null;
		ResultSet response = null;
		try {
			String sql = "SELECT * FROM articles WHERE nom = ?";
			prepare = super.getConnection().prepareStatement(sql);
			prepare.setString(1, nom_article);
			response = prepare.executeQuery();

			if(response.next()) {
				System.out.println("Article trouvé");
				if(response!=null) {
					//	Ajout de l'article dans le panier client:
					ajouter_article_panier(response,quantite);
				}
				return true;
			}
			else {
				System.out.println("Article non trouvé");
				return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		finally {
			if(response != null) {
				try {
					response.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(prepare != null) {
				try {
					prepare.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/** AJOUTER UN ARTICLE AU PANIER*/
	public void ajouter_article_panier(ResultSet response,int quantite) {
		try {
			//	On créé un objet article:
			ArticleDAO articles = new ArticleDAO(
					response.getInt("id"),
					response.getString("nom"),
					response.getString("description"),
					response.getDouble("prixUnitaire"),
					response.getDouble("prixVrac"),
					response.getInt("quantiteVrac"),
					response.getInt("stock")
					);
			if(!getClient().getPanier().panierVide()) {
				Boolean v = true;
				for (Map.Entry<Articles, Integer> i : getClient().getPanier().getPanier().entrySet()) {
					if (articles.getArticle().getId() == i.getKey().getId()) {
						int newQuantity = i.getValue() + quantite;
						getClient().getPanier().getPanier().put(i.getKey(), newQuantity);
					}
				}
				if (v) {
					for(int i=0;i<quantite;i++){
						getClient().ajouterArticle(articles.getArticle());
					}
				}
			}
			else {
				//	Ajout d'1 seul article au panier:
				for(int i=0;i<quantite;i++){
					getClient().ajouterArticle(articles.getArticle());
				}
			}
			System.out.println("Article dans le panier!");
			getClient().getPanier().afficherPanier();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/// -------------------------------------------------------------- ///
	///	CASE 2:
	/// -------------------------------------------------------------- ///
	/** EFFECTUER LA COMMANDE: */
	public void creer_commande(){
		//	Date actuelle pour la commande:
		LocalDateTime maintenant = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(maintenant);

		//	On vérifie si le panier est vide:
		if(client.getPanier().panierVide()) {
			System.out.println("Pas de commande à effectuer: panier vide.");
			return;
		}
		//	Sinon on créé une commande à partir de notre panier:
		else
		{
			for (Map.Entry<Articles, Integer> i : getClient().getPanier().getPanier().entrySet()) {
				String sql = "INSERT INTO commandes"
						+ "(client_id,"
						+ "date_create,"
						+ "statut,"
						+ "article_id,"
						+ "quantite)" 

		        		+ "VALUES"
		        		+ "('"+getClient().getId()+"',"
		        		+ "'"+timestamp+"',"
		        		+ "'"+"EN_COURS"+"',"
		        		+ "'"+i.getKey().getId()+"',"
		        		+ "'"+i.getValue()+"')"
		        		;
				try {
					super.getDatabase().execute(sql);
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
		System.out.println("Table commande remplie...");
	}

	/** VALIDER LA COMMANDE:*/
	public void proceder_commande(String choix) {
		double prixTotal = 0.0;
		//	Créer notre commande:
		creer_commande();


		//	On calcule puis affiche le prix total du panier:
		prixTotal = client.getPanier().calculTotalPrize();

		System.out.println(client.getNom()+", souhaitez-vous effectuer la commande ?");
		System.out.println("Le prix de la commande est de: "+ prixTotal);


		if(choix.equals("terminer")) {
			terminer_commande();
		}
		else if(choix.equals("annuler")) {
			annuler_commande();
		}
		else if(choix.equals("attendre")) {
			attendre_commande();
		}
		else
			System.out.println("Erreur pour confirmer la commande.");

	}

	/** TERMINER LA COMMANDE:*/
	public void terminer_commande() {
		//	On change l'état des commandes:
		for (Map.Entry<Articles, Integer> i : getClient().getPanier().getPanier().entrySet()) {
			String sql = "UPDATE commandes SET statut="
					+ "'TERMINE' WHERE statut='EN_COURS' AND client_id="
					+ getClient().getId() +
					";"
					;
			try {
				super.getDatabase().execute(sql);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			decrementer_stock(i.getValue(), i.getKey().getId());
		}
		//	On remet le panier à 0:
		client.getPanier().initialiserPanier();
		System.out.println("Commande effectuée avec succès et panier vidé.");
	}

	/** DECREMENTER LE STOCK*/
	public void decrementer_stock(int decrement, int id_article) {
		PreparedStatement prepare = null;
		try {
			//	On modifie le stock initiale:
			String sql =  "UPDATE articles SET stock = stock - ? WHERE id = ?";
			prepare = super.getConnection().prepareStatement(sql);
			prepare.setInt(1, decrement);
			prepare.setInt(2,  id_article);

			prepare.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}		finally {
			if(prepare != null) {
				try {
					prepare.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/** METTRE EN ATTENTE LA COMMANDE:*/
	public void attendre_commande() {
		//	On change l'état des commandes:
		String sql = "UPDATE commandes SET statut="
				+ "'EN_ATTENTE' WHERE statut='EN_COURS' AND client_id="
				+ getClient().getId() +
				";"
				;
		try {
			super.getDatabase().execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		System.out.println("Commande en attente et panier non vidé.");
	}

	/** ANNULER LA COMMANDE:*/
	public void annuler_commande() {
		//	On change l'état des commandes:
		String sql = "UPDATE commandes SET statut="
				+ "'ANNULEE' WHERE statut='EN_COURS' AND client_id="
				+ getClient().getId() +
				";"
				;
		try {
			super.getDatabase().execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		//	On remet le panier à 0:
		client.getPanier().initialiserPanier();
		System.out.println("Commande annulé et panier vidé.");
	}

	/// -------------------------------------------------------------- ///
	///	CASE 3:
	/// -------------------------------------------------------------- ///
	/** HISTORIQUE COMMANDES*/
	public void historique_commandes() {
		PreparedStatement prepare = null;
		ResultSet response = null;
		try {
			String sql = "SELECT * FROM commandes WHERE client_id = ? AND statut='TERMINE'";
			prepare = super.getConnection().prepareStatement(sql);
			prepare.setInt(1, getClient().getId());
			response = prepare.executeQuery();

			if(response.next()) {
				System.out.println("Article trouvé");
				afficher_commandes(response);
			}
			else {
				System.out.println("Historique vide");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		finally {
			if(response != null) {
				try {
					response.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(prepare != null) {
				try {
					prepare.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	/** AFFICHER DYNAMIQUEMENT L'HITORIQUE DES COMMANDES*/
	public void afficher_commandes(ResultSet response) {
		try {
			while (response.next()) {
				System.out.print("Date: "+response.getString("date_create"));
				System.out.print(" Id article: "+response.getString("article_id"));
				System.out.println(" Quantite: "+response.getString("quantite"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Articles> getArticlesDAO() {
		ArrayList<Articles> articles = new ArrayList<>();
		PreparedStatement prepare = null;
		ResultSet response = null;

		try {
			String sql = "SELECT * FROM articles";
			prepare = super.getConnection().prepareStatement(sql);
			response = prepare.executeQuery();

			if (!response.next()) {
				System.out.println("Aucun article trouvé");
			} else {
				do {
					System.out.println("Article " + response.getString("nom") + " trouvé");
					Articles article = new Articles(
							response.getInt("id"),
							response.getString("nom"),
							response.getString("description"),
							response.getDouble("prixUnitaire"),
							response.getDouble("prixVrac"),
							response.getInt("quantiteVrac"),
							response.getInt("stock")
					);
					articles.add(article);
				} while (response.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (prepare != null) {
				try {
					prepare.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return articles;
	}
}
