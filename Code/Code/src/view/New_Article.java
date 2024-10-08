package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class New_Article extends JFrame{        //les Jcompsants sont positions et impémentation sont gérer par le .form
    private JPanel panel1;
    private JTextField Nom;
    private JTextField Prix_U;
    private JTextField Prix_Vrac;
    private JTextArea Description;
    private JSpinner Quantite_S;
    private JButton Validé;
    private JSpinner Quantite_V;


    //variable permetant de d'utiliser correctement la page et de stocker les informations
    private int fin=-1;
    private String nom;
    private String description;
    private double prix_V;
    private double prix_uni;
    private int quantite_s;
    private int quantite_v;


    public New_Article(){
        //ajoute des écouteurs sur le bouton
        Validé.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nom=Nom.getText();
                description=Description.getText();
                prix_V=Double.parseDouble(Prix_Vrac.getText());
                prix_uni=Double.parseDouble(Prix_U.getText());
                quantite_s=(int) Quantite_S.getValue();
                quantite_v=(int) Quantite_V.getValue();

                if(!nom.isEmpty()&&!description.isEmpty()&&prix_V>0&&prix_uni>0&&quantite_s>0&&quantite_v>0){
                    fin=0;
                }
            }
        });
    }

    //methode d'affichage
    public int GUI_nouveau_Article(){
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

        return fin;
    }

    public String GUI_Article_getNom(){
        return nom;
    }
    public String GUI_Article_getdescription(){
        return description;
    }
    public double GUI_Article_getprix_V(){
        return prix_V;
    }
    public double GUI_Article_getprix_uni(){
        return prix_uni;
    }
    public int GUI_Article_getquantite_v(){
        return quantite_v;
    }
    public int GUI_Article_getquantite_s(){
        return quantite_s;
    }
}
