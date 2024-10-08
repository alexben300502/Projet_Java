package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class New_Compte extends JFrame{ //les Jcompsants sont positions et impémentation sont gérer par le .form
    private JPanel panel1;
    private JTextField Nom;
    private JButton Validé;
    private JComboBox comboBox1;
    private JTextField Password;
    private JTextField Mail;


    //variable permetant de d'utiliser correctement la page et de stocker les informations
    private String nom;
    private String email;
    private String mdp;
    private String type;
    private int fin=-1;

    public New_Compte(){
        //ajoute des écouteurs sur le bouton
        Validé.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nom=Nom.getText();
                email=Mail.getText();
                mdp=Password.getText();
                type=(String)comboBox1.getSelectedItem();

                if(!nom.isEmpty()&&!email.isEmpty()&&!email.isEmpty()&&!email.isEmpty()){
                    fin=0;
                }
            }
        });

        comboBox1.addItem("EMPLOYE");
        comboBox1.addItem("CLIENT");
    }

    //methode d'affichage
    public int GUI_new_Compte(){
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

    public String GUI_Compte_getNom(){
        return nom;
    }
    public String GUI_Compte_getemail(){
        return email;
    }
    public String GUI_Compte_getmdp(){
        return mdp;
    }
    public String GUI_Compte_gettype(){
        return type;
    }
}
