package model;

import java.util.Scanner;

public class Client extends Utilisateur {
	/// ============================================================== ///
	///	Déclaration des attributs:
	/// ============================================================== ///
	private Panier panier = null;
	
	/// ============================================================== ///
	///	Constructeur surchargé:
	/// ============================================================== ///
	public Client(int id, String n, String mail, String mdp, String type) {
		super(id, n, mail, mdp, type);
		this.panier = new Panier();
	}
	/// ============================================================== ///
	///	Getters:
	/// ============================================================== ///
	public Panier getPanier() {return this.panier;}

	/// ============================================================== ///
	///	Setters:
	/// ============================================================== ///
	public void setPanier(Panier p) {this.panier = p;}
	
	/// ============================================================== ///
	///	Methods:
	/// ============================================================== ///
	public void ajouterArticle(Articles p) {
		getPanier().addArticle(p);
	}
	
	@SuppressWarnings("resource")
	public void creer_client() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Nom: ");
		String nom = scanner.nextLine();
		System.out.println("Mail: ");
		String mail = scanner.nextLine();
		System.out.println("Mot de passe: ");
		String mdp = scanner.nextLine();
		
		super.setNom(nom);
		super.setMail(mail);
		super.setMDP(mdp);
	}
}
