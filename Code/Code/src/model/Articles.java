package model;

import java.util.Scanner;

public class Articles {
	/// ============================================================== ///
	///	Déclaration des attributs:
	/// ============================================================== ///
	private int id;
	private String nom;
	private String description;
	private double prixUnitaire;
	private double prixVrac;
	private int quantiteVrac;
	private int stock;

	/// ============================================================== ///
	///	Constructeur par défaut:
	/// ============================================================== ///
	public Articles(){
		this.id=0;
		this.nom = "";
		this.description="";
		this.prixUnitaire=0;
		this.prixVrac=0;
		this.quantiteVrac=0;
		this.stock = 0;
	}

	/// ============================================================== ///
	///	Constructeur surchargé:
	/// ============================================================== ///
	public Articles(int n,String nn, String d, double prixU, double pv, int qv, int s){
		this.id=n;
		this.nom = nn;
		this.description=d;
		this.prixUnitaire=prixU;
		this.prixVrac=pv;
		this.quantiteVrac=qv;
		this.stock = s;
	}
	/// ============================================================== ///
	/// Getters:
	/// ============================================================== ///
	public int getId() {return this.id;}
	public String getNom() {return this.nom;}
	public String getDescription() {return this.description;}
	public double getPrixUnitaire() {return this.prixUnitaire;}
	public double getPrixVrac() {return this.prixVrac;}
	public int getQuantiteVrac() {return this.quantiteVrac;}
	public int getStock() {return this.stock;}
	/// ============================================================== ///
	///	Setters:
	/// ============================================================== ///
	public void setId(int i) {this.id=i;}
	public void setNom(String name) {this.nom = name;}
	public void setDescription(String d) {this.description=d;}
	public void setPrixUnitaire(double p) {this.prixUnitaire=p;}
	public void setPrixVrac(double p) {this.prixVrac=p;}
	public void setQuantiteVrac(int q) {this.quantiteVrac=q;}
	public void setStock(int s) {this.stock=s;}
	/// ============================================================== ///
	///	Methods:
	/// ============================================================== ///
	public void afficher_article() {
		System.out.println("Informations de l'article:");
		System.out.println("Id: "+getId());
		System.out.println("Nom: "+getNom());
		System.out.println("Description: "+getDescription());
		System.out.println("Prix Unitaire: "+getPrixUnitaire());
		System.out.println("Prix Vrac: "+getPrixVrac());
		System.out.println("Quantité vrac: "+getQuantiteVrac());
		System.out.println("Stock: "+getStock());
	}
	public void creer_article() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("Nom: ");
		String nom = scanner.nextLine();
		System.out.println("Description: ");
		String description = scanner.nextLine();
		System.out.println("Prix unitaire: ");
		double prixUnitaire = scanner.nextDouble();
		System.out.println("Prix Vrac: ");
		double prixVrac = scanner.nextDouble();
		System.out.println("Quantite Vrac: ");
		int quantiteVrac = scanner.nextInt();
		System.out.println("Stock: ");
		int stock = scanner.nextInt();

		setNom(nom);
		setDescription(description);
		setId(0);
		setPrixUnitaire(prixUnitaire);
		setPrixVrac(prixVrac);
		setQuantiteVrac(quantiteVrac);
		setStock(stock);

	}
}
