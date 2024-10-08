package view;


import dao.UtilisateurDAO;
import model.Articles;
import model.Utilisateur;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;


public class Admin extends JFrame{      //les Jcompsants sont positions et impémentation sont gérer par le .form
    private JPanel panel1;
    private JPanel Graphique_1;
    private JPanel Graphique_2;
    private JButton Ajouter_A;
    private JButton ajouterCompteButton;
    private JPanel Articles;
    private JPanel Clients;
    private JPanel panel2;
    private JPanel Gauche;
    private JPanel Droite;
    private JLabel Titre;


    private int fin=-1;         //permet de choisir la fin
    private int index=-1;       //permet de choisir un élément dans une liste

    //methode d'affichage
    public int GUI_Admin(ArrayList<Articles> liste, ArrayList<UtilisateurDAO> users, Map<String,Integer>quantite_v , Map<String,Double>CA,double caT,int qT){

        //partie permettant de visualiser et modifier les articles
        Articles.setLayout(new BoxLayout(Articles, BoxLayout.Y_AXIS));
        Articles.setOpaque(false);
        for(int i=0;i<liste.size();i++){        //boucle pour afficher les différent élement des articles
            //création d'un Jpannel pour contenant les élements d'un produit
            JPanel clonedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,15,2));

            JLabel A1=new JLabel(liste.get(i).getNom());
            JLabel A2=new JLabel("Prix uni : "+String.valueOf(liste.get(i).getPrixUnitaire()));
            JLabel A3=new JLabel("Prix vrac : "+String.valueOf(liste.get(i).getPrixVrac()));
            JLabel A4=new JLabel("Quantité : "+String.valueOf(liste.get(i).getQuantiteVrac()));
            JLabel A5=new JLabel("Stock : "+String.valueOf(liste.get(i).getStock()));

            JButton b1 = new JButton("restocker de 100 unité");
            JButton b2 = new JButton("Supprimer");

            //écouteurs des boutons
            b1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fin=3;
                    JButton button = (JButton) e.getSource();
                    index = Articles.getComponentZOrder((Component) button.getParent());//0 pour le premier
                    index=liste.get(index).getId();
                }
            });

            b2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fin=4;
                    JButton button = (JButton) e.getSource();
                    index = Articles.getComponentZOrder((Component) button.getParent());//0 pour le premier
                }
            });

            //permet d'ajouter les éléments au Jpannel
            clonedPanel.add(A1);
            clonedPanel.add(A2);
            clonedPanel.add(A3);
            clonedPanel.add(A4);
            clonedPanel.add(A5);
            clonedPanel.add(b1);
            clonedPanel.add(b2);

            //ajoute de la couleur aux différent éléments
            A1.setForeground(new Color(255,255,255));
            A2.setForeground(new Color(255,255,255));
            A3.setForeground(new Color(255,255,255));
            A4.setForeground(new Color(255,255,255));
            A5.setForeground(new Color(255,255,255));
            clonedPanel.setBorder(new RoundBorder(5,new Color(255,102,0)));

            clonedPanel.setOpaque(false);

            //ajoute le Jpannel au pannel du .form
            Articles.add(clonedPanel);
        }


        //partie permettant de visualiser et modifier les utilisateurs
        Clients.setLayout(new BoxLayout(Clients, BoxLayout.Y_AXIS));
        Clients.setOpaque(false);

        for(int i=0;i<users.size();i++){//boucle pour afficher les différent élement des articles
            //création d'un Jpannel pour contenant les élements d'un utilisateur
            JPanel clonedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,15,2));

            JLabel A1=new JLabel(users.get(i).getUtilisateur().getNom());
            JLabel A2=new JLabel(users.get(i).getUtilisateur().getMail());
            JLabel A3=new JLabel(users.get(i).getUtilisateur().getMdp());
            JLabel A4=new JLabel(users.get(i).getUtilisateur().getType());

            JButton b2 = new JButton("Supprimé");

            b2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fin=6;
                    JButton button = (JButton) e.getSource();
                    index = Clients.getComponentZOrder((Component) button.getParent());//0 pour le premier
                }
            });

            //permet d'ajouter les éléments au Jpanne
            clonedPanel.add(A1);
            clonedPanel.add(A2);
            clonedPanel.add(A3);
            clonedPanel.add(A4);
            clonedPanel.add(b2);
            clonedPanel.setOpaque(false);

            //ajoute de la couleur aux différent éléments
            A1.setForeground(new Color(255,255,255));
            A2.setForeground(new Color(255,255,255));
            A3.setForeground(new Color(255,255,255));
            A4.setForeground(new Color(255,255,255));
            clonedPanel.setBorder(new RoundBorder(5,new Color(255,102,0)));

            //ajoute le Jpannel au pannel du .form
            Clients.add(clonedPanel);

        }

        //met de la couleur à des éléments
        panel1.setBackground(new Color(17,26,49));
        Titre.setForeground(new Color(255,255,255));
        panel2.setOpaque(false);
        Droite.setOpaque(false);
        Gauche.setOpaque(false);

        //créer les graphiques
        ChartPanel chartPanel = new ChartPanel(createPieChart(quantite_v,qT));
        Graphique_1.setLayout(new BorderLayout());
        Graphique_1.add(chartPanel, BorderLayout.CENTER);

        ChartPanel chartPanel2 = new ChartPanel(create(CA,caT));
        Graphique_2.setLayout(new BorderLayout());
        Graphique_2.add(chartPanel2, BorderLayout.CENTER);

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

        //boucle permettant d'attendre un action de l'utilisateur
        do {
            System.out.println(fin);
        }while (this.fin==-1);

        System.out.println(fin);
        System.out.println(index);


        //les différentes fin
        //0:nouveau produit
        //1:nouveau compte
        //3:modif stock
        //4:supprimer article
        //6:supprimer compte
        return fin;
    }

    public Admin(){
        //ajoute des écouteurs sur les boutons
        Ajouter_A.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fin=0;
            }
        });
        ajouterCompteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fin=1;
            }
        });
    }

    //méthode permettant de créer les graphiques
    private static JFreeChart createPieChart(Map<String, Integer> quantiteArticles,int qT) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        //ajoute les différents élément dans le dataset
        for (Map.Entry<String, Integer> entry : quantiteArticles.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        //créer le graphique camembert 3D
        JFreeChart chart = ChartFactory.createPieChart3D(
                "Nombre total de ventes de : "+qT , // chart title
                dataset, // dataset
                true, // legend
                true, // tooltips
                false // urls
        );
        return chart;
    }

    private static JFreeChart create(Map<String, Double> caArticles,double caT) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int i=1;
        //ajoute les différents élément dans le dataset
        dataset.addValue(caT, "Category "+i, "CA total");
        for (Map.Entry<String, Double> entry : caArticles.entrySet()) {
            i++;
            dataset.addValue(entry.getValue(), "Category "+i, entry.getKey());
        }

        //créer le graphique camembert 3D
        JFreeChart chart = ChartFactory.createBarChart3D(
                "CA par article", // chart title
                "", // domain axis label
                "Value", // range axis label
                dataset, // dataset
                PlotOrientation.VERTICAL, // orientation
                false, // legend
                true, // tooltips
                false // urls
        );
        return chart;
    }

    public int get_index(){
        return index;
    }
}
