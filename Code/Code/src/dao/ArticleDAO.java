package dao;

import model.Articles;

public class ArticleDAO{
	/// ============================================================== ///
	///	Déclaration des attributs:
	/// ============================================================== ///
	private Articles articles = null;

	/// ============================================================== ///
	///	Constructeur par défaut:
	/// ============================================================== ///
	public ArticleDAO() {
		this.articles = new Articles();
	}
	/// ============================================================== ///
	///	Constructeur surchargé:
	/// ============================================================== ///
	public ArticleDAO(int id, String nom, String description, double pu, double pv, int qv, int stock) {
		this.articles = new Articles(id,nom,description,pu,pv,qv,stock);
	}
	/// ============================================================== ///
	///	Getters:
	/// ============================================================== ///
	public Articles getArticle() {
		return this.articles;
	}
}
