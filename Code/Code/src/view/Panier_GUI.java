package view;

import model.Articles;
import model.Panier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class Panier_GUI extends JFrame{     //les Jcompsants sont positions et impémentation sont gérer par le .form
    private JPanel panel1;
    private JButton validéLePanierButton;
    private JPanel Partie_Panier;
    private JPanel Partie_Client;
    private JPanel Articles;
    private JLabel Nom_Client;
    private JLabel Panier;


    private int index;
    private int fin=-1;


    //ajoute des écouteurs sur les boutons
    public Panier_GUI(){
        validéLePanierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Test Valeur panier < Solde puis afficher Validé achat*/
                fin=0;
            }
        });
    }

    //methode d'affichage
    public Articles GUI_Panier(Panier panier){



        //permet de stocker les produits dans un Arraylist
        Map<Articles,Integer>map_temp=panier.get_panier();
        ArrayList<Articles> liste=new ArrayList<>();

        for (Articles article : map_temp.keySet()) {
            liste.add(article);
        }

        //partie permettant de visualiser les articles
        Articles.setLayout(new BoxLayout(Articles, BoxLayout.Y_AXIS));
        Articles.setOpaque(false);
        for(int i=0;i<map_temp.size();i++){     //boucle pour afficher les différent élement des articles
            // création d'un Jpannel pour contenant les élements d'un produit
            JPanel clonedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,15,2));

            JLabel A1=new JLabel(liste.get(i).getNom());
            JLabel A2=new JLabel(String.valueOf(liste.get(i).getPrixUnitaire()));
            JLabel A3=new JLabel(String.valueOf(map_temp.get(liste.get(i))));

            JButton b1 = new JButton("Supprimer");
            //écouteurs du bouton
            b1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fin=1;
                    JButton button = (JButton) e.getSource();
                    index = Articles.getComponentZOrder((Component) button.getParent());//0 pour le premier
                }
            });

            //permet d'ajouter les éléments au Jpannel
            clonedPanel.add(A1);
            clonedPanel.add(A2);
            clonedPanel.add(A3);
            clonedPanel.add(b1);

            //ajoute de la couleur aux différent éléments
            clonedPanel.setOpaque(false);
            A1.setForeground(new Color(255,255,255));
            A2.setForeground(new Color(255,255,255));
            A3.setForeground(new Color(255,255,255));
            clonedPanel.setBorder(new RoundBorder(5,new Color(255,102,0)));

            //ajoute le Jpannel au pannel du .form
            Articles.add(clonedPanel);
        }

        //met de la couleur à des éléments
        Partie_Panier.setOpaque(false);
        Partie_Client.setOpaque(false);
        panel1.setBackground(new Color(17,26,49));
        Nom_Client.setForeground(new Color(255,255,255));
        Panier.setForeground(new Color(255,255,255));
        validéLePanierButton.setBackground(new Color(153,255,51));

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

        do {
            System.out.println(fin);
        }while (this.fin==-1);

        System.out.println(fin);

        if(fin==0){
            return null;
        }
        else {
            return liste.get(index);
        }
    }
}
