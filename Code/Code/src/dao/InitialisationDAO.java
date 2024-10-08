package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitialisationDAO {
	/// ============================================================== ///
	///	Déclaration des attributs:
	/// ============================================================== ///
	private Connection connect = null;
	private Statement database = null;


	public InitialisationDAO(){
		try {
			// Enregistrement du driver JDBC MySQL
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Ouverture de la connexion avec la database:
			String url = "jdbc:mysql://localhost:3306/shopping";
			String username = "root";
			String password = "root";
			connect = DriverManager.getConnection(url, username, password);
			database = connect.createStatement();
			//	Création des tables dans la Base de données:
			create_tables(getConnection(), getDatabase());

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException ee) {
			ee.printStackTrace();
		}
	}

	/// ============================================================== ///
	///	Getters:
	/// ============================================================== ///
	public Connection getConnection() {return this.connect;}
	public Statement getDatabase() {return this.database;}

	/// ============================================================== ///
	///	Fonction de création des tables:
	/// ============================================================== ///
	public void create_tables(Connection connect, Statement database) {
		String sql = null;
		try {
			//	----------------------------------------------------------	//
			// Création de la table utilisateurs:
			//	----------------------------------------------------------	//
			database = connect.createStatement();
			sql =	"CREATE TABLE IF NOT EXISTS utilisateurs " +
					"(id INT AUTO_INCREMENT, " +
					"nom VARCHAR(50) NOT NULL, " +
					"mail VARCHAR(50) NOT NULL, " + 
					"password VARCHAR(50) NOT NULL, " + 
					"type ENUM('EMPLOYE', 'CLIENT') NOT NULL, " +
					"PRIMARY KEY (id))";
			database.executeUpdate(sql);
			System.out.println("Table utilisateur créée avec succès...");

			//	----------------------------------------------------------	//
			// Création de la table articles:
			//	----------------------------------------------------------	//
			database = connect.createStatement();
			sql =	"CREATE TABLE IF NOT EXISTS articles" +
					"(id INT AUTO_INCREMENT," +
					"nom VARCHAR(55) NOT NULL," +
					"description TEXT," +
					/*10 chiffres au total avec une précision 
					 * de 2 chiffres après la virgule :*/ 
					 "prixUnitaire DECIMAL(10,2) NOT NULL," +
					 "prixVrac DECIMAL(10,2) NOT NULL," +
					 "quantiteVrac INT NOT NULL," +
					 "stock INT NOT NULL," +
					 /* L'attribut PRIMARY KEY est utilisé pour définir une clé primaire pour une table de base
					  * de données. Elle est utilisée pour garantir l'intégrité des data et faciliter les opérations
					  * de recherche et de jointure entre les tables:*/
					  "PRIMARY KEY (id))";
			database.executeUpdate(sql);
			System.out.println("Table articles créée avec succès...");

			//	----------------------------------------------------------	//
			// Création de la table commandes:
			//	----------------------------------------------------------	//
			database = connect.createStatement();
			sql =	"CREATE TABLE IF NOT EXISTS commandes" +
					"(id INT AUTO_INCREMENT," +
					"client_id INT NOT NULL," +
					/* L'attribut TIMESTAMP donne la date actuelle dès que la ligne de la table est créée 
					 * ou modifiée:*/
					 "date_create TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +

					/* L'attribut ENNUM est utilisé pour représenter un ensemble de valeurs possibles pour
					 * un champ dans une table de base de données:*/
					 "statut ENUM('EN_ATTENTE', 'EN_COURS', 'TERMINE', 'ANNULEE') NOT NULL," +

					 "article_id INT NOT NULL," +
					 "quantite INT NOT NULL," +

					 /* La clé primaire composé ci-dessous permet de s'assurer qu'un même article ne peut être ajouté
					  * qu'une seule fois dans une commande donnée.*/
					  "PRIMARY KEY (id, article_id)," +

					/* La contrainte de clé étrangère garantit que la valeur de la colonne "client_id"
					 * dans la table "commandes" correspond toujours à une valeur existante dans la colonne
					 * "id" de la table "utilisateurs".*/
					 "FOREIGN KEY (client_id) REFERENCES utilisateurs(id),"+
					 "FOREIGN KEY (article_id) REFERENCES articles(id))"
					 ;
			database.executeUpdate(sql);
			System.out.println("Table des commandes créée avec succès...");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
