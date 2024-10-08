package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import model.Utilisateur;

public class UtilisateurDAO {
	/// ============================================================== ///
	///	Déclaration des attributs:
	/// ============================================================== ///
	protected Utilisateur utilisateur = null;
	protected Connection connect = null;
	protected Statement database = null;

	/// ============================================================== ///
	///	Constructeur surchargé:
	/// ============================================================== ///
	public UtilisateurDAO(Connection c, Statement s){
		this.connect = c;
		this.database = s;
	}

    public UtilisateurDAO(int id, String nom, String mail, String password, String type) {
		this.utilisateur=new Utilisateur(id,nom,mail,password,type);
    }

    /// ============================================================== ///
	///	Getters:
	/// ============================================================== ///
	public Utilisateur getUtilisateur() {return this.utilisateur;}
	public Connection getConnection() {return this.connect;}
	public Statement getDatabase() {return this.database;}

	/// ============================================================== ///
	///	Methods:
	/// ============================================================== ///
	public boolean inscription(String nom,String mail,String mdp,String type) {
		/*Scanner scanner = new Scanner(System.in);
		System.out.println("SAISIR VOTRE NOM:");
		String nom = scanner.nextLine();
		System.out.println("SAISIR VOTRE MAIL:");
		String mail = scanner.nextLine();
		System.out.println("SAISIR VOTRE MDP:");
		String mdp = scanner.nextLine();
		System.out.println("SAISIR VOTRE TYPE (EMPLOYE, CLIENT):");
		String type = scanner.nextLine();*/

		PreparedStatement pstmt = null;
		int check = 0;

		try {
			String sql = "INSERT INTO utilisateurs (nom, mail, password, type) VALUES (?, ?, ?, ?)";
			pstmt = getConnection().prepareStatement(sql);
			pstmt.setString(1, nom);
			pstmt.setString(2, mail);
			pstmt.setString(3, mdp);
			pstmt.setString(4, type);

			check = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		if (check > 0) {
			System.out.println("Inscription réussie !");
			return true;
		} else {
			System.out.println("Erreur lors de l'inscription.");
			return false;
		}
	}
	public Boolean se_connecter(String mail, String mdp) {
		Boolean check = false;
		ResultSet response = null;
		PreparedStatement pstmt = null;

		System.out.println("test co");

		/*@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("SAISIR VOTRE MAIL:");
		String mail = scanner.nextLine();
		System.out.println("SAISIR VOTRE MDP:");
		String mdp = scanner.nextLine();*/

		//	Vérifier si la connexion est valide:
		try {
			String sql = "SELECT * FROM utilisateurs WHERE mail=? AND password=?";
			pstmt = getConnection().prepareStatement(sql);
			pstmt.setString(1, mail);
			pstmt.setString(2, mdp);
			response = pstmt.executeQuery();

			if(response.next())
				check = true;
				System.out.println("test co2");

		}catch (SQLException e) {
			e.printStackTrace();
		}
		//	Réponse:
		if(check) {
			//	Récupération des informations dans la classe Employe:
			try {

				this.utilisateur = new Utilisateur(
						response.getInt("id"),
						response.getString("nom"),
						response.getString("mail"),
						response.getString("password"),
						response.getString("type")
						);
				System.out.println("test co3");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Bienvenue parmis nous "+this.utilisateur.getNom()+ " !");
		}
		else {
			System.out.println("Mince, vous n'êtes pas connecté.");
		}
		System.out.println("test co4");
		return check;
	}
}
