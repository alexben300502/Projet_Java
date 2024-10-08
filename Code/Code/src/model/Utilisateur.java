package model;

public class Utilisateur {
	/// ============================================================== ///
	/// Déclaration des attributs:
	/// ============================================================== ///
	protected int id;
	protected String nom;
	protected String mail;
	protected String mdp;
	protected String type;
	
	/// ============================================================== ///
	///	Contructeur surchargé:
	/// ============================================================== ///
	public Utilisateur(int id, String n, String mail, String mdp, String type){
		this.id = id;
		this.nom = n;
		this.mail = mail;
		this.mdp = mdp;
		this.type = type;
	}
	/// ============================================================== ///
	///	Getters:
	/// ============================================================== ///
	public int getId() {return this.id;}
	public String getNom() {return this.nom;}
	public String getMail() {return this.mail;}
	public String getMdp() {return this.mdp;}
	public String getType() {return this.type;}

	/// ============================================================== ///
	///	Setters:
	/// ============================================================== ///
	public void setId(int id) {this.id = id;}
	public void setNom(String n) {this.nom = n;}
	public void setMail(String m) {this.mail=m;}
	public void setMDP(String mdp) {this.mdp=mdp;}
	public void setType(String type) {this.type=type;}
	
	/// ============================================================== ///
	///	Methods:
	/// ============================================================== ///
	public void display() {
		System.out.println("Id: "+ getId());
		System.out.println("Nom: "+ getNom());
		System.out.println("Mail: "+ getMail());
		System.out.println("Mdo: "+ getMdp());
		System.out.println("Type: "+ getType());
	}
}
