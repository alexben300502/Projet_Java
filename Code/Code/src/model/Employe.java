package model;

public class Employe extends Utilisateur {
	/// ============================================================== ///
	///	Constructeur surchargé:
	/// ============================================================== ///
	public Employe(int id, String n, String mail, String mdp, String type) {
		super(id, n, mail, mdp, type);
	}	
}
