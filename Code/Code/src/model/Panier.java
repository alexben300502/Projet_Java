package model;

import java.util.*;

public class Panier {
	/// ============================================================== ///
	///	Déclaration des variables:
	/// ============================================================== ///
	private int id_panier;
	private Map<Articles, Integer> panier=null;
	/// ============================================================== ///
	///	Constructeur par défaut:
	/// ============================================================== ///
	public Panier(){
		this.panier = new HashMap<>();
	}
	/// ============================================================== ///
	///	Getters:
	/// ============================================================== ///
	public int getIdPanier() {return this.id_panier;}
	public Map<Articles, Integer> getPanier(){return this.panier;}
	/// ============================================================== ///
	///	Setters:
	/// ============================================================== ///
	public void setIdPanier(int i) {this.id_panier=i;}
	/// ============================================================== ///
	///	Methods:
	/// ============================================================== ///
	/** AFFICHER LE PANIER */
	public void afficherPanier() {
		if(panier != null && !panier.isEmpty()) {
			System.out.println("Panier client:");
			for (Map.Entry<Articles, Integer> i : panier.entrySet()) {
				Articles article = i.getKey();
				Integer quantite = i.getValue();
				System.out.println("Article: " + article + ", Quantité: " + quantite);
				article.afficher_article();
			}
		} else {
			System.out.println("Le panier est vide.");
		}
	}

	/** AJOUTER UN ARTICLE AU PANIER */
	public void addArticle(Articles a) {
		//	Vérifier si l'article est déjà dans le panier:
		if(getPanier().containsKey(a)) {
			int newQuantity = getPanier().get(a) + 1;
			getPanier().put(a, newQuantity);
		}
		//	Dans le cas contraire, on ajoute l'article au panier:
		else {
			getPanier().put(a, 1);
		}
	}
	/** MODIFIER LA QUANTITE D'UN ARTICLE DANS LE PANIER */
	public void setQuantity(Articles a, Integer newQuantity) {
		if(getPanier().containsKey(a)) {
			getPanier().put(a, newQuantity);
		}
		else{
			System.out.println("Erreur: L'article que vous souhaitez supprimer n'existe pas.");
		}
	}
	/**	SUPPRIMER UN ARTICLE DE LA COMMANDE */
	public void deleteArticle(Articles a) {
		if(getPanier().containsKey(a)) {
			getPanier().remove(a);
		}
		else{
			System.out.println("Erreur: L'article que vous souhaitez supprimer n'existe pas.");
		}
	}
	/**	CALCULER LE PRIX TOTAL DE LA COMMANDE */
	public double calculTotalPrize() {
		double prixTotal = 0.0;

		///	Parcours de la Map:
		for(Map.Entry<Articles, Integer> i  :getPanier().entrySet()) {
			Articles article = i.getKey();
			int quantite = i.getValue();
			
			int quantiteVrac = article.getQuantiteVrac();
			double prixVrac = article.getPrixVrac();
			double prixUnitaire = article.getPrixUnitaire();
			
			int lotVrac = 1;
			
			int articlesRestants = quantite-quantiteVrac;

			if(quantite<quantiteVrac)
				prixTotal += quantite*prixUnitaire;
			else {
				while(articlesRestants>=quantiteVrac) {
					lotVrac++;
					articlesRestants -= quantiteVrac;
				}
				prixTotal += lotVrac*prixVrac*quantiteVrac + articlesRestants*prixUnitaire ;
			}		
		}
		return prixTotal;
	}
	/** INITIALISER LE PANIER: */
	public void initialiserPanier() {
		this.panier.clear();
	}
	/** PANIER VIDE ?*/
	public boolean panierVide() {
		return getPanier() == null || getPanier().isEmpty();
	}

	public Map<Articles,Integer> get_panier(){
		return panier;
	}
}
