package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Articles;
import model.Employe;

public class EmployeDAO extends UtilisateurDAO{
	/// ============================================================== ///
	///	Déclaration des attributs:
	/// ============================================================== ///
	private Employe employe= null;

	/// ============================================================== ///
	///	Constructeur surchargé:
	/// ============================================================== ///
	public EmployeDAO(Connection c, Statement s, int id, String n, String mail, String mdp, String type) {
		super(c, s);
		this.employe = new Employe(id, n, mail, mdp, type);
	}
	/// ============================================================== ///
	///	Getters:
	/// ============================================================== ///
	public Employe getEmploye() {return this.employe;}

	/// ============================================================== ///
	///	Méthodes:
	/// ============================================================== ///
	/// -------------------------------------------------------------- ///
	///	CASE 1:
	/// -------------------------------------------------------------- ///	
	/** Créer un article */
	public ArticleDAO creer_article() {
		ArticleDAO article = new ArticleDAO();
		System.out.println("Créer un article:");
		article.getArticle().creer_article();
		return article;
	}
	/**	Ajouter un article */
	public void ajouter_article(ArticleDAO article) {
		String sql = "INSERT INTO articles"
				+ "(nom,"
				+ "description,"
				+ "prixUnitaire,"
				+ "prixVrac,"
				+ "quantiteVrac,"
				+ "stock)"
				+ "VALUES"
				+ "('"+article.getArticle().getNom().replace("'", "''")+"',"
				+ "'"+article.getArticle().getDescription().replace("'", "''")+"',"
				+ "'"+article.getArticle().getPrixUnitaire()+"',"
				+ "'"+article.getArticle().getPrixVrac()+"',"
				+ "'"+article.getArticle().getQuantiteVrac()+"',"
				+ "'"+article.getArticle().getStock()+"');"
				;
		try {
			super.getDatabase().execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/// -------------------------------------------------------------- ///
	///	CASE 2:
	/// -------------------------------------------------------------- ///
	public void supprimer_article(String n_article) {
		PreparedStatement prepare = null;
		int response;

		try {
			// Récupérer l'ID de l'article en fonction du nom
			String sql = "SELECT id FROM articles WHERE nom = ?";
			prepare = super.getConnection().prepareStatement(sql);
			prepare.setString(1, n_article);
			ResultSet result = prepare.executeQuery();

			if (result.next()) {
				int article_id = result.getInt("id");
				result.close();

				// Supprimer les références dans la table commandes
				sql = "DELETE FROM commandes WHERE article_id = ?";
				prepare = super.getConnection().prepareStatement(sql);
				prepare.setInt(1, article_id);
				prepare.executeUpdate();

				// Supprimer l'article de la table articles
				sql = "DELETE FROM articles WHERE nom = ?";
				prepare = super.getConnection().prepareStatement(sql);
				prepare.setString(1, n_article);
				response = prepare.executeUpdate();

				if (response == 1)
					System.out.println("Article supprimé avec succès!");
				else
					System.out.println("Article non trouvé.");
			} else {
				System.out.println("Article non trouvé.");
			}
		} catch (SQLException e) {
			System.out.println("Erreur de suppression de l'article.");
			e.printStackTrace();
		} finally {
			if (prepare != null) {
				try {
					prepare.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/// -------------------------------------------------------------- ///
	///	CASE 3:
	/// -------------------------------------------------------------- ///
	public void modifier_article(String champ) {
		PreparedStatement prepare = null;
		int response;
		String valeur = "200";
		String nom_article = "compas";
		String sql = "UPDATE articles SET " + champ + " = '" + valeur + "' WHERE nom = '" + nom_article + "';";
		try {
			prepare = super.getConnection().prepareStatement(sql);
			response = prepare.executeUpdate(sql);
			System.out.println(response);
			if(response==1)
				System.out.println("Article modifié avec succès!");
			else
				System.out.println("Article non modifié.");
		} catch (SQLException e) {
			System.out.println("Erreur de suppression de l'article.");
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
	/// -------------------------------------------------------------- ///
	///	CASE 4:
	/// -------------------------------------------------------------- ///
	/** Créer un client 
	 * @return */
	public ClientDAO creer_client() {
		ClientDAO client = new ClientDAO(super.getConnection(), super.getDatabase());
		System.out.println("Créer un client:");
		client.getClient().creer_client();
		return client;
	}

	public void incrementer(int id_article) {
		int decrement=100;
		PreparedStatement prepare = null;
		try {
			//	On modifie le stock initiale:
			String sql =  "UPDATE articles SET stock = stock + ? WHERE id = ?";
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
	/** Ajouter un client */
	public void ajouter_client(ClientDAO client) {

		String sql = "INSERT INTO utilisateurs"
				+ " (nom, mail, password, type)"
				+ " VALUES"
				+ " ('" + client.getClient().getNom() + "',"
				+ " '" + client.getClient().getMail() + "',"
				+ " '" + client.getClient().getMdp() + "',"                
				+ " 'CLIENT')"
				;
		try {
			super.getDatabase().execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void supprimer_Article(String n_article) {
		PreparedStatement prepare = null;
		int response;
		String sql = "SELECT id FROM articles WHERE nom = ?";
		try {
			prepare = super.getConnection().prepareStatement(sql);
			prepare.setString(1, n_article);
			ResultSet result = prepare.executeQuery();
			if (result.next()) {
				int id_article = result.getInt("id");
				result.close();
				/*
				sql = "DELETE FROM commandes WHERE article_id = ?";
				prepare = super.getConnection().prepareStatement(sql);
				prepare.setInt(1, id_article);
				prepare.executeUpdate();
				*/
				sql = "UPDATE commandes SET id='0' WHERE article_id = ?";
				prepare = super.getConnection().prepareStatement(sql);
				prepare.setInt(1, id_article);
				prepare.executeUpdate();

				sql = "DELETE FROM articles WHERE id = ?";
				prepare = super.getConnection().prepareStatement(sql);
				prepare.setInt(1, id_article);
				prepare.executeUpdate();


				/*sql = "DELETE FROM utilisateurs WHERE nom = ? AND type = 'CLIENT'";
				prepare = super.getConnection().prepareStatement(sql);
				prepare.setString(1, n_client);
				response = prepare.executeUpdate();
				System.out.println(response);*/
				//if (response == 1)
					System.out.println("Client supprimé avec succès!");
			//	else
			//		System.out.println("Client non trouvé.");
			} else {
				System.out.println("Client non trouvé.");
			}
		} catch (SQLException e) {
			System.out.println("Erreur de suppression de client.");
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
	/// -------------------------------------------------------------- ///
	///	CASE 5:
	/// -------------------------------------------------------------- ///
	@SuppressWarnings("resource")
	public void supprimer_client(String n_client) {
		PreparedStatement prepare = null;
		int response;
		String sql = "SELECT id FROM utilisateurs WHERE nom = ?";
		try {
			prepare = super.getConnection().prepareStatement(sql);
			prepare.setString(1, n_client);
			ResultSet result = prepare.executeQuery();
			if (result.next()) {
				int client_id = result.getInt("id");
				result.close();

				sql = "DELETE FROM commandes WHERE client_id = ?";
				prepare = super.getConnection().prepareStatement(sql);
				prepare.setInt(1, client_id);
				prepare.executeUpdate();

				sql = "DELETE FROM utilisateurs WHERE nom = ? AND type = 'CLIENT'";
				prepare = super.getConnection().prepareStatement(sql);
				prepare.setString(1, n_client);
				response = prepare.executeUpdate();
				System.out.println(response);
				if (response == 1)
					System.out.println("Client supprimé avec succès!");
				else
					System.out.println("Client non trouvé.");
			} else {
				System.out.println("Client non trouvé.");
			}
		} catch (SQLException e) {
			System.out.println("Erreur de suppression de client.");
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
	/// -------------------------------------------------------------- ///
	///	CASE 6:
	/// -------------------------------------------------------------- ///
	public int rapport_ventes_stocks() {
		PreparedStatement prepare = null;
		ResultSet response = null;
		int stock_total_vendu;
		try {
			String sql = "SELECT SUM(quantite) AS totalQ FROM commandes WHERE statut='TERMINE'";

			prepare = super.getConnection().prepareStatement(sql);
			// Pas besoin de setString(1, nom_article) puisqu'on n'utilise pas de paramètre dans la requête SQL.

			response = prepare.executeQuery();

			if (response.next()) {
				stock_total_vendu = response.getInt("totalQ"); // Corrigez "total_quantite" en "totalQ" pour correspondre à l'alias défini dans la requête SQL.
				return stock_total_vendu;
			} else {
				System.out.println("Erreur lors du calcul des stocks vendus.");
				return 0;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
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
	/// -------------------------------------------------------------- ///
	///	CASE 7:
	/// -------------------------------------------------------------- ///
	public double rapport_ventes_CA() {
	    PreparedStatement prepare = null;
	    ResultSet response = null;
	    double ca_total = 0;

	    try {
	        String sql = "SELECT c.quantite, a.prixUnitaire, a.prixVrac, a.quantiteVrac " +
	                     "FROM commandes c " +
	                     "JOIN articles a ON c.article_id = a.id " +
	                     "WHERE c.statut = 'TERMINE'";

	        prepare = super.getConnection().prepareStatement(sql);
	        response = prepare.executeQuery();

	        while (response.next()) {
	            int quantite = response.getInt("quantite");
	            double prixUnitaire = response.getDouble("prixUnitaire");
	            double prixVrac = response.getDouble("prixVrac");
	            int quantiteVrac = response.getInt("quantiteVrac");

	            int lotVrac = 1;
	            int articlesRestants = quantite - quantiteVrac;

	            if (quantite < quantiteVrac) {
	                ca_total += quantite * prixUnitaire;
	            } else {
	                while (articlesRestants >= quantiteVrac) {
	                    lotVrac++;
	                    articlesRestants -= quantiteVrac;
	                }
	                ca_total += lotVrac * prixVrac * quantiteVrac + articlesRestants * prixUnitaire;
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
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
	    return ca_total;
	}

	public ArrayList<UtilisateurDAO> getUsersDAO() {
		ArrayList<UtilisateurDAO> utilisateur = new ArrayList<>();
		PreparedStatement prepare = null;
		ResultSet response = null;

		System.out.println("go1");

		try {
			String sql = "SELECT * FROM utilisateurs";
			prepare = super.getConnection().prepareStatement(sql);
			response = prepare.executeQuery();
			System.out.println("go2");

			if (!response.next()) {
				System.out.println("Aucun utilisateur trouvé");
			} else {
				do {
					//System.out.println("Article " + response.getString("nom") + " trouvé");
					UtilisateurDAO nouvel_utilisateur=new UtilisateurDAO(
							response.getInt("id"),
							response.getString("nom"),
							response.getString("mail"),
							response.getString("password"),
							response.getString("type")
					);
					System.out.println("user " + nouvel_utilisateur.getUtilisateur().getNom() + " trouvé");
					utilisateur.add(nouvel_utilisateur);

				} while (response.next());
			}
		} catch (SQLException e) {
			System.out.println("rip");
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
		System.out.println(utilisateur.get(0).getUtilisateur().getNom());
		System.out.println(utilisateur.get(1).getUtilisateur().getNom());
		return utilisateur;
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
	/// -------------------------------------------------------------- ///
	///    CASE 9:
	/// -------------------------------------------------------------- ///
	public Map<String, Integer> quantite_articles() {
		Map<String, Integer> ventes_articles = new HashMap<>();

		PreparedStatement prepare = null;
		ResultSet response = null;
		try {
			String sql = "SELECT a.nom, c.quantite " +
					"FROM commandes c " +
					"JOIN articles a ON c.article_id = a.id " +
					"WHERE c.statut = 'TERMINE'";

			prepare = super.getConnection().prepareStatement(sql);
			response = prepare.executeQuery();

			while (response.next()) {
				String nom_article = response.getString("nom");
				int quantite = response.getInt("quantite");

				ventes_articles.put(nom_article, ventes_articles.getOrDefault(nom_article, 0) + quantite);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
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

		return ventes_articles;
	}
	/// ----------------------------------- ///
	///	CASE 8:
	/// -------------------------------------------------------------- ///
	public Map<String, Double> ca_articles() {
		Map<String, Double> ventes_articles = new HashMap<>();
		PreparedStatement prepare = null;
		ResultSet response = null;
		try {
			//	Requête SQL pour créer un table de jointure à partir de l'id de la table article et de la table commandes:
			String sql = "SELECT a.nom, c.quantite, a.prixUnitaire, a.prixVrac, a.quantiteVrac " +
					"FROM commandes c " +
					"JOIN articles a ON c.article_id = a.id " +
					"WHERE c.statut = 'TERMINE'";

			prepare = super.getConnection().prepareStatement(sql);
			response = prepare.executeQuery();

			//	Calcul du CA total pour chaque article:
			while (response.next()) {
				String nom_article = response.getString("nom");
				int quantite = response.getInt("quantite");
				double prixUnitaire = response.getDouble("prixUnitaire");
				double prixVrac = response.getDouble("prixVrac");
				int quantiteVrac = response.getInt("quantiteVrac");

				int lotVrac = 0;
				int articlesRestants = quantite;
				double ca_total = 0;

				if (quantite < quantiteVrac) {
					ca_total += quantite * prixUnitaire;
				} else {
					while (quantite >= quantiteVrac) {
						lotVrac++;
						quantite -= quantiteVrac;
					}
					ca_total += lotVrac * prixVrac * quantiteVrac + quantite * prixUnitaire;
				}
				System.out.println("ca_total: "+ca_total);
				//	On ajoute l'article dans la map:
				ventes_articles.put(nom_article, ventes_articles.getOrDefault(nom_article, 0.0) + ca_total);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
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

		return ventes_articles;
	}
}





