package view;

import model.Articles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Page_Client extends JFrame{        //les Jcompsants sont positions et impémentation sont gérer par le .form
    private JPanel panel1;
    private JPanel Centre;
    private JPanel Bas;
    private JPanel Liste_Produit;
    private JPanel Produit_1;
    private JSpinner Quantite_1;
    private JButton Acheter_1;
    private JPanel Local_Image_1;
    private JLabel Nom_Produit_1;
    private JComboBox Description_1;
    private JComboBox Description_2;
    private JComboBox Description_3;
    private JPanel Produit_2;
    private JPanel Produit_3;
    private JLabel Prix_1;
    private JLabel Prix_2;
    private JLabel Prix_3;
    private JPanel Haut;
    private JTextField Recherche;
    private JButton button2;
    private JPanel Produit_4;
    private JButton Panier;
    private JPanel Client;
    private JLabel Recap_1;
    private JLabel Nom_Produit_2;
    private JLabel Nom_Produit_3;
    private JLabel Nom_Produit_4;
    private JSpinner Quantite_2;
    private JSpinner Quantite_3;
    private JSpinner Quantite_4;
    private JLabel Recap_2;
    private JLabel Recap_3;
    private JLabel Recap_4;
    private JLabel Prix_4;
    private JComboBox Description_4;
    private JButton Acheter_2;
    private JButton Acheter_3;
    private JButton Acheter_4;
    private JLabel Nom_Client;
    private JLabel Solde;
    private JLabel Solde_Prev;
    private JLabel Total_Panier;
    private JLabel Quantité_1;
    private JLabel Quantité_2;
    private JLabel Quantité_3;
    private JLabel Quantité_4;
    private JLabel rechercherLabel;
    private JLabel Titre;

    //variable permetant de d'utiliser correctement la page et de stocker les informations
    private String recherche;
    private int nb_p;
    private String nom_p;
    private int fin=-1;


    public Page_Client(){
        //ajoute des écouteurs sur les boutons
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recherche=Recherche.getText();
                fin=2;
                /*rechercher produit*/
            }
        });
        Acheter_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nb_p= (int) Quantite_1.getValue();
                nom_p= Nom_Produit_1.getText();
                if(nb_p>0){
                    /* Ajouter produit */
                    fin=1;
                }
            }
        });
        Acheter_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nb_p= (int) Quantite_2.getValue();
                nom_p= Nom_Produit_2.getText();
                if(nb_p>0){
                    /* Ajouter produit */
                    fin=1;
                }
            }
        });
        Acheter_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nb_p= (int) Quantite_3.getValue();
                nom_p= Nom_Produit_3.getText();
                if(nb_p>0){
                    /* Ajouter produit */
                    fin=1;
                }
            }
        });
        Acheter_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nb_p= (int) Quantite_4.getValue();
                nom_p= Nom_Produit_4.getText();
                if(nb_p>0){
                    /* Ajouter produit */
                    fin=1;
                }
            }
        });
        Panier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Validé Validé panier => passer à la page panier */
                fin=0;
            }
        });
    }

    public int GUI_Client(){
        //affiche le Jframe
        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Singleton s=Singleton.getInstance();
        s.xm(screenSize.width);
        s.ym(screenSize.height);

        setSize(s.xm,s.ym);

        setLocationRelativeTo(null);
        setVisible(true);

        Bas.setOpaque(false);


        //ajoute des couleurs

        Titre.setForeground(new Color(255,255,255));
        Quantité_1.setForeground(new Color(255,255,255));
        Prix_1.setForeground(new Color(255,255,255));
        Recap_1.setForeground(new Color(255,255,255));
        Nom_Produit_1.setForeground(new Color(255,255,255));
        Quantité_2.setForeground(new Color(255,255,255));
        Prix_2.setForeground(new Color(255,255,255));
        Recap_2.setForeground(new Color(255,255,255));
        Nom_Produit_2.setForeground(new Color(255,255,255));
        Quantité_3.setForeground(new Color(255,255,255));
        Prix_3.setForeground(new Color(255,255,255));
        Recap_3.setForeground(new Color(255,255,255));
        Nom_Produit_3.setForeground(new Color(255,255,255));
        Quantité_4.setForeground(new Color(255,255,255));
        Prix_4.setForeground(new Color(255,255,255));
        Recap_4.setForeground(new Color(255,255,255));
        Nom_Produit_4.setForeground(new Color(255,255,255));

        rechercherLabel.setForeground(new Color(255,255,255));
        Nom_Client.setForeground(new Color(255,255,255));
        Total_Panier.setForeground(new Color(255,255,255));


        Produit_1.setBorder(new RoundBorder(30,new Color(255,102,0)));
        Produit_2.setBorder(new RoundBorder(30,new Color(255,102,0)));
        Produit_3.setBorder(new RoundBorder(30,new Color(255,102,0)));
        Produit_4.setBorder(new RoundBorder(30,new Color(255,102,0)));
        Titre.setBorder(new RoundBorder(30,new Color(255,102,0)));
        Client.setBorder(new RoundBorder(30,new Color(255,102,0)));

        panel1.setBackground(new Color(17,26,49));
        Haut.setBackground(new Color(17,26,49));
        Liste_Produit.setBackground(new Color(17,26,49));
        Produit_1.setBackground(new Color(17,26,49));
        Produit_2.setBackground(new Color(17,26,49));
        Produit_3.setBackground(new Color(17,26,49));
        Produit_4.setBackground(new Color(17,26,49));

        Acheter_1.setBackground(new Color(153,255,51));
        Acheter_2.setBackground(new Color(153,255,51));
        Acheter_3.setBackground(new Color(153,255,51));
        Acheter_4.setBackground(new Color(153,255,51));
        button2.setBackground(new Color(153,255,51));
        Panier.setBackground(new Color(153,255,51));

        Quantite_1.setBackground(new Color(255,102,0));
        Quantite_2.setBackground(new Color(255,102,0));
        Quantite_3.setBackground(new Color(255,102,0));
        Quantite_4.setBackground(new Color(255,102,0));

        Client.setBackground(new Color(50,50,50));


        do {
            System.out.println(fin);
        }while (this.fin==-1);

        System.out.println(fin);

        return fin;
    }

    public String get_recherche(){
        return recherche;
    }

    public String get_nom(){
        return nom_p;
    }

    public int get_nb(){
        return nb_p;
    }

    //méthode actualisant le JFrame
    public void actualise(Articles n1, Articles n2, Articles n3, Articles n4,double v_panier){

        Edit_Produit(n1,n2,n3,n4);
        Singleton s=Singleton.getInstance();

        Total_Panier.setText("Valeur de votre panier : "+String.valueOf(v_panier)+" "+s.euroSymbol);
    }

    //méthode actualisant les textes des article
    public void Edit_Produit(Articles n1,Articles n2,Articles n3,Articles n4){

        Nom_Produit_1.setText(n1.getNom());
        Prix_1.setText(Prix_1.getText()+" : "+n1.getPrixUnitaire());
        Recap_1.setText("Ajouter au panier "+n1.getNom()+" quantité : "+getSpinnerValue(Quantite_1)/*+" prix total : "+toString(n1.calcul_prix_total(Quantite_1.getValue()))*/);
        Description_1.addItem(n1.getDescription());
        Quantité_1.setText("Quantité disponible : "+n1.getStock());

        Nom_Produit_2.setText(n2.getNom());
        Prix_2.setText(Prix_2.getText()+" : "+n2.getPrixUnitaire());
        Recap_2.setText("Ajouter au panier "+n2.getNom()+" quantité : "+getSpinnerValue(Quantite_2)/*+" prix total : "+toString(n2.calcul_prix_total(Quantite_2.getValue()))*/);
        Description_2.addItem(n1.getDescription());
        Quantité_2.setText("Quantité disponible : "+n2.getStock());

        Nom_Produit_3.setText(n3.getNom());
        Prix_3.setText(Prix_3.getText()+" : "+n3.getPrixUnitaire());
        Recap_3.setText("Ajouter au panier "+n3.getNom()+" quantité : "+getSpinnerValue(Quantite_3)/*+" prix total : "+toString(n3.calcul_prix_total(Quantite_3.getValue()))*/);
        Description_3.addItem(n3.getDescription());
        Quantité_3.setText("Quantité disponible : "+n3.getStock());

        Nom_Produit_4.setText(n4.getNom());
        Prix_4.setText(Prix_4.getText()+" : "+n4.getPrixUnitaire());
        Recap_4.setText("Ajouter au panier "+n4.getNom()+" quantité : "+getSpinnerValue(Quantite_4)/*+" prix total : "+toString(n4.calcul_prix_total(Quantite_4.getValue()))*/);
        Description_4.addItem(n4.getDescription());
        Quantité_4.setText("Quantité disponible : "+n4.getStock());
    }

    public String getSpinnerValue(JSpinner spinner) {
        int value = (int) spinner.getValue();
        return Integer.toString(value);
    }
}
