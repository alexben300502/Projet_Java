package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Valider_Achat extends JFrame{      //les Jcompsants sont positions et impémentation sont gérer par le .form
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField passwordField1;
    private JButton validéButton;
    private JTextField numero_carte;
    private JSpinner spinner1;
    private JLabel Titre;
    private JPanel Panel2;
    private JLabel Nom;
    private JLabel Numero;
    private JLabel Date;
    private JLabel Crypo;
    private JPanel Nom_;
    private JPanel Groupe;
    private JPanel Date_;
    private JPanel Crypo_;
    private JPanel Numero_;

    //variable permetant de d'utiliser correctement la page et de stocker les informations
    private String nom;
    private String numero;
    private int crypto;
    private String date;
    private int fin=-1;

    //ajoute des écouteurs sur les boutons
    public Valider_Achat(){

        validéButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                nom = textField1.getText();
                numero=passwordField1.getText();
                date= textField2.getText();
                crypto= (int) spinner1.getValue();

                System.out.println(nom);

                if(crypto==1) {
                    Titre.setText("Coucou");
                    new Page_Client();
                }
                if(crypto==51){
                    Titre.setText("Ackeur sur toi");
                }
                if(crypto==1000){
                    Titre.setText("mergeztunigshow");
                }
                fin=0;
            }
        });
    }
    //methode d'affichage
    public int GUI_valider_panier(){

        //ajoute de la couleur aux différent éléments
        Numero_.setOpaque(false);
        Nom_.setOpaque(false);
        Date_.setOpaque(false);
        Crypo_.setOpaque(false);
        Groupe.setOpaque(false);
        Panel2.setBorder(new RoundBorder(50,new Color(255,102,0)));
        Panel2.setBackground(new Color(50,50,50));
        panel1.setBackground(new Color(17,26,49));
        Titre.setForeground(new Color(255,255,255));
        Nom.setForeground(new Color(255,255,255));
        Crypo.setForeground(new Color(255,255,255));
        Date.setForeground(new Color(255,255,255));
        Numero.setForeground(new Color(255,255,255));

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
}
